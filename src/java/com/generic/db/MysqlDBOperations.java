/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.generic.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Kemal Sami KARACA
 */

/**
 * TO_DO_1
 *      - while getting object variables with get methods, get methods should
 *          work like if variable is not null then get variable itself otherwise
 *          calculate their values
 * 
 * TO_DO_2
 *      - because of getConnection function we have to put
 *          try-catch to every method which includes conn OR getConnection
 *          method.There will be "throws" in getConnection and these try-catchs
 *          will be deprecated
 * 
 * TO_DO_3
 *      - After every ResultSet request, connection should close.Before 
 *          connection start auto commit should set to false and also start with
 *          commit.If there is an error occur at db process stage then rollback
 *          db to start point
 * 
 * TO_DO_4
 *      - savePoint maybe added to that class 
 *      - link : http://www.tutorialspoint.com/jdbc/jdbc-transactions.htm
 * .
 * 
 */
public class MysqlDBOperations {
    
    private static MysqlDBOperations instance = null;
    private Connection conn;
    private Statement stat;
    private PreparedStatement preStat;
    private ResultSet resultSet;
    private ResultSetMetaData resultSetMeta;        
    
    // if singleton desing pattern used then it will be protected
    public MysqlDBOperations() {
      // Exists only to defeat instantiation.
    }
    
    public static MysqlDBOperations getInstance(){
        if(instance == null) {
            instance = new MysqlDBOperations();
        }
        return instance;
    }
    
    //************************************************************************//
    //************************************************************************//
    //                      Connection Operations
    //************************************************************************//
    //************************************************************************//
    /**
     * To protect commit() OR atomic behavior of execution query, Connection 
     * variable should not be public.
     * 
     * @return 
     */
    public Connection getConnection(String DBName,String uName,String uPass){
        try {        
            Class.forName("com.mysql.jdbc.Driver");
            if (conn == null || conn.isClosed()) {                
                conn = DriverManager.getConnection(DBName,uName,uPass);
                conn.setAutoCommit(false);                
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }
    
    public Connection getConnection(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            if (conn == null || conn.isClosed()) {
                conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/QR_Market_DB", "root", "");                
                conn.setAutoCommit(false);                
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return conn;
    }
    
    public void rollback() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.rollback();
            }
        } catch (SQLException ex) {
            
        }
    }
    
