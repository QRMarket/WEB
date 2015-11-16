/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.generic.checker;

import com.generic.constant.UserRole;
import java.util.Calendar;
import java.util.regex.Pattern;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Kemal Sami KARACA
 * @since 02.2015
 * @version 1.01
 * 
 * @last 12.03.2015
 */
public class Checker {
    
    private boolean isAuthentication=false;
    
    public Checker(){        
                
    }
    
    
    
    //**************************************************************************
    //**************************************************************************
    //**                    CHECK AUTHANTICATION
    //**************************************************************************
    //**************************************************************************
    
    /**
     * 
     * @param HttpServletRequest request parameter will be used to get session 
     * @return 
     */
    public static boolean isAuth(HttpServletRequest request , HttpSession session){                
        //LoggerGuppy.verboseHeader(request);
        
        Cookie[] cookies = request.getCookies();
        
        if(!Checker.anyNull(session,cookies)){
                 
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
    
    
    
    
    
    //**************************************************************************
    //**************************************************************************
    //**                    CHECK USER-AGENTs
    //**************************************************************************
    //**************************************************************************
    
    /**
     * 
     * @param HttpServletRequest request parameter will be used to get session 
     * @return 
     */
    public static boolean isUserAgentBrowser(HttpServletRequest request){
                
        // Check Agent-type is Browser 
        if(request.getHeader("user-agent")!=null){
            
            try{
                return Pattern.compile("(.*)(Mozilla|Chrome|Safari)(.*)").matcher(request.getHeader("user-agent")).find();
            } catch(NullPointerException e){
                return false;
            }            
        }                        
        
        return false;        
    }
    
    /**
     * 
     * @param HttpServletRequest request parameter will be used to get session 
     * @return 
     */
    public static boolean isUserAgentMobileApp(HttpServletRequest request){
                
        // Check Agent-type is Browser 
        if(request.getHeader("user-agent")!=null){
            
            try{
                return Pattern.compile("(.*)(guppy-mobile)(.*)").matcher(request.getHeader("user-agent")).find();
            } catch(NullPointerException e){
                return false;
            }            
        }                        
        
        return false;        
    }
    
    
    
    
    
    //**************************************************************************
    //**************************************************************************
    //**                    CHECK ANYNULL/ALLNULL
    //**************************************************************************
    //**************************************************************************
    
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
    
    /**
     * 
     * @param args
     * @return 
     * 
     * This function check that all parameter is null 
     */
    public static <T> boolean allNull(T... args){
        
        boolean allNull=true;
        for(T arg:args)
            allNull = allNull && ( arg==null );
                
        return allNull;
    }
    
    /**
     * 
     * @param args
     * @return 
     * 
     * This function check that user has role 
     */
    public static <T> boolean hasRole(UserRole userRole, T... args){
        
        boolean hasRole=false;
        for(T arg:args)
            hasRole = hasRole || ( arg==userRole );
                
        return hasRole;
    }
    
    
    
    
    //**************************************************************************
    //**************************************************************************
    //**                    CHECK VALID DATE
    //**************************************************************************
    //**************************************************************************
    
    /**
     * 
     * @param args
     * @return 
     * 
     * This function check that given date is proper or not 
     */
    public static <T> boolean isValidDate(long date){
                
        boolean proper=true;
        Calendar calendar = Calendar.getInstance();
        
        // maxDelay == 1min
        int maxDelay = 1000*60;               
        if(Math.abs(calendar.getTimeInMillis() - date) < maxDelay){
            return proper;
        }
                
        return !proper;
    }
    
    
    
    
    
}
