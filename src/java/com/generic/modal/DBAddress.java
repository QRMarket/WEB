package com.generic.modal;

import com.generic.db.MysqlDBOperations;
import com.generic.checker.Checker;
import com.generic.locale.UtilLocaleHandler;
import com.generic.resources.ResourceProperty;
import com.generic.result.Result;
import com.generic.entity.Address;
import com.generic.orm.ORMHandler;
import com.generic.util.Util;
import java.io.UnsupportedEncodingException;
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
 * @since 10.03.2015
 * @version 1.01
 * 
 * @last 11.03.2015
 */
public class DBAddress extends DBGeneric{
    
    
    // <editor-fold defaultstate="collapsed" desc="GET Operations">
    //------------------------------------------------------------------------------
    //------------------------------------------------------------------------------
    //--                            GET OPERATIONs
    //------------------------------------------------------------------------------
    //------------------------------------------------------------------------------
    
    
        //**************************************************************************
        //**************************************************************************
        //**                    GET ADDRESS BY ID
        //**************************************************************************
        //**************************************************************************
        public static Result getAddressById(String addressID){
                MysqlDBOperations mysql = new MysqlDBOperations();            
                ResourceProperty rs = new ResourceProperty("com.generic.resources.mysqlQuery");
                Connection conn = mysql.getConnection();  
                Address address;

                try{
                    if(!Checker.anyNull(addressID)){
                                            
                        // -1- Get Address
                            PreparedStatement preStat = conn.prepareStatement(rs.getPropertyValue("mysql.address.select.2"));  
                            preStat.setString(1, addressID);
                            ResultSet resultSet = preStat.executeQuery();

                        // -2- Get Result
                            if(resultSet.first()){
                                return Result.SUCCESS.setContent(ORMHandler.resultSetToAddress(resultSet));
                            }else{
                                return Result.SUCCESS_EMPTY;
                            }       

                    }else{
                        return Result.FAILURE_PARAM_MISMATCH;
                    }

                } catch (SQLException ex) {
                    Logger.getLogger(DBAddress.class.getName()).log(Level.SEVERE, null, ex);
                    return Result.FAILURE_DB.setContent(ex.getMessage());                
                }finally{
                    mysql.closeAllConnection();
                } 

                //return Result.FAILURE_PROCESS;
        }
        
        
        
        
        
