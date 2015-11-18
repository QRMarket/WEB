/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.generic.entity;

import com.generic.constant.OrdersPaymentType;
import com.generic.constant.OrdersState;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Kemal Sami KARACA
 * @since 14.03.2015
 * @version 1.01
 * 
 * @last 13.04.2015
 */
public class Orders {
        
        private String id;
        private String note;
        private Long date;  
        private Long delay;  
        private OrdersState state;
        private OrdersPaymentType paymentType;
        private Double payment;
    // -- ** -- // One-To-Many Relation Properties
        private List<OrderProduct> orderProductList;
    // -- ** -- // Many-To-One Relation Properties
        private String userAddressID;
        private String distributerAddressID;
        
    // -- ** -- // Other Object
        private MarketUser user;
        private UserAddress userAddress;
        private List<MarketProduct> productList;
    // -- ** -- // Generic object for append
        //    private Map<String , List<? extends Object>> GenericObjectList;
        
    public Orders(){
        
    }
    

    /**
     * @return the note
     */
    public String getNote() {
        return note;
    }

    /**
     * @param note the note to set
     */
    public void setNote(String note) {
        this.note = note;
    }

    /**
     * @return the date
     */
    public Long getDate() {
        return date;
    }

    /**
     * @param date the date to set
     */
    public void setDate(Long date) {
        this.date = date;
    }

    /**
     * @return the orderProductList
     */
    public List<OrderProduct> getOrderProductList() {
        return orderProductList;
    }

    /**
     * @param orderProductList the orderProductList to set
     */
    public void setOrderProductList(List<OrderProduct> orderProductList) {
        this.orderProductList = orderProductList;
    }

    /**
     * @return the distributerAddressID
     */
    public String getDistributerAddressID() {
        return distributerAddressID;
    }

    /**
     * @param distributerAddressID the distributerAddressID to set
     */
    public void setDistributerAddressID(String distributerAddressID) {
        this.distributerAddressID = distributerAddressID;
    }

    /**
     * @return the delay
     */
    public Long getDelay() {
        return delay;
    }

    /**
     * @param delay the delay to set
     */
    public void setDelay(Long delay) {
        this.delay = delay;
    }

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
     * @return the productList
     */
    public List<? extends Object> getProductList() {
        return productList;
    }

    /**
     * @param productList the productList to set
     */
    public void setProductList(List<MarketProduct> productList) {
        this.productList = productList;
    }

    /**
     * @return the state
     */
    public OrdersState getState() {
        return state;
    }

    /**
     * @param state the state to set
     */
    public void setState(OrdersState state) {
        this.state = state;
    }

    /**
     * @return the paymentType
     */
    public OrdersPaymentType getPaymentType() {
        return paymentType;
    }

    /**
     * @param paymentType the paymentType to set
     */
    public void setPaymentType(OrdersPaymentType paymentType) {
        this.paymentType = paymentType;
    }

    /**
     * @return the payment
     */
    public Double getPayment() {
        return payment;
    }

    /**
     * @param payment the payment to set
     */
    public void setPayment(Double payment) {
        this.payment = payment;
    }

    /**
     * @return the user
     */
    public MarketUser getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(MarketUser user) {
        this.user = user;
    }

    /**
     * @return the userAddressID
     */
    public String getUserAddressID() {
        return userAddressID;
    }

    /**
     * @param userAddressID the userAddressID to set
     */
    public void setUserAddressID(String userAddressID) {
        this.userAddressID = userAddressID;
    }

    /**
     * @return the userAddress
     */
    public UserAddress getUserAddress() {
        return userAddress;
    }

    /**
     * @param userAddress the userAddress to set
     */
    public void setUserAddress(UserAddress userAddress) {
        this.userAddress = userAddress;
    }
    
    
}
