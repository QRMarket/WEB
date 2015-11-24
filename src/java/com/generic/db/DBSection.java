/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.generic.db;

import com.generic.resources.ResourceProperty;
import com.generic.result.Result;
import com.generic.entity.Section;
import com.generic.orm.ORMHandler;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Kemal Sami KARACA
 * @since 03.2015
 * @version 1.01
 *
 * @last 24.11.2015
 */
public class DBSection {

    
    //------------------------------------------------------------------------------
    //------------------------------------------------------------------------------
    //--                            GET OPERATIONs
    //------------------------------------------------------------------------------
    //------------------------------------------------------------------------------
    
    // <editor-fold defaultstate="collapsed" desc="Section GET Operations">
    
        //**************************************************************************
        //**************************************************************************
        //**                        GET SECTION LIST
        //**************************************************************************
        //**************************************************************************
        /**
         *
         * @param parentId
         * @return
         */
        public static Result getSectionList(String parentId) {
            
                MysqlDBOperations mysql = new MysqlDBOperations();
                ResourceProperty rs = new ResourceProperty("com.generic.resources.mysqlQuery");
                Connection conn = mysql.getConnection();
                Result result = Result.FAILURE_PROCESS;
                List<Section> sections = new ArrayList<>();
                
                try {

                    // -1- SET PREPARE STATEMENT
                        PreparedStatement preStat;
                        if (parentId != null) {
                            preStat = conn.prepareStatement(rs.getPropertyValue("mysql.section.select.2"));
                            preStat.setString(1, parentId);
                        } else {
                            preStat = conn.prepareStatement(rs.getPropertyValue("mysql.section.select.3"));
                        }
                        ResultSet resultSet = preStat.executeQuery();

                    // -2- GET RESULT
                        if (resultSet.first()) {
                            
                            do {
                                Section section = ORMHandler.resultSetToSection(resultSet);
                                sections.add(section);
                            } while (resultSet.next());

                            return Result.SUCCESS.setContent(sections);
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
                
        
        
        //**************************************************************************
        //**************************************************************************
        //**                        GET SECTION TREE
        //**************************************************************************
        //**************************************************************************
        /**
         *
         * @param parentSection
         * @param height
         * @return
         */
        public static Result getSectionTree(Section parentSection, int height) {
            
                MysqlDBOperations mysql = new MysqlDBOperations();
                ResourceProperty rs = new ResourceProperty("com.generic.resources.mysqlQuery");
                Connection conn = mysql.getConnection();
                Result result = Result.FAILURE_PROCESS;
                List<Section> childSections;
                
                try {

                    // -0- Check to escape from recursive function
                        if(height==0){
                            return null;
                        }
                        height = height - 1;
                    
                    // -1- SET PREPARE STATEMENT
                        PreparedStatement preStat;
                        if (parentSection.getSid() != null) {
                            preStat = conn.prepareStatement(rs.getPropertyValue("mysql.section.select.2"));
                            preStat.setString(1, parentSection.getSid());
                        } else {
                            preStat = conn.prepareStatement(rs.getPropertyValue("mysql.section.select.3"));
                        }
                        ResultSet resultSet = preStat.executeQuery();

                    // -2- GET RESULT
                        if (resultSet.first()) {
                                                
                            childSections = new ArrayList<>();
                            do{
                                // -2.1- GET SECTION
                                    Section section = ORMHandler.resultSetToSection(resultSet);
                                    childSections.add(section);
                                // -2.2- *** RECURSIVE CALL ***
                                    getSectionTree(section, height);
                                
                            }while(resultSet.next());
                            parentSection.setChildList(childSections);

                            return Result.SUCCESS.setContent(parentSection);
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
    

    // <editor-fold defaultstate="collapsed" desc="Section INSERT Operations">
    public static Result addSection(String pid, String sName, String sImage) {
        ResourceProperty resource = new ResourceProperty("com.generic.resources.mysqlQuery");
        MysqlDBOperations mysql = new MysqlDBOperations();
        Connection conn = mysql.getConnection();

        try {
            PreparedStatement preStat = conn.prepareStatement(resource.getPropertyValue("mysql.section.select.4"));
            preStat.setString(1, sName);

            ResultSet resultSet = preStat.executeQuery();

            boolean empty = true;
            try {
                empty = !(resultSet.first());
                resultSet.beforeFirst();
            } catch (SQLException ex) {
                Logger.getLogger(MysqlDBOperations.class.getName()).log(Level.SEVERE, null, ex);
            }

            if (!empty) {
                return Result.FAILURE_AUTH_MULTIPLE.setContent("Section name already exists.");
            }

            if (pid != null) {
                preStat = conn.prepareStatement(resource.getPropertyValue("mysql.section.select.5"));
                preStat.setString(1, pid);
                resultSet = preStat.executeQuery();

                empty = true;
                try {
                    empty = !(resultSet.first());
                    resultSet.beforeFirst();
                } catch (SQLException ex) {
                    Logger.getLogger(MysqlDBOperations.class.getName()).log(Level.SEVERE, null, ex);
                }

                if (empty) {
                    return Result.FAILURE_PARAM_WRONG.setContent("Section parent id doesn't exist.");
                }
            }

            // -4- If not exist then continue
            preStat = conn.prepareStatement(resource.getPropertyValue("mysql.section.insert.1"));
            preStat.setString(1, "ms-" + UUID.randomUUID().toString());
            preStat.setString(2, pid);
            preStat.setString(3, sName);
            preStat.setString(4, sImage);

            int executeResult = preStat.executeUpdate();
            if (executeResult == 1) {
                mysql.commitAndCloseConnection();
                return Result.SUCCESS;
            } else if (executeResult == 0) {
                return Result.FAILURE_DB_EFFECTED_ROW_NUM.setContent("Effected row count 0");
            } else {
                mysql.rollback();
                return Result.FAILURE_DB_EFFECTED_ROW_NUM.setContent("Effected row count more than 1");
            }

        } catch (SQLException ex) {
            Logger.getLogger(DBUser.class.getName()).log(Level.SEVERE, null, ex);
            return Result.FAILURE_DB.setContent("SQL Exception");

        } finally {
            mysql.closeAllConnection();
        }
    }
    // </editor-fold>

    
    // <editor-fold defaultstate="collapsed" desc="Section DELETE Operations">
    public static Result deleteSection(String sid) {
        ResourceProperty resource = new ResourceProperty("com.generic.resources.mysqlQuery");
        MysqlDBOperations mysql = new MysqlDBOperations();
        Connection conn = mysql.getConnection();

        try {
            PreparedStatement preStat = conn.prepareStatement(resource.getPropertyValue("mysql.section.delete.1"));
            preStat.setString(1, sid);

            int executeResult = preStat.executeUpdate();
            if (executeResult == 1) {
                mysql.commitAndCloseConnection();
                return Result.SUCCESS;
            } else if (executeResult == 0) {
                return Result.FAILURE_DB_EFFECTED_ROW_NUM.setContent("Effected row count 0");
            } else {
                mysql.rollback();
                return Result.FAILURE_DB_EFFECTED_ROW_NUM.setContent("Effected row count more than 1");
            }

        } catch (SQLException ex) {
            Logger.getLogger(DBUser.class.getName()).log(Level.SEVERE, null, ex);
            return Result.FAILURE_DB.setContent("SQL Exception");

        } finally {
            mysql.closeAllConnection();
        }
    }
    // </editor-fold>
    
    
    // <editor-fold defaultstate="collapsed" desc="Section UPDATE Operations">    
    public static Result updateSection(String sid, String sName, String sImage, String pid) {
        ResourceProperty resource = new ResourceProperty("com.generic.resources.mysqlQuery");
        MysqlDBOperations mysql = new MysqlDBOperations();
        Connection conn = mysql.getConnection();

        try {
            PreparedStatement preStat;
            int executeResult = 0;
            int finalExecuteResult = 0; //stores how many changes will be done
            if (sName != null) {
                finalExecuteResult++;
                preStat = conn.prepareStatement(resource.getPropertyValue("mysql.section.update.1"));
                preStat.setString(1, sName);
                preStat.setString(2, sid);

                executeResult += preStat.executeUpdate();
            }
            if(sImage != null){
                finalExecuteResult++;
                preStat = conn.prepareStatement(resource.getPropertyValue("mysql.section.update.2"));
                preStat.setString(1, sImage);
                preStat.setString(2, sid);

                executeResult += preStat.executeUpdate();
            }
            if(pid != null){
                preStat = conn.prepareStatement(resource.getPropertyValue("mysql.section.select.5"));
                preStat.setString(1, pid);
                ResultSet resultSet = preStat.executeQuery();

                boolean empty = true;
                try {
                    empty = !(resultSet.first());
                    resultSet.beforeFirst();
                } catch (SQLException ex) {
                    Logger.getLogger(MysqlDBOperations.class.getName()).log(Level.SEVERE, null, ex);
                    return Result.FAILURE_DB.setContent("SQL Exception");
                }

                if (empty) {
                    return Result.FAILURE_PARAM_WRONG.setContent("Section parent id doesn't exist.");
                }
                
                finalExecuteResult++;
                preStat = conn.prepareStatement(resource.getPropertyValue("mysql.section.update.3"));
                preStat.setString(1, pid);
                preStat.setString(2, sid);

                executeResult += preStat.executeUpdate();
            }
            
            
            if (executeResult == finalExecuteResult) {
                mysql.commitAndCloseConnection();
                return Result.SUCCESS;
            } else {
                mysql.rollback();
                return Result.FAILURE_DB_EFFECTED_ROW_NUM.setContent("Effected row count < 3");
            }

        } catch (SQLException ex) {
            Logger.getLogger(DBUser.class.getName()).log(Level.SEVERE, null, ex);
            return Result.FAILURE_DB.setContent("SQL Exception");

        } finally {
            mysql.closeAllConnection();
        }
    }
    // </editor-fold>
    
}
