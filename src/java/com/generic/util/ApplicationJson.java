/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.generic.util;

import com.generic.entity.Address;
import com.generic.entity.Orders;

/**
 *
 * @author kemalsamikaraca
 */
public class ApplicationJson {
    
    private String jsonDo;
    private Orders ordersContent;

    /**
     * @return the jsonDo
     */
    public String getJsonDo() {
        return jsonDo;
    }

    /**
     * @param jsonDo the jsonDo to set
     */
    public void setJsonDo(String jsonDo) {
        this.jsonDo = jsonDo;
    }

    /**
     * @return the ordersContent
     */
    public Orders getOrdersContent() {
        return ordersContent;
    }

    /**
     * @param ordersContent the ordersContent to set
     */
    public void setOrdersContent(Orders ordersContent) {
        this.ordersContent = ordersContent;
    }
    
    
    
}
