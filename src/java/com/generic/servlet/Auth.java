/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.generic.servlet;


import com.generic.checker.Checker;
import com.generic.db.DBUser;
import com.generic.db.MysqlDBOperations;
import com.generic.logger.LoggerGuppy;
import com.generic.resources.ResourceProperty;
import com.generic.result.Result;
import com.generic.util.Address;
import com.generic.util.Guppy;
import com.generic.util.MarketUser;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Kemal Sami KARACA
 * @since 02.2015
 * @version 1.01
 * 
 * @last 12.03.2015
 */
@WebServlet(name = "Auth", urlPatterns = {"/Auth"})
public class Auth extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");        
        PrintWriter out = response.getWriter();        
        
        /**
         * authDo   :: carpeLogin
         *              :: cduMail
         *              :: cduPass
         *          :: carpeLogout
         *          :: carpeRegister          
         *
         * 
         * !! KISALTMALAR !!
         * carpe diem user --> cdu          
         *
         */
        HttpSession session = request.getSession(false);
        Gson gson = new Gson();        
        ResourceProperty resource = new ResourceProperty("com.generic.resources.mysqlQuery");
        Result res = Result.SUCCESS_EMPTY;               
                 
        
        /**
         *  NOT ::  Kullanıcı her "Login Case" e girdiklerinde kullanıcı adı ve şifresi
         *          doğru verilmesi durumunda yeni bir token yaratmaktadır.Bu işlem 
         *          araştırılacak(yararları ve zararları)
         * 
         *  Öncelikle kullanıcı authentication sağlanıp sağlanmadığına bakılır
         *      İlk olarak kullanıcının session'ı var mı diye bakılır
         *          Yoksa kullanıcı auth değildir
         *          Varsa sessiondan alınan token ile kullanıcıdan gelen token karşılaştırılır
         *              Eğer kullanıcı auth ise kullanıcı bilgileri yüklenecek sayfana yönlendirilir
         *      
         *              Eğer kullanıcı auth değilse bu durumda aşağıdaki işlemler yapılır 
         * 
         *  
         */
                
        LoggerGuppy.verboseURL(request);
        LoggerGuppy.verboseHeader(request);
        
        
        try{                                    
            
            if(request.getParameter("authDo")!=null){                 
                switch(request.getParameter("authDo")){ 
                    
                //**************************************************************
                //**************************************************************
                //**                    LOGIN CASE
                //**************************************************************
                //**************************************************************
                    case "carpeLogin":                                                     
                        
                            if( !Checker.anyNull(request.getParameter("cduMail"),request.getParameter("cduPass")) ){                                   
                                    res = DBUser.opLogin(request, response, session, request.getParameter("cduMail"), request.getParameter("cduPass"));                                
                            }else{
                                    res = Result.FAILURE_PARAM_MISMATCH;
                            }

                        break;
                        
                //**************************************************************
                //**************************************************************
                //**                    LOGOUT CASE
                //**************************************************************
                //**************************************************************
                    case "carpeLogout":
                        
                        session.invalidate();
                        res = Result.SUCCESS_LOGOUT; 
                                                
                        break;
                        
                        
                //**************************************************************
                //**************************************************************
                //**                    REGISTER CASE
                //**************************************************************
                //**************************************************************
                    case "carpeRegister":
                                                   
                            // Check parameter exist
                            if(!Checker.anyNull(request.getParameter("cduMail"), request.getParameter("cduPass"), request.getParameter("cduPassConfirm") )){                                
                                    res = DBUser.insertUser(
                                            request.getParameter("cduMail"), 
                                            request.getParameter("cduPass"), 
                                            request.getParameter("cduPassConfirm"), 
                                            request.getParameter("cduname"), 
                                            request.getParameter("cdusurname"), 
                                            request.getParameter("cduphone"));
                            }else{
                                    res = Result.FAILURE_PARAM_MISMATCH;
                            }
                        
                        break;
                              
                        
                //**************************************************************
                //**************************************************************
                //**                    DEFAULT CASE
                //**************************************************************
                //**************************************************************
                    default:
                        res = Result.FAILURE_PARAM_WRONG;
                        break;
                }

            }else{
                res = Result.FAILURE_PARAM_MISMATCH;
            }
            
            
        } finally{                        

            out.write(gson.toJson(res));
            out.close();
            
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
