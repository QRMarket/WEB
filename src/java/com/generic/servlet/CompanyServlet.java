/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.generic.servlet;

import com.generic.checker.Checker;
import com.generic.controller.ControllerCompany;
import com.generic.controller.ControllerProduct;
import com.generic.db.DBAddress;
import com.generic.db.DBCompany;
import com.generic.db.DBProduct;
import com.generic.db.MysqlDBOperations;
import com.generic.resources.ResourceMysql;
import com.generic.resources.ResourceProperty;
import com.generic.result.Result;
import com.generic.util.Util;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author kemal
 */
@WebServlet(name = "CompanyServlet", urlPatterns = {"/CompanyServlet"})
public class CompanyServlet extends HttpServlet {

    
    private static enum ProductServletOperations{
        NULL,
        INSERT_COMPANY,
        INSERT_DISTRIBUTER,
        GET_COMPANY_LIST,
        GET_DISTRIBUTER_LIST_BY_ADDRESS,
        GET_DISTRIBUTER_LIST_BY_COMPANY,
        REMOVE_COMPANY,
        REMOVE_DISTRIBUTER,
    }        
    
    private ProductServletOperations getRequestOperation(HttpServletRequest request){
        
        if(request.getParameter("do") != null){
            
            switch (request.getParameter("do")) {
                case "addCompany":
                    return ProductServletOperations.INSERT_COMPANY; 
                case "addDistributer":
                    return ProductServletOperations.INSERT_DISTRIBUTER; 
                case "getCompanyList":
                    return ProductServletOperations.GET_COMPANY_LIST; 
                case "getDistributerListByAdrress":
                    return ProductServletOperations.GET_DISTRIBUTER_LIST_BY_ADDRESS; 
                case "getDistributerListByCompany":
                    return ProductServletOperations.GET_DISTRIBUTER_LIST_BY_COMPANY;              
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
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        
        /**
         * cdcsDO   :: getCompanyList
         *
         * 
         * !! SHORTCUTs !!
         * carpe diem company           --> cdm
         * carpe diem company servlet   --> cdms        
         *
         */
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
                                                        
                            //--------------------------------------------------
                            //-- ---           INSERT COMPANY             --- --
                            //--------------------------------------------------
                            case INSERT_COMPANY:                                             
                                    //res = ControllerCompany.insertCompany(request);
                                break;
                                
                                
                                                            
                            //--------------------------------------------------
                            //-- ---           INSERT DISTRIBUTER             --- --
                            //--------------------------------------------------
                            case INSERT_DISTRIBUTER:                                             
                                    //res = ControllerCompany.insertDistributer(request);
                                break;
                                
                                
                            //--------------------------------------------------
                            //-- ---     "do" PARAMETER EXCEPTION         --- --
                            //--------------------------------------------------  
                            case NULL:
                                    res = Result.FAILURE_PARAM_MISMATCH;
                                break;
                                
                                
                                
                            //--------------------------------------------------
                            //-- ---            DEFAULT CASE              --- --
                            //--------------------------------------------------  
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

                            //**************************************************************
                            //**************************************************************
                            //**                    GET COMPANY LIST CASE
                            //**************************************************************
                            //**************************************************************
                                case GET_COMPANY_LIST:

                                    //ControllerCompany.getCompanyList(request);
                                break;

                            //**************************************************************
                            //**************************************************************
                            //**                GET DISTRIBUTER LIST BY ADDRESS CASE
                            //**************************************************************
                            //**************************************************************
                                case GET_DISTRIBUTER_LIST_BY_ADDRESS:

                                    //ControllerCompany.getDistributerListByAddress(request);
                                break;


                            //**************************************************************
                            //**************************************************************
                            //**                    GET DISTRIBUTER LIST BY COMPANY CASE
                            //**************************************************************
                            //**************************************************************
                                case GET_DISTRIBUTER_LIST_BY_COMPANY:
                                    //ControllerCompany.getDistributerListByCompany(request);
                                break;

                            //**************************************************************
                            //**************************************************************
                            //**                    REMOVE CASE
                            //**************************************************************
                            //**************************************************************
                                case REMOVE_COMPANY:
                                    break;

                                
                                //**************************************************************
                            //**************************************************************
                            //**                    REMOVE CASE
                            //**************************************************************
                            //**************************************************************
                                case REMOVE_DISTRIBUTER:
                                    break;

                                
                            //--------------------------------------------------
                            //-- ---     "do" PARAMETER EXCEPTION         --- --
                            //--------------------------------------------------  
                            case NULL:
                                    res = Result.FAILURE_PARAM_MISMATCH;
                                break;
                                
                                
                                
                            //--------------------------------------------------
                            //-- ---            DEFAULT CASE              --- --
                            //--------------------------------------------------  
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
                    
                    break;
                
                
                
            } // content-type switch end
                
            
            
        } catch (Exception ex) {
            Logger.getLogger(ProductServlet.class.getName()).log(Level.SEVERE, null, ex);
        } finally {

            out.write(gson.toJson(res));
            out.close();

        }       
        
    }
    
    
    /**
     * 
     * @param request 
     */
    public void verbose(HttpServletRequest request){
        Enumeration<String> enume = request.getParameterNames();
        while(enume.hasMoreElements()){
            String key = enume.nextElement();
            System.out.println("Incoming parameter  --> " + key + " :: " + request.getParameter(key));
        }
            System.out.println("URL                 --> " + request.getScheme() + "://" + 
                                                            request.getServerName() +":"+
                                                            request.getServerPort() +
                                                            request.getRequestURI() + 
                                                            (request.getQueryString()==null?"":"?" + request.getQueryString()) );
            
        Enumeration<String> enumeHeader = request.getHeaderNames();
        while(enumeHeader.hasMoreElements()){
            String headerKey = enumeHeader.nextElement();            
            System.out.println( "HEADER -- " + headerKey + " :: " + request.getHeader(headerKey));
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
