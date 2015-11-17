/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.generic.db;

import com.generic.checker.Checker;
import com.generic.entity.Address;
import com.generic.ftp.FTPHandler;
import com.generic.resources.ResourceProperty;
import com.generic.result.Result;
import com.generic.entity.CampaignProduct;
import com.generic.entity.CompanyProduct;
import com.generic.entity.MarketProduct;
import com.generic.entity.MarketProductImage;
import com.generic.orm.ORMHandler;
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

    
    
    //------------------------------------------------------------------------------
    //------------------------------------------------------------------------------
    //--                            GET OPERATIONs
    //------------------------------------------------------------------------------
    //------------------------------------------------------------------------------
    
    // <editor-fold defaultstate="collapsed" desc="Product GET Operations">
    
        //**************************************************************************
        //**************************************************************************
        //**                        GET COMPANY PRODUCT
        //**************************************************************************
        //**************************************************************************
        /**
         *
         * @param companyProductId
         * @return
         */
        public static Result getProductOfDistributer(String companyProductId) {

                MysqlDBOperations mysql = new MysqlDBOperations();
                ResourceProperty rs = new ResourceProperty("com.generic.resources.mysqlQuery");
                Connection conn = mysql.getConnection();            
                CompanyProduct companyProduct;
                
                try {

                    // -1- Prepare Statement
                        PreparedStatement preStat = conn.prepareStatement(rs.getPropertyValue("mysql.companyProduct.select.3"));
                        preStat.setString(1, companyProductId);
                        
                        ResultSet resultSet = preStat.executeQuery();
                        
                    // -2- Get Result
                        if(resultSet.first()){
                                
                            companyProduct = new CompanyProduct();
                            companyProduct.setId(resultSet.getString("id"));
                            companyProduct.setDistributer_id(resultSet.getString("d_id"));
                            companyProduct.setProduct_price(resultSet.getDouble("p_price"));
                            
                        // -2.1- Get Product Object
                            MarketProduct product = ORMHandler.resultSetToProduct(resultSet);
                            companyProduct.setProduct(product);
                            
                        // -2.2- After getting product from db then take the product images
                            Result imageResult = DBProductImage.getProductImageList(companyProduct.getProduct().getProductID());
                            if (imageResult.checkResult(Result.SUCCESS)) {
                                companyProduct.getProduct().setProductImages((ArrayList<MarketProductImage>) imageResult.getContent());
                            }
                            
                            return Result.SUCCESS.setContent(companyProduct);

                        }else{
                            return Result.SUCCESS_EMPTY;
                        }
                       
                        
                } catch (SQLException ex) {
                    return Result.FAILURE_DB.setContent(ex.getMessage());
                } finally {
                    mysql.closeAllConnection();
                }
        }
        
          
        
        //**************************************************************************
        //**************************************************************************
        //**                        GET PRODUCT (COMMON)
        //**************************************************************************
        //**************************************************************************
        /**
         *
         * @param pid
         * @return
         */
        public static Result getProduct(String productId) {

                MysqlDBOperations mysql = new MysqlDBOperations();
                ResourceProperty rs = new ResourceProperty("com.generic.resources.mysqlQuery");
                Connection conn = mysql.getConnection();            
                MarketProduct product;

                try {
                    
                    // -1- Prepare Statement
                        PreparedStatement preStat = conn.prepareStatement(rs.getPropertyValue("mysql.product.select.2"));
                        preStat.setString(1, productId);
                        ResultSet resultSet = preStat.executeQuery();
                        
                    // -2- Get Result
                        if (resultSet.first()) {

                            // -2.1- Get&Set Result 
                                product = ORMHandler.resultSetToProduct(resultSet);

                            // -2.1- After getting product from db then take the product images
                                Result imageResult = DBProductImage.getProductImageList(productId);
                                if (imageResult.checkResult(Result.SUCCESS)) {
                                    product.setProductImages((ArrayList<MarketProductImage>) imageResult.getContent());
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

        
        
        //**************************************************************************
        //**************************************************************************
        //**                  GET DISTRIBUTER PRODUCT LIST
        //**************************************************************************
        //**************************************************************************
        /**
         *
         * @param distributerId
         * @param limit
         * @return
         * @description Product list of given distributer
         */
        public static Result getProductListOfDistributer(String distributerId, int limit) {

                MysqlDBOperations mysql = new MysqlDBOperations();
                ResourceProperty rs = new ResourceProperty("com.generic.resources.mysqlQuery");
                Connection conn = mysql.getConnection();            
                Result result = Result.FAILURE_PROCESS;
                ArrayList<MarketProduct> productList;

                try {
                                                
                    // -1- Prepare Statement
                        PreparedStatement preStat = conn.prepareStatement(rs.getPropertyValue("mysql.companyProduct.select.4"));
                        preStat.setString(1, distributerId);
                        preStat.setInt(2, limit);
                        ResultSet resultSet = preStat.executeQuery();
                        
                    // -2- Get Result
                        if (resultSet.first()) {

                            productList = new ArrayList<>();
                            
                            do {
                                // -2.1- ORM Operations
                                    MarketProduct product = ORMHandler.resultSetToProduct(resultSet);
                                    MarketProductImage productImage = ORMHandler.resultSetToProductImage(resultSet);
                                // -2.2- Object Settings
                                    product.getProductImages().add(productImage);
                                    productList.add(product);
                            } while (resultSet.next());

                            return Result.SUCCESS.setContent(productList);

                        } else {
                            return Result.SUCCESS_EMPTY;
                        }

                } catch (SQLException ex) {
                    Logger.getLogger(DBProduct.class.getName()).log(Level.SEVERE, null, ex);
                    return Result.FAILURE_DB.setContent(ex.getMessage());
                } finally {
                    mysql.closeAllConnection();
                }
        }
        
        
        
        //**************************************************************************
        //**************************************************************************
        //**                  GET PRODUCT LIST
        //**************************************************************************
        //**************************************************************************
        /**
         *
         * @param limit
         * @return
         */
        public static Result getProductList(int limit) {
                
                MysqlDBOperations mysql = new MysqlDBOperations();
                ResourceProperty rs = new ResourceProperty("com.generic.resources.mysqlQuery");
                Connection conn = mysql.getConnection();
                Result result = Result.FAILURE_PROCESS;
                List<MarketProduct> productList = new ArrayList<>();

                try {

                    // -1- Prepare Statement
                        PreparedStatement preStat = conn.prepareStatement(rs.getPropertyValue("mysql.product.select.3"));
                        preStat.setInt(1, limit);
                        ResultSet resultSet =  preStat.executeQuery();

                    // -2- Get Result
                        if (resultSet.first()) {

                            do{
                                
                                MarketProduct product = ORMHandler.resultSetToProduct(resultSet);

                                // After get production then we will take product images                          
                                preStat = conn.prepareStatement(rs.getPropertyValue("mysql.productImage.select.3"));
                                preStat.setString(1, product.getProductID());
                                ResultSet innerQuery =  preStat.executeQuery();
                                if (innerQuery.first()) {

                                    do {
                                        MarketProductImage productImage = new MarketProductImage();
                                        productImage.setImageID(innerQuery.getString("imageID"));
                                        productImage.setImageContentType(innerQuery.getString("imgContType"));
                                        productImage.setImageSourceType(innerQuery.getString("imgSaveType"));
                                        productImage.setImageType(innerQuery.getString("imgType"));
                                        productImage.setImageSource(innerQuery.getString("imgSource"));
                                        product.getProductImages().add(productImage);
                                    } while (innerQuery.next());

                                }
                                productList.add(product);

                            }while(resultSet.next());

                            return Result.SUCCESS.setContent(productList);

                        } else {
                            return Result.SUCCESS_EMPTY;
                        }

                } catch (SQLException ex) {
                    Logger.getLogger(DBProduct.class.getName()).log(Level.SEVERE, null, ex);
                    return Result.FAILURE_DB.setContent(ex.getMessage());
                } finally {
                    mysql.closeAllConnection();
                }
        }
   
    
    
    
    
    
    
    
    
    
    
    
    

    /**
     *
     * @param sectionId
     * @param limit
     * @return
     * @description Product list of given section
     */
    public static Result getProductListBySection(String sectionId, Integer limit) {

        Result result = Result.FAILURE_PROCESS;
        MysqlDBOperations mysql = new MysqlDBOperations();
        ResourceProperty rs = new ResourceProperty("com.generic.resources.mysqlQuery");
        Connection conn = mysql.getConnection();
        ArrayList<MarketProduct> productList;

        try {
            // PREPARE QUERY                
            
            PreparedStatement preStat = conn.prepareStatement(rs.getPropertyValue("mysql.productSection.select.1"));
            preStat.setString(1, sectionId);
            preStat.setInt(2, limit);
            ResultSet mysqlResult =  preStat.executeQuery();
            if (mysqlResult.first()) {

                productList = new ArrayList<>();
                // GET CITIES FOR DB
                
                do {
                    MarketProduct product = new MarketProduct();
                    //product.setAmount(mysqlResult.getString("cprID"));
                    product.setBranchName(mysqlResult.getString("cpr.cprID"));
                    product.setBrandID(mysqlResult.getString("p.brands_id"));
                    product.setPrice(mysqlResult.getDouble("cpr.p_price"));
                    product.setPriceType(mysqlResult.getString("cpr.p_priceType"));
                    product.setProductCode(mysqlResult.getString("p.productCode"));
                    product.setProductDesc(mysqlResult.getString("p.productDesc"));
                    product.setProductID(mysqlResult.getString("p.pid"));
                    product.setProductName(mysqlResult.getString("p.productName"));
                    product.setSectionID(mysqlResult.getString("p.section_id"));
                    product.setUserID(mysqlResult.getString("p.user_id"));
                    MarketProductImage productImage = new MarketProductImage();
                    productImage.setImageID(mysqlResult.getString("pi.imageID"));
                    productImage.setImageContentType(mysqlResult.getString("pi.imgContType"));
                    productImage.setImageSourceType(mysqlResult.getString("pi.imgSaveType"));
                    productImage.setImageType(mysqlResult.getString("pi.imgType"));
                    productImage.setImageSource(mysqlResult.getString("pi.imgSource"));
                    product.getProductImages().add(productImage);
                    productList.add(product);
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
    
    
    // </editor-fold>  
    
    
    
    
    
    //------------------------------------------------------------------------------
    //------------------------------------------------------------------------------
    //--                            INSERT OPERATIONs
    //------------------------------------------------------------------------------
    //------------------------------------------------------------------------------
    
    // <editor-fold defaultstate="collapsed" desc="Product INSERT Operations">
    /**
     *
     * @param product
     * @return
     */
    public static Result addProduct(MarketProduct product) {

        Result result = Result.FAILURE_PROCESS.setContent("DBProduct>addProduct>initial case");
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
                preStat.setString(5, product.getSectionID());
                preStat.setString(6, product.getBrandID());
                preStat.setString(7, product.getUserID());

                int executeResult = preStat.executeUpdate();

            // -1.2- Get&Check insert result
                if (executeResult == 0) {
                    return Result.SUCCESS_EMPTY;
                } else if (executeResult > 1) {
                    return Result.FAILURE_PROCESS;
                }

            // -1.3- Commit if product added                    
                mysql.commit();

                // - 2 - "productImage" DB
            // -2.1- Insert to DB-->"productImage"
                for (MarketProductImage productImage : product.getProductImages()) {

                    preStat = conn.prepareStatement(rs.getPropertyValue("mysql.productImage.update.insert.1"));
                    preStat.setString(1, productImage.getImageID());
                    preStat.setString(2, product.getProductID());
                    preStat.setString(3, productImage.getImageContentType());
                    preStat.setString(4, productImage.getImageSourceType());
                    preStat.setString(5, productImage.getImageSource());

                    int productImageQueryResult = preStat.executeUpdate();

                    if (productImageQueryResult == 1) {
                        FTPClient client = FTPHandler.getFTPClient();
                        if (client.rename(FTPHandler.dirTemps + productImage.getImageFileName(), FTPHandler.dirProducts + productImage.getImageFileName())) {
                            mysql.commit();
                        }

                        FTPHandler.closeFTPClient();
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

}
