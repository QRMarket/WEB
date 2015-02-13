/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.generic.servlet;

import com.generic.checker.Checker;
import com.generic.db.MysqlDBOperations;
import com.generic.resources.ResourceProperty;
import com.generic.result.Result;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.UUID;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Kemal Sami KARACA
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
        MysqlDBOperations mysql = new MysqlDBOperations();
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
        Enumeration<String> enume = request.getParameterNames();
        while(enume.hasMoreElements()){
            System.out.println("Incoming parameters :: " + enume.nextElement());
        }
                
        try{                                    
            
            if(request.getParameter("authDo")!=null){                 
                switch(request.getParameter("authDo")){ 
                    
                //**************************************************************
                //**************************************************************
                //**                    LOGIN CASE
                //**************************************************************
                //**************************************************************
                    case "carpeLogin": 

                            if(request.getParameter("cduMail")!=null && request.getParameter("cduPass")!=null){

                                    System.out.println("--> /Auth?authDo=carpeLogin&cduMail="+request.getParameter("cduMail")+"&cduPass="+request.getParameter("cduPass"));                                    
                                    String query  = String.format( resource.getPropertyValue("selectUserFromEmail") , request.getParameter("cduMail"), request.getParameter("cduPass"));

                                    /**
                                     * If resultSet is empty -> user OR password is wrong
                                     * else if resultSet size is equals 1 -> user match 
                                     * else resultSet size > 1 -> multiple row returned
                                     */
                                    ResultSet mysqlResult = mysql.getResultSet(query);
                                    if(mysql.resultSetIsEmpty()){       

                                        res = Result.FAILURE_AUTH_WRONG;
                                        
                                    }else if(mysql.getResultSetSize()==1){                                                  
                                        
                                        session = request.getSession(true);
                                        session.setAttribute("cduToken", UUID.randomUUID().toString());
                                        session.setAttribute("cduName", request.getParameter("cduMail"));
                                        res = Result.SUCCESS.setContent(session.getAttribute("cduToken"));
                                        
                                    }else{

                                        res = Result.FAILURE_AUTH_MULTIPLE;
                                    }                                    

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
                        break;
                        
                        
                //**************************************************************
                //**************************************************************
                //**                    REGISTER CASE
                //**************************************************************
                //**************************************************************
                    case "carpeRegister":
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
            
            
        }finally{                        
                                                
            mysql.closeAllConnection();
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