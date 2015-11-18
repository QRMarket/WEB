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
public enum OrdersState {
    
    CANCELLED(0), 
    PENDING(1),
    PROCESSING(2),
    SHIPPED(3),
    DELIVERED(4);
    
    private int orderStateNo;

    private static Map<Integer, OrdersState> map = new HashMap<Integer, OrdersState>();
    
    static {
        for (OrdersState stateEnum : OrdersState.values()) {
            map.put(stateEnum.orderStateNo, stateEnum);
        }
    }
    
    private OrdersState(final int stateValue){ 
        orderStateNo = stateValue; 
    }

    public static OrdersState valueOf(int stateValue) {
        return map.get(stateValue);
    }
    
    public static int intValue(OrdersState stateValue){
        int orderStateValue = -1;
        mapLoop:
        for (Map.Entry<Integer, OrdersState> entry : map.entrySet()) {
            if(entry.getValue()==stateValue){
                orderStateValue = entry.getKey();
                break mapLoop;
            }
        }
        
        return orderStateValue;
    }
    
}
