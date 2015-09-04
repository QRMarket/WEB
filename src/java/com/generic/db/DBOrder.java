package com.generic.db;

import com.generic.checker.Checker;
import com.generic.result.Result;
import com.generic.util.Address;
import com.generic.util.MarketOrder;
import com.generic.util.MarketProduct;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Kemal Sami KARACA
 * @since 02.2015
 * @version 1.01
 * 
 * @last 11.03.2015
 */
public class DBOrder {
    
    
    /**
     * 
     * @return 
     */
    public static String orderIDGenerator(){
        return "order-"+UUID.randomUUID().toString();
    }
    
    /**
     *   
     * @return  
     */
    public static Result confirmOrder(HttpSession session , String addID, long date, String ptype , String note){
        
            
            if(!Checker.isValidDate(date))
                return Result.FAILURE_CHECKER_DATE;
            
        
            MysqlDBOperations mysql = new MysqlDBOperations();
            ResourceBundle rs = ResourceBundle.getBundle("com.generic.resources.mysqlQuery");
            Connection conn = mysql.getConnection();
                        
            try{   
                        
                // -1- Initialization
                PreparedStatement preStat = conn.prepareStatement(rs.getString("mysql.order.update.insert.1"));
                Map proMap = (Map) session.getAttribute("cduPMap");
                boolean isSuccessInsertion = (proMap.size()>0);
                String query;
                
                // -1.1-GENERATE ORDER-ID
                String oid = orderIDGenerator(); 
                
                // -1.2-GET USER-ID
                String userID = (String) session.getAttribute("cduUserId");
                
                                
                // -2- Insert Order to table
                try {
                    preStat.setString(1, oid);
                    preStat.setString(2, "UNDELIVERED");
                    preStat.setString(3, ptype);
                    preStat.setLong(4, date);
                    preStat.setLong(5, 0);
                    preStat.setString(6, note);
                    preStat.setDouble(7, 10);
                    preStat.setString(8, userID);
                    preStat.setString(9, "c_34567");
                    preStat.setString(10, addID);

                    if(preStat.executeUpdate()==1){
                                                
                        // -3- Insert Products to orderProduct
                        ArrayList<MarketProduct> proList = new ArrayList( ((Map)session.getAttribute("cduPMap")).values());

                        // -3.1- If process fail then break operation
                        insertionFail:
                            for(MarketProduct pro:proList){

                                try {
                                    preStat = conn.prepareStatement(rs.getString("mysql.orderProduct.update.insert.1"));
                                    preStat.setString(1, oid);
                                    preStat.setString(2, pro.getProductID());
                                    preStat.setDouble(3, pro.getAmount());

                                    if(preStat.executeUpdate()!=1){
                                        isSuccessInsertion = false;
                                        break insertionFail;
                                    }

                                } catch (SQLException ex) {
                                    Logger.getLogger(DBOrder.class.getName()).log(Level.SEVERE, null, ex);
                                    isSuccessInsertion = false;
                                    break insertionFail;
                                }                                                                            
                            }

                        // -4- Return success
                        if(isSuccessInsertion){
                            mysql.commitAndCloseConnection();
                            return Result.SUCCESS;
                        }
                        
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(DBOrder.class.getName()).log(Level.SEVERE, null, ex);                        
                }
                                
                   
                // -5- If operation fail then rollback 
                mysql.rollbackAndCloseConnection();
                                               
                
            }catch(ClassCastException e){                                
                return Result.FAILURE_PROCESS_CASTING.setContent("Class casting error. Check server log");
            } catch (SQLException ex) {
                Logger.getLogger(DBOrder.class.getName()).log(Level.SEVERE, null, ex);
            }finally{
                mysql.closeAllConnection();
            }                
        
        return Result.FAILURE_PROCESS;
            
    }
    
    /**
     * 
     * @param cdoID     
     * @return  
     */
    public static Result getCartInfo(String cdoID){
            
            ResourceBundle rs = ResourceBundle.getBundle("com.generic.resources.mysqlQuery");
            MysqlDBOperations mysql = new MysqlDBOperations();
            
            MarketOrder marketOrder = new MarketOrder();            
            List<MarketProduct> pList = new ArrayList<>();            
            
            
            ArrayList<MarketProduct> cartList = new ArrayList();
            String query;
            try{
                
                // GET CURRENT ORDERLIST ID FROM DB                 
                query = String.format(  "SELECT * FROM opRelation " +
                                        "INNER JOIN cpRelation ON opRelation.pid=cpRelation.cprID " +
                                        "INNER JOIN products ON products.pid=cpRelation.p_id " + 
                                        "AND oid='%s'" , cdoID);
                
                System.out.println(query);
                ResultSet mysqlResult = mysql.getResultSet(query);
                
                String pId,pName,ppType;
                double pQuantity,pPrice;
                if(mysqlResult.first()){
                    do{
                        pId = mysqlResult.getString("cprID");
                        pName = mysqlResult.getString("productName");
                        ppType = mysqlResult.getString("productPriceType");
                        pQuantity = mysqlResult.getDouble("quantity");
                        pPrice = mysqlResult.getDouble("p_price");                        
                        pList.add(new MarketProduct(pId, pName, ppType, pPrice, pQuantity));
                    }while(mysqlResult.next());
                    
                    marketOrder.setProducts(pList);                    
                    query = String.format(rs.getString("mysql.order.select.2"), cdoID);
                    
                    mysqlResult = mysql.getResultSet(query);
                    if(mysqlResult.first()){
                            marketOrder.setPaymentType(mysqlResult.getString("ptype"));
                            marketOrder.setDate(mysqlResult.getString("date"));
                            marketOrder.setNote(mysqlResult.getString("note"));                            
                    }                                                            
                    
                    return Result.SUCCESS.setContent(marketOrder);
                
                }else{
                    return Result.SUCCESS_EMPTY;
                }                                
                
            } catch (SQLException ex) {                
                return Result.FAILURE_DB;
            }finally{
                mysql.closeAllConnection();
            }                
        
    }
    
    /**
     * 
     * @param uid 
     * @return  
     */
    public static Result getUserCartList(String uid){
            
            MysqlDBOperations mysql = new MysqlDBOperations();
            ArrayList<String> cartList = new ArrayList();
            String query;
            try{
                
                // GET CURRENT ORDERLIST ID FROM DB
                query = String.format("SELECT oid FROM orders WHERE user_id='%s' " , uid);
                ResultSet mysqlResult = mysql.getResultSet(query);                                  
                
                if(mysqlResult.first()){
                    do{
                        cartList.add(mysqlResult.getString("oid"));                        
                    }while(mysqlResult.next());
                    
                    return Result.SUCCESS.setContent(cartList);
                
                }else{
                    return Result.SUCCESS_EMPTY;
                }                                
                
            } catch (SQLException ex) {                
                return Result.FAILURE_DB;
            }finally{
                mysql.closeAllConnection();
            }                        
    }
        
    /**
     * 
     * @param uid 
     * @return  
     */
    public static Result getUserCartListExt(String uid){
                    
            MysqlDBOperations mysql = new MysqlDBOperations();           
            ResourceBundle rs = ResourceBundle.getBundle("com.generic.resources.mysqlQuery");
            ArrayList<MarketOrder> cartList = new ArrayList();
            String query;
            try{
                                
                // GET CURRENT ORDERLIST ID FROM DB                
                query = String.format(  rs.getString("mysql.order.select.3") , uid );
                ResultSet mysqlResult = mysql.getResultSet(query);                                  
                
                if(mysqlResult.first()){
                    do{
                        MarketOrder marketOrder = new MarketOrder();
                        marketOrder.setOrderID(mysqlResult.getString("oid"));
                        marketOrder.setPaymentType(mysqlResult.getString("ptype"));
                        marketOrder.setNote(mysqlResult.getString("note"));
                        marketOrder.setDate(mysqlResult.getString("date"));
                        marketOrder.setCompanyName(mysqlResult.getString("companyName"));
                         
                        // Get address of order
                        Result address = DBAddress.getAddress(mysqlResult.getString("add_id"));
                        if(address.checkResult(Result.SUCCESS)){
                            marketOrder.setAdress((Address)address.getContent());
                        }                                                                        
                        
                        cartList.add(marketOrder);                        
                    }while(mysqlResult.next());                                        
                    
                    return Result.SUCCESS.setContent(cartList);
                
                }else{
                    return Result.SUCCESS_EMPTY;
                }                                
                
            } catch (SQLException ex) {                
                return Result.FAILURE_DB;
            }finally{
                mysql.closeAllConnection();
            }                        
    }
    
    
        
    
    
    
    
    /**
     ***********************************************************************************************
     ***********************************************************************************************
     *                              DEPRECATED METHODS
     ***********************************************************************************************
     ***********************************************************************************************
     */
    
    /**
     * @deprecated 
     * @param uID
     * @param pUID
     * @param pAmount
     * @return 
     *      
     */
    public static Result addProductToOrderList(String uID, String pUID , double pAmount){                    

            MysqlDBOperations mysql = new MysqlDBOperations();
            String query;
            
            try{
                
                // GET CURRENT ORDERLIST ID FROM DB
                query = String.format("SELECT * FROM orders WHERE user_id='%s' AND type='CURRENT' " , uID);
                ResultSet mysqlResult = mysql.getResultSet(query);                                
                                
                // INSERT PRODUCT TO ORDERLIST WITH GIVEN USER_ID
                String orderID = mysqlResult.first() ? mysqlResult.getString("oid") : orderIDGenerator();   
                
                query  = String.format("INSERT INTO orders VALUES ('%s', '%s', '%s', '%s', '%s', '%.2f')" ,
                                        orderID , "CURRENT" , "21-10-1763", uID, pUID, pAmount);

                int effectedRowNumber = mysql.execUpdate(query);
                if(effectedRowNumber>0 ){
                    mysql.commitAndCloseConnection();                                        
                }else{                               
                    mysql.rollbackAndCloseConnection();
                    return Result.FAILURE_DB_UPDATE;  
                }
                
            } catch (SQLException ex) {                
                return Result.FAILURE_DB;        
            
            }finally{
                mysql.closeAllConnection();
            }
            
        return Result.SUCCESS;
    }
    
}
