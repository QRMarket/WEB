/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.generic.servlet;

import com.generic.db.DBProduct;
import com.generic.resources.ResourceProperty;
import com.generic.result.Result;
import com.google.gson.Gson;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
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
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
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
@WebServlet(name = "ProductAddServlet", urlPatterns = {"/ProductAddServlet"})
public class ProductAddServlet extends HttpServlet {

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
                             
    
        try{            
            DiskFileItemFactory factory = new DiskFileItemFactory();
            // Sets the size threshold beyond which files are written directly to disk
            factory.setSizeThreshold(maxMemSize);
            // Sets the directory used to temporarily store files that are larger than the configured size threshold
            factory.setRepository(new File("/tmp/QR_Market_Web/images"));
            
            ServletFileUpload upload = new ServletFileUpload(factory);
            upload.setSizeMax( maxFileSize );            
            List fileItems =upload.parseRequest(request);                        
            
            Iterator i = fileItems.iterator();           
            while ( i.hasNext () ){
                FileItem fileItem = (FileItem)i.next(); 
                if ( fileItem.isFormField () ){
                    parameters.put(fileItem.getFieldName(), fileItem.getString());    
                }
                
            }
            
            
            
            if(parameters.get("cdpsDo")!=null){
                switch((String)parameters.get("cdpsDo")){ 
                    
                //**************************************************************
                //**************************************************************
                //**                    GET PRODUCT CASE
                //**************************************************************
                //**************************************************************
                    case "addProduct":                                         
                            
                            Iterator iterator = fileItems.iterator();           
                            while ( iterator.hasNext () ){
                                FileItem fileItem = (FileItem)iterator.next(); 
                                if ( !fileItem.isFormField () ){                                    
                                    
                                    // FILE TO BASE64
                                    InputStream inputStream = fileItem.getInputStream();
                                    byte[] bytes = IOUtils.toByteArray(inputStream);
                                    String fileBase64 = new BASE64Encoder().encode(bytes); 
                                    
                                    res = DBProduct.addProduct(
                                            (String) parameters.get("cdpName"), 
                                            (String) parameters.get("cdpBranch"),
                                            (String) parameters.get("cdpBarcode"),
                                            (String) parameters.get("cdpDesc"),
                                            fileItem.getContentType(),
                                            fileBase64);                                                                        
                                }
                            }
                               
                        break;
                }
                
            }
                    
                    
            
        } catch (FileUploadException ex) {
            Logger.getLogger(ProductAddServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(ProductAddServlet.class.getName()).log(Level.SEVERE, null, ex);
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
