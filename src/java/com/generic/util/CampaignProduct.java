/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.generic.util;

/**
 *
 * @author ulakbim
 */
public class CampaignProduct extends MarketProduct{
    private long c_start_date;
    private long c_end_date;
    private double c_price;
    /**
     * @return the c_start_date
     */
    public long getC_start_date() {
        return c_start_date;
    }

    /**
     * @param c_start_date the c_start_date to set
     */
    public void setC_start_date(long c_start_date) {
        this.c_start_date = c_start_date;
    }

    /**
     * @return the c_end_date
     */
    public long getC_end_date() {
        return c_end_date;
    }

    /**
     * @param c_end_date the c_end_date to set
     */
    public void setC_end_date(long c_end_date) {
        this.c_end_date = c_end_date;
    }

    /**
     * @return the c_price
     */
    public double getC_price() {
        return c_price;
    }

    /**
     * @param c_price the c_price to set
     */
    public void setC_price(double c_price) {
        this.c_price = c_price;
    }
    
}
