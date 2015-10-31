/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.generic.util;

/**
 *
 * @author Kemal Sami KARACA
 * @since 25.03.2015
 * @version 1.01
 * 
 * @last 25.03.2015
 */
public class MarketProductImage {
    
    
    private String imageID;
    private String imageProductID;      // image owner id       
    private String imageSource;
    private String imageSourceType;     // base64, url, ...
    private String imageContentType;    // image/png
    private String imageType;           // tumbnail,normal, ...    
    
    public MarketProductImage(){
        
    }

    /**
     * @return the imageID
     */
    public String getImageID() {
        return imageID;
    }

    /**
     * @param imageID the imageID to set
     */
    public void setImageID(String imageID) {
        this.imageID = imageID;
    }

    /**
     * @return the imageSource
     */
    public String getImageSource() {
        return imageSource;
    }

    /**
     * @param imageSource the imageSource to set
     */
    public void setImageSource(String imageSource) {
        this.imageSource = imageSource;
    }

    /**
     * @return the imageSourceType
     */
    public String getImageSourceType() {
        return imageSourceType;
    }

    /**
     * @param imageSourceType the imageSourceType to set
     */
    public void setImageSourceType(String imageSourceType) {
        this.imageSourceType = imageSourceType;
    }

    /**
     * @return the imageContentType
     */
    public String getImageContentType() {
        return imageContentType;
    }

    /**
     * @param imageContentType the imageContentType to set
     */
    public void setImageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
    }

    /**
     * @return the imageType
     */
    public String getImageType() {
        return imageType;
    }

    /**
     * @param imageType the imageType to set
     */
    public void setImageType(String imageType) {
        this.imageType = imageType;
    }

    /**
     * @return the imageProductID
     */
    public String getImageProductID() {
        return imageProductID;
    }

    /**
     * @param imageProductID the imageProductID to set
     */
    public void setImageProductID(String imageProductID) {
        this.imageProductID = imageProductID;
    }
    
    
    
}
