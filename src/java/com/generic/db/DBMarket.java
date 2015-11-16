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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import sun.net.ResourceManager;


/**
 *
 * @author Kemal Sami KARACA
 * @since 11.03.2015
 * @version 1.01
 * 
 * @last 13.03.2015
 */
public class DBMarket extends DBGeneric {
    
    
    /**
     * 
     * @return 
     */
    public String distributerIDGenerator(){
        return "dist-"+UUID.randomUUID().toString();
    }
    
    
    /**     
     * @param distID
     * @return 
     *      
     * This function return orders for given company with specific addresses
     */
    public static Result getOrders(String companyID , List<String> addressID){            
        
            MysqlDBOperations mysql = new MysqlDBOperations();
            ArrayList<String> addList = new ArrayList();
            String query,cond = "";                       
            
            if(addressID != null ){
                for(String add : addressID){
                    cond = cond.length()==0 ? " AND ( " + cond + "uaRelation.aid='" + add + "'" : cond + " OR " + "uaRelation.aid='" + add + "'";
                }            
                cond = cond.length()==0 ? "" : cond + ") ";
            }            
            
            try{
                
                // GET MARKET ADDRESS LIST FROM DB
                query = String.format(  "SELECT * FROM %s " + 
                                        "INNER JOIN %s ON user_id=uaRelation.uid AND orders.comp_id='%s' " + cond + 
                                        "INNER JOIN %s ON address.aid=uaRelation.aid ;" , 
                                        ResourceMysql.TABLE_ORDER , 
                                        ResourceMysql.TABLE_REL_USER_ADDRESS , 
                                        companyID,
                                        ResourceMysql.TABLE_ADDRESS); 
                
                System.out.println(query);
                ResultSet mysqlResult = mysql.getResultSet(query);                                
                                               
                if(mysqlResult.first()){
                    
                    // GET COLUMN FROM DB
                    do{
                        addList.add(mysqlResult.getString("oid"));                        
                    }while(mysqlResult.next());
                    
                    return Result.SUCCESS.setContent(addList);
                
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
     * @param distID
     * @return 
     *      
     */
    public static Result getAddresses(String distID){            
        
            MysqlDBOperations mysql = new MysqlDBOperations();
            Map resultMap = new HashMap();            
            ArrayList<String> addList = new ArrayList();
            String query;
                        
            try{
                
                // GET MARKET ADDRESS LIST FROM DB
                query = String.format(  "SELECT * FROM %s " + 
                                        "INNER JOIN %s ON distributerAddress.disID = distID AND distID='%s'; " , ResourceMysql.TABLE_DISTRIBUTER , ResourceMysql.TABLE_DISTRIBUTER_ADDRESS , distID);                                                
                
                System.out.println(query);
                ResultSet mysqlResult = mysql.getResultSet(query);                                
                                               
                if(mysqlResult.first()){
                    
                    // GET COLUMN FROM DB
                    do{
                        addList.add(mysqlResult.getString("addID"));                        
                    }while(mysqlResult.next());
                    
                    resultMap.put("distID", distID);
                    resultMap.put("distAddressList", addList);                    
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
