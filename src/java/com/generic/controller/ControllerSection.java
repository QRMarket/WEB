package com.generic.controller;

import com.generic.checker.Checker;
import com.generic.db.DBSection;
import com.generic.result.Result;
import javax.servlet.http.HttpServletRequest;


public class ControllerSection {
    
    
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
    
    
    public static Result getSections(HttpServletRequest request){
        String parentId = request.getParameter("parentId");
        return DBSection.getSections(parentId);
    }
    
    
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
