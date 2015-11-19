/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.generic.test;

import com.generic.constant.UserRole;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import org.apache.commons.io.IOUtils;
import sun.misc.BASE64Encoder;

/**
 *
 * @author kemal
 */
public class CarpeTester {
    
    public static void main(String[] args){
                
        System.out.println( UserRole.intValue(UserRole.ADMIN) );
        
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
