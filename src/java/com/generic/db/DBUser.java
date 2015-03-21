/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.generic.db;

import com.generic.resources.ResourceMysql;
import com.generic.result.Result;
import com.generic.util.MarketProduct;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Kemal Sami KARACA
 * @since 14.03.2015
 * @version 1.01
 * 
 * @last 14.03.2015
 */
public class DBUser extends DBGeneric{
    
    

    /**
     *       
     * @param uid
     * @return  
     */
    public static Result getUserCompany(String uid){
                    
            MysqlDBOperations mysql = new MysqlDBOperations();
            Map resultMap = new HashMap();
            ArrayList<String> addList = new ArrayList();
            ArrayList<String> distList = new ArrayList();
            String query;
            try{
                
                // PREPARE QUERY                
                query = String.format(  "SELECT * FROM %s " +                                        
                                        "INNER JOIN %s ON distID=dis_id " + 
                                        "AND mu_id='%s'" , 
                                        ResourceMysql.TABLE_REL_USER_DISTRIBUTER, 
                                        ResourceMysql.TABLE_DISTRIBUTER,
                                        uid);
                
                ResultSet mysqlResult = mysql.getResultSet(query);
                                                
                
                if(mysqlResult.first()){
                    // GET COLUMN FROM DB
                    do{
                        addList.add(mysqlResult.getString("comID"));                        
                        distList.add(mysqlResult.getString("distID")); 
                    }while(mysqlResult.next());
                    
                    resultMap.put("userCompany", addList);
                    resultMap.put("userDistributer", distList);                    
                    return Result.SUCCESS.setContent(resultMap);
                
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
