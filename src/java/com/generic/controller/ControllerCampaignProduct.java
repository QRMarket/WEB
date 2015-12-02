package com.generic.controller;

import com.generic.checker.Checker;
import com.generic.constant.CharsetList;
import com.generic.modal.DBProduct;
import com.generic.ftp.FTPHandler;
import com.generic.result.Result;
import com.generic.entity.MarketProduct;
import com.generic.entity.MarketProductImage;
import com.generic.constant.UserRole;
import com.generic.entity.CampaignProduct;
import com.generic.entity.DistributerProduct;
import com.generic.locale.UtilLocaleHandler;
import com.generic.modal.CampaignProductModal;
import com.generic.servlet.ProductServlet;
import com.generic.util.Util;
import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.activation.MimetypesFileTypeMap;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import org.apache.commons.net.ftp.FTPClient;

/**
 *
 * @author Kemal Sami KARACA
 * @since 10.2015
 * @version 1.01
 * 
 * @last 30.10.2015
 */
public class ControllerCampaignProduct {
    
    
    
    //------------------------------------------------------------------------------
    //------------------------------------------------------------------------------
    //--                            GET OPERATIONs
    //------------------------------------------------------------------------------
    //------------------------------------------------------------------------------
    
    // <editor-fold defaultstate="collapsed" desc="GET Operations">
    
        //**************************************************************************
        //**************************************************************************
        //**                        GET CAMPAIGN PRODUCT
        //**************************************************************************
        //**************************************************************************
        /**
         * @param request
         * @return 
         */
        public static Result getCampaignProduct(HttpServletRequest request){
        
                Result result = Result.FAILURE_PROCESS;
                if (request.getParameter("companyProductId") != null) {
                    return DBProduct.getProductOfDistributer(request.getParameter("companyProductId"));
                }else if (request.getParameter("productId") != null) {
                    return DBProduct.getProduct(request.getParameter("productId"));
                }else if(request.getParameter("productCode") != null){
                    return DBProduct.getProductByCode(request.getParameter("productCode"));
                }else{
                    result = Result.FAILURE_PARAM_MISMATCH;
                }

            return result;
        }
        
        
        
        
        //**************************************************************************
        //**************************************************************************
        //**                        GET CAMPAIGN PRODUCT LIST
        //**************************************************************************
        //**************************************************************************
        /**
         * 
         * @param request
         * @return 
         */
        public static Result getCampaignProductList(HttpServletRequest request){
        
                Result result = Result.FAILURE_PROCESS;
                int limit = 20;
                
                if(request.getParameter("distributerId") != null){
                    
                        try {
                            limit = Integer.parseInt(request.getParameter("limit"))>0 ? Integer.parseInt(request.getParameter("limit")) : limit;
                        } catch (Exception ex){
                            Logger.getLogger(ControllerCampaignProduct.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        
                    return CampaignProductModal.getCampaignProductList(limit);
                   
                }else{
                    return Result.FAILURE_PARAM_MISMATCH.setContent(ControllerCampaignProduct.class.getName() + " -> getCampaignProductList");
                }

        }
    
    // </editor-fold>
    
    
    
    
    
    //------------------------------------------------------------------------------
    //------------------------------------------------------------------------------
    //--                            INSERT OPERATIONs
    //------------------------------------------------------------------------------
    //------------------------------------------------------------------------------
    
    // <editor-fold defaultstate="collapsed" desc="INSERT Operations">
    
        
    // </editor-fold>
    
}
