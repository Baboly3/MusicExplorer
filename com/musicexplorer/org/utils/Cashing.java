/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.musicexplorer.org.utils;

import java.util.HashMap;
import java.util.Map;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.EntityTag;

/**
 *
 * @author Babak Tamjidi  baboly@gmail.com
 */
public class Cashing<T> {

    private Class<T> entityClass;
    private int hashValue;
    CacheControl cc; 
    
    public Cashing(){
        cc = new CacheControl();
    }
    
    public CacheControl setCacheControl(int maxAge, boolean privat, T entity){
        hashValue = entity.hashCode();
        cc.setMaxAge(maxAge);
        cc.setPrivate(privat);      
        
        return cc;
    }
    public EntityTag getEntityTag(){
        
         EntityTag eTag = new EntityTag(Integer.toString(hashValue));
         return eTag;
    }
}
