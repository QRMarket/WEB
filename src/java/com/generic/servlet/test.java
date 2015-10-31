/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.generic.servlet;

import com.generic.result.Result;
import com.generic.util.Util;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.Iterator;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

/**
 *
 * @author kemalsamikaraca
 */

@MultipartConfig
@WebServlet(name = "test", urlPatterns = {"/test"})
public class test extends HttpServlet {

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
        
        
        
        PrintWriter outTemp = response.getWriter();        
        
        Result res = Result.FAILURE_PROCESS;
        Gson gson = new Gson();
                
        
        
        
        try  { 
            
            System.out.println(" **** **** **** **** **** ");
            System.out.println("/test Servlet called");
            System.out.println(request.getContentType());
            System.out.println(" **** **** **** **** **** ");
            res = res.setContent(request.getContentType());
            
            switch (Util.getContentType(request)){
                case MULTIPART_FORM_DATA:
                    
                    Part p = request.getPart("files");
                    Collection<String> theader = p.getHeaderNames();
                    
                    Iterator<String> headerIter = theader.iterator();
                    while ( headerIter.hasNext () ){
                        String headerName = headerIter.next(); 
                        System.out.println(headerName);
                        System.out.println(p.getHeader(headerName));
                        System.out.println("--- ---- --- ---- --- ---- ---");
                    }
                    
                    
//                    Collection<Part> parts = request.getParts();
//                    Iterator<Part> iterator = parts.iterator();
//                    
//                    System.out.println("--- ---- ---- ---");
//                    System.out.println(request.getParameter("name"));
//                    System.out.println(request.getParameter("surname"));
//                    System.out.println("--- ---- ---- ---");
//                    
//                    Part reqPart = request.getPart("n");
//                    System.out.println("Content-type :: " + reqPart.getContentType());
//                    System.out.println("Name :: " + reqPart.getName());
//                    System.out.println("Submitted fileName :: " + reqPart.getSubmittedFileName());
//                    
//                    while ( iterator.hasNext () ){
//                        Part p = iterator.next();
//                        
//                        System.out.println("Content-type :: " + p.getContentType());
//                        System.out.println("Name :: " + p.getName());
//                        System.out.println("Submitted fileName :: " + p.getSubmittedFileName());
//                    }
                    
                    res = res.setContent("Multipart-content-type called");
                    
                    break;

                case APPLICATION_FORM_URLENCODED:                    
                    res = res.setContent("Application form urlencoded");
                    break;

                case NULL:
                    res = res.setContent("Application null ");
                    break;

                default:
                    res = res.setContent(Util.getContentType(request));
                    break;
            }
            
            
            
        }catch (Exception e){
            
        }finally{
            outTemp.write(gson.toJson(res));
            outTemp.close();
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
