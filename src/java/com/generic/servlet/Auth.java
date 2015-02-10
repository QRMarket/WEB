/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.generic.servlet;

import com.generic.db.MysqlDBOperations;
import com.generic.resources.ResourceProperty;
import com.generic.result.Result;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Kemal Sami KARACA
 */
@WebServlet(name = "Auth", urlPatterns = {"/Auth"})
public class Auth extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");        
        PrintWriter out = response.getWriter();        
        
        /**
         * authDo   :: carpeLogin
         *              :: cduMail
         *              :: cduPass
         *          :: carpeLogout
         *          :: carpeRegister          
         *
         * 
         * !! KISALTMALAR !!
         * carpe diem user --> cdu          
         *
         */
        HttpSession session = request.getSession(true);
        Gson gson = new Gson();
        MysqlDBOperations mysql = new MysqlDBOperations();
        ResourceProperty resource = new ResourceProperty("com.generic.resources.mysqlQuery");
        Result res = Result.SUCCESS_EMPTY;               
                 
        
        try{            
            
            if(request.getParameter("authDo")!=null){                 
                switch(request.getParameter("authDo")){   
                    
                    case "carpeLogin": 
                        
                            if(request.getParameter("cduMail")!=null && request.getParameter("cduPass")!=null){
                                                                                    
                                    String query  = String.format( resource.getPropertyValue("selectUserFromEmail") , request.getParameter("cduMail"), request.getParameter("cduPass"));
                                    System.out.println(query);


                                    ResultSet rs=mysql.getResultSet(query);
                                    String id = "";
                                    while(rs.next()){      
                                        id = rs.getString("mu_id");
                                        System.out.println(id);
                                    }

                                    
                                    
                                    System.out.println("--> /Auth?authDo=carpeLogin&cduMail="+request.getParameter("cduMail")+"&cduPass="+request.getParameter("cduPass"));

                                    res = Result.SUCCESS.setContent(id);
                            }else{
                                    res = Result.FAILURE_PARAM_MISMATCH;
                            }
                        
                        break;
                    case "carpeLogout":
                        break;
                    case "carpeRegister":
                        break;
                    default:
                        res = Result.FAILURE_PARAM_WRONG;
                        break;
                }
                
            }else{
                res = Result.FAILURE_PARAM_MISMATCH;
            }
            
            
        } catch (SQLException ex) {
            /**
             * !!! SQLException ERROR !!!
             */
            res = Result.FAILURE_DB;
            
        }finally{                        
                                                
            mysql.closeAllConnection();
            out.write(gson.toJson(res));
            out.close();
            
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
