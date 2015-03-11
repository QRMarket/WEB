/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.generic.util;

/**
 *
 * @author Kemal Sami KARACA
 */
public class MarketUser {
    
    
    private String userMail;
    private String userName;   
    private String userType;
    private String userToken;
    private String userSession;
    
    public MarketUser(){
        
    }
    
    public MarketUser(String userMail, String userName){
        this.userMail = userMail;
        this.userName = userName;
        
    }
    

    /**
     * @return the userMail
     */
    public String getUserMail() {
        return userMail;
    }

    /**
     * @param userMail the userMail to set
     */
    public void setUserMail(String userMail) {
        this.userMail = userMail;
    }
    
    /**
     * @return the userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @param userName the userName to set
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * @return the userType
     */
    public String getUserType() {
        return userType;
    }

    /**
     * @param userType the userType to set
     */
    public void setUserType(String userType) {
        this.userType = userType;
    }

    /**
     * @return the userToken
     */
    public String getUserToken() {
        return userToken;
    }

    /**
     * @param userToken the userToken to set
     */
    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    /**
     * @return the userSession
     */
    public String getUserSession() {
        return userSession;
    }

    /**
     * @param userSession the userSession to set
     */
    public void setUserSession(String userSession) {
        this.userSession = userSession;
    }
    
}
