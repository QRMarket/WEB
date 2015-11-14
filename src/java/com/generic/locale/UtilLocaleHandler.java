/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.generic.locale;

import com.generic.constant.CharsetList;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Guppy Org.
 * @since 15.11.2015
 * @version 1.01
 * 
 * @last 15.11.2015
 */
public class UtilLocaleHandler {
    
    
    /**
     * 
     * @param source
     * @param charsettype
     * @return 
     */
    public static String useCharset(String source, CharsetList charsettype){
        
            String result = source;
            try {

                if( CharsetList.TR == charsettype ){
                    result = new String(source.getBytes("ISO-8859-9"),"UTF-8");
                }

            } catch (UnsupportedEncodingException ex) {
                Logger.getLogger(UtilLocaleHandler.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex){
                Logger.getLogger(UtilLocaleHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
      
        return result;
    } 
    
    
}
