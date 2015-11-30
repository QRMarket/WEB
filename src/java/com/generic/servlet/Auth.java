/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.generic.servlet;

import com.generic.controller.ControllerUser;
import com.generic.result.Result;
import com.generic.util.Util;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

    
    //**************************************************************
    //**************************************************************
        private static enum ServletOperations{
            NULL,
            USER_LOGIN,
            USER_REGISTER,
            GET_ADDRESS_LIST,
            ADD_ADDRESS
        }        

        private ServletOperations getRequestOperation(HttpServletRequest request){
            if(request.getParameter("do") != null){
                switch (request.getParameter("do")) {
                    case "login":
                        return ServletOperations.USER_LOGIN;
                    case "register":
                        return ServletOperations.USER_REGISTER;
                    case "getAddressList":
                        return ServletOperations.GET_ADDRESS_LIST;
                    case "addAddress":
                        return ServletOperations.ADD_ADDRESS;
                    default:
                        return ServletOperations.NULL;
                }
            }  
            return ServletOperations.NULL;
        }
        
        
        
        
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
        
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();              
        Gson gson = new Gson();
        Result res = Result.FAILURE_PROCESS.setContent("Auth_Servlet >> INITIAL ERROR");        
                  
        try{     
            switch (Util.getContentType(request)){
                    
                    //**************************************************************
                    //**************************************************************
                    //**        Content-Type :: application/x-www-form-urlencoded
                    //**************************************************************
                    //**************************************************************
                    case APPLICATION_FORM_URLENCODED:  
                        
                            switch (getRequestOperation(request)){

                                case USER_LOGIN:
                                        res = ControllerUser.callLoginOperation(request);
                                    break;

                                case USER_REGISTER:
                                        res = ControllerUser.userRegister(request);
                                    break;

                                case GET_ADDRESS_LIST:
                                        res = ControllerUser.getUserAddressList(request);
                                    break;

                                case ADD_ADDRESS:
                                        res = ControllerUser.addUserAddress(request);
                                    break;

                                default:
                                        res = Result.FAILURE_PARAM_MISMATCH.setContent("Auth Servlet -> Default Content-Type -> Default Case");
                                    break;
                            }
                            
                        break;
                    
                    
                    //**************************************************************
                    //**************************************************************
                    //**        Content-Type :: default case
                    //**************************************************************
                    //**************************************************************
                    default:
                            res = Result.FAILURE_PARAM_MISMATCH.setContent("AuthServlet -> Default Content-Type Error");
                        break;
                                       
            }
         
        }catch (Exception ex){
            res = Result.FAILURE_PROCESS.setContent(ex.getMessage());
            
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
