/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.generic.constant;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Guppy Org.
 * @since 13.11.2015
 * @version 1.01
 * 
 * @last 13.11.2015
 */
public enum UserRole {
    
    CUSTOMER(1000), 
    MARKET_DISTRIBUTER_ADMIN(110),
    MARKET_ADMIN(100),
    ADMIN(10);
    
    private int userRoleNo;

    private static Map<Integer, UserRole> map = new HashMap<Integer, UserRole>();
    
    static {
        for (UserRole userEnum : UserRole.values()) {
            map.put(userEnum.userRoleNo, userEnum);
        }
    }
    
    private UserRole(final int roleValue){ 
        userRoleNo = roleValue; 
    }

    public static UserRole valueOf(int roleValue) {
        return map.get(roleValue);
    }
    
    public static int intValue(UserRole userRole){
        int userRoleValue = -1;
        mapLoop:
        for (Map.Entry<Integer, UserRole> entry : map.entrySet()) {
            if(entry.getValue()==userRole){
                userRoleValue = entry.getKey();
                break mapLoop;
            }
        }
        
        return userRoleValue;
    }
    
}
