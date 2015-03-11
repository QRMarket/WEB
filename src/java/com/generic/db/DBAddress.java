package com.generic.db;

import static com.generic.db.DBOrder.orderIDGenerator;
import com.generic.result.Result;
import com.generic.util.MarketProduct;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Kemal Sami KARACA
 * @since 10.03.2015
 * @version 1.01
 * 
 * @last 11.03.2015
 */
public class DBAddress extends DBGeneric{
    
    
    
    
    
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
     * This class is inherited from DBGeneric. Use "selectDistict" for core 
     * selection operations
     * 
     */
    public static Result getCityList(){
            MysqlDBOperations mysql = new MysqlDBOperations();
                        
            try{                                
                
                String query;
                List<String> cities = new ArrayList<>();
                
                // GET CURRENT ORDERLIST ID FROM DB                 
                query = String.format( "SELECT DISTINCT city FROM address" );
                                                
                ResultSet mysqlResult = mysql.getResultSet(query);                                
                
                
                if(mysqlResult.first()){
                    // GET CITIES FOR DB
                    do{
                        cities.add(mysqlResult.getString("city"));                        
                    }while(mysqlResult.next());
                    
                    return Result.SUCCESS.setContent(cities);
                
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
    
    
    //**************************************************************************
    //**************************************************************************
    //**                    GET BOROUGH LIST
    //**************************************************************************
    //**************************************************************************
    public static Result getBoroughList(String city){
            MysqlDBOperations mysql = new MysqlDBOperations();
                        
            try{                                
                
                String query;
                List<String> boroughs = new ArrayList<>();
                
                // GET CURRENT ORDERLIST ID FROM DB                 
                query = String.format( "SELECT DISTINCT borough FROM address WHERE city='%s'" , city );
                                                
                ResultSet mysqlResult = mysql.getResultSet(query);                                
                
                
                if(mysqlResult.first()){
                    // GET BOROUGHS
                    do{
                        boroughs.add(mysqlResult.getString("borough"));                        
                    }while(mysqlResult.next());
                    
                    return Result.SUCCESS.setContent(boroughs);
                
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
    
    
    //**************************************************************************
    //**************************************************************************
    //**                    GET BOROUGH LIST
    //**************************************************************************
    //**************************************************************************
    public static Result getLocalityList(String city , String borough){
            MysqlDBOperations mysql = new MysqlDBOperations();
                        
            try{                                
                
                String query;
                List<String> locality = new ArrayList<>();
                
                // GET CURRENT ORDERLIST ID FROM DB                 
                query = String.format( "SELECT DISTINCT locality FROM address WHERE city='%s' AND borough='%s'" , city , borough );
                                                
                ResultSet mysqlResult = mysql.getResultSet(query);                                
                
                
                if(mysqlResult.first()){
                    // GET BOROUGHS
                    do{
                        locality.add(mysqlResult.getString("locality"));                        
                    }while(mysqlResult.next());
                    
                    return Result.SUCCESS.setContent(locality);
                
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