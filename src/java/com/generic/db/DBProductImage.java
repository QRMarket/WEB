/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.generic.db;

import com.generic.result.Result;
import com.generic.util.MarketProductImage;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Kemal Sami KARACA
 * @since 13.04.2015
 * @version 1.01
 * 
 * @last 13.04.2015
 *
 */
public class DBProductImage extends DBGeneric {
    
    
    //**************************************************************************
    //**************************************************************************
    //**                    GET CITY LIST
    //**************************************************************************
    //**************************************************************************
    /**
     * 
     * @param pID product id
     * @return 
     */
    public static Result getProductImageList(String pID){
            
            MysqlDBOperations mysql = new MysqlDBOperations();
            ResourceBundle rs = ResourceBundle.getBundle("com.generic.resources.mysqlQuery");
            List<MarketProductImage> pImages;
                        
            try{                                                
                String query;
                pImages = new ArrayList<>();
                                           
                query = String.format(  rs.getString("mysql.productImage.select.3") , pID);                
                                                
                ResultSet mysqlResult = mysql.getResultSet(query);                                                
                
                if(mysqlResult.first()){
                    
                    do{
                        MarketProductImage pImage = new MarketProductImage();
                        pImage.setImageID(mysqlResult.getString("imageID"));
                        pImage.setImageContentType(mysqlResult.getString("imgContType"));
                        pImage.setImageSource(mysqlResult.getString("imgSource"));
                        pImage.setImageSourceType(mysqlResult.getString("imgSaveType"));
                        pImage.setImageType(mysqlResult.getString("imgType"));
                        pImages.add(pImage);                        
                    }while(mysqlResult.next());
                    
                    return Result.SUCCESS.setContent(pImages);
                
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
    
}
