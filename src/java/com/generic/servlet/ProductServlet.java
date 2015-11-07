package com.generic.servlet;

import com.generic.controller.ControllerProduct;
import com.generic.db.DBProduct;
import com.generic.result.Result;
import com.generic.util.Util;
import com.google.gson.Gson;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;
import org.apache.commons.net.ftp.FTPClient;
import sun.misc.BASE64Encoder;

/**
 *
 * @author Kemal Sami KARACA
 * @since 10.03.2015
 * @version 1.01
 * 
 * @last 10.09.2015
 *
 * This servlet will be used to manage production data
 * 
 *      Bu servlet(servis)'in amacı product ile ilgili işlemlerin
 *      yapılmasıdır Kullanıcı product görüntüleme, market tarafından yeni
 *      product eklenme, product fiyatı editleme veya ilgili product silme
 *      gibi temel işlevleri karşılar
 * 
 */

@MultipartConfig
@WebServlet(name = "ProductServlet", urlPatterns = {"/ProductServlet"})
public class ProductServlet extends HttpServlet {

    private static enum ProductServletOperations{
        NULL,
        INSERT_PRODUCT,
        GET_PRODUCT,
        GET_PRODUCT_LIST,
        GET_CAMPAIGN_PRODUCT_LIST,
        REMOVE_PRODUCT,
    }        
    
    private ProductServletOperations getRequestOperation(HttpServletRequest request){
        
        if(request.getParameter("do") != null){
            
            switch (request.getParameter("do")) {
                case "addProduct":
                    return ProductServletOperations.INSERT_PRODUCT; 
                case "getProduct":
                    return ProductServletOperations.GET_PRODUCT; 
                case "getProductList":
                    return ProductServletOperations.GET_PRODUCT_LIST; 
                case "getCampaignProductList":
                    return ProductServletOperations.GET_CAMPAIGN_PRODUCT_LIST; 
                case "removeProduct":
                    return ProductServletOperations.REMOVE_PRODUCT;              
            }
        }  
        
        return ProductServletOperations.NULL;
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
        
        
        /**
         *  cdpsDo :: 
         *      getProduct :: 
         *              cdpUID || cdpCID
         *
         *
         * !! KISALTMALAR !! 
         * carpe diem product --> cdp 
         * carpe dirm product servlet --> cdps 
         * carpe diem product unique id --> cdpUID 
         * carpe diem product common id --> cdpCID 
         * carpe diem product market id --> cdpmID
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
                            //-- ---           INSERT PRODUCT             --- --
                            //--------------------------------------------------
                            case INSERT_PRODUCT:                                             
//                                    res = DBProduct.addProduct(request);
                                    res = ControllerProduct.insertProduct(request);
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
                            //**                    GET PRODUCT CASE
                            //**************************************************************
                            //**************************************************************
                                case GET_PRODUCT:

                                    if (request.getParameter("productUniqueId") != null) {

                                        res = ControllerProduct.getCompanyProductInfo(request);

                                    } else if (request.getParameter("productCommonId") != null) {

                                        res = ControllerProduct.getProduct(request);

                                    } else {
                                        res = Result.FAILURE_PARAM_MISMATCH;
                                    }

                                    break;

                            //**************************************************************
                            //**************************************************************
                            //**                GET PRODUCT LIST CASE
                            //**************************************************************
                            //**************************************************************
                                case GET_PRODUCT_LIST:

                                    if (request.getParameter("sectionId") != null) {

                                        res = ControllerProduct.getProductListBySection(request);

                                    } else if (request.getParameter("distributerId") != null) {
                                        res = ControllerProduct.getProductListByDistributer(request);

                                    } else {
                                        res = Result.FAILURE_PARAM_MISMATCH;
                                    }

                                    break;


                            //**************************************************************
                            //**************************************************************
                            //**                    GET CAMPAIGN PRODUCT LIST CASE
                            //**************************************************************
                            //**************************************************************
                                case GET_CAMPAIGN_PRODUCT_LIST:
                                        res = DBProduct.getActiveCampaignProducts();
                                    break;

                            //**************************************************************
                            //**************************************************************
                            //**                    REMOVE CASE
                            //**************************************************************
                            //**************************************************************
                                case REMOVE_PRODUCT:
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
