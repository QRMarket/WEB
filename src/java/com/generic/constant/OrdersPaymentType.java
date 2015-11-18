/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.generic.constant;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Guppy Org.
 * @since 13.11.2015
 * @version 1.01
 * 
 * @last 18.11.2015
 */
public enum OrdersPaymentType {
    
    CASH(1), 
    CREDIT_CARD(2);
    
    private int orderPaymentNo;

    private static Map<Integer, OrdersPaymentType> map = new HashMap<Integer, OrdersPaymentType>();
    
    static {
        for (OrdersPaymentType stateEnum : OrdersPaymentType.values()) {
            map.put(stateEnum.orderPaymentNo, stateEnum);
        }
    }
    
    private OrdersPaymentType(final int stateValue){ 
        orderPaymentNo = stateValue; 
    }

    public static OrdersPaymentType valueOf(int stateValue) {
        return map.get(stateValue);
    }
    
    public static int intValue(OrdersPaymentType stateValue){
        int orderStateValue = -1;
        mapLoop:
        for (Map.Entry<Integer, OrdersPaymentType> entry : map.entrySet()) {
            if(entry.getValue()==stateValue){
                orderStateValue = entry.getKey();
                break mapLoop;
            }
        }
        
        return orderStateValue;
    }
    
}
