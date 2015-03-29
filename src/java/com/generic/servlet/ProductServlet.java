package com.generic.servlet;

import com.generic.db.DBProduct;
import com.generic.db.MysqlDBOperations;
import com.generic.resources.ResourceProperty;
import com.generic.result.Result;
import com.google.gson.Gson;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
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
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;


/**
 *
 * @author Kemal Sami KARACA
 * @version 1.001 
 * 
 *      This servlet will be used to manage production data
 */
@MultipartConfig
@WebServlet(name = "ProductServlet", urlPatterns = {"/ProductServlet"})
public class ProductServlet extends HttpServlet {
    
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
         * cdpsDo   :: getProduct
         *              :: cdpUID         
         *          :: setProduct
         *              :: cdpUID         
         *          :: removeProduct
         *
         * 
         * !! KISALTMALAR !!
         * carpe diem product           --> cdp
         * carpe dirm product servlet   --> cdps
         * carpe diem product unique id --> cdpUID
         * carpe diem product common id --> cdpCID
         *
         */
        HttpSession session = request.getSession(false);
        Gson gson = new Gson();
        MysqlDBOperations mysql = new MysqlDBOperations();
        ResourceProperty resource = new ResourceProperty("com.generic.resources.mysqlQuery");
        Result res = Result.FAILURE_PROCESS;               
        
        
        /**
         *  Bu servlet(servis)'in amacı product ile ilgili işlemlerin yapılmasıdır
         *  Kullanıcı product görüntüleme, market tarafından yeni product eklenme,
         *  product fiyatı editleme veya ilgili product silme gibi temel işlevleri
         *  karşılar
         *  
         */
        Enumeration<String> enume = request.getParameterNames();
        while(enume.hasMoreElements()){
            String key = enume.nextElement();
            System.out.println("Incoming parameter --> " + key + " :: " + request.getParameter(key));
        }        
        
        try {
            
            if(request.getParameter("cdpsDo")!=null){                 
                switch(request.getParameter("cdpsDo")){ 
                    
                //**************************************************************
                //**************************************************************
                //**                    GET PRODUCT CASE
                //**************************************************************
                //**************************************************************
                    case "getProduct": 

                            if(request.getParameter("cdpUID")!=null){
                                    
                                    res = DBProduct.getCompanyProductInfo(request.getParameter("cdpUID"));

                            } else if(request.getParameter("cdpCID")!=null){
                                
                                    res = DBProduct.getProduct(request.getParameter("cdpCID"));
                                
                            } else{
                                    res = Result.FAILURE_PARAM_MISMATCH;
                            }

                        break;
                        
                //**************************************************************
                //**************************************************************
                //**                    ADD CASE
                //**************************************************************
                //**************************************************************
                    case "addProduct":
                                                
                        
                        break;
                        
                        
                //**************************************************************
                //**************************************************************
                //**                    REMOVE CASE
                //**************************************************************
                //**************************************************************
                    case "removeProduct":
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
         
            
        } catch (Exception ex) {
            Logger.getLogger(ProductServlet.class.getName()).log(Level.SEVERE, null, ex);
        } finally{                        
                                                
            mysql.closeAllConnection();
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
