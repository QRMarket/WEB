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
public class OrderProduct {
    
    
    private String order_id;
    private String companyProduct_id;
    private Double quantity;

    public OrderProduct(){}

    /**
     * @return the order_id
     */
    public String getOrder_id() {
        return order_id;
    }

    /**
     * @param order_id the order_id to set
     */
    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    /**
     * @return the companyProduct_id
     */
    public String getCompanyProduct_id() {
        return companyProduct_id;
    }

    /**
     * @param companyProduct_id the companyProduct_id to set
     */
    public void setCompanyProduct_id(String companyProduct_id) {
        this.companyProduct_id = companyProduct_id;
    }

    /**
     * @return the quantity
     */
    public Double getQuantity() {
        return quantity;
    }

    /**
     * @param quantity the quantity to set
     */
    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }
    
}
