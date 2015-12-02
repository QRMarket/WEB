/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.generic.orm;

import com.generic.constant.OrdersPaymentType;
import com.generic.constant.OrdersState;
import com.generic.constant.UserRole;
import com.generic.entity.Address;
import com.generic.entity.CampaignProduct;
import com.generic.entity.DistributerProduct;
import com.generic.entity.Distributer;
import com.generic.entity.DistributerAddress;
import com.generic.entity.MarketProduct;
import com.generic.entity.MarketProductImage;
import com.generic.entity.MarketUser;
import com.generic.entity.Orders;
import com.generic.entity.Section;
import com.generic.entity.User;
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
    
    
    public static String campaignProductTableAs = "campaignProduct";
    public static String userTableAs = "users";
    public static String marketUserTableAs = "marketUser";
    public static String userAddressTableAs = "userAddress";
    public static String addressTableAs = "address";    
    public static String productTableAs = "product";
    public static String productImageTableAs = "productImage";
    public static String ordersTableAs = "orders";
    public static String companyProductTableAs = "companyProduct";
    public static String distributerTableAs = "distributers";
    public static String distributerProductTableAs = "distributerProduct";
    public static String distributerAddressTableAs = "distributerAddress";
    public static String sectionTableAs = "sections";
    
    
    
    
    public static DistributerProduct resultSetToDistributerProduct(ResultSet resultSet) throws SQLException{
            DistributerProduct distributerProduct = new DistributerProduct();
            distributerProduct.setId(resultSet.getString("id"));
            distributerProduct.setDistributerID(resultSet.getString("distributer_id"));
            distributerProduct.setProductPrice(resultSet.getDouble("price"));
            return distributerProduct;
    }
            
    public static CampaignProduct resultSetToCampaignProduct(ResultSet resultSet) throws SQLException{
            CampaignProduct campaignProduct = new CampaignProduct();
            campaignProduct.setId(resultSet.getString(campaignProductTableAs+".id"));
            campaignProduct.setCampaignPrice(resultSet.getDouble(campaignProductTableAs+".c_price"));
            return campaignProduct;
    }
    
    
    public static Distributer resultSetToDistributer(ResultSet resultSet) throws SQLException{
            Distributer distributer = new Distributer();
            distributer.setId(resultSet.getString(distributerTableAs+".id"));
            distributer.setCompanyId(resultSet.getString(distributerTableAs+".company_id"));
            distributer.setOpenHourAt(resultSet.getTime(distributerTableAs+".time_open"));
            distributer.setCloseHourAt(resultSet.getTime(distributerTableAs+".time_close"));
            distributer.setState(resultSet.getInt(distributerTableAs+".state"));
            return distributer;
    }
    
    public static DistributerAddress resultSetToDistributerAddress(ResultSet resultSet) throws SQLException{
            DistributerAddress distributerAddress = new DistributerAddress();
            distributerAddress.setId(resultSet.getString(distributerAddressTableAs+".id"));
            distributerAddress.setDistributerId(resultSet.getString(distributerAddressTableAs+".distributer_id"));
            distributerAddress.setAddressId(resultSet.getString(distributerAddressTableAs+".address_id"));
            distributerAddress.setAddressType(resultSet.getString(distributerAddressTableAs+".address_type"));
            distributerAddress.setMinimumPriceForDeliver(resultSet.getDouble(distributerAddressTableAs+".minPriceForDeliver"));
            return distributerAddress;
    }
    
    
    public static User resultSetToUser(ResultSet resultSet) throws SQLException{
            
            User user = new User();
            user.setId(resultSet.getString(userTableAs+".id"));
            user.setMail(resultSet.getString(userTableAs+".mail"));
            user.setName(resultSet.getString(userTableAs+".name"));
            user.setSurname(resultSet.getString(userTableAs+".surname"));
            user.setPhone(resultSet.getString(userTableAs+".phone"));
            return user;
    }
    
    
    public static MarketUser resultSetToMarketUser(ResultSet resultSet) throws SQLException{
            
            MarketUser user = new MarketUser();
            user.setUserId(resultSet.getString(marketUserTableAs+".mu_id"));
            user.setUserName(resultSet.getString(marketUserTableAs+".mu_name"));
            user.setUserSurname(resultSet.getString(marketUserTableAs+".mu_surname"));
            user.setUserMail(resultSet.getString(marketUserTableAs+".mu_mail"));
            user.setUserPhoneNumber(resultSet.getString(marketUserTableAs+".mu_phone"));
            user.setUserRole(UserRole.valueOf(Integer.parseInt(resultSet.getString(marketUserTableAs+".mu_type"))));
            return user;
    }
    
    
    public static UserAddress resultSetToUserAddress(ResultSet resultSet) throws SQLException{
            
            UserAddress userAddress = new UserAddress();
            userAddress.setId(resultSet.getString(userAddressTableAs+".ua_id"));
            userAddress.setStreet(resultSet.getString(userAddressTableAs+".street"));
            userAddress.setAvenue(resultSet.getString(userAddressTableAs+".avenue"));
            userAddress.setDescription(resultSet.getString(userAddressTableAs+".description"));
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
    
    
    public static Section resultSetToSection(ResultSet resultSet) throws SQLException{
            
            Section section = new Section();
            section.setSid(sectionTableAs+".");
            section.setSid(resultSet.getString(sectionTableAs+".sid"));
            section.setSec_parent_id(resultSet.getString(sectionTableAs+".sec_parent_id"));
            section.setSec_name(resultSet.getString(sectionTableAs+".sec_name"));
            section.setSec_image(resultSet.getString(sectionTableAs+".sec_image"));
            return section;
    }
    
}
