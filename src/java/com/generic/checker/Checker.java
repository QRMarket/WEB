/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.generic.checker;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Kemal Sami KARACA
 */
public class Checker {
    
    private boolean isAuthentication=false;
    
    public Checker(){        
                
    }
    
    
    /**
     * 
     * @param HttpServletRequest request parameter will be used to get session 
     * @return 
     */
    public static boolean isAuth(HttpServletRequest request){
        
        HttpSession session = request.getSession(false);
        String token = request.getParameter("cdu-token");
        if(session!=null){            
            if(session.getAttribute("cduToken")!=null && token!=null){
                return ((String)session.getAttribute("cduToken")).equalsIgnoreCase(token);
            }                       
        }        
        return false;        
    }
    
    
    /**
     * 
     * @param args
     * @return 
     * 
     * This function check that any parameter is null 
     */
    public static boolean anyParameterNull(String... args){
        
        boolean notNull=true;
        for(String arg:args)
            notNull = notNull && ( arg!=null );
                
        return !notNull;
    }
    
    
    
    
    
}
