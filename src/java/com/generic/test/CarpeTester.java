/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.generic.test;

import com.generic.result.Result;

/**
 *
 * @author kemal
 */
public class CarpeTester {
    
    public static void main(String[] args){
        Result res = Result.FAILURE_DB_MONGO;
        
        Student s = new Student();
                
        
        Result resNew = res.setContent(s); 
        
        
        Student s2 = (Student) res.getContent();
        
        System.out.println(resNew.getResultCode());
        System.out.println(resNew.getResultText());
        System.out.println(resNew.getContent().toString());
    }
}
