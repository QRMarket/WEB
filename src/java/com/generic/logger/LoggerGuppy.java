/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.generic.logger;

import java.util.Enumeration;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Kemal Sami KARACA
 * @since 12.03.2015
 * @version 1.01
 * 
 * @last 12.03.2015
 */
public class LoggerGuppy {
    
    
    
    
    /**
     * 
     * @param request 
     */
    public static void verboseHeader(HttpServletRequest request){
        Enumeration<String> enumeHeader = request.getHeaderNames();
        while(enumeHeader.hasMoreElements()){
            String headerKey = enumeHeader.nextElement();            
            System.out.println( "HEADER -- " + headerKey + " :: " + request.getHeader(headerKey));
        }
    }
    
    /**
     * 
     * @param request 
     */
    public static void verboseURL(HttpServletRequest request){
        
        System.out.println("URL \t\t--> " + request.getScheme() + "://" + 
                                                            request.getServerName() +":"+
                                                            request.getServerPort() +
                                                            request.getRequestURI() + 
                                                            (request.getQueryString()==null?"":"?" + request.getQueryString()) );
        
        Enumeration<String> enume = request.getParameterNames();
        while(enume.hasMoreElements()){
            String key = enume.nextElement();
            System.out.println("Incoming parameter  --> " + key + " :: " + request.getParameter(key));
        }         
    }
    
    
    
    
}
