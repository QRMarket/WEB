package com.generic.db;

import com.generic.result.Result;
import com.generic.util.MarketOrder;
import com.generic.util.MarketProduct;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.UUID;
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
    public static Result confirmOrder(HttpSession session , String addID, String ptype , String note){
            
            MysqlDBOperations mysql = new MysqlDBOperations();
            ResourceBundle rs = ResourceBundle.getBundle("com.generic.resources.mysqlQuery");
                        
            try{                                                
                Map proMap = (Map) session.getAttribute("cduPMap");                                            
                String query;
                
                // GENERATE ORDER-ID
                String oid = orderIDGenerator();
                
                // GET USER-ID
                String userID = (String) session.getAttribute("cduUserId");
                
                // GET CURRENT ORDERLIST ID FROM DB                   
                query = String.format(  rs.getString("mysql.order.update.insert.1") , oid , "UNDELIVERED" , ptype , note , userID , "c_34567" , addID );
                                
                int effectedRowNum = mysql.execUpdate(query); 
                boolean isSuccessInsertion = (proMap.size()>0);
                if(effectedRowNum==1){                                        
                    
                    ArrayList<MarketProduct> proList = new ArrayList( ((Map)session.getAttribute("cduPMap")).values());
                    insertionFail:
                    for(MarketProduct pro:proList){
                        query = String.format(  "INSERT INTO opRelation " + 
                                        "(oid, pid, quantity)" +
                                        "VALUES ('%s','%s',%f)" , oid , pro.getProductID() , pro.getAmount() );
                        if(mysql.execUpdate(query)!=1){
                            isSuccessInsertion = false;
                            break insertionFail;
                        }                                                    
                    }
                                                                                   
                    if(isSuccessInsertion){
                        mysql.commitAndCloseConnection();
                        return Result.SUCCESS;
                    }else{
                        mysql.rollbackAndCloseConnection();
                        return Result.FAILURE_DB_UPDATE;
                    }
                    
                    
                }else{
                    mysql.rollbackAndCloseConnection();
                    return Result.FAILURE_DB_UPDATE;
                }                           
                
            }catch(ClassCastException e){                
                return Result.FAILURE_PROCESS_CASTING.setContent(e);
            }finally{
                mysql.closeAllConnection();
            }                
        
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
