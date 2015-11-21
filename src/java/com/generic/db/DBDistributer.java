/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.generic.db;

import com.generic.entity.Distributer;
import com.generic.orm.ORMHandler;
import com.generic.resources.ResourceMysql;
import com.generic.resources.ResourceProperty;
import com.generic.result.Result;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Kemal Sami KARACA
 * @since 11.03.2015
 * @version 1.01
 * 
 * @last 21.11.2015
 */
public class DBDistributer extends DBGeneric{
            
    
    //------------------------------------------------------------------------------
    //------------------------------------------------------------------------------
    //--                            GET OPERATIONs
    //------------------------------------------------------------------------------
    //------------------------------------------------------------------------------
    
    // <editor-fold defaultstate="collapsed" desc="GET Operations">
    
        //**************************************************************************
        //**************************************************************************
        //**                  GET DISTRIBUTER LIST BY ADDRESS
        //**************************************************************************
        //**************************************************************************
        /**
         *
         * @param addressId
         * @return
         */
        public static Result getDistributerList(String addressId, int limit) {
                
                MysqlDBOperations mysql = new MysqlDBOperations();
                ResourceProperty rs = new ResourceProperty("com.generic.resources.mysqlQuery");
                Connection conn = mysql.getConnection();
                Result result = Result.FAILURE_PROCESS;
                List<Distributer> distributerList;

                try {

                    // -1- Prepare Statement
                        PreparedStatement preStat = conn.prepareStatement(rs.getPropertyValue("mysql.distributer.select.2"));
                        preStat.setString(1, addressId);
                        preStat.setInt(2, limit);
                        ResultSet resultSet =  preStat.executeQuery();

                    // -2- Get Result
                        if (resultSet.first()) {

                            distributerList = new ArrayList<>();
                            
                            do{
                                Distributer distributer = ORMHandler.resultSetToDistributer(resultSet);
                                distributer.setDistributerAddress(ORMHandler.resultSetToDistributerAddress(resultSet));
                                distributerList.add(distributer);
                            }while(resultSet.next());

                            return Result.SUCCESS.setContent(distributerList);
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
    
    
    // </editor-fold> 
    
    
}
