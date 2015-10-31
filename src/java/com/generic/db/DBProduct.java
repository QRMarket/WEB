/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.generic.db;

import com.generic.checker.Checker;
import com.generic.ftp.FTPHandler;
import com.generic.resources.ResourceProperty;
import com.generic.result.Result;
import com.generic.util.CampaignProduct;
import com.generic.util.MarketProduct;
import com.generic.util.MarketProductImage;
import com.generic.util.Util;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import org.apache.commons.net.ftp.FTPClient;

/**
 *
 * @author Kemal Sami KARACA
 * @since 03.2015
 * @version 1.01
 *
 * @last 11.03.2015
 */
public class DBProduct {

    
    // <editor-fold defaultstate="collapsed" desc="Product GET Operations">
    /**
     *
     * @param pUID
     * @return
     */
    public static Result getCompanyProductInfo(String pUID) {

        MysqlDBOperations mysql = new MysqlDBOperations();
        ResourceBundle rs = ResourceBundle.getBundle("com.generic.resources.mysqlQuery");
        MarketProduct product;
        String query;
        try {

            // PREPARE QUERY                
            query = String.format("SELECT * FROM cpRelation "
                    + "INNER JOIN products ON cpRelation.p_id=products.pid "
                    + "AND cprID='%s'", pUID);

            ResultSet mysqlResult = mysql.getResultSet(query);

            String pId, pName, ppType;
            String p_id;
            double pQuantity, pPrice;
            if (mysqlResult.first()) {
                p_id = mysqlResult.getString("p_id");
                pId = mysqlResult.getString("cprID");
                pName = mysqlResult.getString("productName");
                ppType = mysqlResult.getString("productPriceType");
                pPrice = mysqlResult.getDouble("p_price");
                product = new MarketProduct(pId, pName, ppType, pPrice);

                        // VERSION -1-
                // After get production then we will take product images                          
//                        query = String.format(  rs.getString("mysql.productImage.select.3") , p_id);
//                                                
//                        mysqlResult = mysql.getResultSet(query);
//                        if(mysqlResult.first()){       
//                            
//                            do{                                
//                                product.getImages().add(mysqlResult.getString("imageID"));
//                            }while(mysqlResult.next());
//                            
//                        }
                        // VERSION -2-
                // After get production then we will take product images
                Result resImages = DBProductImage.getProductImageList(p_id);
                if (resImages.checkResult(Result.SUCCESS)) {
                    product.setProductImages((ArrayList<MarketProductImage>) resImages.getContent());
                }

                return Result.SUCCESS.setContent(product);

            } else {
                return Result.SUCCESS_EMPTY;
            }

        } catch (SQLException ex) {
            return Result.FAILURE_DB;
        } finally {
            mysql.closeAllConnection();
        }
    }
    
    
    
    /**
     *
     * @param pUID
     * @return
     */
    public static Result getProduct(String pid) {

        MysqlDBOperations mysql = new MysqlDBOperations();
        ResourceBundle rs = ResourceBundle.getBundle("com.generic.resources.mysqlQuery");
        MarketProduct product;
        String query;

        try {

            // PREPARE QUERY                
            query = String.format(rs.getString("mysql.product.select.2"), pid);

            ResultSet mysqlResult = mysql.getResultSet(query);
            if (mysqlResult.first()) {

                product = new MarketProduct();
                product.setProductID(mysqlResult.getString("pid"));
                product.setProductName(mysqlResult.getString("productName"));
                product.setPriceType(mysqlResult.getString("productPriceType"));
                product.setBranchName(mysqlResult.getString("productBranch"));
                product.setProductCode(mysqlResult.getString("productCode"));
                product.setProductDesc(mysqlResult.getString("productDesc"));

                // After get production then we will take product images                          
                query = String.format(rs.getString("mysql.productImage.select.3"), pid);
                mysqlResult = mysql.getResultSet(query);
                if (mysqlResult.first()) {

                    do {
                        MarketProductImage productImage = new MarketProductImage();
                        productImage.setImageID(mysqlResult.getString("imageID"));
                        productImage.setImageContentType(mysqlResult.getString("imgContType"));
                        productImage.setImageSourceType(mysqlResult.getString("imgSaveType"));
                        productImage.setImageType(mysqlResult.getString("imgType"));
                        productImage.setImageSource(mysqlResult.getString("imgSource"));
                        product.getProductImages().add(productImage);
                    } while (mysqlResult.next());

                }

                return Result.SUCCESS.setContent(product);

            } else {
                return Result.SUCCESS_EMPTY;
            }

        } catch (SQLException ex) {
            Logger.getLogger(DBProduct.class.getName()).log(Level.SEVERE, null, ex);
            return Result.FAILURE_DB;
        } finally {
            mysql.closeAllConnection();
        }
    }

    
    
    /**
     *
     * @param companyId
     * @return
     * @description Product list of given company
     */
    public static Result getProductList(String companyId) {

        MysqlDBOperations mysql = new MysqlDBOperations();
        ResourceBundle rs = ResourceBundle.getBundle("com.generic.resources.mysqlQuery");
        ArrayList<String> productList;
        String query;

        try {
            // PREPARE QUERY                
            query = String.format(rs.getString("mysql.productCompany.select.2"), companyId);

            ResultSet mysqlResult = mysql.getResultSet(query);
            if (mysqlResult.first()) {

                productList = new ArrayList();
                // GET CITIES FOR DB
                do {
                    productList.add(mysqlResult.getString("cprID"));
                } while (mysqlResult.next());

                return Result.SUCCESS.setContent(productList);

            } else {
                return Result.SUCCESS_EMPTY;
            }

        } catch (SQLException ex) {
            Logger.getLogger(DBProduct.class.getName()).log(Level.SEVERE, null, ex);
            return Result.FAILURE_DB;
        } finally {
            mysql.closeAllConnection();
        }
    }

    
    // </editor-fold>  
    
    
    
