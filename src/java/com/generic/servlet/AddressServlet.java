/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.generic.servlet;

import com.generic.checker.Checker;
import com.generic.db.DBAddress;
import com.generic.db.MysqlDBOperations;
import com.generic.resources.ResourceMysql;
import com.generic.resources.ResourceProperty;
import com.generic.result.Result;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
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
         * cdasDO   :: getCityList
         *
         *          :: getBoroughList
         *              --> cdaCity
         * 
         *          :: getLocalityList
         *              --> cdaCity
         *              --> cdaBorough
         * 
         *
         * 
         * !! SHORTCUTs !!
         * carpe diem address           --> cdm
         * carpe diem address servlet   --> cdms        
         *
         */
        HttpSession session = request.getSession(false);
        Gson gson = new Gson();
        MysqlDBOperations mysql = new MysqlDBOperations();
        ResourceProperty resource = new ResourceProperty("com.generic.resources.mysqlQuery");
        Result res = Result.FAILURE_PROCESS;
        Map resultMap = new HashMap();
        
        
        
        //verbose(request);
        
        
        
        //**********************************************************************
        //**********************************************************************
        //**                 STRIKE UP SERVLET OPERATION
        //**********************************************************************
        //**********************************************************************
        try {
            
            
                        
            if(request.getParameter("cdasDO")!=null){                 
                switch(request.getParameter("cdasDO")){ 
                    
                //**************************************************************
                //**************************************************************
                //**                    ADD TO ADDRESS CASE
                //**************************************************************
                //**************************************************************
                    case "addAddress":
                        
                            

                        break;
                        
                        
                //**************************************************************
                //**************************************************************
                //**                GET ADRESS-CITY CASE
                //**************************************************************
                //**************************************************************
                    case "getCityList":                                                        
                                                        
                            res = DBAddress.selectDistict( ResourceMysql.TABLE_ADDRESS , "city");
                            
                        break;
                        
                
                //**************************************************************
                //**************************************************************
                //**                GET ADRESS-BOROUGH CASE
                //**************************************************************
                //**************************************************************
                    case "getBoroughList":                                                        
                            
                            if(!Checker.anyNull(request.getParameter("cdaCity"))){
                                res = DBAddress.getBoroughList(request.getParameter("cdaCity"));
                            }else{
                                res = Result.FAILURE_PARAM_MISMATCH;
                            }
                                                        
                        break;
                        
                
                //**************************************************************
                //**************************************************************
                //**                GET ADRESS-LOCALITY CASE
                //**************************************************************
                //**************************************************************
                    case "getLocalityList":
                                                        
                            if(!Checker.anyNull(request.getParameter("cdaCity") , request.getParameter("cdaBorough"))){
                                res = DBAddress.getLocalityList(request.getParameter("cdaCity") , request.getParameter("cdaBorough"));
                            }else{
                                res = Result.FAILURE_PARAM_MISMATCH;
                            }
                            
                        break;
                        
                        
                //**************************************************************
                //**************************************************************
                //**                REMOVE ADDRESS CASE
                //**************************************************************
                //**************************************************************
                    case "deleteAdress":
                                                                            
                            
                            
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