/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.generic.ftp;

import java.io.IOException;
import org.apache.commons.net.ftp.FTPClient;

/**
 *
 * @author kemalsamikaraca
 */
public class FTPHandler {
    
    public static String ftpHost = "188.226.240.230";
    public static String ftpUsername = "guppyftp";
    public static String ftpPassword = "guppyftp";
    
    public static final String dirTemps         = "images/temps/";
    public static final String dirProducts      = "images/products/";
    public static final String dirSections      = "images/sections/";
    public static final String dirBrands        = "images/brands/";
    
    private static FTPClient ftpClient;
            
        
    
    public static FTPClient getFTPClient() throws IOException{
       
        ftpClient = (ftpClient==null ? new FTPClient() : ftpClient);
        
        if(!ftpClient.isConnected()){
            ftpClient.connect(ftpHost);
        }
    
        if(ftpClient.login(ftpUsername, ftpPassword)){
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE); 
            ftpClient.enterLocalPassiveMode();
            return ftpClient;
        }        
        
        return null;
    }  
    
    
    public static void closeFTPClient() throws IOException{
        ftpClient.logout();
        ftpClient.disconnect();
    }
    
    
    public static String getFTP_URL(String directory){
        return String.format("ftp://%s:%s@%s/%s", ftpUsername, ftpPassword, ftpHost,directory);
    }
    
}
