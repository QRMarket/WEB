/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.generic.test;

import com.generic.db.DBMarket;
import com.generic.db.DBOrder;
import com.generic.db.DBUser;
import com.generic.result.Result;
import com.generic.util.MarketProduct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author kemal
 */
public class CarpeTester {
    
    public static void main(String[] args){
        
        Result res = DBOrder.getCartInfo("order_da90c44d-1dbc-47dc-a9f3-eb455b4079fa");
        System.out.println(res.getResultCode());
        System.out.println(res.getResultText());
        
        ArrayList<MarketProduct> cartList = (ArrayList<MarketProduct>) res.getContent();
        double total = 0;
        for(MarketProduct mp : cartList){
            System.out.println("\n***** ***** *****");
            System.out.println("Product id      ::" + mp.getProductID());
            System.out.println("Product name    ::" + mp.getProductName());
            System.out.println("Product price   ::" + mp.getPrice() + mp.getPriceType());
            System.out.println("Product amount  ::" + mp.getAmount());
            System.out.println("Cost            ::" + mp.getAmount()*mp.getPrice());
            total = total + mp.getAmount()*mp.getPrice();            
        }
        
        System.out.println("TOTAL PRICE :: " + total);
        
        
        
    }
}
