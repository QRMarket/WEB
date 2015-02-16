/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.generic.util;

/**
 *
 * @author Kemal Sami KARACA
 */
public class MarketProduct {
    
    private String productID;
    private String productName;
    private String priceType;
    private double price; 
    
    public MarketProduct(String productID , String productName, String productionType , double price){
        this.productID = productID;
        this.productName = productName;
        this.priceType = productionType;
        this.price = price;
    }
    

    /**
     * @return the productID
     */
    public String getProductID() {
        return productID;
    }

    /**
     * @param productID the productID to set
     */
    public void setProductID(String productID) {
        this.productID = productID;
    }

    /**
     * @return the productName
     */
    public String getProductName() {
        return productName;
    }

    /**
     * @param productName the productName to set
     */
    public void setProductName(String productName) {
        this.productName = productName;
    }

    /**
     * @return the priceType
     */
    public String getPriceType() {
        return priceType;
    }

    /**
     * @param priceType the priceType to set
     */
    public void setPriceType(String priceType) {
        this.priceType = priceType;
    }

    /**
     * @return the price
     */
    public double getPrice() {
        return price;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(double price) {
        this.price = price;
    }
    
}
