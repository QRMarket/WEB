/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.generic.servlet;

import com.generic.controller.ControllerOrder;
import com.generic.controller.ControllerProduct;
import com.generic.result.Result;
import com.generic.util.Util;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Kemal Sami KARACA
 * @since 02.2015
 * @version 1.01
 * 
 * @last 15.11.2015
 */

@WebServlet(name = "OrderServlet", urlPatterns = {"/OrderServlet"})
public class OrderServlet extends HttpServlet {

    private static enum ServletOperations{
        NULL,
        CONFIRM_ORDER,
        GET_ORDER
    }
    
    private ServletOperations getRequestOperation(HttpServletRequest request){
        if(request.getParameter("do") != null){
            switch (request.getParameter("do")) {
                case "getOrder":
                    return ServletOperations.GET_ORDER;
            }
        }  
        return ServletOperations.NULL;
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
            Result res = Result.FAILURE_PROCESS.setContent("ProductServlet -> initial error");
            
            try {

                switch (Util.getContentType(request)) {

                    //**************************************************************
                    //**************************************************************
                    //**        Content-Type :: application/json
                    //**************************************************************
                    //**************************************************************
                    case APPLICATION_JSON:
                            res = ControllerOrder.confirmOrder(request);
                        break;
                        
                        
                        
                        
                        
                    //**************************************************************
                    //**************************************************************
                    //**        Content-Type :: application/json
                    //**************************************************************
                    //**************************************************************
                    case APPLICATION_FORM_URLENCODED:
                            
                            switch (getRequestOperation(request)){

                        
                                    case GET_ORDER:
                                            res = ControllerOrder.getOrder(request);
                                        break;



                                    default:
                                            res = Result.FAILURE_PARAM_MISMATCH.setContent("ProductServlet -> APPLICATION_FORM_URLENCODED -> Default Case");
                                        break;
                                    }
                            
                        break;
                        
                        
                        
                        
                        
                    //**************************************************************
                    //**************************************************************
                    //**        Content-Type :: Default
                    //**************************************************************
                    //**************************************************************
                    default: 
                            res = Result.FAILURE_PROCESS.setContent("OrderServlet -> Default Content-Type -> This content-type not used for that service");
                        break;

                }

            } catch (Exception ex) {
                Logger.getLogger(ProductServlet.class.getName()).log(Level.SEVERE, null, ex);
                res = Result.FAILURE_PROCESS.setContent(ex.getMessage());
            } finally {
                out.write(gson.toJson(res));
                out.close();
            }
        
            
            
            
            
            
        
//        try {
//            
//            if(request.getParameter("cdosDo")!=null){                 
//                switch(request.getParameter("cdosDo")){                                     
//                                
//                    
//                //--------------------------------------------------------------
//                //--------------------------------------------------------------
//                //  --- --- --- ---- ----- INSERT PART ----- ---- --- --- ---
//                    
//                //**************************************************************
//                //**************************************************************
//                //**            SET&ADD PRODUCT TO ORDERLIST
//                //**************************************************************
//                //**************************************************************
//                    case "productUpdate": 
//                        
//                            System.out.println("--> /OrderServlet?cdosDo=productUpdate&cdpUID="+request.getParameter("cdpUID")+"&cdpAmount="+request.getParameter("cdpAmount"));                                                    
//                            
//                            if(!Checker.anyNull(request.getParameter("cdpUID"),request.getParameter("cdpAmount"),session)){
//                               
//                                /**
//                                 *  IF order product map "cduPMap" exist 
//                                 *      -1-get product list "cduPMap"
//                                 *      -2-check product exist in "cduPMap"
//                                 *      -3- IF product exist in map
//                                 *          -3.1- get product from map
//                                 *          -3.2- update product info
//                                 *      -4- IF product NOT in map
//                                 *          -4.1- get product from DB
//                                 *          -4.2- update it & put it map
//                                 * 
//                                 *  IF order product map "cduPMap" NOT exist 
//                                 *      -1-get product from DB
//                                 *      -2-create map "cduPMap"
//                                 *      -3-put product to map
//                                 */
//                                String cdpUID = request.getParameter("cdpUID");
//                                String cdpAmount = request.getParameter("cdpAmount");
//                                if(session.getAttribute("cduPMap")!=null){
//                                    
//                                    try{                                        
//                                        Map orderProduct = (Map) session.getAttribute("cduPMap");                                        
//                                          
//                                        MarketProduct product = (orderProduct.get(cdpUID)!=null) ? 
//                                                                        (MarketProduct) orderProduct.get(cdpUID) : 
//                                                                        (MarketProduct) DBProduct.getCompanyProductInfo(cdpUID).getContent();
//                                                                                
//                                        if(Double.parseDouble(cdpAmount)>0){
//                                            product.setAmount(Double.parseDouble(cdpAmount));
//                                            orderProduct.put(product.getProductID(), product);
//                                        }else{
//                                            orderProduct.remove(product.getProductID());
//                                        }                                        
//                                        
//                                        res = Result.SUCCESS.setContent(product);
//                                        
//                                    }catch(ClassCastException e){
//                                        res = Result.FAILURE_PROCESS_CASTING.setContent(e);
//                                    }
//
//                                // product Map not exist case
//                                }else{
//                                    
//                                    MarketProduct product = (MarketProduct) DBProduct.getCompanyProductInfo(cdpUID).getContent();
//                                    product.setAmount(Double.parseDouble(cdpAmount));
//                                    
//                                    Map orderProduct = new HashMap();
//                                    orderProduct.put(product.getProductID(), product);
//                                    
//                                    session.setAttribute("cduPMap", orderProduct);
//                                    
//                                    // set result
//                                    res = Result.SUCCESS.setContent(product);                                    
//                                }                                                                
//                                
//                            }else{
//                                
//                                res = Checker.anyNull(session) ? Result.FAILURE_AUTH_SESSION : Result.FAILURE_PARAM_MISMATCH;
//                                
//                            }
//                        
//                        break;
//                        
//                        
//                        
//                        
//                        
//                        
//                        
//                //--------------------------------------------------------------
//                //--------------------------------------------------------------
//                //  --- --- --- ---- ----- GET PART ----- ---- --- --- ---
//                 
//                //**************************************************************
//                //**************************************************************
//                //**                GET ORDER COUNT
//                //**************************************************************
//                //**************************************************************
//                    case "getOrderCount":
//                                                                            
//                            res = DBOrder.getOrderCount();
//                            
//                        break;
//                        
//                        
//                //**************************************************************
//                //**************************************************************
//                //**          GET CURRENT ORDER-LIST INFO CASE
//                //**************************************************************
//                //**************************************************************
//                    case "getCurrentOrderInfo":                                                        
//                                                        
//                            if(session.getAttribute("cduPMap")!=null){                                                                
//                                try{  
//                                    
//                                    List<MarketProduct> pList = new ArrayList( ((Map)session.getAttribute("cduPMap")).values());
//                                    res = Result.SUCCESS.setContent(pList);
//                                                                        
//                                }catch(ClassCastException e){
//                                    res = Result.FAILURE_PROCESS_CASTING.setContent(e);
//                                }                                    
//                            }else{
//                                res = Result.SUCCESS_EMPTY;
//                            }                                                        
//                            
//                        break;
//                        
//                        
//                //**************************************************************
//                //**************************************************************
//                //**                GET ORDER-LIST INFO CASE
//                //**************************************************************
//                //**************************************************************
//                    case "getOrderInfo":
//                                                                            
//                            if(request.getParameter("cdoID")!=null){
//                                res = DBOrder.getCartInfo(request.getParameter("cdoID"));
//                            }
//                            
//                        break;
//                
//                
//                
//                
//                
//                //**************************************************************
//                //**************************************************************
//                //**                    GET ORDER-LISTS CASE
//                //**************************************************************
//                //**************************************************************
//                    case "getOrderList":                                                        
//                                     
//                            // because of cduUserId taken from session, if it is not exist then result returns FAILURE                            
//                            res = session!=null ? session.getAttribute("cduUserId")!=null ? DBOrder.getUserCartListExt((String) session.getAttribute("cduUserId")) : Result.FAILURE_AUTH : Result.FAILURE_AUTH_SESSION; 
//                        
//                        break;
//                  
//                        
//                        
//                        
//                        
//                //**************************************************************
//                //**************************************************************
//                //**                    GET ORDER-LISTS CASE
//                //**************************************************************
//                //**************************************************************
//                    case "getOrderLists":                                                        
//                                     
//                            // because of cduUserId taken from session, if it is not exist then result returns FAILURE
//                            res = session!=null ? session.getAttribute("cduUserId")!=null ? DBOrder.getUserCartList((String) session.getAttribute("cduUserId")) : Result.FAILURE_AUTH : Result.FAILURE_AUTH_SESSION;
//                        
//                        break;
//                        
//                        
//                        
//                //**************************************************************
//                //**************************************************************
//                //**                    GET ORDER-LISTS CASE
//                //**************************************************************
//                //**************************************************************
//                    case "confirmOrderList":
//                                                    
//                            if( !Checker.anyNull(request.getParameter("aid"),request.getParameter("ptype"),request.getParameter("date"),request.getParameter("note")) ){
//                                
//                                String addressID = request.getParameter("aid");
//                                String paymentType = request.getParameter("ptype");
//                                String orderNote = request.getParameter("note");    
//                                try{
//                                    long date = Long.parseLong(request.getParameter("date"), 10);
//                                
//                                    if(session.getAttribute("cduPMap")!=null){                                                                
//                                        try{                                        
//                                            res = DBOrder.confirmOrder(session,addressID,date,paymentType,orderNote);
//                                            if(res.checkResult(Result.SUCCESS)){
//                                                session.setAttribute("cduPMap",null);
//                                            }
//                                        }catch(ClassCastException e){
//                                            res = Result.FAILURE_PROCESS_CASTING;
//                                        }                                    
//                                    }else{
//                                        res = Result.SUCCESS_EMPTY;
//                                    }
//                                                                        
//                                }catch(NumberFormatException e){                                    
//                                    res = Result.FAILURE_PROCESS_CASTING.setContent("Number Format Exception @Order Service");
//                                }
//                                
//                            }else{
//                                
//                                res = Result.FAILURE_PARAM_MISMATCH;
//                                
//                            }
//                                                
//                        break;
//                        
//                        
//                        
//                //**************************************************************
//                //**************************************************************
//                //**                    REMOVE CASE
//                //**************************************************************
//                //**************************************************************
//                    case "removeOrderList":
//                                                
//                            System.out.println("--> /OrderServlet?cdosDo=removeOrderList"); 
//                            res = Result.SUCCESS.setContent("deneme").setContent("denem");
//                        
//                        break;
//                              
//                    
//                        
//                //**************************************************************
//                //**************************************************************
//                //**                    ADD TO ORDERLIST CASE
//                //**************************************************************
//                //**************************************************************
////                    case "addToOrderList": 
////                        
////                            System.out.println("--> /OrderServlet?cdosDo=addToOrderList&cdpUID="+request.getParameter("cdpUID")+"&cdpAmount="+request.getParameter("cdpAmount"));
////
////                            String cdpUID = request.getParameter("cdpUID");
////                            String cdpAmount = request.getParameter("cdpAmount");
////                            if(!Checker.anyNull(cdpUID,cdpAmount)){                                
////                                /**
////                                 * IF session exist "cduPlist" value
////                                 *      -1.1-get "cduPList" arraylist
////                                 *      -1.2-get Product info with given cdpUID
////                                 *      -1.2-set 
////                                 */
////                                if(session.getAttribute("cduPList")!=null){                                   
////                                    try{
////                                        ArrayList<MarketProduct> pList = (ArrayList<MarketProduct>) session.getAttribute("cduPList"); 
////                                        Result product = DBProduct.getCompanyProductInfo(cdpUID);
////                                        if(product.checkResult(Result.SUCCESS)){
////                                            // set "MarketProduct"
////                                            MarketProduct y = (MarketProduct) DBProduct.getCompanyProductInfo(cdpUID).getContent();
////                                            y.setAmount(Double.parseDouble(cdpAmount));
////                                            
////                                            // add new "MarketProduct" to session
////                                            pList.add(y);
////                                            session.setAttribute("cduPList", pList); 
////                                            
////                                            // set result
////                                            res = Result.SUCCESS.setContent(y);
////                                        }
////                                        
////                                    }catch(ClassCastException e){
////                                        res = Result.FAILURE_PROCESS_CASTING;
////                                    }                                    
////                                }else{
////                                    ArrayList<MarketProduct> pList = new ArrayList();
////                                    Result product = DBProduct.getCompanyProductInfo(cdpUID);
////                                    if(product.checkResult(Result.SUCCESS)){
////                                        // set "MarketProduct"
////                                        MarketProduct y = (MarketProduct) DBProduct.getCompanyProductInfo(cdpUID).getContent();
////                                        y.setAmount(Double.parseDouble(cdpAmount));
////                                        
////                                        // add new "MarketProduct" to session
////                                        pList.add(y);
////                                        session.setAttribute("cduPList", pList);
////                                        
////                                        // set result
////                                        res = Result.SUCCESS.setContent(y);
////                                    }                                    
////                                }
////                                
////                            }else{
////
////                                res = Result.FAILURE_PARAM_MISMATCH;
////                            }                                                     
////
////                        break;
//                
//                        
//                //**************************************************************
//                //**************************************************************
//                //**                    DEFAULT CASE
//                //**************************************************************
//                //**************************************************************
//                    default:
//                        res = Result.FAILURE_PARAM_WRONG;
//                        break;
//                }
//
//            }else{
//                res = Result.FAILURE_PARAM_MISMATCH;
//            }
//         
//        }finally{                        
//                        
//            out.write(gson.toJson(res));
//            out.close();
//            
//        }
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
