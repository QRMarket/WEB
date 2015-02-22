/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.generic.servlet;

import com.generic.checker.Checker;
import com.generic.db.MysqlDBOperations;
import com.generic.resources.ResourceProperty;
import com.generic.result.Result;
import com.generic.util.MarketOrder;
import com.generic.util.MarketProduct;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.UUID;
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
 * @author Kemal Sami KARACA
 */

@WebServlet(name = "OrderServlet", urlPatterns = {"/OrderServlet"})
public class OrderServlet extends HttpServlet {

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
         *          :: addProduct
         *          :: removeProduct
         *
         * 
         * !! KISALTMALAR !!
         * carpe diem order             --> cdo
         * carpe diem order servlet     --> cdos
         * carpe diem product unique id --> cdpUID
         *
         */
        HttpSession session = request.getSession(false);
        Gson gson = new Gson();
        MysqlDBOperations mysql = new MysqlDBOperations();
        ResourceProperty resource = new ResourceProperty("com.generic.resources.mysqlQuery");
        Result res = Result.SUCCESS_EMPTY;
        
        
        /**
         *  Bu servlet(servis)'in amacı order ile ilgili işlemlerin yapılmasıdır
         *  Kullanıcıya sipariş oluşturma, sipariş listesi silme, sipariş listesine
         *  ürün eklemek gibi işlemleri gerçekleştirir
         * 
         *  Kullanıcının filter'dan geçtiği göze alınır Yani kullanıcı bilgisi session
         *  üzerinden alınır
         * 
         *  addToOrderList ::
         *  Kullanıcı, sepet(orderList) belirtmemişse bu durumda yeni bir sepet 
         *  yaratılır Eğer servise belirli bir sepet id'si ile gelmişse bu durumda
         *  ürün ilgili sepete eklenir
         *  
         */
        Enumeration<String> enume = request.getParameterNames();
        while(enume.hasMoreElements()){
            String key = enume.nextElement();
            System.out.println("Incoming parameter --> " + key + " :: " + request.getParameter(key));
        }
        
        
        try {
            
            if(request.getParameter("cdosDo")!=null){                 
                switch(request.getParameter("cdosDo")){ 
                    
                //**************************************************************
                //**************************************************************
                //**                    ADD TO ORDERLIST CASE
                //**************************************************************
                //**************************************************************
                    case "addToOrderList": 

                            if(request.getParameter("cdoID")!=null){

                                    System.out.println("--> /OrderServlet?cdosDo=addToOrderList&cdoID="+request.getParameter("cdoID")); 
                                    String query  = String.format( resource.getPropertyValue("selectProductByUID") , request.getParameter("cdpUID"));
                                    System.out.println("--> " + query);

                                    /**
                                     * If resultSet is empty -> there is not any product with that id
                                     * else if resultSet size is equals 1 -> product exist 
                                     * else resultSet size > 1 -> multiple row returned
                                     */
                                    ResultSet mysqlResult = mysql.getResultSet(query);
                                    if(mysql.resultSetIsEmpty()){       

                                        res = Result.FAILURE_AUTH_WRONG;
                                        
                                    }else if(mysql.getResultSetSize()==1){                                                  
                                        
                                        if(mysqlResult.first()){
                                            
                                            //res = Result.SUCCESS.setContent(new MarketProduct(mysqlResult.getString("pid"),mysqlResult.getString("productName"),mysqlResult.getString("productPriceType"),mysqlResult.getDouble("p_price")));
                                        
                                        }else{
                                            
                                        }                                                                                
                                        
                                    }else{

                                        res = Result.FAILURE_AUTH_MULTIPLE;
                                    }                                    

                            }else{                                    
                                    /**
                                     *  No initial order-list, add product case
                                     * 
                                     *  If "user has not active order-list" AND "try to add product to order-list"
                                     *          then "Create new order-list"
                                     *  Else "there is not any product"
                                     *          then "FAILURE_PARAM_MISMATCH"
                                     */                                    
                                    String cdpUID = request.getParameter("cdpUID");
                                    String cdpAmount = request.getParameter("cdpAmount");
                                    if(!Checker.anyParameterNull(cdpUID,cdpAmount)){
                                        
                                        System.out.println("--> /OrderServlet?cdosDo=addToOrderList&cdpUID="+request.getParameter("cdpUID")+"&cdpAmount="+request.getParameter("cdpAmount"));
                                                                                
                                        res = MarketOrder.addProductToOrderList( (String)session.getAttribute("cduUserId"), cdpUID, Double.parseDouble(cdpAmount) );                                         
                                        
                                    }else{
                                        
                                        res = Result.FAILURE_PARAM_MISMATCH;
                                    }                                    
                                    
                            }

                        break;
                 
                //**************************************************************
                //**************************************************************
                //**                    GET ORDER-LIST CASE
                //**************************************************************
                //**************************************************************
                    case "getOrderList":
                        break;
                        
                        
                //**************************************************************
                //**************************************************************
                //**                    GET ORDER-LISTS CASE
                //**************************************************************
                //**************************************************************
                    case "getOrderLists":
                        break;
                        
                        
                //**************************************************************
                //**************************************************************
                //**                    REMOVE CASE
                //**************************************************************
                //**************************************************************
                    case "removeOrderList":
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
         
        } catch (SQLException ex) {
            Logger.getLogger(ProductServlet.class.getName()).log(Level.SEVERE, null, ex);
        }finally{                        
                        
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
