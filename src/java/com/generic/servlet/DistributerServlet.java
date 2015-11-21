/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.generic.servlet;

import com.generic.controller.ControllerAddress;
import com.generic.controller.ControllerDistributer;
import com.generic.result.Result;
import com.generic.util.Util;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Guppy Org.
 * @since 03.2015
 * @version 1.01
 * 
 * @last 21.11.2015
 */
@WebServlet(name = "DistributerServlet", urlPatterns = {"/DistributerServlet"})
public class DistributerServlet extends HttpServlet {

    
    private static enum ProductServletOperations{
        NULL,
        GET_DISTRIBUTER_LIST,
        INSERT_COMPANY,
        INSERT_DISTRIBUTER,
        GET_DISTRIBUTER_LIST_BY_ADDRESS,
        GET_DISTRIBUTER_LIST_BY_COMPANY,
        REMOVE_COMPANY,
        REMOVE_DISTRIBUTER,
    }        
    
    private ProductServletOperations getRequestOperation(HttpServletRequest request){
        
        if(request.getParameter("do") != null){
            switch (request.getParameter("do")) {
                case "getDistributerList":
                    return ProductServletOperations.GET_DISTRIBUTER_LIST; 
            }
        }  
        
        return ProductServletOperations.NULL;
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
                        
            switch (Util.getContentType(request)){                
                    
                //**************************************************************
                //**************************************************************
                //**        Content-Type :: multipart/form-data
                //**************************************************************
                //**************************************************************
                case MULTIPART_FORM_DATA:                                            
                                                
                        switch (getRequestOperation(request)){
                                                        
                            case INSERT_COMPANY:                                             
                                    //res = ControllerCompany.insertCompany(request);
                                    res = Result.SUCCESS_EMPTY.setContent("DistributerServlet -> MULTIPART -> insert company");
                                break;
                                
                            
                            case INSERT_DISTRIBUTER:                                             
                                    //res = ControllerCompany.insertDistributer(request);
                                    res = Result.SUCCESS_EMPTY.setContent("DistributerServlet -> MULTIPART -> insert distributer");
                                break;
                                
                                
                            case NULL:
                                    res = Result.FAILURE_PARAM_MISMATCH.setContent("DistributerServlet -> MULTIPART -> NULL");
                                break;
                                
                                
                            default:
                                    res = Result.FAILURE_PROCESS.setContent("Unexpected Error On ProductServlet>MULTIPART_FORM_DATA>default case");
                                break;
                        }
                        
                    break;
                    
                    
                    
                    
                    
                //**************************************************************
                //**************************************************************
                //**        Content-Type :: application/x-www-form-urlencoded
                //**************************************************************
                //**************************************************************    
                case APPLICATION_FORM_URLENCODED:            
            
                        switch (getRequestOperation(request)){

                            case GET_DISTRIBUTER_LIST:
                                    res = ControllerDistributer.getDistributerList(request);
                                break;


                            case GET_DISTRIBUTER_LIST_BY_COMPANY:
                                    //ControllerCompany.getDistributerListByCompany(request);
                                break;

                            case REMOVE_COMPANY:
                                    //ControllerCompany.getDistributerListByCompany(request);
                                break;

                                
                            case REMOVE_DISTRIBUTER:
                                break;

                                
                            case NULL:
                                    res = Result.FAILURE_PARAM_MISMATCH;
                                break;
                                
                            
                            default:
                                    res = Result.FAILURE_PROCESS.setContent("Unexpected Error On ProductServlet>APPLICATION_FORM_URLENCODED>default case");
                                break;
                            }

                    break;
                    
                    
                //**************************************************************
                //**************************************************************
                //**        Content-Type :: EXCEPTION
                //**************************************************************
                //**************************************************************    
                default:
                        res = Result.FAILURE_PROCESS.setContent("CompanyServlet -> Unknown content-type");
                    break;
                
                
                
            } // content-type switch end
                
            
            
        } catch (Exception ex) {
            Logger.getLogger(ProductServlet.class.getName()).log(Level.SEVERE, null, ex);
        } finally {

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
