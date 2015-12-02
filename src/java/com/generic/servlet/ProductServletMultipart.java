/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.generic.servlet;

import com.generic.modal.DBProduct;
import com.generic.logger.LoggerGuppy;
import com.generic.resources.ResourceProperty;
import com.generic.result.Result;
import com.google.gson.Gson;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import org.apache.commons.io.IOUtils;
import sun.misc.BASE64Encoder;

/**
 *
 * @author Kemal Sami KARACA
 * @since 16.03.2015
 * @version 1.01
 * 
 * @last 16.03.2015
 * 
 * @IMPORTANT
 *      Bu class "if ((contentType.indexOf("multipart/form-data") >= 0)){...}" 
 *      kullanılarak ProductServlet'e eklenecektir.
 */

@MultipartConfig
@WebServlet(name = "ProductServletMultipart", urlPatterns = {"/ProductServletMultipart"})
public class ProductServletMultipart extends HttpServlet {
    
    private int maxMemSize =  10 * 1024 * 1024;
    private int maxFileSize = 50 * 1024 * 1024;
    private File file ;
    
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
         * cdpsDo            
         *          :: addProduct
         *              :: cdpName
         *              :: cdpBranch
         *              :: cdpBarcode
         *              :: cdpDesc
         *              :: cdpImages 
         * 
         * !! KISALTMALAR !!
         * carpe diem product           --> cdp
         * carpe dirm product servlet   --> cdps
         * carpe diem product unique id --> cdpUID
         *
         */
        HttpSession session = request.getSession(false);
        Gson gson = new Gson();        
        ResourceProperty resource = new ResourceProperty("com.generic.resources.mysqlQuery");        
        Map parameters = new HashMap();
        Result res = Result.FAILURE_PROCESS; 
        OutputStream outputStream = null;
        InputStream inputStream = null;
                             
    
        try{         
                        
            LoggerGuppy.verboseURL(request);
            LoggerGuppy.verboseHeader(request);
            
            
            if(request.getParameter("cdpsDo")!=null){
                switch(request.getParameter("cdpsDo")){ 

                //**************************************************************
                //**************************************************************
                //**                    GET PRODUCT CASE
                //**************************************************************
                //**************************************************************
                    case "addProduct":     
                        
                        
                            if(request.getHeader("content-type")!=null && (request.getHeader("content-type").indexOf("multipart/form-data") >= 0 )){

                                // -1- Get Parts from request
                                Collection<Part> parts = request.getParts();
                                Iterator<Part> iterator = parts.iterator();

                                String cdpName=null;
                                String cdpBranch=null;
                                String cdpBarcode=null;
                                String cdpDesc=null;
                                String cdpFileContent=null;
                                String cdpFile=null;

                                while ( iterator.hasNext () ){
                                    Part p = iterator.next();
                                    System.out.println("Content-type :: " + p.getContentType());
                                    System.out.println("Nmae :: " + p.getName());
                                    System.out.println("Submitted fileName :: " + p.getSubmittedFileName());

                                    // -1.1- Get form field 
                                    if(p.getContentType()==null){
                                        System.out.println("Value :: " + IOUtils.toString(p.getInputStream()));

                                    // -1.2- Get file as base64
                                    }else{
                                        byte[] byteForString = IOUtils.toByteArray(p.getInputStream());
                                        cdpFile = new BASE64Encoder().encode(byteForString);
                                        cdpFileContent = p.getContentType();
                                        
                                        
                                        outputStream = new FileOutputStream(new File("aaa.pdf"));
                                        inputStream = p.getInputStream();

                                        int read = 0;
                                        final byte[] bytes = new byte[1024];

                                        while ((read = inputStream.read(bytes)) != -1) {
                                            outputStream.write(bytes, 0, read);
                                        }
                                                                                    
                                    }

                                }
                                
                                //res = DBProduct.addProduct( cdpName, cdpBranch, cdpBarcode, cdpDesc, cdpFileContent, cdpFile);  


                            }else {
                                res = Result.FAILURE_PROCESS_CONTENTTYPE.setContent("ProductServletMultipart - content-type should be multipart/form-data");
                            }
                                                                           
                                
                        break;
                }

            }
                    
                    
            
        } catch (Exception ex) {
            Logger.getLogger(ProductServletMultipart.class.getName()).log(Level.SEVERE, null, ex);
            res = Result.FAILURE_PROCESS.setContent("ProductServletMultipart - Exception");
        } finally{
            
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
