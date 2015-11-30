/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.generic.entity;

import java.util.ArrayList;

/**
 *
 * @author Kemal Sami KARACA
 * @since 25.11.2015
 * @version 1.01
 * 
 * @last 25.11.2015
 */
public class User extends Person{
    
    private String id;
    private ArrayList<Address> userAddress;

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the userAddress
     */
    public ArrayList<Address> getUserAddress() {
        return userAddress;
    }

    /**
     * @param userAddress the userAddress to set
     */
    public void setUserAddress(ArrayList<Address> userAddress) {
        this.userAddress = userAddress;
    }
    
}
