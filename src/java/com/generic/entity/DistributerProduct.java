/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.generic.entity;

/**
 *
 * @author kemalsamikaraca
 */
public class DistributerProduct {
    
    
    private String id;
    private String distributerID;
    private String productID;
    private Double productPrice;
    private String productPriceType;    
    private MarketProduct product;          // One-To-One Relation Properties

    public DistributerProduct(){}

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
     * @return the product
     */
    public MarketProduct getProduct() {
        return product;
    }

    /**
     * @param product the product to set
     */
    public void setProduct(MarketProduct product) {
        this.product = product;
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
     * @return the distributerID
     */
    public String getDistributerID() {
        return distributerID;
    }

    /**
     * @param distributerID the distributerID to set
     */
    public void setDistributerID(String distributerID) {
        this.distributerID = distributerID;
    }

    /**
     * @return the productPrice
     */
    public Double getProductPrice() {
        return productPrice;
    }

    /**
     * @param productPrice the productPrice to set
     */
    public void setProductPrice(Double productPrice) {
        this.productPrice = productPrice;
    }

    /**
     * @return the productPriceType
     */
    public String getProductPriceType() {
        return productPriceType;
    }

    /**
     * @param productPriceType the productPriceType to set
     */
    public void setProductPriceType(String productPriceType) {
        this.productPriceType = productPriceType;
    }
    
    
    
}
