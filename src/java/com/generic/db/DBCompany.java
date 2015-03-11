/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.generic.db;

import com.generic.resources.ResourceMysql;
import com.generic.result.Result;
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
 * @since 11.03.2015
 * @version 1.01
 * 
 * @last 11.03.2015
 */
public class DBCompany extends DBGeneric{
            
    
    //**************************************************************************
    //**************************************************************************
    //**                    GET CITY LIST
    //**************************************************************************
    //**************************************************************************
    /**
     * 
     * @return 
     * @deprecated 
     * 
     * This class is inherited from DBGeneric. Therefore; for core selection operations
     * you should call "selectDistict" in DBGeneric
     */
    public static Result getCompanyList(){
            MysqlDBOperations mysql = new MysqlDBOperations();
            
            try{                                
                
                String query;
                List<String> comName = new ArrayList<>();
                
                // GET CURRENT ORDERLIST ID FROM DB                 
                query = String.format( "SELECT DISTINCT * FROM %s" , ResourceMysql.TABLE_COMPANY );
                                                
                ResultSet mysqlResult = mysql.getResultSet(query);                                
                
                
                if(mysqlResult.first()){
                    // GET CITIES FOR DB
                    do{
                        comName.add(mysqlResult.getString("companyName"));                        
                    }while(mysqlResult.next());
                    
                    return Result.SUCCESS.setContent(comName);
                
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
