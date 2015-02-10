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
    
    public Checker(HttpServletRequest request){
        HttpSession session = request.getSession(true);
                
    }
    
    
    
}
