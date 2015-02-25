package com.generic.db;

import com.generic.result.Result;
import com.generic.util.MarketProduct;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;
import javax.servlet.http.HttpSession;

/**
 * @author Kemal Sami KARACA
 */
public class DBOrder {
    
    
    /**
     * 
     * @return 
     */
    public static String orderIDGenerator(){
        return "order_"+UUID.randomUUID().toString();
    }
    
    /**
     *   
     * @return  
     */
    public static Result confirmCart(HttpSession session){
            
            MysqlDBOperations mysql = new MysqlDBOperations();
                        
            try{                                
                ArrayList<MarketProduct> proList = (ArrayList<MarketProduct>) session.getAttribute("cduPList");
                String query;
                
                // GENERATE ORDER-ID
                String oid = orderIDGenerator();
                
                // GET USER-ID
                String userID = (String) session.getAttribute("cduUserId");
                
                // GET CURRENT ORDERLIST ID FROM DB                 
                query = String.format(  "INSERT INTO orders " + 
                                        "(oid, user_id, type, date)" +
                                        "VALUES ('%s','%s','SERVER', NOW())" , oid , userID );
                                
                int effectedRowNum = mysql.execUpdate(query); 
                boolean isSuccessInsertion = (proList.size()>0);
                if(effectedRowNum==1){                    
                    
                    insertionFail:
                    for(MarketProduct pro:proList){
                        query = String.format(  "INSERT INTO opRelation " + 
                                        "(oid, pid, quantity)" +
                                        "VALUES ('%s','%s',%f)" , oid , pro.getProductID() , pro.getAmount() );
                        if(mysql.execUpdate(query)!=1){
                            isSuccessInsertion = false;
                            break insertionFail;
                        }                                                    
                    }
                    
                    if(isSuccessInsertion){
                        mysql.commitAndCloseConnection();
                        return Result.SUCCESS;
                    }else{
                        mysql.rollbackAndCloseConnection();
                        return Result.FAILURE_DB_UPDATE;
                    }
                    
                    
                }else{
                    mysql.rollbackAndCloseConnection();
                    return Result.FAILURE_DB_UPDATE;
                }                           
                
            }catch(ClassCastException e){
                return Result.FAILURE_PROCESS_CASTING;
            }finally{
                mysql.closeAllConnection();
            }                
        
    }
    
    /**
     * 
     * @param cdoID     
     * @return  
     */
    public static Result getCartInfo(String cdoID){
            
            MysqlDBOperations mysql = new MysqlDBOperations();
            ArrayList<MarketProduct> cartList = new ArrayList();
            String query;
            try{
                
                // GET CURRENT ORDERLIST ID FROM DB                 
                query = String.format(  "SELECT * FROM opRelation " +
                                        "INNER JOIN cpRelation ON opRelation.pid=cpRelation.cprID " +
                                        "INNER JOIN products ON products.pid=cpRelation.p_id " + 
                                        "AND oid='%s'" , cdoID);
                
                System.out.println(query);
                ResultSet mysqlResult = mysql.getResultSet(query);                                
                
                String pId,pName,ppType;
                double pQuantity,pPrice;
                if(mysqlResult.first()){
                    do{
                        pId = mysqlResult.getString("cprID");
                        pName = mysqlResult.getString("productName");
                        ppType = mysqlResult.getString("productPriceType");
                        pQuantity = mysqlResult.getDouble("quantity");
                        pPrice = mysqlResult.getDouble("p_price");                        
                        cartList.add(new MarketProduct(pId, pName, ppType, pPrice, pQuantity));
                    }while(mysqlResult.next());
                    
                    return Result.SUCCESS.setContent(cartList);
                
                }else{
                    return Result.SUCCESS_EMPTY;
                }                                
                
            } catch (SQLException ex) {                
                return Result.FAILURE_DB;
            }finally{
                mysql.closeAllConnection();
            }                
        
    }
    
    /**
     * 
     * @param uid 
     * @return  
     */
    public static Result getUserCartList(String uid){
            
            MysqlDBOperations mysql = new MysqlDBOperations();
            ArrayList<String> cartList = new ArrayList();
            String query;
            try{
                
                // GET CURRENT ORDERLIST ID FROM DB
                query = String.format("SELECT oid FROM orders WHERE user_id='%s' " , uid);
                ResultSet mysqlResult = mysql.getResultSet(query);                                  
                
                if(mysqlResult.first()){
                    do{
                        cartList.add(mysqlResult.getString("oid"));                        
                    }while(mysqlResult.next());
                    
                    return Result.SUCCESS.setContent(cartList);
                
                }else{
                    return Result.SUCCESS_EMPTY;
                }                                
                
            } catch (SQLException ex) {                
                return Result.FAILURE_DB;
            }finally{
                mysql.closeAllConnection();
            }                        
    }
    
    
    
        
    
    
    
    
    /**
     * @deprecated 
     * @param uID
     * @param pUID
     * @param pAmount
     * @return 
     *      
     */
    public static Result addProductToOrderList(String uID, String pUID , double pAmount){                    

            MysqlDBOperations mysql = new MysqlDBOperations();
            String query;
            
            try{
                
                // GET CURRENT ORDERLIST ID FROM DB
                query = String.format("SELECT * FROM orders WHERE user_id='%s' AND type='CURRENT' " , uID);
                ResultSet mysqlResult = mysql.getResultSet(query);                                
                                
                // INSERT PRODUCT TO ORDERLIST WITH GIVEN USER_ID
                String orderID = mysqlResult.first() ? mysqlResult.getString("oid") : orderIDGenerator();   
                
                query  = String.format("INSERT INTO orders VALUES ('%s', '%s', '%s', '%s', '%s', '%.2f')" ,
                                        orderID , "CURRENT" , "21-10-1763", uID, pUID, pAmount);

                int effectedRowNumber = mysql.execUpdate(query);
                if(effectedRowNumber>0 ){
                    mysql.commitAndCloseConnection();                                        
                }else{                               
                    mysql.rollbackAndCloseConnection();
                    return Result.FAILURE_DB_UPDATE;  
                }
                
            } catch (SQLException ex) {                
                return Result.FAILURE_DB;        
            
            }finally{
                mysql.closeAllConnection();
            }
            
        return Result.SUCCESS;
    }
    
}