    // <editor-fold defaultstate="collapsed" desc="Product INSERT Operations">
    /**
     *
     * @param product
     * @return
     */
    public static Result addProduct(MarketProduct product) {
  
            Result result = Result.FAILURE_PROCESS;
            MysqlDBOperations mysql = new MysqlDBOperations();            
            ResourceProperty rs = new ResourceProperty("com.generic.resources.mysqlQuery");
            Connection conn = mysql.getConnection();                        
            
            try {
                                                 
                // - 1 - "products" DB 
                // -1.1- Insert to DB-->"products" 
                    PreparedStatement preStat = conn.prepareStatement(rs.getPropertyValue("mysql.product.update.insert.1"));
                    preStat.setString(1, product.getProductID());
                    preStat.setString(2, product.getProductName());
                    preStat.setString(3, product.getProductCode());
                    preStat.setString(4, product.getProductDesc());
                    preStat.setString(5, product.getBrandID());

                    int executeResult = preStat.executeUpdate(); 

                // -1.2- Get&Check insert result
                    if(executeResult==0){
                        return Result.SUCCESS_EMPTY;
                    }else if(executeResult>1){
                        return Result.FAILURE_PROCESS;
                    }                    

                // -1.3- Commit if product added                    
                    mysql.commit();
                    
                    
                // - 2 - "productImage" DB
                // -2.1- Insert to DB-->"productImage"
                    for (MarketProductImage productImage : product.getProductImages()) {

                        preStat = conn.prepareStatement(rs.getPropertyValue("mysql.productImage.update.insert.1"));
                        preStat.setString(1, productImage.getImageID());
                        preStat.setString(2, null);
                        preStat.setString(3, productImage.getImageContentType());
                        preStat.setString(4, productImage.getImageSourceType());
                        preStat.setString(5, productImage.getImageSource());

                        int productImageQueryResult = preStat.executeUpdate(); 
                        
                        if(productImageQueryResult==1){
                            FTPClient client = FTPHandler.getFTPClient();
                            if(client.rename("temps/"+productImage.getImageFileName() , productImage.getImageSource() )){
                               mysql.commit(); 
                            }
                            
                            client.logout();
                            client.disconnect();
                        }
                    }                

                return Result.SUCCESS.setContent(product);                                       
                
            } catch (NullPointerException ex) {
                Logger.getLogger(DBProduct.class.getName()).log(Level.SEVERE, null, ex);
                result = Result.FAILURE_PROCESS.setContent(ex.toString());
            } catch (SQLException ex) {                
                Logger.getLogger(DBProduct.class.getName()).log(Level.SEVERE, null, ex);
                result = Result.FAILURE_DB.setContent(ex.toString());
            } catch (IOException ex) {
                Logger.getLogger(DBProduct.class.getName()).log(Level.SEVERE, null, ex);
                result = Result.FAILURE_PROCESS.setContent(ex.toString());
            } finally {
                mysql.closeAllConnection();
            }

        // -- ** --UNREACHABLE STATE
        return result;
    }
    
    
    // </editor-fold>
    

    
    public static Result getActiveCampaignProducts() {
        MysqlDBOperations mysql = new MysqlDBOperations();
        ResourceProperty rs = new ResourceProperty("com.generic.resources.mysqlQuery");
        Connection conn = mysql.getConnection();

        List<CampaignProduct> products = new ArrayList<>();
        try {

            // GET ADDRESS
            PreparedStatement preStat;
            Date now = new Date();
            long test = now.getTime();
            preStat = conn.prepareStatement(rs.getPropertyValue("mysql.campaignProduct.select.1"));
            preStat.setLong(1, now.getTime());
            preStat.setLong(2, now.getTime());
            ResultSet mysqlResult = preStat.executeQuery();

            if (mysqlResult.first()) {

                do {
                    CampaignProduct product;
                    product = new CampaignProduct();
                    product.setProductID(mysqlResult.getString("p.pid"));
                    product.setProductName(mysqlResult.getString("p.productName"));
                    product.setPriceType(mysqlResult.getString("cp.p_priceType"));
                    product.setBranchName(mysqlResult.getString("p.productBranch"));
                    product.setProductCode(mysqlResult.getString("p.productCode"));
                    product.setProductDesc(mysqlResult.getString("p.productDesc"));
                    product.setC_start_date(mysqlResult.getLong("c_start_date"));
                    product.setC_end_date(mysqlResult.getLong("c_end_date"));
                    product.setPrice(mysqlResult.getDouble("cp.p_price"));
                    product.setC_price(mysqlResult.getDouble("c.c_price"));
                    products.add(product);
                } while (mysqlResult.next());

                return Result.SUCCESS.setContent(products);

            } else {
                return Result.SUCCESS_EMPTY;
            }

        } catch (Exception ex) {
            Logger.getLogger(DBAddress.class.getName()).log(Level.SEVERE, null, ex);
            return Result.FAILURE_DB;
        } finally {
            mysql.closeAllConnection();
        }
    }

}
