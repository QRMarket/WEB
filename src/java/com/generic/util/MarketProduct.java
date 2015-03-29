/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.generic.util;

import java.util.ArrayList;

/**
 *
 * @author Kemal Sami KARACA
 * @since 03.2015
 * @version 1.01
 * 
 * @last 24.03.2015
 */
public class MarketProduct {
    
    private String productID;
    private String productName;
    private String productCode;
    private String productDesc;
    private String branchName;
    private String priceType;
    private double price; 
    private double amount;
    private ArrayList<MarketProductImage> productImages;
    
    /**
     * @deprecated 
     */
    private ArrayList<String> images;   
    
    
    public MarketProduct(){}
    
    public MarketProduct(String productID , double amount){
        this.productID = productID;
        this.amount = amount;
        this.images = new ArrayList();
    }
    
    public MarketProduct(String productID , String productName, String productionType , double price){
        this.productID = productID;
        this.productName = productName;
        this.priceType = productionType;
        this.price = price;        
        this.images = new ArrayList();
    }
    
    public MarketProduct(String productID , String productName, String productionType , double price , double amount){
        this.productID = productID;
        this.productName = productName;
        this.priceType = productionType;
        this.price = price;
        this.amount = amount;
        this.images = new ArrayList();
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

    /**
     * @return the amount
     */
    public double getAmount() {
        return amount;
    }

    /**
     * @param amount the amount to set
     */
    public void setAmount(double amount) {
        this.amount = amount;
    }

    /**
     * @return the images
     */
    public ArrayList<String> getImages() {
        return images;
    }

    /**
     * @param images the images to set
     */
    public void setImages(ArrayList<String> images) {
        this.images = images;
    }

    /**
     * @return the branchName
     */
    public String getBranchName() {
        return branchName;
    }

    /**
     * @param branchName the branchName to set
     */
    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    /**
     * @return the productCode
     */
    public String getProductCode() {
        return productCode;
    }

    /**
     * @param productCode the productCode to set
     */
    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    /**
     * @return the productDesc
     */
    public String getProductDesc() {
        return productDesc;
    }

    /**
     * @param productDesc the productDesc to set
     */
    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
    }

    /**
     * @return the productImages
     */
    public ArrayList<MarketProductImage> getProductImages() {        
        return productImages==null ? productImages=new ArrayList() : productImages;
    }

    /**
     * @param productImages the productImages to set
     */
    public void setProductImages(ArrayList<MarketProductImage> productImages) {
        this.productImages = productImages;
    }
    
}
