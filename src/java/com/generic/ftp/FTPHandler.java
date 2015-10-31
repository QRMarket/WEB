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
    
    private static String ftpHost = "188.226.240.230";
    private static String ftpUsername = "guppyftp";
    private static String ftpPassword = "guppyftp";
    
    private static FTPClient ftpClient;
            
        
    
    public static FTPClient getFTPClient() throws IOException{
       
        ftpClient = (ftpClient==null ? new FTPClient() : ftpClient);
        
        if(!ftpClient.isConnected()){
            ftpClient.connect(ftpHost);
        }
    
        if(ftpClient.login(ftpUsername, ftpPassword)){
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);  
            return ftpClient;
        }        
        
        return null;
    }  
    
    
    public static void closeFTPClient() throws IOException{
        ftpClient.logout();
        ftpClient.disconnect();
    }
    
    
    public static String getFTP_URL(){
        return "ftp://" + ftpUsername + ":" + ftpPassword + "@" + ftpHost + "/";
    }
    
}
