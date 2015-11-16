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
    
    
    public static Result callLoginOperation(HttpServletRequest request){
        
            Result result = Result.FAILURE_PROCESS.setContent("Auth_Controller -> callLoginOperation -> INITIAL ERROR");
            String mail = request.getParameter("mail");
            String pass = request.getParameter("pass");
                        
            if( !Checker.anyNull(mail,pass) ){
                    result = DBUser.userLogin(mail, pass);
            }else{
                    result = Result.FAILURE_PARAM_MISMATCH;
            }
        
        return result;
    }
    
    
    public static Result callRegisterOperation(HttpServletRequest request){
        
            Result result = Result.FAILURE_PROCESS.setContent("Auth_Controller -> callRegisterOperation -> INITIAL ERROR");
            
            String mail = request.getParameter("mail");
            String pass = request.getParameter("pass");
            String passConfirm = request.getParameter("passconfirm");
            String name = request.getParameter("name");
            String surname = request.getParameter("surname");
            String phone = request.getParameter("phone");
            
            if( !Checker.anyNull( mail, pass, passConfirm, name, surname, phone )){
                 
                try {    
                    // -1- Check mail address vaild or not
                        InternetAddress emailAddr = new InternetAddress(mail);
                        emailAddr.validate();
                    
                    // -2- Check password is valid
                        if(!(pass.equalsIgnoreCase(passConfirm))){                     
                            return Result.FAILURE_PARAM_INVALID.setContent("Mismatch password failure");
                        }
                    
                    // -3- Check number is valid
                    
                    // -4- Call function
                        result = DBUser.userRegister( mail, pass, name, surname, phone);
                    
                } catch (AddressException ex) {                    
                    Logger.getLogger(Auth.class.getName()).log(Level.SEVERE, null, ex);
                    return Result.FAILURE_PARAM_INVALID.setContent("Invalid mail");
                }
                
            }else{
                result = Result.FAILURE_PARAM_MISMATCH;
            }
                          
        return result;
    }
           
    
    public static Result callLogoutOperation(HttpServletRequest request){
            
            Result result = Result.FAILURE_PROCESS.setContent("Auth_Controller -> callLogoutOperation -> INITIAL ERROR");
            
            if(!Checker.anyNull(request.getParameter("token"))){
            
                // Check User Permission
                String sessionId = request.getParameter("token");
                ServletContext sc = request.getServletContext();
                HttpSession session = (HttpSession)sc.getAttribute(sessionId);

                if(session==null){
                    return Result.FAILURE_CACHE.setContent("CACHE NOT FOUND ERROR");
                }else{
                    sc.removeAttribute(sessionId);
                    session.invalidate();
                    return Result.SUCCESS;
                }

            }else{
                return Result.FAILURE_PARAM_MISMATCH.setContent("ControllerProduct -> getProductList");
            }
    }
    
}
