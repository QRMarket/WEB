/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.generic.entity;

import java.sql.Time;
import java.util.List;

/**
 *
 * @author Kemal Sami KARACA
 * @since 21.11.2015
 * @version 1.01
 * 
 * @last 21.11.2015
 */
public class Distributer {
    
        private String id;
        private String companyId;
        private Time openHourAt;
        private Time closeHourAt;
        private int state;
    // -- ** -- // Other Object
        private DistributerAddress distributerAddress;
        private List<DistributerAddress> distributerAddressList;

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
     * @return the companyId
     */
    public String getCompanyId() {
        return companyId;
    }

    /**
     * @param companyId the companyId to set
     */
    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    /**
     * @return the distributerAddressList
     */
    public List<DistributerAddress> getDistributerAddressList() {
        return distributerAddressList;
    }

    /**
     * @param distributerAddressList the distributerAddressList to set
     */
    public void setDistributerAddressList(List<DistributerAddress> distributerAddressList) {
        this.distributerAddressList = distributerAddressList;
    }

    /**
     * @return the distributerAddress
     */
    public DistributerAddress getDistributerAddress() {
        return distributerAddress;
    }

    /**
     * @param distributerAddress the distributerAddress to set
     */
    public void setDistributerAddress(DistributerAddress distributerAddress) {
        this.distributerAddress = distributerAddress;
    }

    /**
     * @return the openHourAt
     */
    public Time getOpenHourAt() {
        return openHourAt;
    }

    /**
     * @param openHourAt the openHourAt to set
     */
    public void setOpenHourAt(Time openHourAt) {
        this.openHourAt = openHourAt;
    }

    /**
     * @return the closeHourAt
     */
    public Time getCloseHourAt() {
        return closeHourAt;
    }

    /**
     * @param closeHourAt the closeHourAt to set
     */
    public void setCloseHourAt(Time closeHourAt) {
        this.closeHourAt = closeHourAt;
    }

    /**
     * @return the state
     */
    public int getState() {
        return state;
    }

    /**
     * @param state the state to set
     */
    public void setState(int state) {
        this.state = state;
    }

    
}
