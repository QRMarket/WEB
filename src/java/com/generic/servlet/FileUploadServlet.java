/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.generic.servlet;

import com.generic.result.Result;
import com.generic.util.Util;
import com.google.gson.Gson;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Iterator;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import org.apache.commons.io.IOUtils;

/**
 *
 * @author kemalsamikaraca
 * @since 08.09.2015
 * @version 1.01
 * 
 * @last 08.09.2015
 * 
 * @description
 *      Bu class File upload etmeyi ve sonucunda da url döndürmeyi sağlamaktadır.
 * 
 */

@MultipartConfig
@WebServlet(name = "FileUploadServlet", urlPatterns = {"/FileUploadServlet"})
public class FileUploadServlet extends HttpServlet {

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
                                
        /**
         * cdfsDo            
         *          :: addFile
         *              :: cdFile
         * 
         * !! KISALTMALAR !!
         * carpe diem file servlet      --> cdfs
         * 
         */
        PrintWriter out = response.getWriter();
        OutputStream outputStream = null;
        InputStream inputStream = null;
        
        HttpSession session = request.getSession(false);
        Gson gson = new Gson();        
        Result res = Result.FAILURE_PROCESS.setContent("initial"); 
        
        
        
        try{
                        
            switch(Util.getContentType(request)){ 
                    
                //**************************************************************
                //**************************************************************
                //**            CONTENT-TYPE MULTIPART CASE
                //**************************************************************
                //**************************************************************
                    case MULTIPART :                                                     
                        
                            // -1- Get Parts from request
                            Collection<Part> parts = request.getParts();
                            
                            if(parts.isEmpty()){                                
                                res = Result.FAILURE_PARAM_MISMATCH;
                                
                            }else{
                                
                                Iterator<Part> iterator = parts.iterator();
                                while ( iterator.hasNext () ){
                                    Part part = iterator.next();

                                    // -1.1- Get form field 
                                    if(part.getContentType()==null){
                                        System.out.println("Value :: " + IOUtils.toString(part.getInputStream()));

                                    // -1.2- Get file as base64
                                    }else{

                                        String fileName = Util.generateID() + part.getSubmittedFileName().substring(part.getSubmittedFileName().lastIndexOf("."));
                                        outputStream = new FileOutputStream(new File(getServletContext().getRealPath("/images/sections"), fileName) );
                                        inputStream = part.getInputStream();

                                        int read = 0;
                                        final byte[] bytes = new byte[1024];

                                        while ((read = inputStream.read(bytes)) != -1) {
                                            outputStream.write(bytes, 0, read);
                                        }

                                        res = Result.SUCCESS.setContent( Util.getBaseURL(request) + "/images/sections/" + fileName );
                                    }
                                }
                            }
                        
                        break;
                        
                        
                        
                //**************************************************************
                //**************************************************************
                //**            CONTENT-TYPE NULL CASE
                //**************************************************************
                //**************************************************************
                    default:
                        res = Result.FAILURE_PROCESS_CONTENTTYPE;
                        break;
                                                
            }
            
            
//            if(request.getParameter("cdfsDo")!=null){
//                switch(request.getParameter("cdfsDo")){ 
//
//                //**************************************************************
//                //**************************************************************
//                //**                    ADD FILE CASE
//                //**************************************************************
//                //**************************************************************
//                    case "addFile":     
//                        
//                            if(request.getHeader("content-type")!=null && (request.getHeader("content-type").indexOf("multipart/form-data") >= 0 )){
//
//                                // -1- Get Parts from request
//                                Collection<Part> parts = request.getParts();
//                                Iterator<Part> iterator = parts.iterator();
//                                
//                                while ( iterator.hasNext () ){
//                                    Part part = iterator.next();
//                                    
//                                    // -1.1- Get form field 
//                                    if(part.getContentType()==null){
//                                        System.out.println("Value :: " + IOUtils.toString(part.getInputStream()));
//
//                                    // -1.2- Get file as base64
//                                    }else{
//                                        
//                                        String fileName = Util.generateID() + part.getSubmittedFileName().substring(part.getSubmittedFileName().lastIndexOf("."));
//                                        outputStream = new FileOutputStream(new File(getServletContext().getRealPath("/images/sections"), fileName) );
//                                        inputStream = part.getInputStream();
//
//                                        int read = 0;
//                                        final byte[] bytes = new byte[1024];
//
//                                        while ((read = inputStream.read(bytes)) != -1) {
//                                            outputStream.write(bytes, 0, read);
//                                        }
//                                                                                                                        
//                                        res = Result.SUCCESS.setContent( Util.getBaseURL(request) + "/images/sections/" + fileName );
//                                    }
//                                }
//
//                            }else{
//                                res = Result.FAILURE_PROCESS_CONTENTTYPE;
//                            }
//                        
//                        break;
//                }
//
//            }else{
//                res = Result.FAILURE_PARAM_MISMATCH;
//            }
            
        } catch (Exception e){
            res = Result.FAILURE_PROCESS.setContent("Exception");
            
        } finally{   
            
            if(inputStream!=null)
                inputStream.close();
            
            if(outputStream!=null)
                outputStream.close();
                        
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
