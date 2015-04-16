/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.generic.filter;

import com.generic.checker.Checker;
import com.generic.logger.LoggerGuppy;
import com.generic.util.Guppy;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Enumeration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Kemal Sami KARACA
 * @since 02.2015
 * @version 1.01
 * 
 * @last 12.03.2015
 */


//@WebFilter(filterName = "AuthFilter", urlPatterns = {"/*"})
public class AuthFilter implements Filter {
    
    private static final boolean debug = true;

    // The filter configuration object we are associated with.  If
    // this value is null, this filter instance is not currently
    // configured. 
    private FilterConfig filterConfig = null;
    
    public AuthFilter() {
    }    
    
    // <editor-fold defaultstate="collapsed" desc="doBeforeProcessing && doAfterProcessing methods.">
    private void doBeforeProcessing(ServletRequest request, ServletResponse response)
            throws IOException, ServletException {
        if (debug) {
            log("AuthFilter:DoBeforeProcessing");
        }

	// Write code here to process the request and/or response before
        // the rest of the filter chain is invoked.
	// For example, a logging filter might log items on the request object,
        // such as the parameters.
	/*
         for (Enumeration en = request.getParameterNames(); en.hasMoreElements(); ) {
         String name = (String)en.nextElement();
         String values[] = request.getParameterValues(name);
         int n = values.length;
         StringBuffer buf = new StringBuffer();
         buf.append(name);
         buf.append("=");
         for(int i=0; i < n; i++) {
         buf.append(values[i]);
         if (i < n-1)
         buf.append(",");
         }
         log(buf.toString());
         }
         */                
    }    
    
    private void doAfterProcessing(ServletRequest request, ServletResponse response)
            throws IOException, ServletException {
        if (debug) {
            log("AuthFilter:DoAfterProcessing");
        }

	// Write code here to process the request and/or response after
        // the rest of the filter chain is invoked.
	// For example, a logging filter might log the attributes on the
        // request object after the request has been processed. 
	/*
         for (Enumeration en = request.getAttributeNames(); en.hasMoreElements(); ) {
         String name = (String)en.nextElement();
         Object value = request.getAttribute(name);
         log("attribute: " + name + "=" + value.toString());

         }
         */
	// For example, a filter might append something to the response.
	/*
         PrintWriter respOut = new PrintWriter(response.getWriter());
         respOut.println("<P><B>This has been appended by an intrusive filter.</B>");
         */
    }
    // </editor-fold>
    
    /**
     *
     * @param request The servlet request we are processing
     * @param response The servlet response we are creating
     * @param chain The filter chain we are processing
     *
     * @exception IOException if an input/output error occurs
     * @exception ServletException if a servlet error occurs
     *     
     * 
     */
    public void doFilter(ServletRequest request, ServletResponse response,
                    FilterChain chain)
                    throws IOException, ServletException {
        
            HttpServletRequest req  = (HttpServletRequest) request;
            HttpServletResponse res = (HttpServletResponse) response;
            HttpSession session     = req.getSession(false); 
            
            // CHECK USER AUTHANTICATION
            boolean isAuth          = Checker.isAuth(req, session);
            String uri              = req.getRequestURI();
            String agentType        = null;
                                               
            // Check Url finish with proper file extensions
            String urlPatterns = "(jsp|html|php)$";                        
            Pattern pattern = Pattern.compile(urlPatterns);
            Matcher matcher = pattern.matcher(uri);            
            
            //LoggerGuppy.verboseHeader(req);
            
            if(Checker.isUserAgentBrowser(req)){                                      
                    
                    if(matcher.find()){
                                                  
                            System.out.println("BROWSER \t<--> AuthFilter"); 
                            // If user auth OK 
                            if(isAuth){
                                // If user wants to go login.jsp then redirect him to testPanel
                                // Otherwise go to next filter
                                if(uri.endsWith(Guppy.page_login)){                        
                                    res.sendRedirect(Guppy.page_userMain);
                                }else{                        
                                    chain.doFilter(request, response);
                                }                    

                            // If user auth FAIL
                            }else{                                        

                                // AND if requested page other than login page, request redirected to login page
                                if(!uri.endsWith(Guppy.page_login)){                        
                                    res.sendRedirect(Guppy.page_login);
                                }else{
                                    chain.doFilter(request, response);
                                }                    
                            }
                            
                            
                    }else{
                        
                            //System.out.println("BROWSER \t<--> AuthFilter \t<--> " + req.getRequestURI());    
                            chain.doFilter(request, response);
                        
                    }
                
            }else if(Checker.isUserAgentMobileApp(req)){
                                
                    System.out.println("MOBILE \t\t<--> AuthFilter");
                    // If user auth OK 
                    if(isAuth){     
                        
                    // If user auth FAIL   
                    }else{}                    
                    chain.doFilter(request, response);                                
                    
            }else{
                
                    System.out.println("AuthFilter <--> UNKNOWN AGENT CASE");                                        
                    chain.doFilter(request, response);
            }
    }
    

    // <editor-fold defaultstate="collapsed" desc="other filter methods.">
    
    /**
     * Return the filter configuration object for this filter.
     */
    public FilterConfig getFilterConfig() {
        return (this.filterConfig);
    }

    /**
     * Set the filter configuration object for this filter.
     *
     * @param filterConfig The filter configuration object
     */
    public void setFilterConfig(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
    }

    /**
     * Destroy method for this filter
     */
    public void destroy() {        
    }

    /**
     * Init method for this filter
     */
    public void init(FilterConfig filterConfig) {        
        this.filterConfig = filterConfig;
        if (filterConfig != null) {
            if (debug) {                
                log("AuthFilter:Initializing filter");
            }
        }
    }

    /**
     * Return a String representation of this object.
     */
    @Override
    public String toString() {
        if (filterConfig == null) {
            return ("AuthFilter()");
        }
        StringBuffer sb = new StringBuffer("AuthFilter(");
        sb.append(filterConfig);
        sb.append(")");
        return (sb.toString());
    }
    
    private void sendProcessingError(Throwable t, ServletResponse response) {
        String stackTrace = getStackTrace(t);        
        
        if (stackTrace != null && !stackTrace.equals("")) {
            try {
                response.setContentType("text/html");
                PrintStream ps = new PrintStream(response.getOutputStream());
                PrintWriter pw = new PrintWriter(ps);                
                pw.print("<html>\n<head>\n<title>Error</title>\n</head>\n<body>\n"); //NOI18N

                // PENDING! Localize this for next official release
                pw.print("<h1>The resource did not process correctly</h1>\n<pre>\n");                
                pw.print(stackTrace);                
                pw.print("</pre></body>\n</html>"); //NOI18N
                pw.close();
                ps.close();
                response.getOutputStream().close();
            } catch (Exception ex) {
            }
        } else {
            try {
                PrintStream ps = new PrintStream(response.getOutputStream());
                t.printStackTrace(ps);
                ps.close();
                response.getOutputStream().close();
            } catch (Exception ex) {
            }
        }
    }
    
    public static String getStackTrace(Throwable t) {
        String stackTrace = null;
        try {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            t.printStackTrace(pw);
            pw.close();
            sw.close();
            stackTrace = sw.getBuffer().toString();
        } catch (Exception ex) {
        }
        return stackTrace;
    }
    
    public void log(String msg) {
        filterConfig.getServletContext().log(msg);        
    }
    // </editor-fold>
    
}
