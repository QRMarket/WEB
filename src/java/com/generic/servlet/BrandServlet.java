/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.generic.servlet;

import com.generic.controller.ControllerBrand;
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
 * @author kemalsamikaraca
 */

@WebServlet(name = "BrandServlet", urlPatterns = {"/BrandServlet"})
public class BrandServlet extends HttpServlet {

    private static enum ServletOperations{
        NULL,
        GET_BRAND_LIST,
        UPDATE_BRAND,
        REMOVE_BRAND
    }        
    
    private ServletOperations getRequestOperation(HttpServletRequest request){
        
        if(request.getParameter("do") != null){
            
            switch (request.getParameter("do")) {
                case "getBrandList":
                    return ServletOperations.GET_BRAND_LIST; 
                case "updateBrand":
                    return ServletOperations.UPDATE_BRAND; 
                case "removeBrand":
                    return ServletOperations.REMOVE_BRAND;              
            }
        }  
        
        return ServletOperations.NULL;
    }
    
    
    /**
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
                //**        Content-Type :: multipart/form-data
                //**************************************************************
                //**************************************************************
                case MULTIPART_FORM_DATA:

                        switch (getRequestOperation(request)) {

                            //--------------------------------------------------
                            //-- ---            DEFAULT CASE              --- --
                            //--------------------------------------------------  
                            default:
                                res = Result.FAILURE_PROCESS.setContent("Unexpected Error On SectionServlet>MULTIPART_FORM_DATA>default case");
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

                            
                            case GET_BRAND_LIST:
                                    res = ControllerBrand.getBrandByID(request);
                                break;


                            case UPDATE_BRAND:
                                    res = Result.SUCCESS_EMPTY.setContent("This Servlet On Progress > UPDATE_BRAND");
                                break;


                            case REMOVE_BRAND:
                                    res = Result.SUCCESS_EMPTY.setContent("This Servlet On Progress > REMOVE_BRAND");
                                break;

                                
                            default:
                                    res = Result.FAILURE_PARAM_MISMATCH.setContent("Unexpected Error On BrandServlet>APPLICATION_FORM_URLENCODED>default case");
                                break;
                        }

                    break;
                
                    
                //**************************************************************
                //**************************************************************
                //**        Content-Type :: Default Content-Type
                //**************************************************************
                //**************************************************************    
                default:
                        res = Result.FAILURE_PROCESS.setContent("Unexpected Error On BrandServlet>Main Content-Type Switch>default case");
                    break;
            }
            
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
