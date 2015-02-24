/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.generic.db;

import com.generic.result.Result;
import com.generic.util.MarketProduct;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Kemal Sami KARACA
 */
public class DBProduct {
    
    
    /**
     *       
     * @param pUID
     * @return  
     */
    public static Result getProductInfo(String pUID){
                    
            MysqlDBOperations mysql = new MysqlDBOperations();
            MarketProduct product;
            String query;
            try{
                
                // PREPARE QUERY                
                query = String.format(  "SELECT * FROM cpRelation " +                                        
                                        "INNER JOIN products ON cpRelation.p_id=products.pid " + 
                                        "AND cprID='%s'" , pUID);                                
                
                ResultSet mysqlResult = mysql.getResultSet(query);
                
                String pId,pName,ppType;
                double pQuantity,pPrice;
                if(mysqlResult.first()){                    
                        pId = mysqlResult.getString("cprID");
                        pName = mysqlResult.getString("productName");
                        ppType = mysqlResult.getString("productPriceType");                                              
                        pPrice = mysqlResult.getDouble("p_price");                                     
                        product = new MarketProduct(pId, pName, ppType, pPrice);                                        
                    return Result.SUCCESS.setContent(product);
                
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
