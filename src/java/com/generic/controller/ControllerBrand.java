/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.generic.controller;

import com.generic.checker.Checker;
import com.generic.db.DBBrand;
import com.generic.result.Result;
import com.generic.util.Brands;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author kemalsamikaraca
 */
public class ControllerBrand {
    
    
    public static Result getBrandAll(){
        
        Result result = Result.FAILURE_PROCESS;
        
        try {                                
                                    
            return DBBrand.getBrand_All();
                    
        } catch (Exception ex) {
            Logger.getLogger(ControllerProduct.class.getName()).log(Level.SEVERE, null, ex);
            result = Result.FAILURE_PROCESS.setContent(ex.toString());
        }
        
        return result;        
    }
    
    
    public static Result getBrandByID(HttpServletRequest request){
        
        try {                                
                
            // -1.1- Create Product Object
                Brands brandObj = new Brands();        
                brandObj.setId(request.getParameter("id"));                    

            // -1.2- Check Product Object Values
                if(!Checker.anyNull( brandObj.getId() )){ 
                    return DBBrand.getBrand_ById(brandObj);
                }                       
                    
        } catch (Exception ex) {
            Logger.getLogger(ControllerProduct.class.getName()).log(Level.SEVERE, null, ex);
            return Result.FAILURE_PROCESS.setContent(ex.toString());
        }
        
        return Result.FAILURE_PROCESS;    
    }
    
}
