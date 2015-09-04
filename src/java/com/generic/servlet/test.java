/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.generic.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author kemalsamikaraca
 */
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
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            
            
            // Recipient's email ID needs to be mentioned.
            String to = "kskaraca@gmail.com";

            // Sender's email ID needs to be mentioned
            String from = "web@gmail.com";

            // Assuming you are sending email from localhost
            String host = "localhost";

            // Get system properties
            Properties properties = System.getProperties();

            // Setup mail server
            properties.setProperty("mail.smtp.host", host);

            // Get the default Session object.
            Session session = Session.getDefaultInstance(properties);

            try{
                // Create a default MimeMessage object.
                MimeMessage message = new MimeMessage(session);

                try {
                    // Set From: header field of the header.
                    message.setFrom(new InternetAddress(from));
                } catch (MessagingException ex) {
                    Logger.getLogger(test.class.getName()).log(Level.SEVERE, null, ex);
                }

                // Set To: header field of the header.
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

                // Set Subject: header field
                message.setSubject("This is the Subject Line!");

                // Now set the actual message
                message.setText("This is actual message");

                // Send message
                Transport.send(message);
                System.out.println("Sent message successfully....");
            }catch (MessagingException mex) {
               mex.printStackTrace();
            }
            
/*
            Connection conn=null;
            Statement stmt=null;
            ResultSet rs=null;
            try {
                Class.forName("com.mysql.jdbc.Driver");
                if (conn == null || conn.isClosed()) {       
                    conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/QR_Market_DB", "root", "");
                    conn.setAutoCommit(false);                
                }
                
                String username = "admin' OR '1'='1";
                String password = "";
                String sql = "select * from marketUser where mu_mail='" + username +"' and mu_password='" + password + "'";
                stmt = conn.createStatement();
                rs = stmt.executeQuery(sql);
                if (rs.next()) {                
                        out.println("Successfully logged in");
                } else {
                        out.println("Username and/or password not recognized");
                }
                
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(test.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                
                try {
                    conn.close();
                    stmt.close();
                    rs.close();
                    
                } catch (SQLException ex) {
                    Logger.getLogger(test.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }
*/            
            
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
