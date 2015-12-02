package com.generic.controller;

import com.generic.modal.DBDistributer;
import com.generic.modal.DBProduct;
import com.generic.result.Result;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;




public class ControllerDistributer {
    
    
    //------------------------------------------------------------------------------
    //------------------------------------------------------------------------------
    //--                            GET OPERATIONs
    //------------------------------------------------------------------------------
    //------------------------------------------------------------------------------
    
    // <editor-fold defaultstate="collapsed" desc="GET Operations">
    
        //**************************************************************************
        //**************************************************************************
        //**                        GET DISTRIBUTER LIST
        //**************************************************************************
        //**************************************************************************
        /**
         * @param request
         * @return 
         */
        public static Result getDistributerList(HttpServletRequest request){
        
                Result result = Result.FAILURE_PROCESS;
                int limit = 10;
                try {
                    limit = Integer.parseInt(request.getParameter("limit"))>0 ? Integer.parseInt(request.getParameter("limit")) : limit;
                } catch (Exception ex){
                    Logger.getLogger(ControllerProduct.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                    
                if (request.getParameter("addressId") != null) {
                    return DBDistributer.getDistributerList(request.getParameter("addressId"), limit);
                }else{
                    result = Result.FAILURE_PARAM_MISMATCH;
                }

            return result;
        }
    
    // </editor-fold>
        
}
