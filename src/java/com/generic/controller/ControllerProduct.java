package com.generic.controller;

import com.generic.checker.Checker;
import com.generic.db.DBProduct;
import com.generic.ftp.FTPHandler;
import com.generic.result.Result;
import com.generic.entity.MarketProduct;
import com.generic.entity.MarketProductImage;
import com.generic.constant.UserRole;
import com.generic.entity.CompanyProduct;
import com.generic.servlet.ProductServlet;
import com.generic.util.Util;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Iterator;
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
                
                if(request.getParameter("distributerId") != null){
                    
                        try {
                            limit = Integer.parseInt(request.getParameter("limit"))>0 ? Integer.parseInt(request.getParameter("limit")) : limit;
                        } catch (Exception ex){
                            Logger.getLogger(ControllerProduct.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        
                    return DBProduct.getDistributerProductList(request.getParameter("distributerId"), limit);
                    
                }else{
                        try {
                            limit = Integer.parseInt(request.getParameter("limit"))>0 ? Integer.parseInt(request.getParameter("limit")) : limit;
                        } catch (Exception ex){
                            Logger.getLogger(ControllerProduct.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        
                    return DBProduct.getProductList(limit);
                }
                
                
                
//                if (request.getParameter("sectionId") != null) {
//
//                    res = ControllerProduct.getProductListBySection(request);
//
//                } else if (request.getParameter("distributerId") != null) {
//                    res = ControllerProduct.getProductListByDistributer(request);
//
//                } 

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
        //**                        INSERT PRODUCT
        //**************************************************************************
        //**************************************************************************
        /**
         * @param request
         * @return 
         */
        public static Result insertProduct(HttpServletRequest request){

                Result result = Result.FAILURE_PROCESS.setContent("Controller>Product>insert>initial case");;
                try {

                    // -1.1- Create Product Object
                        MarketProduct productObj = new MarketProduct();
                        productObj.setProductID("t_" + Util.generateID());
                        productObj.setProductName(request.getParameter("name"));
                        productObj.setProductCode(request.getParameter("barcode"));
                        productObj.setProductDesc(request.getParameter("desc"));
                        productObj.setBrandID(request.getParameter("brand_id"));
                        productObj.setSectionID(request.getParameter("section_id"));
                        productObj.setUserID("123459");


                    // -1.2- Check Product Object Values
                        if(!Checker.anyNull( productObj.getProductName(), productObj.getBrandID(), productObj.getUserID())){ 

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
                                            productImageObject.setImageID(generatedID);
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
        
        // </editor-fold>
    
    
    
    
    
    
    
    
    
    
    
    
    
    public static Result getProductListBySection(HttpServletRequest request){
        
        String sectionId = request.getParameter("sectionId");

    // -1.1- Check Product Common Id
        if(!Checker.anyNull( sectionId)){ 
            String limit = request.getParameter("limit");
            if(limit == null){
                limit = "20";
            }
            return DBProduct.getProductListBySection(sectionId, Integer.parseInt(limit));

        }else{
            return Result.FAILURE_PARAM_MISMATCH.setContent("ControllerProduct>");
        }
        
    }
    
    
}
