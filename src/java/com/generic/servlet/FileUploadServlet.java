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
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

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
        response.setContentType("application/json;charset=UTF-8");
                                
        /**
         * cdfsDo            
         *          :: addFile
         *              :: cdFile
         *              :: cdDest
         * 
         * !! KISALTMALAR !!
         * carpe diem file servlet      --> cdfs
         * carpe diem destination       --> cdDest
         * carpe dime file              --> cdFile
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
                    case MULTIPART_FORM_DATA :                                                     
                        
                            // -1- Get Parts from request
                            Collection<Part> parts = request.getParts();
                        
                            // -2- Check parts is not empty
                            if(parts.isEmpty()){                                
                                res = Result.FAILURE_PARAM_MISMATCH;
                                
                            // -3- Get Parameters From parts
                            }else{
                                
                                Map params = Util.getParameterFromParts(parts);                                
                                try{
                                    
                                    // -- Get Operation Parameter from Map
                                    String cdfsDo = (String)params.get("cdfsDo");
                                    switch(cdfsDo){ 
                                                                                
                                    //**************************************************************
                                    //**************************************************************
                                    //**                    UPLOAD FILE CASE
                                    //**************************************************************
                                    //**************************************************************
                                        case "addFile":  
                                                                                            
                                                // -3.1- Get parameter from  part 
                                                String cdTo = (String) params.get("cdfType");
                                                String fileLocation = null;
                                                Part part = (Part) params.get("cdFile");
                                                
                                                // -3.2- Get type of upload file
                                                switch(cdTo){
                                                    case "1":
                                                            fileLocation = "sections";
                                                        break;
                                                    
                                                    case "2":
                                                            fileLocation = "products";
                                                        break;
                                                        
                                                    default :
                                                            fileLocation = null;
                                                        break;
                                                }
                                                
                                                // -3.3- File Operation
                                                if( fileLocation!=null ){                                                                                                    
                                                                                                        
                                                    String fileName = Util.generateID() + part.getSubmittedFileName().substring(part.getSubmittedFileName().lastIndexOf("."));
                                                    outputStream = new FileOutputStream(new File(getServletContext().getRealPath( Util.DirectoryImage + "/" + fileLocation), fileName) );
                                                    inputStream = part.getInputStream();

                                                    int read = 0;
                                                    final byte[] bytes = new byte[1024];

                                                    while ((read = inputStream.read(bytes)) != -1) {
                                                        outputStream.write(bytes, 0, read);
                                                    }

                                                    res = Result.SUCCESS.setContent( Util.getBaseURL(request) + Util.DirectoryImage + "/" + fileLocation + "/" + fileName );
                                                
                                                }else{
                                                    res = Result.FAILURE_PROCESS.setContent( "Upload file type error." );
                                                }
                                                
                                            break;                                           
                                            
                                    //**************************************************************
                                    //**************************************************************
                                    //**                    DEFAULT CASE
                                    //**************************************************************
                                    //**************************************************************
                                        default:
                                                res = Result.FAILURE_PARAM_MISMATCH;
                                                
                                            break;                                            
                                            
                                    }
                                    
                                } catch(NullPointerException e){
                                    // -??- Multipart değerlerden parametrenin çekilememe durumudur
                                    res = Result.FAILURE_PARAM_MISMATCH.setContent("FileUploadServlet - NullPointerException caused by parameter mismatch");
                                
                                } catch(ClassCastException e){
                                    // -??- Multipart değerlerden parametre casting error durumudur
                                    res = Result.FAILURE_PROCESS_CASTING.setContent("Casting error for parameter");
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
