/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.generic.test;

import com.generic.db.DBOrder;
import com.generic.result.Result;

/**
 *
 * @author kemal
 */
public class CarpeTester {
    
    public static void main(String[] args){
        
        Result res = DBOrder.getCartInfo("or_71e57d9d-05fe-423d-b8c7-ab32778f8d1c");
        
        System.out.println(res.getResultCode());
        System.out.println(res.getResultText());
        
    }
}
