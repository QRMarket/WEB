/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.generic.orm;

import com.generic.constant.OrdersPaymentType;
import com.generic.constant.OrdersState;
import com.generic.entity.Address;
import com.generic.entity.CompanyProduct;
import com.generic.entity.MarketProduct;
import com.generic.entity.MarketProductImage;
import com.generic.entity.MarketUser;
import com.generic.entity.Orders;
import com.generic.entity.UserAddress;
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
    
    
    public static String userTableAs = "users";
    public static String addressTableAs = "address";
    public static String userAddressTableAs = "userAddress";
    public static String productTableAs = "product";
    public static String productImageTableAs = "productImage";
    public static String ordersTableAs = "orders";
    public static String companyProductTableAs = "companyProduct";
    
    
    public static MarketUser resultSetToUser(ResultSet resultSet) throws SQLException{
            
            MarketUser user = new MarketUser();
            user.setUserId(resultSet.getString(userTableAs+".mu_id"));
            user.setUserName(resultSet.getString(userTableAs+".mu_name"));
            user.setUserSurname(resultSet.getString(userTableAs+".mu_surname"));
            user.setUserMail(resultSet.getString(userTableAs+".mu_mail"));
            user.setUserPhoneNumber(resultSet.getString(userTableAs+".mu_phone"));
            return user;
    }
    
    
    public static UserAddress resultSetToUserAddress(ResultSet resultSet) throws SQLException{
            
            UserAddress userAddress = new UserAddress();
            userAddress.setId(resultSet.getString(userAddressTableAs+".ua_id"));
            userAddress.setStreet(resultSet.getString(userAddressTableAs+".street"));
            userAddress.setAvenue(resultSet.getString(userAddressTableAs+".avenue"));
            userAddress.setDescription(resultSet.getString(userAddressTableAs+".desc"));
            return userAddress;
    }
    
    
    public static Address resultSetToAddress(ResultSet resultSet) throws SQLException{
            
            Address address = new Address();
            address.setId(resultSet.getString(addressTableAs+".aid"));
            address.setCity(resultSet.getString(addressTableAs+".city"));
            address.setBorough(resultSet.getString(addressTableAs+".borough"));
            address.setLocality(resultSet.getString(addressTableAs+".locality"));
            return address;
    }
    
    
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
            orders.setState(OrdersState.valueOf(Integer.parseInt(resultSet.getString(ordersTableAs+".state"))));
            orders.setPaymentType(OrdersPaymentType.valueOf(Integer.parseInt(resultSet.getString(ordersTableAs+".payment_type"))));
            orders.setDate(resultSet.getLong(ordersTableAs+".date"));
            orders.setDelay(resultSet.getLong(ordersTableAs+".delay"));
            orders.setPayment(resultSet.getDouble(ordersTableAs+".payment"));
            orders.setUserAddressID(resultSet.getString(ordersTableAs+".userAddress_id"));
            orders.setDistributerAddressID(resultSet.getString(ordersTableAs+".distributerAddress_id"));
            return orders;
    }
    
    public static CompanyProduct resultSetToCompanyProduct(){
     
            CompanyProduct companyProduct = new CompanyProduct();
            
            return companyProduct;
            
    }
    
   
    
}
