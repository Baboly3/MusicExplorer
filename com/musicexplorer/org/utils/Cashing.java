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
    
    CacheControl cc; 
    
    public Cashing(){
        cc = new CacheControl();
    }
    
    public Map setCashing(int maxAge, boolean privateTorF, T entity){
        int hashValue = entity.hashCode();
        CacheControl cc = new CacheControl();
        cc.setMaxAge(maxAge);
        cc.setPrivate(privateTorF);      
        EntityTag eTag = new EntityTag(Integer.toString(hashValue));        
        Map<CacheControl,EntityTag> cashing = new HashMap();
        cashing.put(cc, eTag);
        return cashing;
    }
}