        //**************************************************************************
        //**************************************************************************
        //**                    GET ADDRESS
        //**************************************************************************
        //**************************************************************************
        public static Result searchAddress(String city, String borough, String locality){
                MysqlDBOperations mysql = new MysqlDBOperations();            
                ResourceProperty rs = new ResourceProperty("com.generic.resources.mysqlQuery");
                Connection conn = mysql.getConnection();  
                List<Address> addressList = new ArrayList<Address>();

                try{                                                

                    if(!Checker.allNull(city,borough,locality)){
                        
                        // -0- before start execute query set values
                        city = city!=null ? "%"+city+"%" : "%%";
                        borough = borough!=null ? "%"+borough+"%" : "%%";
                        locality = locality!=null ? "%"+locality+"%" : "%%";                                               
                        
                        // -1- Get Address
                        PreparedStatement preStat = conn.prepareStatement(rs.getPropertyValue("mysql.address.select.7"));  
                        preStat.setString(1, city);
                        preStat.setString(2, borough);
                        preStat.setString(3, locality);
                        ResultSet resultSet = preStat.executeQuery();

                        // -2- Get Result
                        if(resultSet.first()){

                            do{
                                Address address = new Address();
                                address.setId(resultSet.getString("aid"));
                                address.setCity(resultSet.getString("city"));
                                address.setBorough(resultSet.getString("borough"));
                                address.setLocality(resultSet.getString("locality"));
                                addressList.add(address);

                            }while(resultSet.next());

                            return Result.SUCCESS.setContent(addressList);

                        }else{
                            return Result.SUCCESS_EMPTY;
                        }       
                        
                    }else{
                        return Result.FAILURE_PARAM_MISMATCH;
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
        public static Result getCityList(){
                MysqlDBOperations mysql = new MysqlDBOperations();
                ResourceProperty rs = new ResourceProperty("com.generic.resources.mysqlQuery");
                Connection conn = mysql.getConnection();  

                try{                                

                    // -1- Check address exist
                        PreparedStatement preStat = conn.prepareStatement(rs.getPropertyValue("mysql.address.select.4"));  
                        ResultSet resultSet = preStat.executeQuery();
                        ArrayList<String> cities = new ArrayList<>();

                    // -2- Get Result
                        if(resultSet.first()){ 

                            do{
                                cities.add(resultSet.getString("city"));
                            }while(resultSet.next());

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

        }





        //**************************************************************************
        //**************************************************************************
        //**                    GET BOROUGH LIST
        //**************************************************************************
        //**************************************************************************
        public static Result getBoroughList(String city){
                MysqlDBOperations mysql = new MysqlDBOperations();
                ResourceProperty rs = new ResourceProperty("com.generic.resources.mysqlQuery");
                Connection conn = mysql.getConnection();   

                try{                                

                    if(!Checker.anyNull(city)){

                        // -1- 
                            PreparedStatement preStat = conn.prepareStatement(rs.getPropertyValue("mysql.address.select.5"));  
                            preStat.setString(1, city);
                            ResultSet resultSet = preStat.executeQuery();                   
                            ArrayList<String> boroughs = new ArrayList<>();

                        // -2-
                            if(resultSet.first()){
                                // GET BOROUGHS
                                do{
                                    boroughs.add(resultSet.getString("borough"));                        
                                }while(resultSet.next());

                                return Result.SUCCESS.setContent(boroughs);

                            }else{
                                return Result.SUCCESS_EMPTY;
                            } 

                    }else{
                        return Result.FAILURE_PARAM_MISMATCH;
                    }

                } catch (SQLException ex) {
                    Logger.getLogger(DBAddress.class.getName()).log(Level.SEVERE, null, ex);
                    return Result.FAILURE_DB;                
                }finally{
                    mysql.closeAllConnection();
                } 

        }





        //**************************************************************************
        //**************************************************************************
        //**                    GET BOROUGH LIST
        //**************************************************************************
        //**************************************************************************
        public static Result getLocalityList(String city , String borough){
                MysqlDBOperations mysql = new MysqlDBOperations();
                ResourceProperty rs = new ResourceProperty("com.generic.resources.mysqlQuery");
                Connection conn = mysql.getConnection();   

                try{                                

                    // -- 1 --
                        PreparedStatement preStat = conn.prepareStatement(rs.getPropertyValue("mysql.address.select.6"));  
                        preStat.setString(1, city);
                        preStat.setString(2, borough);
                        ResultSet resultSet = preStat.executeQuery();                   
                        ArrayList<String> locality = new ArrayList<>();

                    // -- 2 --
                        if(resultSet.first()){
                            // GET LOCALITIES
                            do{
                                locality.add(resultSet.getString("locality"));                        
                            }while(resultSet.next());

                            return Result.SUCCESS.setContent(locality);

                        }else{
                            return Result.SUCCESS_EMPTY;
                        }

                } catch (SQLException ex) {
                    Logger.getLogger(DBAddress.class.getName()).log(Level.SEVERE, null, ex);
                    return Result.FAILURE_DB;
                } catch (Exception ex) {
                    Logger.getLogger(DBAddress.class.getName()).log(Level.SEVERE, null, ex);
                    return Result.FAILURE_PROCESS.setContent("Exception");
                } finally{
                    mysql.closeAllConnection();
                } 

        }
    // </editor-fold>
    
    
    // <editor-fold defaultstate="collapsed" desc="INSERT Operations"> 
    
    //------------------------------------------------------------------------------
    //------------------------------------------------------------------------------
    //--                            INSERT OPERATIONs
    //------------------------------------------------------------------------------
    //------------------------------------------------------------------------------
    
        
        //**************************************************************************
        //**************************************************************************
        //**                        ADD ADDRESS
        //**************************************************************************
        //**************************************************************************
        public static Result addAddress(String city , String borough, String locality){
                MysqlDBOperations mysql = new MysqlDBOperations();
                ResourceProperty rs = new ResourceProperty("com.generic.resources.mysqlQuery");
                Connection conn = mysql.getConnection();

                try{                                

                    if(!Checker.anyNull(city,borough,locality)){               

                        // -1- Check address exist
                        PreparedStatement preStat = conn.prepareStatement(rs.getPropertyValue("mysql.address.select.3"));                    
                        preStat.setString(1, city.toUpperCase());
                        preStat.setString(2, borough.toUpperCase());
                        preStat.setString(3, locality.toUpperCase());

                        ResultSet resultSet = preStat.executeQuery();

                        boolean empty = false;
                        try {
                            empty = !(resultSet.first());
                            resultSet.beforeFirst();
                        } catch (SQLException ex) {
                            Logger.getLogger(MysqlDBOperations.class.getName()).log(Level.SEVERE, null, ex);
                        }

                        if (!empty) {
                            return Result.FAILURE_PROCESS.setContent("ALREADY EXIST");
                        }


                        // -2- Create prepare statement
                        preStat = conn.prepareStatement(rs.getPropertyValue("mysql.address.update.insert.1"));
                        preStat.setString(1, "adr-"+Util.generateID());
                        preStat.setString(2, city.toUpperCase());
                        preStat.setString(3, borough.toUpperCase());
                        preStat.setString(4, locality.toUpperCase());

                        // -3- Execute query
                        int executeResult = preStat.executeUpdate();

                        // -4- Result
                        if(executeResult==1){
                            mysql.commitAndCloseConnection();
                            return Result.SUCCESS;
                        }else if(executeResult==0){
                            return Result.SUCCESS_EMPTY;
                        }else{
                            return Result.FAILURE_PROCESS;
                        }  

                    }else{
                        return Result.FAILURE_PARAM_MISMATCH;
                    }

                } catch (SQLException ex) {
                    Logger.getLogger(DBAddress.class.getName()).log(Level.SEVERE, null, ex);
                    return Result.FAILURE_DB;                
                }finally{
                    mysql.closeAllConnection();
                } 

        }
        
        // </editor-fold>   
        
        
        
    //------------------------------------------------------------------------------
    //------------------------------------------------------------------------------
    //--                            DELETE OPERATIONs
    //------------------------------------------------------------------------------
    //------------------------------------------------------------------------------
        
        
        //**************************************************************************
        //**************************************************************************
        //**                        DELETE ADDRESS
        //**************************************************************************
        //**************************************************************************
        public static Result deleteAddress(String aid){
                MysqlDBOperations mysql = new MysqlDBOperations();
                ResourceProperty rs = new ResourceProperty("com.generic.resources.mysqlQuery");
                Connection conn = mysql.getConnection();

                try{                                

                    if(!Checker.anyNull(aid)){               

                        // -1- Check address exist
                        PreparedStatement preStat = conn.prepareStatement(rs.getPropertyValue("mysql.address.update.delete.1"));
                        preStat.setString(1, aid);

                        // -3- Execute query
                        int executeResult = preStat.executeUpdate();

                        // -4- Result
                        if(executeResult==1){
                            mysql.commitAndCloseConnection();
                            return Result.SUCCESS;
                        }else if(executeResult==0){
                            return Result.FAILURE_DB_EFFECTED_ROW_NUM.setContent("Effected row count 0");
                        }else{
                            mysql.rollback();
                            return Result.FAILURE_DB_EFFECTED_ROW_NUM.setContent("Effected row count more than 1");                            
                        }  

                    }else{
                        return Result.FAILURE_PARAM_MISMATCH;
                    }

                } catch (SQLException ex) {
                    Logger.getLogger(DBAddress.class.getName()).log(Level.SEVERE, null, ex);
                    return Result.FAILURE_DB;                
                }finally{
                    mysql.closeAllConnection();
                } 

        }       
   
}
