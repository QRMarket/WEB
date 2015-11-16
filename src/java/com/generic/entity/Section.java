/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.generic.entity;

/**
 *
 * @author ulakbim
 */
public class Section {
    private String sid;
    private String sec_parent_id;
    private String sec_name;
    private String sec_image;

    /**
     * @return the sid
     */
    public String getSid() {
        return sid;
    }

    /**
     * @param sid the sid to set
     */
    public void setSid(String sid) {
        this.sid = sid;
    }

    /**
     * @return the sec_parent_id
     */
    public String getSec_parent_id() {
        return sec_parent_id;
    }

    /**
     * @param sec_parent_id the sec_parent_id to set
     */
    public void setSec_parent_id(String sec_parent_id) {
        this.sec_parent_id = sec_parent_id;
    }

    /**
     * @return the sec_name
     */
    public String getSec_name() {
        return sec_name;
    }

    /**
     * @param sec_name the sec_name to set
     */
    public void setSec_name(String sec_name) {
        this.sec_name = sec_name;
    }

    /**
     * @return the sec_image
     */
    public String getSec_image() {
        return sec_image;
    }

    /**
     * @param sec_image the sec_image to set
     */
    public void setSec_image(String sec_image) {
        this.sec_image = sec_image;
    }
    
}
