/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.generic.test;

import com.generic.db.MysqlDBOperations;
import com.generic.resources.ResourceProperty;
import com.generic.result.Result;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

/**
 *
 * @author kemal
 */
@WebServlet(name = "SampleUploadServlet", urlPatterns = {"/SampleUploadServlet"})
@MultipartConfig
public class SampleUploadServlet extends HttpServlet {

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
        Gson gson = new Gson();                
        Result res = Result.SUCCESS_EMPTY;  
        
        try{
            
//            System.out.println("XXX XXX XXX");
//            Part p = request.getPart("txtHighSchool");
//            System.out.println(p.getName());
//            System.out.println(p.getContentType());
//            System.out.println(p.getSize());
//            Iterator it = p.getHeaderNames().iterator();
//            while(it.hasNext()){
//                System.out.println("Header -- " + it.next());
//            }
//            
//            
//            System.out.println("YYY YYY YYY");
            
            
            System.out.println(request.getParameter("txtCollege"));
            System.out.println(request.getParameter("txtHighSchool"));
            
            
            System.out.println("Deneme");
            
        }finally{            
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
