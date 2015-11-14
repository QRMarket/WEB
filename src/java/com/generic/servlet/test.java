/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.generic.servlet;

import com.generic.checker.Checker;
import com.generic.logger.LoggerGuppy;
import com.generic.result.Result;
import com.generic.util.UserRole;
import com.generic.util.Util;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
                
        System.out.println("Incoming request from :: " + request.getSession().getId());
        
        try  {             
            
            switch (Util.getContentType(request)){
                case MULTIPART_FORM_DATA:
                    res = res.setContent("Content-type :: multipart ");
                    break;

                case APPLICATION_FORM_URLENCODED:                    
                    res = res.setContent("Content-type :: application/url_encoded ");
                    break;

                default:
                    
                    if(request.getParameter("a")!=null){
                        HttpSession session = request.getSession(true);
                        
                        session.setAttribute("name", request.getParameter("a")); 
                        session.setAttribute("userrole", UserRole.ADMIN);
                        
                        ServletContext sc=request.getSession().getServletContext();
                        sc.setAttribute(session.getId(), session);
                        
                        res = Result.SUCCESS.setContent(session);
                        
                    }else if(request.getParameter("token")!=null){
                        
                        String sessionId = request.getParameter("token");
                        ServletContext sc = request.getSession().getServletContext();
                        HttpSession session = (HttpSession)sc.getAttribute(sessionId);

                        if(session==null){
                            res = Result.FAILURE_CACHE.setContent("CACHE NOT FOUND ERROR");
                        }else if(!Checker.hasRole((UserRole)session.getAttribute("userrole"), UserRole.CUSTOMER)){
                            res = Result.FAILURE_AUTH_PERMISSION.setContent("ADMIN Permission Required");
                        }else{
                            res = Result.SUCCESS.setContent(session);
                        }
                        
                        
                    }
                    
                break;
            }
            
            
        }catch (Exception e){
            System.out.println(e.getMessage());
        }finally{
            
            System.out.println("----- ----- ----- ----- ----- -----");
            System.out.println("----- ----- ----- ----- ----- -----");
            LoggerGuppy.verboseURL(request);
            LoggerGuppy.verboseHeader(request);
            
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
