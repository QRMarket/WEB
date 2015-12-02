/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.generic.modal;

import com.generic.db.MysqlDBOperations;
import com.generic.ftp.FTPHandler;
import com.generic.resources.ResourceProperty;
import com.generic.result.Result;
import com.generic.entity.CampaignProduct;
import com.generic.entity.DistributerProduct;
import com.generic.entity.MarketProduct;
import com.generic.entity.MarketProductImage;
import com.generic.orm.ORMHandler;
import com.generic.util.Util;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.net.ftp.FTPClient;

/**
 *
 * @author Kemal Sami KARACA
 * @since 03.2015
 * @version 1.01
 *
 * @last 24.11.2015
 */
public class CampaignProductModal {

    
    
    //------------------------------------------------------------------------------
    //------------------------------------------------------------------------------
    //--                            GET OPERATIONs
    //------------------------------------------------------------------------------
    //------------------------------------------------------------------------------
    
    // <editor-fold defaultstate="collapsed" desc="GET Operations">
    
        //**************************************************************************
        //**************************************************************************
        //**                        GET CAMPAIGN PRODUCT LIST
        //**************************************************************************
        //**************************************************************************
        /**
         *
         * @param limit
         * @return
         */
        public static Result getCampaignProductList(int limit) {
                
                MysqlDBOperations mysql = new MysqlDBOperations();
                ResourceProperty rs = new ResourceProperty("com.generic.resources.mysqlQuery");
                Connection conn = mysql.getConnection();
                Result result = Result.FAILURE_PROCESS;
                List<CampaignProduct> campaignProductList = new ArrayList<>();

                try {

                    // -1- Prepare Statement
                        PreparedStatement preStat = conn.prepareStatement(rs.getPropertyValue("mysql.campaignProduct.select.1"));
                        preStat.setString(1, "dist-1234-1212");
                        preStat.setInt(2, limit);
                        ResultSet resultSet =  preStat.executeQuery();

                    // -2- Get Result
                        if (resultSet.first()) {

                            do{
                                CampaignProduct campaignProduct = ORMHandler.resultSetToCampaignProduct(resultSet);
                                campaignProduct.setProduct(ORMHandler.resultSetToProduct(resultSet));
                                campaignProductList.add(campaignProduct);
                            }while(resultSet.next());

                            return Result.SUCCESS.setContent(campaignProductList);

                        } else {
                            return Result.SUCCESS_EMPTY;
                        }

                } catch (SQLException ex) {
                    Logger.getLogger(CampaignProductModal.class.getName()).log(Level.SEVERE, null, ex);
                    return Result.FAILURE_DB.setContent(ex.getMessage());
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
    
    // <editor-fold defaultstate="collapsed" desc="INSERT Operations">
    
    
    // </editor-fold>

}
