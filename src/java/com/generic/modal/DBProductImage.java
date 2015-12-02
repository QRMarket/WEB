/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.generic.modal;

import com.generic.db.MysqlDBOperations;
import com.generic.entity.DistributerProduct;
import com.generic.result.Result;
import com.generic.entity.MarketProductImage;
import com.generic.resources.ResourceProperty;
import java.sql.Connection;
import java.sql.PreparedStatement;
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
    //**                    GET PRODUCT IMAGE LIST
    //**************************************************************************
    //**************************************************************************
    /**
     * 
     * @param productId
     * @return 
     */
    public static Result getProductImageList(String productId){
        
            MysqlDBOperations mysql = new MysqlDBOperations();
            ResourceProperty rs = new ResourceProperty("com.generic.resources.mysqlQuery");
            Connection conn = mysql.getConnection();            
            List<MarketProductImage> productImages;
                        
            try{       
                
                // -1- Check address exist
                    PreparedStatement preStat = conn.prepareStatement(rs.getPropertyValue("mysql.productImage.select.3"));
                    preStat.setString(1, productId);
                        
                    ResultSet resultSet = preStat.executeQuery();
                
                // -2- Get Result
                    if(resultSet.first()){
                        productImages = new ArrayList<>();
                        do{
                            MarketProductImage pImage = new MarketProductImage();
                            pImage.setImageID(resultSet.getString("imageID"));
                            pImage.setImageContentType(resultSet.getString("imgContType"));
                            pImage.setImageSource(resultSet.getString("imgSource"));
                            pImage.setImageSourceType(resultSet.getString("imgSaveType"));
                            pImage.setImageType(resultSet.getString("imgType"));
                            productImages.add(pImage);                        
                        }while(resultSet.next());

                        return Result.SUCCESS.setContent(productImages);

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
