package com.generic.controller;

import com.generic.checker.Checker;
import com.generic.db.DBSection;
import com.generic.entity.Section;
import com.generic.result.Result;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;


public class ControllerSection {
    
    
    //------------------------------------------------------------------------------
    //------------------------------------------------------------------------------
    //--                            GET OPERATIONs
    //------------------------------------------------------------------------------
    //------------------------------------------------------------------------------
    
    // <editor-fold defaultstate="collapsed" desc="GET Operations">
    
        //**************************************************************************
        //**************************************************************************
        //**                        GET PRODUCT
        //**************************************************************************
        //**************************************************************************
        /**
         * @param request
         * @return 
         */
        public static Result getSectionList(HttpServletRequest request){

                Result result = Result.FAILURE_PROCESS;
                if (request.getParameter("listType") != null && request.getParameter("listType").equalsIgnoreCase("tree")) {
                    
                        Section section = new Section();
                        int limit = 3;
                        try {
                            limit = Integer.parseInt(request.getParameter("limit"))>0 ? Integer.parseInt(request.getParameter("limit")) : limit;
                        } catch (Exception ex){
                            Logger.getLogger(ControllerProduct.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    
                    return DBSection.getSectionTree(section,limit);
                }else{
                    return DBSection.getSectionList(request.getParameter("parentId"));
                }
            
        }
    // </editor-fold>
    
        
        
    //------------------------------------------------------------------------------
    //------------------------------------------------------------------------------
    //--                            INSERT OPERATIONs
    //------------------------------------------------------------------------------
    //------------------------------------------------------------------------------
    
    // <editor-fold defaultstate="collapsed" desc="GET Operations">
    
        //**************************************************************************
        //**************************************************************************
        //**                        INSERT SECTION
        //**************************************************************************
        //**************************************************************************
        /**
         * @param request
         * @return 
         */
        public static Result insertSection(HttpServletRequest request){

            String sectionId = request.getParameter("sectionId");
            String parentId = request.getParameter("parentId");
            String sectionName = request.getParameter("sectionName");
            String sectionImage = request.getParameter("sectionImage");

        // -1.1- Check Product Common Id
            if(!Checker.anyNull(parentId, sectionName, sectionImage)){ 
                return DBSection.addSection(parentId, sectionName, sectionImage);

            }else{
                return Result.FAILURE_PARAM_MISMATCH.setContent("ControllerSection>");
            }

        }
    // </editor-fold>
    
    
    
    
    
    
    
    
    
    
    
    
    
    public static Result removeSection(HttpServletRequest request){
        
        String sectionId = request.getParameter("sectionId");

    // -1.1- Check Product Common Id
        if(!Checker.anyNull( sectionId)){ 
            return DBSection.deleteSection(sectionId);

        }else{
            return Result.FAILURE_PARAM_MISMATCH.setContent("ControllerSection>");
        }
    }
    
    
    
    
    public static Result updateSection(HttpServletRequest request){
        
        String sectionId = request.getParameter("sectionId");
        String sectionName = request.getParameter("sectionName");
        String sectionImage = request.getParameter("sectionImage");
        String parentId = request.getParameter("parentId");

    // -1.1- Check Product Common Id
        if(!Checker.anyNull(sectionId, sectionName, sectionImage, parentId)){ 
            return DBSection.updateSection(sectionId, sectionName, sectionImage, parentId);

        }else{
            return Result.FAILURE_PARAM_MISMATCH.setContent("ControllerSection>");
        }
    }
    
    
}
