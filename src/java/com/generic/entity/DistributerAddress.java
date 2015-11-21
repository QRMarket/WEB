/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.generic.entity;

/**
 *
 * @author Kemal Sami KARACA
 * @since 21.11.2015
 * @version 1.01
 * 
 * @last 21.11.2015
 */
public class DistributerAddress {
   
        private String id;
        private String distributerId;
        private String addressId;
        private String addressType;
        private double minimumPriceForDeliver; 

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
     * @return the distributerId
     */
    public String getDistributerId() {
        return distributerId;
    }

    /**
     * @param distributerId the distributerId to set
     */
    public void setDistributerId(String distributerId) {
        this.distributerId = distributerId;
    }

    /**
     * @return the addressId
     */
    public String getAddressId() {
        return addressId;
    }

    /**
     * @param addressId the addressId to set
     */
    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    /**
     * @return the addressType
     */
    public String getAddressType() {
        return addressType;
    }

    /**
     * @param addressType the addressType to set
     */
    public void setAddressType(String addressType) {
        this.addressType = addressType;
    }

    /**
     * @return the minimumPriceForDeliver
     */
    public double getMinimumPriceForDeliver() {
        return minimumPriceForDeliver;
    }

    /**
     * @param minimumPriceForDeliver the minimumPriceForDeliver to set
     */
    public void setMinimumPriceForDeliver(double minimumPriceForDeliver) {
        this.minimumPriceForDeliver = minimumPriceForDeliver;
    }
        
    
}
