package com.generic.controller;

import com.generic.checker.Checker;
import com.generic.db.DBProduct;
import com.generic.db.DBSection;
import com.generic.ftp.FTPHandler;
import com.generic.result.Result;
import com.generic.util.MarketProduct;
import com.generic.util.MarketProductImage;
import com.generic.util.MarketUser;
import com.generic.util.Util;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.activation.MimetypesFileTypeMap;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import org.apache.commons.net.ftp.FTPClient;


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
