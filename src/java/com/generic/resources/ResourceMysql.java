/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.generic.resources;

import java.util.ResourceBundle;

/**
 *
 * @author Kemal Sami KARACA
 * @since 11.03.2015
 * @version 1.01
 * 
 * @last 11.03.2015
 */
public class ResourceMysql {
        
    
    public static String TABLE_COMPANY;
    public static String TABLE_ADDRESS;
    public static String TABLE_DISTRIBUTER;
    public static String TABLE_USER;
    public static String TABLE_PRODUCT;
    public static String TABLE_ORDER;
    
    
    static{
        initializeStaticObjects(ResourceBundle.getBundle("com.generic.resources.mysql"));
    }
    
    private static void initializeStaticObjects(ResourceBundle rs){
        TABLE_COMPANY           = rs.getString("mysql.guppy.table.company");
        TABLE_ADDRESS           = rs.getString("mysql.guppy.table.address");
        TABLE_DISTRIBUTER       = rs.getString("mysql.guppy.table.distributer");
        TABLE_USER              = rs.getString("mysql.guppy.table.user");
        TABLE_PRODUCT           = rs.getString("mysql.guppy.table.product");
        TABLE_ORDER             = rs.getString("mysql.guppy.table.order");
    }
    
}
