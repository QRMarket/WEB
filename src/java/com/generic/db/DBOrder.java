package com.generic.db;

import com.generic.resources.ResourceProperty;
import com.generic.result.Result;
import com.generic.entity.Address;
import com.generic.entity.Orders;
import com.generic.entity.MarketProduct;
import com.generic.entity.OrderProduct;
import com.generic.util.Util;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Kemal Sami KARACA
 * @since 02.2015
 * @version 1.01
 * 
 * @last 01.10.2015
 */
public class DBOrder {
    
    
    //------------------------------------------------------------------------------
    //------------------------------------------------------------------------------
    //--                            INSERT OPERATIONs
    //------------------------------------------------------------------------------
    //------------------------------------------------------------------------------
    
    // <editor-fold defaultstate="collapsed" desc="INSERT Operations">
    
        //**************************************************************************
        //**************************************************************************
        //**                        CONFIRM ORDER
        //**************************************************************************
        //**************************************************************************
        /**
         * 
         * @param session
         * @param addID
         * @param date
         * @param ptype
         * @param note
         * @return 
         */
//        public static Result confirmOrder(HttpSession session , String addID, long date, String ptype , String note){
        public static Result confirmOrder(Orders orderObj){
        
                Result result = Result.FAILURE_PROCESS;
                MysqlDBOperations mysql = new MysqlDBOperations();
                ResourceBundle rs = ResourceBundle.getBundle("com.generic.resources.mysqlQuery");
                Connection conn = mysql.getConnection();
                PreparedStatement preStat;
                boolean isSuccessInsertion;
            
//                if(!Checker.isValidDate(date))
//                    return Result.FAILURE_CHECKER_DATE;

                try{   
                    String order_id = Util.generateID();
                    
                    preStat = conn.prepareStatement(rs.getString("mysql.order.update.insert.1"));
                    preStat.setString(1, order_id);
                    preStat.setString(2, "1" );
                    preStat.setString(3, null);
                    preStat.setLong(4, orderObj.getDate());
                    preStat.setLong(5, orderObj.getDelay());
                    preStat.setString(6, orderObj.getNote());
                    preStat.setDouble(7, -1);
                    preStat.setString(8, orderObj.getUserID());
                    preStat.setString(9, orderObj.getDistributerAddressID());
                    
                    
                    if(preStat.executeUpdate()==1){
                        List<OrderProduct> orderProductList = orderObj.getOrderProductList();
                        isSuccessInsertion = orderProductList.size()>0;

                        // - * - If process fail then break operation
                        insertionFail:
                            for(OrderProduct orderProduct:orderProductList){
                                preStat = conn.prepareStatement(rs.getString("mysql.orderProduct.update.insert.1"));
                                preStat.setString(1, order_id);
                                preStat.setString(2, orderProduct.getCompanyProduct_id());
                                preStat.setDouble(3, orderProduct.getQuantity());

                                if(preStat.executeUpdate()!=1){
                                    isSuccessInsertion = false;
                                    break insertionFail;
                                }
                            }

                        // - * - Return success
                            if(isSuccessInsertion){
                                mysql.commitAndCloseConnection();
                                return Result.SUCCESS.setContent("onProgress");
                            }

                        // - * - If operation fail then rollback 
                            mysql.rollbackAndCloseConnection();
                    }

                } catch (Exception ex) {
                    mysql.rollbackAndCloseConnection();
                    Logger.getLogger(DBOrder.class.getName()).log(Level.SEVERE, null, ex);
                    return Result.FAILURE_PROCESS.setContent(ex.getMessage());
                } finally {
                    mysql.closeAllConnection();
                }                

            return result;
        }
    
    // </editor-fold>
    
    
    
        
        
        
        
        
        
    
    
    //------------------------------------------------------------------------------
    //------------------------------------------------------------------------------
    //--                            GET OPERATIONs
    //------------------------------------------------------------------------------
    //------------------------------------------------------------------------------
    
    
        //**************************************************************************
        //**************************************************************************
        //**                    GET ORDER COUNT
        //**************************************************************************
        //**************************************************************************
        public static Result getOrderCount(){
                MysqlDBOperations mysql = new MysqlDBOperations();            
                ResourceProperty rs = new ResourceProperty("com.generic.resources.mysqlQuery");
                Connection conn = mysql.getConnection();  

                try{                                                

                    // -1- Get Query
                    PreparedStatement preStat = conn.prepareStatement(rs.getPropertyValue("mysql.order.count.1"));
                    ResultSet resultSet = preStat.executeQuery();

                    // -2- Get Result
                    if(resultSet.first()){
                        
                        int count = resultSet.getInt(1);
                        return Result.SUCCESS.setContent(count);

                    }else{
                        return Result.SUCCESS_EMPTY;
                    }       
                        

                } catch (SQLException ex) {
                    Logger.getLogger(DBAddress.class.getName()).log(Level.SEVERE, null, ex);
                    return Result.FAILURE_DB;                
                }finally{
                    mysql.closeAllConnection();
                } 

                //return Result.FAILURE_PROCESS;
        }
    
       
        
        
        
        
        
    //------------------------------------------------------------------------------
    //------------------------------------------------------------------------------
    //--                            XXX OPERATIONs
    //------------------------------------------------------------------------------
    //------------------------------------------------------------------------------
    
    
    /**
     * 
     * @param cdoID     
     * @return  
     */
    public static Result getCartInfo(String cdoID){
            
            ResourceBundle rs = ResourceBundle.getBundle("com.generic.resources.mysqlQuery");
            MysqlDBOperations mysql = new MysqlDBOperations();
            
            Orders marketOrder = new Orders();            
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
                    
//                    marketOrder.setProducts(pList);                    
                    query = String.format(rs.getString("mysql.order.select.2"), cdoID);
                    
                    mysqlResult = mysql.getResultSet(query);
                    if(mysqlResult.first()){
                            marketOrder.setPaymentType(mysqlResult.getString("ptype"));
//                            marketOrder.setDate(mysqlResult.getString("date"));
//                            marketOrder.setNote(mysqlResult.getString("note"));                            
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
            ArrayList<Orders> cartList = new ArrayList();
            String query;
            try{
                                
                // GET CURRENT ORDERLIST ID FROM DB                
                query = String.format(  rs.getString("mysql.order.select.3") , uid );
                ResultSet mysqlResult = mysql.getResultSet(query);                                  
                
                if(mysqlResult.first()){
                    do{
                        Orders marketOrder = new Orders();
                        marketOrder.setOrderID(mysqlResult.getString("oid"));
                        marketOrder.setPaymentType(mysqlResult.getString("ptype"));
                        marketOrder.setNote(mysqlResult.getString("note"));
//                        marketOrder.setDate(mysqlResult.getString("date"));
                        marketOrder.setCompanyName(mysqlResult.getString("companyName"));
                         
                        // Get address of order
                        Result address = DBAddress.getAddressById(mysqlResult.getString("add_id"));
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
                String orderID = mysqlResult.first() ? mysqlResult.getString("oid") : "order-"+Util.generateID();   
                
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
