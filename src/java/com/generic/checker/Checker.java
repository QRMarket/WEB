/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.generic.checker;

import javax.servlet.http.Cookie;
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
        Cookie[] cookies = request.getCookies();
        
        if(cookies != null){
                    
            Cookie cduCookie=null;
            String cduToken = (String) session.getAttribute("cduToken");            

            for(Cookie cookie : cookies){
                if(cookie.getName().equalsIgnoreCase("cduToken")){
                    cduCookie = cookie;
                }
            }                                            

            return !Checker.anyNull(cduCookie,cduToken) ? cduCookie.getValue().equalsIgnoreCase(cduToken):false;            
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
    public static <T> boolean anyNull(T... args){
        
        boolean notNull=true;
        for(T arg:args)
            notNull = notNull && ( arg!=null );
                
        return !notNull;
    }
    
    
    
    
    
}
