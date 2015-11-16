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
public class CompanyProduct {
    
    
    private String id;
    private String product_id;
    private String distributer_id;
    private Double product_price;
    private String product_price_type;

    public CompanyProduct(){}

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
     * @return the product_id
     */
    public String getProduct_id() {
        return product_id;
    }

    /**
     * @param product_id the product_id to set
     */
    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    /**
     * @return the distributer_id
     */
    public String getDistributer_id() {
        return distributer_id;
    }

    /**
     * @param distributer_id the distributer_id to set
     */
    public void setDistributer_id(String distributer_id) {
        this.distributer_id = distributer_id;
    }

    /**
     * @return the product_price
     */
    public Double getProduct_price() {
        return product_price;
    }

    /**
     * @param product_price the product_price to set
     */
    public void setProduct_price(Double product_price) {
        this.product_price = product_price;
    }

    /**
     * @return the product_price_type
     */
    public String getProduct_price_type() {
        return product_price_type;
    }

    /**
     * @param product_price_type the product_price_type to set
     */
    public void setProduct_price_type(String product_price_type) {
        this.product_price_type = product_price_type;
    }
    
    
    
}
