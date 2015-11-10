/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.musicexplorer.exception;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 *
 * @author Babak Tamjidi  baboly@gmail.com
 */
@Provider
public class MyExceptionMapper implements ExceptionMapper<Throwable>{

    @Override
    public Response toResponse(Throwable ex) {
        if(ex instanceof WebApplicationException){
            
            if(ex instanceof NotAuthorizedException){
                
            }
            if(ex instanceof ForbiddenException){
                
            }
            if(ex instanceof NotFoundException){
                
            }
            
            return null;
            
        }
        return null;
            
    }

    
}
