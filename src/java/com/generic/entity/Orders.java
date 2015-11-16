/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.generic.entity;

import java.util.List;

/**
 *
 * @author Kemal Sami KARACA
 * @since 14.03.2015
 * @version 1.01
 * 
 * @last 13.04.2015
 */
public class Orders {
        
    private String orderID;
    private String paymentType;
    private String note;
    private Long date;  
    private Long delay;  
    private String companyName;         // ???
    private Address address;            // ???
    // -- ** -- // One-To-Many Relation Properties
    private List<OrderProduct> orderProductList;
    // -- ** -- // Many-To-One Relation Properties
    private String userID;
    private String distributerAddressID;
    
    public Orders(){
        
    }
        
    /**
     * @return the orderID
     */
    public String getOrderID() {
        return orderID;
    }

    /**
     * @param orderID the orderID to set
     */
    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }
    

    /**
     * @return the paymentType
     */
    public String getPaymentType() {
        return paymentType;
    }

    /**
     * @param paymentType the paymentType to set
     */
    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    /**
     * @return the note
     */
    public String getNote() {
        return note;
    }

    /**
     * @param note the note to set
     */
    public void setNote(String note) {
        this.note = note;
    }

    /**
     * @return the date
     */
    public Long getDate() {
        return date;
    }

    /**
     * @param date the date to set
     */
    public void setDate(Long date) {
        this.date = date;
    }

    /**
     * @return the companyName
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * @param companyName the companyName to set
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * @return the address
     */
    public Address getAdress() {
        return address;
    }

    /**
     * @param adress the address to set
     */
    public void setAdress(Address adress) {
        this.address = adress;
    }

    /**
     * @return the orderProductList
     */
    public List<OrderProduct> getOrderProductList() {
        return orderProductList;
    }

    /**
     * @param orderProductList the orderProductList to set
     */
    public void setOrderProductList(List<OrderProduct> orderProductList) {
        this.orderProductList = orderProductList;
    }

    /**
     * @return the userID
     */
    public String getUserID() {
        return userID;
    }

    /**
     * @param userID the userID to set
     */
    public void setUserID(String userID) {
        this.userID = userID;
    }

    /**
     * @return the distributerAddressID
     */
    public String getDistributerAddressID() {
        return distributerAddressID;
    }

    /**
     * @param distributerAddressID the distributerAddressID to set
     */
    public void setDistributerAddressID(String distributerAddressID) {
        this.distributerAddressID = distributerAddressID;
    }

    /**
     * @return the delay
     */
    public Long getDelay() {
        return delay;
    }

    /**
     * @param delay the delay to set
     */
    public void setDelay(Long delay) {
        this.delay = delay;
    }
    
    
    
    
}
