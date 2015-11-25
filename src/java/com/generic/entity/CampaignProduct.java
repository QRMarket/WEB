/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.generic.entity;

/**
 *
 * @author Kemal Sami KARACA
 * @since 03.2015
 * @version 1.01
 *
 * @last 24.11.2015
 */
public class CampaignProduct extends CompanyProduct{
    
    private String id;
    private String companyProductId;
    private long startAt;
    private long finishAt;
    private double campaignPrice;

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
     * @return the startAt
     */
    public long getStartAt() {
        return startAt;
    }

    /**
     * @param startAt the startAt to set
     */
    public void setStartAt(long startAt) {
        this.startAt = startAt;
    }

    /**
     * @return the finishAt
     */
    public long getFinishAt() {
        return finishAt;
    }

    /**
     * @param finishAt the finishAt to set
     */
    public void setFinishAt(long finishAt) {
        this.finishAt = finishAt;
    }

    /**
     * @return the campaignPrice
     */
    public double getCampaignPrice() {
        return campaignPrice;
    }

    /**
     * @param campaignPrice the campaignPrice to set
     */
    public void setCampaignPrice(double campaignPrice) {
        this.campaignPrice = campaignPrice;
    }

    /**
     * @return the companyProductId
     */
    public String getCompanyProductId() {
        return companyProductId;
    }

    /**
     * @param companyProductId the companyProductId to set
     */
    public void setCompanyProductId(String companyProductId) {
        this.companyProductId = companyProductId;
    }
    
    
    
    
    
}
