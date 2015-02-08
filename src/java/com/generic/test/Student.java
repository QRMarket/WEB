/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.generic.test;

/**
 *
 * @author kemal
 */
public class Student {
    
    public String name;
    public String surname;
    
    public Student(){
        this.name = "kemal";
        this.surname = "karaca";
    }
    
    @Override
    public String toString(){
        return "name : "+ this.name + " -- surname : "+ this.surname;
    }
    
}
