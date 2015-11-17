/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.generic.orm;

import com.generic.entity.CompanyProduct;
import com.generic.entity.MarketProduct;
import com.generic.entity.MarketProductImage;
import com.generic.entity.Orders;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Kemal Sami KARACA
 * @since 16.11.2015
 * @version 1.01
 * 
 * @last 16.11.2015
 */
public class ORMHandler {
    
    
    public static String productTableAs = "product";
    public static String productImageTableAs = "productImage";
    public static String ordersTableAs = "orders";
    public static String companyProductTableAs = "companyProduct";
    
    
    
    public static MarketProduct resultSetToProduct(ResultSet resultSet) throws SQLException{
            
            MarketProduct product = product = new MarketProduct();
            product.setProductID(resultSet.getString(productTableAs+".pid"));
            product.setProductName(resultSet.getString(productTableAs+".productName"));
            product.setProductCode(resultSet.getString(productTableAs+".productCode"));
            product.setProductDesc(resultSet.getString(productTableAs+".productDesc"));
            product.setBrandID(resultSet.getString(productTableAs+".brands_id"));
            product.setSectionID(resultSet.getString(productTableAs+".section_id"));
            return product;
    }
    
    
    public static MarketProductImage resultSetToProductImage(ResultSet resultSet) throws SQLException{
            
            MarketProductImage productImage = new MarketProductImage();
            productImage.setImageID(resultSet.getString(productImageTableAs+".imageID"));
            productImage.setImageContentType(resultSet.getString(productImageTableAs+".imgContType"));
            productImage.setImageSourceType(resultSet.getString(productImageTableAs+".imgSaveType"));
            productImage.setImageType(resultSet.getString(productImageTableAs+".imgType"));
            productImage.setImageSource(resultSet.getString(productImageTableAs+".imgSource"));
            return productImage;
    }
    
    
    public static Orders resultSetToOrder(ResultSet resultSet) throws SQLException{
            
            Orders orders = new Orders();
            orders.setId(resultSet.getString(ordersTableAs+".id"));
            orders.setNote(resultSet.getString(ordersTableAs+".note"));
            orders.setPaymentType(resultSet.getString(ordersTableAs+".payment_type"));
            return orders;
    }
    
    public static CompanyProduct resultSetToCompanyProduct(){
     
            CompanyProduct companyProduct = new CompanyProduct();
            
            return companyProduct;
            
    }
    
   
    
}
