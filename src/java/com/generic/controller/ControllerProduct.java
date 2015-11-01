package com.generic.controller;

import com.generic.checker.Checker;
import com.generic.db.DBProduct;
import com.generic.ftp.FTPHandler;
import com.generic.result.Result;
import com.generic.util.MarketProduct;
import com.generic.util.MarketProductImage;
import com.generic.util.MarketUser;
import com.generic.util.Util;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.activation.MimetypesFileTypeMap;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
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
    
    
    public static Result insertProduct(HttpServletRequest request){
        
            try {                                
                
                // -1.1- Create Product Object
                    MarketProduct productObj = new MarketProduct();        
                    productObj.setProductID("t_" + Util.generateID());
                    productObj.setProductName(request.getParameter("name"));
                    productObj.setProductCode(request.getParameter("barcode"));
                    productObj.setProductDesc(request.getParameter("desc"));
                    productObj.setBrandID(request.getParameter("brand_id"));
                    productObj.setUserID("123459");
                    productObj.setSectionID("100");

                // -1.2- Check Product Object Values
                    if(!Checker.anyNull( productObj.getProductName(), productObj.getBrandID(), productObj.getUserID())){ 

                        Collection<Part> parts = request.getParts();
                        Iterator<Part> partsIterator = parts.iterator();
                        
                        while ( partsIterator.hasNext () ){
                            Part part = partsIterator.next();
                            
                            if(part.getName().equalsIgnoreCase("files")){
                                                         
                                String generatedID = Util.generateID();
                                String imageFileName = generatedID + part.getSubmittedFileName().substring(part.getSubmittedFileName().lastIndexOf("."));
                                InputStream imageInputStream = part.getInputStream();
                                                                
                                FTPClient client = FTPHandler.getFTPClient();
                                if(client.storeFile("temps/"+imageFileName, imageInputStream)){
                                    
                                    MarketProductImage productImageObject = new MarketProductImage();
                                    productImageObject.setImageID(generatedID);
                                    productImageObject.setImageFileName(imageFileName);
                                    productImageObject.setImageSource(String.format("http://%s/products/", FTPHandler.ftpHost) + imageFileName);
                                    productImageObject.setImageSourceType("FTP");          
                                    productImageObject.setImageContentType(
                                                        new MimetypesFileTypeMap().getContentType(new File(FTPHandler.getFTP_URL("temps") +imageFileName)));
                                                                        
                                    productObj.getProductImages().add(productImageObject);
                                
                                }
                                
                                FTPHandler.closeFTPClient();
                                imageInputStream.close();
                            }
                        }

                    }else{
                        return Result.FAILURE_PARAM_MISMATCH.setContent("ControllerProduct>");
                    }
                                                        
                    return DBProduct.addProduct(productObj);
                    
            } catch (IOException ex) {
                Logger.getLogger(ControllerProduct.class.getName()).log(Level.SEVERE, null, ex);
                return Result.FAILURE_PROCESS.setContent(ex.toString());
            } catch (ServletException ex) {
                Logger.getLogger(ControllerProduct.class.getName()).log(Level.SEVERE, null, ex);
                return Result.FAILURE_PROCESS.setContent(ex.toString());
            }
            
        
        
//        return Result.FAILURE_PROCESS;
        
    }
    
    
}
