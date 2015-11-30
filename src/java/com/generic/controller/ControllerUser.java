/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.generic.controller;

import com.generic.checker.Checker;
import com.generic.db.DBProduct;
import com.generic.db.DBUser;
import com.generic.result.Result;
import com.generic.servlet.Auth;
import com.generic.constant.UserRole;
import com.generic.entity.MarketProduct;
import com.generic.entity.MarketUser;
import com.generic.entity.UserAddress;
import com.generic.util.Util;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Guppy Org.
 * @version 1.1
 * 
 */
public class ControllerUser {
    
    
    
    //------------------------------------------------------------------------------
    //------------------------------------------------------------------------------
    //--                            GET OPERATIONs
    //------------------------------------------------------------------------------
    //------------------------------------------------------------------------------
    
    // <editor-fold defaultstate="collapsed" desc="GET Operations">
    
    
        //**************************************************************************
        //**************************************************************************
        //**                        OPERATION LOGIN
        //**************************************************************************
        //**************************************************************************
        /**
         * 
         * @param request
         * @return 
         */
        public static Result callLoginOperation(HttpServletRequest request){
        
            
                Result result = Result.FAILURE_PROCESS.setContent("Auth_Controller -> callLoginOperation -> INITIAL ERROR");
                String mail = request.getParameter("mail");
                String pass = request.getParameter("pass");
                String type = request.getParameter("type");

                if( !Checker.anyNull(mail,pass,type) ){
                    if(type.equalsIgnoreCase("admin")){
                        result = DBUser.adminUserLogin(mail, pass);
                    }else if(type.equalsIgnoreCase("customer")){
                        result = DBUser.userLogin(mail, pass);
                    }else {
                        result = Result.FAILURE_PARAM_MISMATCH.setContent("ControllerUser -> callLoginOperation -> user type parameter mismatch"); 
                    }
                }else{
                        result = Result.FAILURE_PARAM_MISMATCH;
                }

            return result;
        }
    
        //**************************************************************************
        //**************************************************************************
        //**                        GET USER ADDRESS LIST
        //**************************************************************************
        //**************************************************************************
        /**
         * 
         * @param request
         * @return 
         */
        public static Result getUserAddressList(HttpServletRequest request){
                
                Result result = Result.FAILURE_PROCESS;
                
                String userId = request.getParameter("userId");
                if( !Checker.anyNull(userId) ){
                    result = DBUser.getUserAddressList(userId);
                }else{
                    result = Result.FAILURE_PARAM_MISMATCH;
                }
            
            return result;
        }
    
    // </editor-fold>
    
        
        
    //------------------------------------------------------------------------------
    //------------------------------------------------------------------------------
    //--                            INSERT OPERATIONs
    //------------------------------------------------------------------------------
    //------------------------------------------------------------------------------
    
    // <editor-fold defaultstate="collapsed" desc="INSERT Operations">
        
        //**************************************************************************
        //**************************************************************************
        //**                        REGISTER
        //**************************************************************************
        //**************************************************************************
        public static Result userRegister(HttpServletRequest request){
        
                Result result = Result.FAILURE_PROCESS.setContent("Auth_Controller -> callRegisterOperation -> INITIAL ERROR");

                String mail = request.getParameter("mail");
                String pass = request.getParameter("pass");
                String passConfirm = request.getParameter("passconfirm");
                String name = request.getParameter("name");
                String surname = request.getParameter("surname");
                String phone = request.getParameter("phone");
                String type = request.getParameter("registerType");

                if( !Checker.anyNull( mail, pass, passConfirm, name, surname, phone, type)){

                    try {    
                        // -1- Check mail address vaild or not
                            InternetAddress emailAddr = new InternetAddress(mail);
                            emailAddr.validate();

                        // -2- Check password is valid
                            if(!(pass.equalsIgnoreCase(passConfirm))){                     
                                return Result.FAILURE_PARAM_INVALID.setContent("Mismatch password failure");
                            }

                        // -3- is phone number valid

                        // -4- Call function
                            if(type.equalsIgnoreCase("admin")){
                                
                                // -4.1- is TCKN valid
                                    MarketUser marketUser = new MarketUser();
                                    marketUser.setUserMail(mail);
                                    marketUser.setUserPassword(pass);
                                    marketUser.setUserName(name);
                                    marketUser.setUserSurname(surname);
                                    marketUser.setUserPhoneNumber(phone);
                                    marketUser.setIdentityNo(request.getParameter("idNo"));
                                    marketUser.setIdentityType(request.getParameter("idType"));
                                    marketUser.setRole(request.getParameter("role"));
                                    result = DBUser.adminRegister(marketUser);
                                    
                            }else if(type.equalsIgnoreCase("customer")){
                                    result = DBUser.userRegister( mail, pass, name, surname, phone);
                            }else{
                                    result = Result.FAILURE_PARAM_MISMATCH.setContent("ControllerUser -> userRegister -> user type parameter mismatch"); 
                            }
                            

                    } catch (AddressException ex) {                    
                        Logger.getLogger(Auth.class.getName()).log(Level.SEVERE, null, ex);
                        return Result.FAILURE_PARAM_INVALID.setContent("Invalid mail");
                    } catch (Exception ex) {
                        Logger.getLogger(ControllerUser.class.getName()).log(Level.SEVERE, null, ex);
                        return Result.FAILURE_PROCESS.setContent(ex.getMessage());
                    }

                }else{
                    result = Result.FAILURE_PARAM_MISMATCH;
                }

            return result;
        }
        
        
        
        //**************************************************************************
        //**************************************************************************
        //**                        ADD ADDRESS TO USER
        //**************************************************************************
        //**************************************************************************
        /**
         * 
         * @param request
         * @return 
         */
        public static Result addUserAddress(HttpServletRequest request){
                
                Result result = Result.FAILURE_PROCESS;
                
                String userId = request.getParameter("userId");
                String addressId = request.getParameter("addressId");
                if( !Checker.anyNull(userId,addressId) ){
                    
                    try {

                    // -1.1- Create Product Object
                        UserAddress userAddress = new UserAddress();
                        userAddress.setId("test"+Util.generateID());
                        userAddress.setUserID(userId);
                        userAddress.setAddressID(addressId);
                        userAddress.setStreet(request.getParameter("street"));
                        userAddress.setAvenue(request.getParameter("avenue"));
                        userAddress.setDescription(request.getParameter("description"));                        
                                                
                        if(Checker.allNull(userAddress.getStreet(), userAddress.getAvenue(), userAddress.getDescription()) || 
                                Checker.allStringSizeZero( userAddress.getStreet(), userAddress.getAvenue(), userAddress.getDescription() )){
                            return Result.FAILURE_PARAM_MISMATCH.setContent("ControllerUser -> adduserAddress -> at least one address parameter should be filled");
                        }
                        
                        return DBUser.addUserAddress(userAddress);
                        
                    } catch (Exception ex) {
                        Logger.getLogger(ControllerProduct.class.getName()).log(Level.SEVERE, null, ex);
                        return Result.FAILURE_PROCESS.setContent(ex.toString());
                    }
                    
                }else{
                    result = Result.FAILURE_PARAM_MISMATCH;
                }
            
            return result;
        }
        
    // </editor-fold>
     
}
