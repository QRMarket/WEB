/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.generic.controller;

import com.generic.db.DBOrder;
import com.generic.result.Result;
import com.generic.entity.Orders;
import com.generic.entity.MarketProduct;
import com.generic.entity.OrderProduct;
import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Guppy Org.
 * @since 11.2015
 * @version 1.01
 * 
 * @last 15.11.2015
 */
public class ControllerOrder {
    
    
    //------------------------------------------------------------------------------
    //------------------------------------------------------------------------------
    //--                            GET OPERATIONs
    //------------------------------------------------------------------------------
    //------------------------------------------------------------------------------
    
    // <editor-fold defaultstate="collapsed" desc="GET Operations">
    
        //**************************************************************************
        //**************************************************************************
        //**                        GET ORDER
        //**************************************************************************
        //**************************************************************************
        /**
         * @param request
         * @return 
         */
        public static Result getOrder(HttpServletRequest request){
        
                Result result = Result.FAILURE_PROCESS;
                if (request.getParameter("orderId") != null) {
                    return DBOrder.getOrder(request.getParameter("orderId"));
                }else{
                    result = Result.FAILURE_PARAM_MISMATCH;
                }

            return result;
        }
        
        
        
        
        //**************************************************************************
        //**************************************************************************
        //**                        GET ORDER LIST
        //**************************************************************************
        //**************************************************************************
        /**
         * @param request
         * @return 
         */
        public static Result getOrderList(HttpServletRequest request){
        
                Result result = Result.FAILURE_PROCESS;
                int limit = 20;
                
                try {
                    limit = Integer.parseInt(request.getParameter("limit"))>0 ? Integer.parseInt(request.getParameter("limit")) : limit;
                } catch (Exception ex){
                    Logger.getLogger(ControllerProduct.class.getName()).log(Level.SEVERE, null, ex);
                }

                return DBOrder.getOrderList(request.getParameter("distributerId"), limit);
                                
//            return result;
        }
        
    
    // </editor-fold>
    
    
    
    
    
    //------------------------------------------------------------------------------
    //------------------------------------------------------------------------------
    //--                            INSERT OPERATIONs
    //------------------------------------------------------------------------------
    //------------------------------------------------------------------------------
    
    // <editor-fold defaultstate="collapsed" desc="INSERT Operations">
    
        //**************************************************************************
        //**************************************************************************
        //**                        CONFIRM ORDER
        //**************************************************************************
        //**************************************************************************
        public static Result confirmOrder(HttpServletRequest request){
            
                Gson gson = new Gson();
                Result result = Result.FAILURE_PROCESS.setContent("ControllerOrder -> confirmOrder -> :: initial case");
                String json = "";
                
                try {
                    
                    // -- 1 -- Read data
                        BufferedReader bufferReader = new BufferedReader(new InputStreamReader(request.getInputStream()));
                        StringBuilder builder = new StringBuilder();

                        String line;
                        while( (line = bufferReader.readLine()) != null) {
                           builder.append(line);
                        }
                        
                        
                    // -- 2 -- Parse data to "Order" object
                        json = builder.toString();
                        Orders orderObject = gson.fromJson(json, Orders.class);
                        
                        
                    // -- 3 -- Insert order products to 
                        List<OrderProduct> orderProductList = orderObject.getOrderProductList();
                        if(orderProductList!=null && orderProductList.size()>0){
                            return DBOrder.confirmOrder(orderObject);
                        }else{
                            return Result.FAILURE_PROCESS.setContent("ControllerOrder -> confirmOrder -> :: Product size equals to zero");
                        }
                    
                } catch (Exception ex) {
                    Logger.getLogger(ControllerOrder.class.getName()).log(Level.SEVERE, null, ex);
                    result = Result.FAILURE_PROCESS.setContent("ControllerOrder -> confirmOrder -> :: " + ex.getMessage());
                }
            
            return result;
        }
    
    
    // </editor-fold>
    
    
}
