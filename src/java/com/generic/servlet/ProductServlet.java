package com.generic.servlet;

import com.generic.controller.ControllerProduct;
import com.generic.logger.LoggerGuppy;
import com.generic.result.Result;
import com.generic.util.Util;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Kemal Sami KARACA
 * @since 10.03.2015
 * @version 1.01
 * 
 * @last 10.09.2015
 */

@MultipartConfig
@WebServlet(name = "ProductServlet", urlPatterns = {"/ProductServlet"})
public class ProductServlet extends HttpServlet {

    private static enum ProductServletOperations{
        NULL,
        INSERT_PRODUCT,
        ADD_DISTRIBUTER_PRODUCT,
        ADD_CAMPAIGN_PRODUCT,
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
                case "addDistributerProduct":
                    return ProductServletOperations.ADD_DISTRIBUTER_PRODUCT; 
                case "addCampaignProduct":
                    return ProductServletOperations.ADD_CAMPAIGN_PRODUCT; 
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
        
        Gson gson = new Gson();
        Result res = Result.FAILURE_PROCESS.setContent("ProductServlet -> processRequest -> initial error");                
        
        try {
                        
            switch (Util.getContentType(request)){
                    
                //**************************************************************
                //**************************************************************
                //**        Content-Type :: multipart/form-data
                //**************************************************************
                //**************************************************************
                case MULTIPART_FORM_DATA:                                            
                                                
                        switch (getRequestOperation(request)){
                                                        
                            case INSERT_PRODUCT:
                                    res = ControllerProduct.insertProduct(request);
                                break;
                                         
                            default:
                                    res = Result.FAILURE_PARAM_MISMATCH.setContent("ProductServlet -> MULTIPART_FORM_DATA");
                                break;
                        }
                        
                    break;
                    
                    
                    
                    
                    
                //**************************************************************
                //**************************************************************
                //**        Content-Type :: application/json
                //**************************************************************
                //**************************************************************
                case APPLICATION_JSON:
                        res = ControllerProduct.insertProductVia_ApplicationJson(request);
                    break;
                    
                    
                    
                    
                    
                //**************************************************************
                //**************************************************************
                //**        Content-Type :: application/x-www-form-urlencoded
                //**************************************************************
                //**************************************************************    
                case APPLICATION_FORM_URLENCODED:            
            
                        switch (getRequestOperation(request)){
                            
                                case INSERT_PRODUCT:
                                        res = ControllerProduct.insertProductViaURLEncoded(request);
                                    break;
                            
                                case ADD_DISTRIBUTER_PRODUCT:
                                       res = ControllerProduct.addDistributerProduct(request);
                                    break;
                                    
                                case ADD_CAMPAIGN_PRODUCT:
                                       res = ControllerProduct.addCampaignProduct(request);
                                    break;
                                    
                                case GET_PRODUCT:
                                        res = ControllerProduct.getProduct(request);
                                    break;
                                    
                                    
                                case GET_PRODUCT_LIST:
                                        res = ControllerProduct.getProductList(request);
                                    break;
                                    
                                    
                                case GET_CAMPAIGN_PRODUCT_LIST:
//                                        res = DBProduct.getActiveCampaignProducts();
                                    break;
                                    
                                    
                                case REMOVE_PRODUCT:
                                        res = Result.SUCCESS_EMPTY.setContent("onProgress");
                                    break;
                                    
                                    
                                default:
                                        res = Result.FAILURE_PARAM_MISMATCH.setContent("ProductServlet -> MULTIPART_FORM_DATA");
                                    break;
                                }

                    break;
                    
                    
                    
                    
                    
                //**************************************************************
                //**************************************************************
                //**        Content-Type :: EXCEPTION
                //**************************************************************
                //**************************************************************    
                default:
                        res = Result.FAILURE_PROCESS.setContent("ProductServlet -> Default Content-Type Case");
                    break;
                
            } // content-type switch end
                
            
        } catch (Exception ex) {
            Logger.getLogger(ProductServlet.class.getName()).log(Level.SEVERE, null, ex);
            res = Result.FAILURE_PROCESS.setContent(ex.toString());
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
