/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.generic.db;

import com.generic.result.Result;
import com.generic.util.MarketProduct;
import com.generic.util.MarketProductImage;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.fileupload.FileItem;

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
    public static Result getCompanyProductInfo(String pUID){
                    
            MysqlDBOperations mysql = new MysqlDBOperations();
            ResourceBundle rs = ResourceBundle.getBundle("com.generic.resources.mysqlQuery");
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
                        query = String.format(  rs.getString("mysql.productImage.select.3") , p_id);
                                                
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
    public static Result getProduct(String pid){
                    
            MysqlDBOperations mysql = new MysqlDBOperations();
            ResourceBundle rs = ResourceBundle.getBundle("com.generic.resources.mysqlQuery");
            MarketProduct product;
            String query;
            
            try{               
                
                // PREPARE QUERY                
                query = String.format(  rs.getString("mysql.product.select.2"),pid);
                
                ResultSet mysqlResult = mysql.getResultSet(query);
                if(mysqlResult.first()){
                    
                        product = new MarketProduct();
                        product.setProductID(mysqlResult.getString("pid"));
                        product.setProductName(mysqlResult.getString("productName"));
                        product.setPriceType(mysqlResult.getString("productPriceType"));
                        product.setBranchName(mysqlResult.getString("productBranch"));
                        product.setProductCode(mysqlResult.getString("productCode"));
                        product.setProductDesc(mysqlResult.getString("productDesc"));
                       
                        // After get production then we will take product images                          
                        query = String.format(  rs.getString("mysql.productImage.select.3") , pid);
                        mysqlResult = mysql.getResultSet(query);
                        if(mysqlResult.first()){       

                            do{      
                                MarketProductImage productImage = new MarketProductImage();
                                productImage.setImageID(mysqlResult.getString("imageID"));
                                productImage.setImageContentType(mysqlResult.getString("imgContType"));
                                productImage.setImageSourceType(mysqlResult.getString("imgSaveType"));
                                productImage.setImageType(mysqlResult.getString("imgType"));
                                productImage.setImageSource(mysqlResult.getString("imgSource"));                                
                                product.getProductImages().add(productImage);
                            }while(mysqlResult.next());

                        }
                       
                    return Result.SUCCESS.setContent(product);
                    
                }else{
                    return Result.SUCCESS_EMPTY;
                }  
                
            } catch (SQLException ex) {                
                Logger.getLogger(DBProduct.class.getName()).log(Level.SEVERE, null, ex);
                return Result.FAILURE_DB;
            } finally{
                mysql.closeAllConnection();
            }                          
    }
    
    
    /**
     *            
     * @return  
     */
    public static Result addProduct(String pName, String pBranch, String pCode, String pDesc , String fileContentType, String fileBase64){
        
        Result res = Result.FAILURE_PROCESS;
        MysqlDBOperations mysql = new MysqlDBOperations();
        
        try{
                // GENERATE PRODUCT && PRODUCTIMAGE ID
                String productID = "p-"+UUID.randomUUID().toString();
                String imageID = "pi-"+UUID.randomUUID().toString();                                
            
                // PREPARE QUERY
                ResourceBundle rs = ResourceBundle.getBundle("com.generic.resources.mysqlQuery");
                
                // EXECUTE QUERY
                String query = String.format( rs.getString("mysql.product.update.insert.1"),productID,pName,pBranch,pCode,pDesc);                
                int effectedRowNumber = mysql.execUpdate(query);                                
                
                query = String.format( rs.getString("mysql.productImage.update.insert.1"),imageID,productID,fileContentType,fileBase64);
                int effectedRowNumber2 = mysql.execUpdate(query);
                
                if(effectedRowNumber>0 && effectedRowNumber2>0){                    
                    mysql.commitAndCloseConnection();
                    res = Result.SUCCESS;                    
                }else{                                                   
                    res = Result.FAILURE_DB_EFFECTED_ROW_NUM;
                }
                
        } catch (NullPointerException ex) {                
            return Result.FAILURE_DB;
        }finally{
            mysql.closeAllConnection();
        } 
        
        return res;
    }
    
}
