/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.generic.util;

import java.util.List;

/**
 *
 * @author Kemal Sami KARACA
 * @since 14.03.2015
 * @version 1.01
 * 
 * @last 13.04.2015
 */
public class MarketOrder {
        
    private String orderID;
    private List<MarketProduct> products;    
    private String adressID;
    private String paymentType;
    private String note;
    private String date;    
    private String companyName;
    private Address address;
    
    public MarketOrder(){
        
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
     * @return the products
     */
    public List<MarketProduct> getProducts() {
        return products;
    }

    /**
     * @param products the products to set
     */
    public void setProducts(List<MarketProduct> products) {
        this.products = products;
    }

    /**
     * @return the adressID
     */
    public String getAdressID() {
        return adressID;
    }

    /**
     * @param adressID the adressID to set
     */
    public void setAdressID(String adressID) {
        this.adressID = adressID;
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
    public String getDate() {
        return date;
    }

    /**
     * @param date the date to set
     */
    public void setDate(String date) {
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
    
    
    
    
}
