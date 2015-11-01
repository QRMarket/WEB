/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.generic.db;

import com.generic.resources.ResourceProperty;
import com.generic.result.Result;
import com.generic.util.Brands;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author kemalsamikaraca
 */
public class DBBrand {
    
    // <editor-fold defaultstate="collapsed" desc="Brand GET Operations">
    
    public static Result getBrand_ById(Brands brand){
        
        
            Result result = Result.FAILURE_PROCESS;
            ResourceProperty rs = new ResourceProperty("com.generic.resources.mysqlQuery");
            MysqlDBOperations mysql = new MysqlDBOperations();            
            Connection conn = mysql.getConnection();
           
        
            try {
                
                // -1.1- Select from DB-->"brand" 
                    PreparedStatement preStat = conn.prepareStatement(rs.getPropertyValue("mysql.brand.select.2"));
                    preStat.setString(1, brand.getId());
                    
                // -1.2- Get result
                    ResultSet resultSet = preStat.executeQuery();
                
                // -1.3- Check Result                     
                    if(resultSet.first()){
                        
                        result = Result.SUCCESS.setContent(resultSet.getString("brand_name"));
                        
                    }else{
                        result = Result.SUCCESS_EMPTY;
                    }                                        
                    
            }catch(SQLException ex){
                Logger.getLogger(DBProduct.class.getName()).log(Level.SEVERE, null, ex);
                result = Result.FAILURE_DB.setContent(ex.toString());
            }finally{
                mysql.closeAllConnection();
            }            
        
        return result;
    }
    
    // </editor-fold>  
    
}