    private void commit(){
        try {
            if(conn!=null && !conn.isClosed()){
                conn.commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
            try {
                conn.close();
            } catch (Exception ex) {
                e.printStackTrace();
            }
        }
    }        
    
    public void rollbackAndCloseConnection(){
        try {            
            if (conn != null && !conn.isClosed()) {
                conn.rollback();
                conn.close();
            }
        } catch (Exception e) {
            e.printStackTrace();                        
        }
    }
    
    public void commitAndCloseConnection(){        
        try {            
            if (conn != null && !conn.isClosed()) {
                conn.commit();
                conn.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }            
        }
    }
            
    public void closeConnection(){        
        try {
            if (conn != null && !conn.isClosed()) {                
                conn.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }                
    }
    
    public void closeAllConnection(){
        try {    
            if (resultSet != null && !resultSet.isClosed()) {                
                resultSet.close();
            }
            if (preStat != null && !preStat.isClosed()) {                
                preStat.close();
            }
            if (stat != null && !stat.isClosed()) {                
                stat.close();
            }
            if (conn != null && !conn.isClosed()) {                
                conn.close();
            }            
        } catch (SQLException ex) {
            Logger.getLogger(MysqlDBOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //************************************************************************//
    //************************************************************************//
    //                      Statement Operations
    //************************************************************************//
    //************************************************************************//
    /**
     * 
     * @param query
     * @return 
     */
    public Statement getStatement() {
        try {
            if (conn != null && !conn.isClosed()) {                
                this.stat = conn.createStatement();
                
            }else{
                this.stat = getConnection().createStatement();
            }           
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stat;
    }
    
    
    //************************************************************************//
    //************************************************************************//
    //                      PreparedStatement Operations
    //************************************************************************//
    //************************************************************************//
    /**
     * 
     * @param query
     * @return
     * 
     * @will
     *      - to make updatable resultset, settings will be added          
     *          par1 = ResultSet.TYPE_SCROLL_SENSITIVE
     *          par2 = ResultSet.CONCUR_UPDATABLE
     *          prepareStatement(query , par1 , par2)  
     */
    public PreparedStatement getPreparedStatement(String query) {
        
        if(this.preStat != null)
            return this.preStat;
        
        try {
            if (conn != null && !conn.isClosed()) {                
                this.preStat = conn.prepareStatement(query);
                
            }else{
                this.preStat = getConnection().prepareStatement(query);
            }           
        } catch (Exception e) {
            e.printStackTrace();
        }
        return preStat;
    }    
    
    /**
     * 
     */
    public PreparedStatement getPreparedStatement(){
        return this.preStat;
    }
    
    /**
     * 
     * @param preStat 
     */
    public void setPreparedStatement(PreparedStatement preStat){
        this.preStat = preStat;
    }
    
    /**
     * 
     * @param   query
     * @desc    This function set prepared statement with a given query
     */
    public void setPreparedStatement(String query){
        try {
            if (conn != null && !conn.isClosed()) {                
                this.preStat = conn.prepareStatement(query);
                
            }else{
                this.preStat = getConnection().prepareStatement(query);
            }           
        } catch (Exception e) {
            e.printStackTrace();
        }        
    }
    
    
    //************************************************************************//
    //************************************************************************//
    //                      CRUD Operations
    //************************************************************************//
    //************************************************************************//

    //************************************************************************//
    //                      INSERT, UPDATE, DELETE
    //************************************************************************//   
    /**
     * 
     * @param query
     * @return number of rows which is effected from execution of query 
     */
    public int execUpdate(String query){        
        try {            
            return getStatement().executeUpdate(query);            
        } catch (SQLException ex) {
            Logger.getLogger(MysqlDBOperations.class.getName()).log(Level.SEVERE, null, ex);
            rollback();
            return -1;
        }     
    }    
    
    //************************************************************************//
    //                      ResultSet Operations
    //************************************************************************//    
    /**
     * @return 
     */
    public ResultSet getResultSet(){ 
        
        try {            
            this.resultSet = getPreparedStatement().executeQuery();
            this.resultSet.beforeFirst();
        } catch (Exception e) {            
            e.printStackTrace();            
        }
        return this.resultSet;
    }
    
    /**
     * 
     * @param query
     * @return 
     */
    public ResultSet getResultSet(String query){
        try {            
            this.resultSet = getPreparedStatement(query).executeQuery();
            this.resultSet.beforeFirst();
        } catch (Exception e) {            
            e.printStackTrace();            
        }
        return this.resultSet;
    }  
    
    /**
     * 
     * @return 
     */
    public int getResultSetSize(){
        int size=0;                
        try {
            if(this.resultSet!=null){
                this.resultSet.last();
                size = this.resultSet.getRow();
                this.resultSet.beforeFirst();
            }
        } catch (SQLException ex) {
            Logger.getLogger(MysqlDBOperations.class.getName()).log(Level.SEVERE, null, ex);
        }                            
        return size;
    }
    
    public boolean resultSetIsEmpty(){
        boolean empty = true;
        try {
            empty = !(this.resultSet.first());
            this.resultSet.beforeFirst();
        } catch (SQLException ex) {            
            Logger.getLogger(MysqlDBOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return empty;
    }
    
    public void setResultSet(ResultSet resultSet){
        this.resultSet = resultSet;
    }
    
        
    //************************************************************************//
    //                      ResultSetMetaData Operations
    //************************************************************************//
    /**
     * 
     * @param query
     * @return 
     */
    public ResultSetMetaData getResultSetMeta(String query){
        try {
            this.resultSetMeta = getResultSet(query).getMetaData();
        } catch (Exception e) {
            e.printStackTrace(); 
        }
        return this.resultSetMeta;
    }
    /**
     * 
     * @return 
     */
    public ResultSetMetaData getResultSetMeta(){
        try {
            this.resultSetMeta = this.resultSet.getMetaData();
        } catch (Exception e) {
            e.printStackTrace(); 
        }
        return this.resultSetMeta;
    }
    
    
    //************************************************************************//
    //************************************************************************//
    //                      GET RESULT OPERATION
    //************************************************************************//
    //************************************************************************//
    /**
     * 
     * @param columnName
     * @return ArrayList
     * 
     * @will
     *      - if columnName=='*' then result all column
     *      - if column type is different than String then return arraylist with
     *          that values
     */
    public ArrayList<String> selectedColumnValues(String columnName){
        ArrayList<String> columnValues = new ArrayList<>();
        
        try {
            if(!resultSetIsEmpty()){   
                resultSet.beforeFirst();
                while(resultSet.next()){                                    
                    columnValues.add(resultSet.getString(columnName));                                
                }
            }            
        } catch (SQLException ex) {            
            Logger.getLogger(MysqlDBOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return columnValues;
    }
    
    
    /**
     * 
     * @param columnName
     * @return Map
     * 
     * @will - @WORK
     *      - if columnName=='*' then result all column
     *      - if column type is different than String then return arraylist with
     *          that values
     */
    public Map selectedColumnValues(){        
        Map resultRow = new HashMap();        
        
        try {
            
            ResultSetMetaData rsmd = resultSet.getMetaData();
            if(!resultSetIsEmpty()){   
                resultSet.beforeFirst();                                
                        
                while(resultSet.next()){                                    
                    ResultSetMetaData resultMeta = resultSet.getMetaData();
                    for(int j=0; j<resultMeta.getColumnCount(); j++){
                        String columnName = resultMeta.getColumnName(j);
                        String columnTypeName = resultMeta.getColumnTypeName(j);
                        System.out.println("Column Name : " + columnName + " - ColumnType : " + columnTypeName);
                    }
                }
            }            
        } catch (SQLException ex) {            
            Logger.getLogger(MysqlDBOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resultRow;
    }
    
    
    
    
    
}
