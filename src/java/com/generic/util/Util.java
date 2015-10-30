/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.generic.util;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import org.apache.commons.io.IOUtils;

/**
 *
 * @author Kemal Sami KARACA
 * @since 08.09.2015
 * @version 1.01
 * 
 * @last 08.09.2015
 */
public class Util {
    
    public static final String DirectoryImage = "/images";
        
    
    public static enum ContentType{
        NULL,        
        MULTIPART_FORM_DATA,
        APPLICATION_FORM_URLENCODED,
        TEXT_PLAIN,
        TEXT_XML,
        TEXT_HTML,
        APPLICATION_JSON,
        APPLICATION_JAVASCRIPT,
        APPLICATION_XML
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
                            REQUEST CONTENT-TYPE :: MULTIPART
    ----------------------------------------------------------------------------
    ----------------------------------------------------------------------------*/
    public static Map getParameterFromParts(Collection<Part> parts){
                
        // -1- Initialize
        Map parameters = new HashMap();        
        Iterator<Part> iterator = parts.iterator();
        
        // -2- Iterate multipart element
        try {
            
                while ( iterator.hasNext() ){

                    Part part = iterator.next();
                                        
                    // -2.1- Get form field 
                    if(part.getContentType()==null){
                        parameters.put(part.getName(), IOUtils.toString(part.getInputStream()));
                      
                    // -2.2- Get file as base64
                    }else{                        
                        // -?????-
                        // -2.2.1- IT CAN BE PUT "BASE64 STRING" INSTEAD "PART" TYPE
                        parameters.put(part.getName(), part);
                    }
                }
                
        } catch (IOException ex) {
            Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
            parameters = null;
        }
        
        return parameters;
        
    }
    
    
    
    
    
    /*
    ----------------------------------------------------------------------------
    ----------------------------------------------------------------------------
                                REQUEST HEADER
    ----------------------------------------------------------------------------
    ----------------------------------------------------------------------------*/
    public static ContentType getContentType(HttpServletRequest request){
        
        if(request.getHeader("content-type")!=null){
            if(request.getHeader("content-type").indexOf("multipart/form-data") >= 0){
                return ContentType.MULTIPART_FORM_DATA;
            }else if( request.getHeader("content-type").indexOf("application/x-www-form-urlencoded") >= 0 ){
                return ContentType.APPLICATION_FORM_URLENCODED;
            }else if( request.getHeader("content-type").indexOf("text/plain") >= 0 ){
                return ContentType.TEXT_PLAIN;
            }else if( request.getHeader("content-type").indexOf("text/xml") >= 0 ){
                return ContentType.TEXT_XML;
            }else if( request.getHeader("content-type").indexOf("text/html") >= 0 ){
                return ContentType.TEXT_HTML;
            }else if( request.getHeader("content-type").indexOf("application/json") >= 0 ){
                return ContentType.APPLICATION_JSON;
            }else if( request.getHeader("content-type").indexOf("application/javascript") >= 0 ){
                return ContentType.APPLICATION_JAVASCRIPT;
            }else if( request.getHeader("content-type").indexOf("application/xml") >= 0 ){
                return ContentType.APPLICATION_XML;
            } 
            
        }            
        
        return ContentType.NULL;
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
