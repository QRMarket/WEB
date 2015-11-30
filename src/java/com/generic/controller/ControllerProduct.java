package com.generic.controller;

import com.generic.checker.Checker;
import com.generic.constant.CharsetList;
import com.generic.db.DBProduct;
import com.generic.ftp.FTPHandler;
import com.generic.result.Result;
import com.generic.entity.MarketProduct;
import com.generic.entity.MarketProductImage;
import com.generic.constant.UserRole;
import com.generic.entity.CampaignProduct;
import com.generic.entity.CompanyProduct;
import com.generic.locale.UtilLocaleHandler;
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
public class ControllerProduct {
    
    
    
    //------------------------------------------------------------------------------
    //------------------------------------------------------------------------------
    //--                            GET OPERATIONs
    //------------------------------------------------------------------------------
    //------------------------------------------------------------------------------
    
    // <editor-fold defaultstate="collapsed" desc="GET Operations">
    
        //**************************************************************************
        //**************************************************************************
        //**                        GET PRODUCT
        //**************************************************************************
        //**************************************************************************
        /**
         * @param request
         * @return 
         */
        public static Result getProduct(HttpServletRequest request){
        
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
        //**                        GET PRODUCT LIST
        //**************************************************************************
        //**************************************************************************
        /**
         * 
         * @param request
         * @return 
         */
        public static Result getProductList(HttpServletRequest request){
        
                Result result = Result.FAILURE_PROCESS;
                int limit = 20;
                
                if(request.getParameter("distributerId") != null && request.getParameter("sectionId") == null){
                    
                        try {
                            limit = Integer.parseInt(request.getParameter("limit"))>0 ? Integer.parseInt(request.getParameter("limit")) : limit;
                        } catch (Exception ex){
                            Logger.getLogger(ControllerProduct.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        
                    return DBProduct.getDistributerProductList(request.getParameter("distributerId"), limit);
                   
                }else if(request.getParameter("distributerId") != null && request.getParameter("sectionId") != null){
                    
                        try {
                            limit = Integer.parseInt(request.getParameter("limit"))>0 ? Integer.parseInt(request.getParameter("limit")) : limit;
                        } catch (Exception ex){
                            Logger.getLogger(ControllerProduct.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    
                    return DBProduct.getDistributerProductListBySection(request.getParameter("distributerId"), request.getParameter("sectionId"), limit);
                    
                }else{
                        try {
                            limit = Integer.parseInt(request.getParameter("limit"))>0 ? Integer.parseInt(request.getParameter("limit")) : limit;
                        } catch (Exception ex){
                            Logger.getLogger(ControllerProduct.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        
                    return DBProduct.getProductList(limit);
                }

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
        //**       INSERT PRODUCT VIA APPLICATION JSON WITH IMAGE URLs
        //**************************************************************************
        //**************************************************************************
        /**
         * @param request
         * @return 
         */
        public static Result insertProductVia_ApplicationJson(HttpServletRequest request){

                Gson gson = new Gson();
                Result result = Result.FAILURE_PROCESS.setContent("ControllerOrder -> insertProductVia_ApplicationJson -> :: initial case");
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
                        MarketProduct productObj = gson.fromJson(json, MarketProduct.class);

                    // -- 3 -- Insert order products to 
                        productObj.setProductID(Util.generateID());
                        return DBProduct.addProduct(productObj);

                } catch (Exception ex) {
                    Logger.getLogger(ControllerOrder.class.getName()).log(Level.SEVERE, null, ex);
                    result = Result.FAILURE_PROCESS.setContent("ControllerOrder -> confirmOrder -> :: " + ex.getMessage());
                }

            return result;

        }
        
        
        //**************************************************************************
        //**************************************************************************
        //**                INSERT PRODUCT VIA FILES
        //**************************************************************************
        //**************************************************************************
        /**
         * @param request
         * @return 
         */
        public static Result insertProduct(HttpServletRequest request){

                Result result = Result.FAILURE_PROCESS.setContent("ControllerOrder -> insertProduct -> :: initial case");
                try {

                    // -1.1- Create Product Object
                        MarketProduct productObj = new MarketProduct();
                        productObj.setProductID("t_" + Util.generateID());
                        productObj.setProductName(request.getParameter("name"));
                        productObj.setProductCode(request.getParameter("barcode"));
                        productObj.setProductDesc(request.getParameter("desc"));
                        productObj.setBrandID(request.getParameter("brand_id"));
                        productObj.setSectionID(request.getParameter("section_id"));
                        productObj.setUserID(request.getParameter("admin_id"));


                    // -1.2- Check Product Object Values
                        if(!Checker.anyNull( productObj.getProductName(), productObj.getProductCode(), productObj.getBrandID(), productObj.getUserID())){ 

                            Collection<Part> parts = request.getParts();
                            Iterator<Part> partsIterator = parts.iterator();

                            while ( partsIterator.hasNext () ){
                                Part part = partsIterator.next();

                                if(part.getName().equalsIgnoreCase("files")){

                                    try{

                                        String generatedID = Util.generateID();
                                        String imageFileName = generatedID + part.getSubmittedFileName().substring(part.getSubmittedFileName().lastIndexOf("."));
                                        InputStream imageInputStream = part.getInputStream();

                                        FTPClient client = FTPHandler.getFTPClient();

                                        if(client.storeFile(FTPHandler.dirTemps + imageFileName, imageInputStream)){

                                            MarketProductImage productImageObject = new MarketProductImage();
                                            productImageObject.setImageFileName(imageFileName);
                                            productImageObject.setImageSource(String.format("http://%s/%s", FTPHandler.ftpHost, FTPHandler.dirProducts) + imageFileName);
                                            productImageObject.setImageSourceType("FTP");          
                                            productImageObject.setImageContentType(
                                                                new MimetypesFileTypeMap().getContentType(new File(FTPHandler.getFTP_URL(FTPHandler.dirTemps) +imageFileName)));

                                            productObj.getProductImages().add(productImageObject);

                                        }

                                        FTPHandler.closeFTPClient();
                                        imageInputStream.close();  

                                    }catch(Exception ex){
                                        Logger.getLogger(ControllerProduct.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                }
                            }

                        }else{
                            return Result.FAILURE_PARAM_MISMATCH.setContent("ControllerProduct > AnyNull Exception");
                        }

                        return DBProduct.addProduct(productObj);

                } catch (IOException | ServletException ex) {
                    Logger.getLogger(ControllerProduct.class.getName()).log(Level.SEVERE, null, ex);
                    return Result.FAILURE_PROCESS.setContent(ex.toString());
                }

            // return result;

        }
        
        
        
        
        //**************************************************************************
        //**************************************************************************
        //**                INSERT PRODUCT VIA FILES
        //**************************************************************************
        //**************************************************************************
        /**
         * @param request
         * @return 
         */
        public static Result insertProductViaURLEncoded(HttpServletRequest request){

                Result result = Result.FAILURE_PROCESS.setContent("ControllerOrder -> insertProduct -> :: initial case");
                try {

                    // -1.1- Create Product Object
                        MarketProduct productObj = new MarketProduct();
                        productObj.setProductID("t_" + Util.generateID());
                        productObj.setProductName(UtilLocaleHandler.useCharset(request.getParameter("name"), CharsetList.TR));
                        productObj.setProductCode(request.getParameter("barcode"));
                        productObj.setProductDesc(request.getParameter("desc"));
                        productObj.setBrandID(request.getParameter("brandID"));
                        productObj.setSectionID(request.getParameter("sectionID"));
                        productObj.setPriceType(request.getParameter("priceType"));
                        productObj.setUserID(request.getParameter("userID"));

                        
                        MarketProductImage productImageObject = new MarketProductImage();
                        productImageObject.setImageSource(request.getParameter("imageSource"));
                        productImageObject.setImageSourceType("URL");   
                        productObj.getProductImages().add(productImageObject);
                       
                        return DBProduct.addProduct(productObj);

                } catch (Exception ex) {
                    Logger.getLogger(ControllerProduct.class.getName()).log(Level.SEVERE, null, ex);
                    return Result.FAILURE_PROCESS.setContent(ex.toString());
                }

            // return result;

        }
        
        
        
        
        //**************************************************************************
        //**************************************************************************
        //**                    ADD DISTRIBUTER PRODUCT 
        //**************************************************************************
        //**************************************************************************
        /**
         * @param request
         * @return 
         */
        public static Result addDistributerProduct(HttpServletRequest request){
        
                Result result = Result.FAILURE_PROCESS;
                
                String productId = request.getParameter("productId");
                String distributerId = request.getParameter("distributerId");
                double productPrice = -1;
                try {
                    productPrice = Double.parseDouble(request.getParameter("productPrice"))>0 ? Double.parseDouble(request.getParameter("productPrice")) : productPrice;
                } catch (Exception ex){
                    Logger.getLogger(ControllerProduct.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                try {
                    
                    // -1.1- Check Distibuter Product  Values
                        if(!Checker.anyNull( productId,distributerId) && productPrice>0){ 
                            
                            CompanyProduct companyProduct = new CompanyProduct();
                            companyProduct.setId(Util.generateID());
                            companyProduct.setDistributerID(distributerId);
                            companyProduct.setProductID(productId);
                            companyProduct.setProductPrice(productPrice);
                            companyProduct.setProductPriceType("TL");
                            
                            return DBProduct.addDistributerProduct(companyProduct);
                        }else {
                            return Result.FAILURE_PARAM_MISMATCH;
                        }
                    
                }catch(Exception ex){
                    result = Result.FAILURE_PROCESS.setContent(ex.getMessage());
                }

            return result;
        }
        
        
        
        
        //**************************************************************************
        //**************************************************************************
        //**                    ADD CAMPAIGN PRODUCT 
        //**************************************************************************
        //**************************************************************************
        /**
         * @param request
         * @return 
         */
        public static Result addCampaignProduct(HttpServletRequest request){
            
                Result result = Result.FAILURE_PROCESS.setContent("ControllerProduct" + " -- " + "addCampaignProduct :: initial case");
                String distributerProductId;
                long campaignStartAt,campaignFinishAt;
                double productNewPrice;
                
                try{
                    // - 1 - Initialization
                        distributerProductId = request.getParameter("distributerProductId");
                        productNewPrice = Double.parseDouble(request.getParameter("newPrice"));
                        campaignStartAt = Long.parseLong(request.getParameter("campaignStartAt"));
                        campaignFinishAt = Long.parseLong(request.getParameter("campaignFinishAt"));
                        
                }catch(ClassCastException ex){
                    return result.setContent(ex.getMessage());
                }
                
                
                try {
                    // -2- Check Values
                        if(!Checker.anyNull( distributerProductId,productNewPrice,campaignStartAt,campaignFinishAt) && productNewPrice>=0){
                            CampaignProduct campaignProduct = new CampaignProduct();
                            campaignProduct.setId(Util.generateID());
                            campaignProduct.setCompanyProductId(distributerProductId);
                            campaignProduct.setStartAt(campaignStartAt);
                            campaignProduct.setFinishAt(campaignFinishAt);
                            campaignProduct.setCampaignPrice(productNewPrice);
                            return DBProduct.addCampaignProduct(campaignProduct);
                        }else {
                            return Result.FAILURE_PARAM_MISMATCH;
                        }
                }catch(Exception ex){
                    result = Result.FAILURE_PROCESS.setContent(ex.getMessage());
                }

            return result;
        }
        
        // </editor-fold>
    
}
