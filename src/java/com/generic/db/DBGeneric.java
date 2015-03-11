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
public abstract class DBGeneric {
    
    
    /**
     * SELECT operation is used by every DB*** classes
     */
    
    //**************************************************************************
    //**************************************************************************
    //**                    RETURN SINGLE COLUMN
    //**************************************************************************
    //**************************************************************************
    public static Result selectDistict(String tableName , String columnName){
            MysqlDBOperations mysql = new MysqlDBOperations();
            
            try{                                
                
                String query;
                List<String> resultColumn = new ArrayList<>();
                
                // GET CURRENT ORDERLIST ID FROM DB                 
                query = String.format( "SELECT DISTINCT %s FROM %s" , columnName , tableName );
                                                
                ResultSet mysqlResult = mysql.getResultSet(query);                                
                
                
                if(mysqlResult.first()){
                    // GET COLUMN FROM DB
                    do{
                        resultColumn.add(mysqlResult.getString(columnName));                        
                    }while(mysqlResult.next());
                    
                    return Result.SUCCESS.setContent(resultColumn);
                
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
    
}
