/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.generic.servlet;

import com.generic.checker.Checker;
import com.generic.db.DBSection;
import com.generic.db.MysqlDBOperations;
import com.generic.resources.ResourceProperty;
import com.generic.result.Result;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author ulakbim
 */
@WebServlet(name = "SectionServlet", urlPatterns = {"/SectionServlet"})
public class SectionServlet extends HttpServlet {

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
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        /**
         * cdssDO   :: getSections
         *              -->parentId
         *          
         *          
         * 
         *          
         *          
         *          
         * 
         *
         * 
         * !! SHORTCUTs !!
         * carpe diem address           --> cdm
         * carpe diem address servlet   --> cdms        
         *
         */
        
        HttpSession session = request.getSession(false);
        Gson gson = new Gson();
        MysqlDBOperations mysql = new MysqlDBOperations();
        ResourceProperty resource = new ResourceProperty("com.generic.resources.mysqlQuery");
        Result res = Result.FAILURE_PROCESS;
        Map resultMap = new HashMap();
        
        try{
            if(request.getParameter("cdssDo")!=null){                 
                switch(request.getParameter("cdssDo")){ 
                    
                //**************************************************************
                //**************************************************************
                //**                    GET SECTIONS CASE
                //**************************************************************
                //**************************************************************
                    case "getSections":
                        String pid = request.getParameter("cdsParentId");
                       
                        res = DBSection.getSections(pid);
                        break;
                    case "addSection":
                        String pid2 = request.getParameter("cdsParentId");
                        String sName = request.getParameter("cdsName");
                        String sImage = request.getParameter("cdsImage");
                        if(Checker.anyNull(sName)){
                            res = Result.FAILURE_PARAM_MISMATCH;
                        }else{
                            res = DBSection.addSection(pid2, sName, sImage);
                        }
                        break;
                    case "deleteSection":
                        String sid = request.getParameter("cdsId");
                        if(Checker.anyNull(sid)){
                            res = Result.FAILURE_PARAM_MISMATCH;
                        }else{
                            res = DBSection.deleteSection(sid);
                        }
                        break;
                }
            }else{
                res = Result.FAILURE_PARAM_MISMATCH;
            }
        }catch(Exception ex){
            System.out.println(ex.getMessage());
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
