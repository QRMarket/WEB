/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.generic.db;

import com.generic.resources.ResourceMysql;
import com.generic.resources.ResourceProperty;
import com.generic.result.Result;
import com.generic.servlet.Auth;
import com.generic.entity.Address;
import com.generic.entity.MarketUser;
import com.generic.constant.UserRole;
import com.generic.util.Util;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Kemal Sami KARACA
 * @since 14.03.2015
 * @version 1.01
 * 
 * @last 13.04.2015
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
    
    
    
    
    
    //**************************************************************************
    //**************************************************************************
    //**                    GET USER ADDRESS
    //**************************************************************************
    //**************************************************************************
    /**
     * 
     * @param uid user-id
     * @return 
     */
    public static Result getUserAddressList(String uid){
            
            ResourceProperty resource = new ResourceProperty("com.generic.resources.mysqlQuery");
            MysqlDBOperations mysql = new MysqlDBOperations();
            Connection conn = mysql.getConnection();
            PreparedStatement preStat;
            
            List<Address> userAddressList;
            
            try{                  
                
                    preStat = conn.prepareStatement(resource.getPropertyValue("mysql.useraddress.select.3"));
                    preStat.setString(1, uid);
                                
                    ResultSet mysqlResult = preStat.executeQuery();
                    if(mysqlResult.first()){

                        userAddressList = new ArrayList<>();
                        do{

                            Address userAddress = new Address();
                            userAddress.setAid(mysqlResult.getString("aid"));
                            userAddress.setCity(mysqlResult.getString("city"));
                            userAddress.setBorough(mysqlResult.getString("borough"));
                            userAddress.setLocality(mysqlResult.getString("locality"));
                            userAddress.setStreet(mysqlResult.getString("street"));
                            userAddress.setAvenue(mysqlResult.getString("avenue"));
                            userAddress.setDesc(mysqlResult.getString("desc"));

                            userAddressList.add(userAddress);

                        }while(mysqlResult.next());

                        return Result.SUCCESS.setContent(userAddressList);

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
    //**                    USER LOGIN
    //**************************************************************************
    //**************************************************************************
    public static Result userLogin(String userMail, String userPass){

            ResourceProperty resource = new ResourceProperty("com.generic.resources.mysqlQuery");
            MysqlDBOperations mysql = new MysqlDBOperations();
            Connection conn = mysql.getConnection();
            PreparedStatement preStat;
            
            try {
                    preStat = conn.prepareStatement(resource.getPropertyValue("mysql.user.select.3"));
                    preStat.setString(1, userMail);
                    preStat.setString(2, userPass);
                    
                    ResultSet mysqlResult = preStat.executeQuery();                                        
                    if(mysqlResult.first()){

                        MarketUser loginUser = new MarketUser();
                        loginUser.setUserMail(userMail);
                        loginUser.setUserId(mysqlResult.getString("mu_id"));
                        loginUser.setUserName(mysqlResult.getString("mu_name"));
                        loginUser.setUserSurname(mysqlResult.getString("mu_surname"));
                        loginUser.setUserPhoneNumber(mysqlResult.getString("mu_phone"));
                        loginUser.setUserRole(UserRole.valueOf(Integer.parseInt(mysqlResult.getString("mu_type"))));

                        // SET USER ADDRESS
                        Result userAddress = DBUser.getUserAddressList(loginUser.getUserId());
                        if(userAddress.checkResult(Result.SUCCESS)){
                            loginUser.setUserAddress((ArrayList<Address>) userAddress.getContent());
                        }

                        // RETURN VALUE 
                        return Result.SUCCESS.setContent(loginUser);  

                    }else{
                        return Result.FAILURE_AUTH_MULTIPLE;
                    }                                        
                
            } catch (Exception ex) {                
                    Logger.getLogger(DBUser.class.getName()).log(Level.SEVERE, null, ex);
                    return Result.FAILURE_DB.setContent(ex.getMessage());                
            } finally{                                                                        
                    mysql.closeAllConnection();
            }        
        
    }
    
    
    
    
    
    
    //**************************************************************************
    //**************************************************************************
    //**                    INSERT USER 
    //**************************************************************************
    //**************************************************************************
    /**
     * 
     * @param userEmail
     * @param password
     * @param username
     * @param usersurname
     * @param userphone
     * @return 
     */
    public static Result userRegister(String userEmail, String password, String username, String usersurname, String userphone){
                    
            ResourceProperty resource = new ResourceProperty("com.generic.resources.mysqlQuery");
            MysqlDBOperations mysql = new MysqlDBOperations();
            Connection conn = mysql.getConnection();
                        
            try {
                
                // -1- Check user mail exist
                    PreparedStatement preStat = conn.prepareStatement(resource.getPropertyValue("mysql.user.select.4"));
                    preStat.setString(1, userEmail);
                    ResultSet resultSet = preStat.executeQuery();
                    if(resultSet.first()){
                        if(resultSet.getInt("count")==1){
                            return Result.FAILURE_AUTH_REGISTER.setContent("DBUser -> userRegister -> ::User already exist");
                        }
                    }
                
                // -2- If not exist then continue
                    preStat = conn.prepareStatement(resource.getPropertyValue("mysql.user.update.insert.1"));                
                    preStat.setString(1, "mu-"+Util.generateID());
                    preStat.setString(2, userEmail);
                    preStat.setString(3, password);
                    preStat.setString(4, username);
                    preStat.setString(5, usersurname);
                    preStat.setString(6, userphone);
                    preStat.setString(7, ""+UserRole.intValue(UserRole.CUSTOMER));
                    preStat.setString(8, "REGISTER");
                    preStat.setLong(9, System.currentTimeMillis());
                    int executeResult = preStat.executeUpdate();
                    if( executeResult==1 ){
                        mysql.commitAndCloseConnection();
                        return Result.SUCCESS;
                    }else if (executeResult==0){
                        return Result.FAILURE_DB_EFFECTED_ROW_NUM.setContent("Effected row count 0");
                    }else{
                        mysql.rollback();
                        return Result.FAILURE_DB_EFFECTED_ROW_NUM.setContent("Effected row count more than 1");
                    }
                                
            } catch (SQLException ex) {
                Logger.getLogger(DBUser.class.getName()).log(Level.SEVERE, null, ex);
                return Result.FAILURE_DB.setContent("SQL Exception");
              
            } finally{
                mysql.closeAllConnection();
            }        
            
    }
    
    
    
    
}
