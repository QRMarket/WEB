package com.generic.entity;

/**
 *
 * @author Kemal Sami KARACA
 * @since 10.03.2015
 * @version 1.01
 * 
 * @last 13.04.2015
 */
public class Address  {
    
    
    private String aid;
    private String city;
    private String borough;
    private String locality;
    private String street;
    private String avenue;
    private String desc;

    public Address(){
        
    }
    
    /**
     * @return the city
     */
    public String getCity() {
        return city;
    }

    /**
     * @param city the city to set
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * @return the borough
     */
    public String getBorough() {
        return borough;
    }

    /**
     * @param borough the borough to set
     */
    public void setBorough(String borough) {
        this.borough = borough;
    }

    /**
     * @return the locality
     */
    public String getLocality() {
        return locality;
    }

    /**
     * @param locality the locality to set
     */
    public void setLocality(String locality) {
        this.locality = locality;
    }

    /**
     * @return the street
     */
    public String getStreet() {
        return street;
    }

    /**
     * @param street the street to set
     */
    public void setStreet(String street) {
        this.street = street;
    }

    /**
     * @return the avenue
     */
    public String getAvenue() {
        return avenue;
    }

    /**
     * @param avenue the avenue to set
     */
    public void setAvenue(String avenue) {
        this.avenue = avenue;
    }

    /**
     * @return the desc
     */
    public String getDesc() {
        return desc;
    }

    /**
     * @param desc the desc to set
     */
    public void setDesc(String desc) {
        this.desc = desc;
    }

    /**
     * @return the aid
     */
    public String getAid() {
        return aid;
    }

    /**
     * @param aid the aid to set
     */
    public void setAid(String aid) {
        this.aid = aid;
    }
    
    
    
}
