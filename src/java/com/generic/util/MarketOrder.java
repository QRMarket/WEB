/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.generic.util;

import com.generic.db.MysqlDBOperations;
import com.generic.result.Result;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Kemal Sami KARACA
 */
public class MarketOrder {
    
    
    public static String orderIDGenerator(){
        return "or_"+UUID.randomUUID().toString();
    }
    
    /**
     * 
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
                }
                
            } catch (SQLException ex) {                
                return Result.FAILURE_DB;        
            
            }finally{
                mysql.closeAllConnection();
            }
            
        return Result.SUCCESS;
    }
    
}
