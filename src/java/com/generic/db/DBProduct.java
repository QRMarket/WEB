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
 * @since 03.2015
 * @version 1.01
 * 
 * @last 11.03.2015
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
                String p_id;
                double pQuantity,pPrice;
                if(mysqlResult.first()){
                        p_id = mysqlResult.getString("p_id");
                        pId = mysqlResult.getString("cprID");
                        pName = mysqlResult.getString("productName");
                        ppType = mysqlResult.getString("productPriceType");
                        pPrice = mysqlResult.getDouble("p_price");
                        product = new MarketProduct(pId, pName, ppType, pPrice);
                        
                        
                        // After get production then we will take product images                          
                        query = String.format(  "SELECT imageID FROM piRelation " + 
                                                "WHERE productID='%s'" , p_id);
                                                
                        mysqlResult = mysql.getResultSet(query);
                        if(mysqlResult.first()){       
                            
                            do{                                
                                product.getImages().add(mysqlResult.getString("imageID"));
                            }while(mysqlResult.next());
                            
                        }
                    
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
    
    
    
    /**
     *       
     * @param pUID
     * @return  
     */
    public static Result addProduct(String pUID){
                    
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
                String p_id;
                double pQuantity,pPrice;
                if(mysqlResult.first()){
                        p_id = mysqlResult.getString("p_id");
                        pId = mysqlResult.getString("cprID");
                        pName = mysqlResult.getString("productName");
                        ppType = mysqlResult.getString("productPriceType");
                        pPrice = mysqlResult.getDouble("p_price");
                        product = new MarketProduct(pId, pName, ppType, pPrice);
                        
                        
                        // After get production then we will take product images                          
                        query = String.format(  "SELECT imageID FROM piRelation " + 
                                                "WHERE productID='%s'" , p_id);
                                                
                        mysqlResult = mysql.getResultSet(query);
                        if(mysqlResult.first()){       
                            
                            do{                                
                                product.getImages().add(mysqlResult.getString("imageID"));
                            }while(mysqlResult.next());
                            
                        }
                    
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
