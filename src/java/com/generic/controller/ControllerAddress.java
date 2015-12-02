/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.generic.controller;

import com.generic.checker.Checker;
import com.generic.constant.CharsetList;
import com.generic.modal.DBAddress;
import com.generic.locale.UtilLocaleHandler;
import com.generic.result.Result;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Guppy Org.
 * @since 11.2015
 * @version 1.01
 * 
 * @last 15.11.2015
 */
public class ControllerAddress {
    
    // <editor-fold defaultstate="collapsed" desc="GET Operations">
    /**
     * 
     * @param request
     * @return 
     */
    public static Result getAddressById(HttpServletRequest request){
        
            Result result = Result.FAILURE_PROCESS;

            String address_id = request.getParameter("id");
            if(!Checker.anyNull(address_id)){
                return DBAddress.getAddressById(address_id);
            }else{
                result = Result.FAILURE_PARAM_MISMATCH.setContent("ControllerAddress -> getAddressById -> address id required");
            }
        
        return result;        
    }
    
    
    /**
     * 
     * @param request
     * @return 
     */
    public static Result searchAddress(HttpServletRequest request){
        
            Result result = Result.FAILURE_PROCESS;

        // -- 1 -- Get Parameters
            String city = request.getParameter("city");
            String borough = request.getParameter("borough");
            String locality = request.getParameter("locality");

            if(!Checker.allNull(city,borough,locality)){
                // Convert to Turkish charset    
                city = UtilLocaleHandler.useCharset(city, CharsetList.TR);
                borough = UtilLocaleHandler.useCharset(borough, CharsetList.TR);
                locality = UtilLocaleHandler.useCharset(locality, CharsetList.TR);
                
                return DBAddress.searchAddress(city, borough, locality);
            }else{
                result = Result.FAILURE_PARAM_MISMATCH.setContent("ControllerAddress -> searchAddress -> all parameters are null");
            }
        
        return result;
    }
    
    
    /**
     * 
     * @return 
     */
    public static Result getCityList(){
        return DBAddress.getCityList();
    }
    
    
    /**
     * 
     * @return 
     */
    public static Result getBoroughList(HttpServletRequest request){
        
            Result result = Result.FAILURE_PROCESS;
            
        // -- 1 -- Get Parameters
            String cityName = request.getParameter("cityName");

            if(!Checker.anyNull(cityName)){
                
                // Convert to Turkish charset    
                cityName = UtilLocaleHandler.useCharset(cityName, CharsetList.TR);
                
                return DBAddress.getBoroughList(cityName);
            }else{
                result = Result.FAILURE_PARAM_MISMATCH.setContent("ControllerAddress -> getBoroughList -> null pointer exception");
            }
        
        return result;
    }
    
    
    /**
     * 
     * @param request
     * @return 
     */
    public static Result getLocalityList(HttpServletRequest request){
        
            Result result = Result.FAILURE_PROCESS;

        // -- 1 -- Get Parameters
            String cityName = request.getParameter("cityName");
            String boroughName = request.getParameter("boroughName");
       
            if(!Checker.anyNull(cityName, boroughName)){
                
                // Convert to Turkish charset    
                cityName = UtilLocaleHandler.useCharset(cityName, CharsetList.TR);
                boroughName = UtilLocaleHandler.useCharset(boroughName, CharsetList.TR);
                
                return DBAddress.getLocalityList(cityName,boroughName);
                
            }else{
                result = Result.FAILURE_PARAM_MISMATCH.setContent("ControllerAddress -> getBoroughList -> null pointer exception");
            }
        
        return result;
    }
    // </editor-fold>
    
}
