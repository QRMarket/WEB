/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.generic.test;

import com.generic.db.DBMarket;
import com.generic.db.DBOrder;
import com.generic.db.DBProduct;
import com.generic.db.DBUser;
import com.generic.result.Result;
import com.generic.util.MarketProduct;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.IOUtils;
import sun.misc.BASE64Encoder;

/**
 *
 * @author kemal
 */
public class CarpeTester {
    
    public static void main(String[] args){
                
        String base64 = fileToBase64("/Users/kemal/Desktop/Richard-Harrow2.jpg");
        
        //Result res = DBProduct.addProduct(null , base64);        
        //System.out.println(res.getResultCode());
        
    }
    
    public static String fileToBase64(String fileName){
          
        String fileBase64 = null;
        try{
            File file = new File(fileName);
            InputStream inputStream = new FileInputStream(file);
            
            byte[] bytes = IOUtils.toByteArray(inputStream);
            
            BASE64Encoder encoder = new BASE64Encoder();
            fileBase64 = new BASE64Encoder().encode(bytes);                        
            
        } catch(FileNotFoundException ex){            
        } catch (IOException ex) {            
        } finally{
            return fileBase64;
        }
                
    }
}
