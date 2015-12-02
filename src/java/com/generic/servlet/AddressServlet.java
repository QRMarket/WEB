/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.generic.servlet;

import com.generic.checker.Checker;
import com.generic.controller.ControllerAddress;
import com.generic.modal.DBAddress;
import com.generic.db.MysqlDBOperations;
import com.generic.logger.LoggerGuppy;
import com.generic.resources.ResourceMysql;
import com.generic.resources.ResourceProperty;
import com.generic.result.Result;
import com.generic.util.Util;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Kemal Sami KARACA
 * @since 10.03.2015
 * @version 1.01
 * 
 * @last 10.03.2015
 */
@WebServlet(name = "AddressServlet", urlPatterns = {"/AddressServlet"})
public class AddressServlet extends HttpServlet {

    
    private static enum ServletOperations{
        NULL,
        GET_ADDRESS,
        SEARCH_ADDRESS,
        GET_CITY_LIST,
        GET_BOROUGH_LIST,
        GET_LOCALITY_LIST
    }        
    
    private ServletOperations getRequestOperation(HttpServletRequest request){
        
        if(request.getParameter("do") != null){
            
            switch (request.getParameter("do")) {
                case "getAddressById":
                    return ServletOperations.GET_ADDRESS; 
                case "searchAddress":
                    return ServletOperations.SEARCH_ADDRESS; 
                case "getCityList":
                    return ServletOperations.GET_CITY_LIST;  
                case "getBoroughList":
                    return ServletOperations.GET_BOROUGH_LIST;  
                case "getLocalityList":
                    return ServletOperations.GET_LOCALITY_LIST;
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
        Result res = Result.FAILURE_PROCESS;
        
        try {
                
            switch (Util.getContentType(request)) {   
                        
                    //**************************************************************
                    //**************************************************************
                    //**        Content-Type :: application/x-www-form-urlencoded
                    //**************************************************************
                    //**************************************************************
                    case APPLICATION_FORM_URLENCODED:  

                            switch (getRequestOperation(request)){

                                case GET_ADDRESS:
                                        res = ControllerAddress.getAddressById(request);
                                    break;

                                case SEARCH_ADDRESS:
                                        res = ControllerAddress.searchAddress(request);
                                    break;

                                case GET_CITY_LIST:
                                        res = ControllerAddress.getCityList();
                                    break;
                                    
                                case GET_BOROUGH_LIST:
                                        res = ControllerAddress.getBoroughList(request);
                                    break;
                                    
                                case GET_LOCALITY_LIST:
                                        res = ControllerAddress.getLocalityList(request);
                                    break;
                                    
                                default:
                                        res = Result.FAILURE_PARAM_MISMATCH.setContent("Unexpected Error On AddressServlet>APPLICATION_FORM_URLENCODED>default case");
                                    break;
                            }

                        break;
                    
                    //**************************************************************
                    //**************************************************************
                    //**        Content-Type :: Default content-type
                    //**************************************************************
                    //**************************************************************
                    default:
                            res = Result.FAILURE_PARAM_MISMATCH.setContent("AddressServlet -> Default Content-Type Error");
                        break;
                    
                
            }
                    
            
            
            
//            if(request.getParameter("cdasDo")!=null){                 
//                switch(request.getParameter("cdasDo")){ 
//                    
//                //**************************************************************
//                //**************************************************************
//                //**                    ADD TO ADDRESS CASE
//                //**************************************************************
//                //**************************************************************
//                    case "addAddress":                       
//                            res = DBAddress.addAddress(request.getParameter("city"), request.getParameter("borough"), request.getParameter("locality"));
//                        break;
//                        
//                //**************************************************************
//                //**************************************************************
//                //**                REMOVE ADDRESS CASE
//                //**************************************************************
//                //**************************************************************
//                    case "deleteAddress":
//                            res = DBAddress.deleteAddress(request.getParameter("cdaID"));
//                        break;
//                        
//                //**************************************************************
//                //**************************************************************
//                //**                    DEFAULT CASE
//                //**************************************************************
//                //**************************************************************
//                    default:
//                        res = Result.FAILURE_PARAM_WRONG;
//                        break;
//                }
//
//            }else{
//                res = Result.FAILURE_PARAM_MISMATCH;
//            }
         
            
            
        }finally{
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
