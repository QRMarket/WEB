package com.generic.db;

import com.generic.resources.ResourceProperty;
import com.generic.result.Result;
import com.generic.entity.Address;
import com.generic.entity.Orders;
import com.generic.entity.MarketProduct;
import com.generic.entity.MarketUser;
import com.generic.entity.OrderProduct;
import com.generic.entity.UserAddress;
import com.generic.orm.ORMHandler;
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
    //--                            GET OPERATIONs
    //------------------------------------------------------------------------------
    //------------------------------------------------------------------------------
    
    // <editor-fold defaultstate="collapsed" desc="GET Operations">
        
        //**************************************************************************
        //**************************************************************************
        //**                    GET ORDER 
        //**************************************************************************
        //**************************************************************************
        /**
         *     
         * @param orderId
         * @return  
         */
        public static Result getOrder(String orderId){
            
                MysqlDBOperations mysql = new MysqlDBOperations();
                ResourceProperty rs = new ResourceProperty("com.generic.resources.mysqlQuery");
                Connection conn = mysql.getConnection();            
                Orders order;
                List<MarketProduct> productList;

                try{

                    // -1- Prepare Statement
                        PreparedStatement preStat = conn.prepareStatement(rs.getPropertyValue("mysql.order.select.4"));
                        preStat.setString(1, orderId);
                        
                        ResultSet resultSet = preStat.executeQuery();
                        
                    // -2- Get Result
                        if(resultSet.first()){
                         
                            // -2.1- Get Order Object
                                order = ORMHandler.resultSetToOrder(resultSet);
                            
                            // -2.2- Get User & UserAddress Object
                                MarketUser user = ORMHandler.resultSetToUser(resultSet);
                                UserAddress userAddress = ORMHandler.resultSetToUserAddress(resultSet);
                                userAddress.setAddress(ORMHandler.resultSetToAddress(resultSet));
                                
                            // -2.3- Get MarketProduct list 
                                productList = new ArrayList<>();
                                do{
                                    MarketProduct product = ORMHandler.resultSetToProduct(resultSet);
                                    productList.add(product);
                                }while(resultSet.next());
                            
                            // -2.4- Set 
                                order.setUserAddress(userAddress);
                                order.setUser(user);
                                order.setProductList(productList);
                                
                            return Result.SUCCESS.setContent(order);
                        }else{
                            return Result.SUCCESS_EMPTY;
                        }                                                                      

                } catch (SQLException ex) {                
                    return Result.FAILURE_DB.setContent(ex.getMessage());
                }finally{
                    mysql.closeAllConnection();
                }                
        }
        
        
        
        //**************************************************************************
        //**************************************************************************
        //**                    GET ORDER 
        //**************************************************************************
        //**************************************************************************
        /**
         *     
         * @param distributerId
         * @param limit
         * @return  
         */
        public static Result getOrderList(String distributerId, int limit){
            
                MysqlDBOperations mysql = new MysqlDBOperations();
                ResourceProperty rs = new ResourceProperty("com.generic.resources.mysqlQuery");
                Connection conn = mysql.getConnection();            
                List<Orders> orderList;

                try{

                    // -1- Prepare Statement
                        PreparedStatement preStat=conn.prepareStatement(rs.getPropertyValue("mysql.order.select.1"));
                        if(distributerId!=null){
                            preStat = conn.prepareStatement(rs.getPropertyValue("mysql.order.select.6"));
                            preStat.setString(1, distributerId);
                            preStat.setInt(2, limit);
                        }else{
                            preStat = conn.prepareStatement(rs.getPropertyValue("mysql.order.select.5"));
                            preStat.setInt(1, limit);
                        }
                        
                        ResultSet resultSet = preStat.executeQuery();
                        
                    // -2- Get Result
                        if(resultSet.first()){
                            
                            orderList = new ArrayList<>();
                            do{
                                Orders order = ORMHandler.resultSetToOrder(resultSet);
                                MarketUser user = ORMHandler.resultSetToUser(resultSet);
                                order.setUser(user);
                                
                                orderList.add(order);
                            }while(resultSet.next());
                            
                            return Result.SUCCESS.setContent(orderList);
                        }else{
                            return Result.SUCCESS_EMPTY;
                        }                                                                      

                } catch (SQLException ex) {                
                    return Result.FAILURE_DB.setContent(ex.getMessage());
                }finally{
                    mysql.closeAllConnection();
                }                
        }
        
        
        
        //**************************************************************************
        //**************************************************************************
        //**                    GET ORDER COUNT
        //**************************************************************************
        //**************************************************************************
        /**
         * 
         * @return 
         */
        public static Result getOrderCount(){
            
                MysqlDBOperations mysql = new MysqlDBOperations();
                ResourceProperty rs = new ResourceProperty("com.generic.resources.mysqlQuery");
                Connection conn = mysql.getConnection();            
                
                try{                                                

                    // -1- Get Query
                        PreparedStatement preStat=conn.prepareStatement(rs.getPropertyValue("mysql.order.select.1"));
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

        }
      
    // </editor-fold>
    
    
    
    
    
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
         * @param orderObj
         * @return 
         */
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
                    preStat.setString(8, orderObj.getUserAddressID());
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
    //--                            XXX OPERATIONs
    //------------------------------------------------------------------------------
    //------------------------------------------------------------------------------
    
    
    
    
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
                        marketOrder.setId(mysqlResult.getString("oid"));
//                        marketOrder.setPaymentType(mysqlResult.getString("ptype"));
                        marketOrder.setNote(mysqlResult.getString("note"));
//                        marketOrder.setDate(mysqlResult.getString("date"));
//                        marketOrder.setCompanyName(mysqlResult.getString("companyName"));
                         
                        // Get address of order
                        Result address = DBAddress.getAddressById(mysqlResult.getString("add_id"));
                        if(address.checkResult(Result.SUCCESS)){
//                            marketOrder.setAdress((Address)address.getContent());
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
    
    
}
