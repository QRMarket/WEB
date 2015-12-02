/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.generic.servlet;

import com.generic.controller.ControllerCampaignProduct;
import com.generic.result.Result;
import com.generic.util.Util;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Kemal Sami KARACA
 * @since 02.12.2015
 * @version 1.01
 * 
 * @last 02.12.2015
 */

@WebServlet(name = "CampaignProductServlet", urlPatterns = {"/CampaignProductServlet"})
public class CampaignProductServlet extends HttpServlet {

    private static enum ServletOperations{
        NULL,
        GET_CAMPAIGN_PRODUCT,
        GET_CAMPAIGN_PRODUCT_LIST,
    }
    
    private ServletOperations getRequestOperation(HttpServletRequest request){
        if(request.getParameter("do") != null){
            switch (request.getParameter("do")) {
                case "getCampaignProduct":
                    return ServletOperations.GET_CAMPAIGN_PRODUCT;
                case "getCampaignProductList":
                    return ServletOperations.GET_CAMPAIGN_PRODUCT_LIST;
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
            Result res = Result.FAILURE_PROCESS.setContent("ProductServlet -> initial error");
            
            try {

                switch (Util.getContentType(request)) {

                   
                        
                    //**************************************************************
                    //**************************************************************
                    //**        Content-Type :: application/json
                    //**************************************************************
                    //**************************************************************
                    case APPLICATION_FORM_URLENCODED:
                            
                            switch (getRequestOperation(request)){

                        
                                    case GET_CAMPAIGN_PRODUCT:
                                            res = Result.SUCCESS_EMPTY.setContent("aa");
                                        break;
                                    
                                    case GET_CAMPAIGN_PRODUCT_LIST:
                                            res = ControllerCampaignProduct.getCampaignProductList(request);
                                        break;
                                        
                                    default:
                                            res = Result.FAILURE_PARAM_MISMATCH.setContent("CampaignProductServlet -> APPLICATION_FORM_URLENCODED -> Default Case");
                                        break;
                                    }
                            
                        break;
                        
                        
                        
                        
                        
                    //**************************************************************
                    //**************************************************************
                    //**        Content-Type :: Default
                    //**************************************************************
                    //**************************************************************
                    default: 
                            res = Result.FAILURE_PROCESS.setContent("CampaignProductServlet -> Default Content-Type -> This content-type not used for that service");
                        break;

                }

            } catch (Exception ex) {
                Logger.getLogger(ProductServlet.class.getName()).log(Level.SEVERE, null, ex);
                res = Result.FAILURE_PROCESS.setContent(ex.getMessage());
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
