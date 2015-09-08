/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.generic.util;

import java.util.UUID;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Kemal Sami KARACA
 * @since 08.09.2015
 * @version 1.01
 * 
 * @last 08.09.2015
 */
public class Util {
    
    
    public static enum ContentType{
        MULTIPART,
        URL_ENCODED
    }
    
    
    /**
     * 
     * @return id String
     */
    public static String generateID(){
        return UUID.randomUUID().toString();
    }
    
    
    
    
    
    /*
    ----------------------------------------------------------------------------
    ----------------------------------------------------------------------------
                                REQUEST HEADER
    ----------------------------------------------------------------------------
    ----------------------------------------------------------------------------*/
    public static ContentType getContentType(HttpServletRequest request){
        
        if(request.getHeader("content-type")!=null && (request.getHeader("content-type").indexOf("multipart/form-data") >= 0))
            return ContentType.MULTIPART;
        
        return null;
    }
    
    
    
    
    /*
    ----------------------------------------------------------------------------
    ----------------------------------------------------------------------------
                                URL VALUES 
    ----------------------------------------------------------------------------
    ----------------------------------------------------------------------------*/        
    /**
     * @param request
     * @return baseURL String
     */
    public static String getBaseURL(HttpServletRequest request){
        
            StringBuffer url = request.getRequestURL();
            String uri = request.getRequestURI();
            String ctx = request.getContextPath();
            
        return url.substring(0, url.length() - uri.length() + ctx.length());        
    }
    
    
}
