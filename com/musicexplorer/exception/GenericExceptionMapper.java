/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.musicexplorer.exception;

import com.musicexplorer.org.utils.ErrorMessage;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

/**
 *
 * @author Babak Tamjidi  baboly@gmail.com
 */
public class GenericExceptionMapper implements ExceptionMapper<Throwable>{

    @Override
    public Response toResponse(Throwable ex) {
        ErrorMessage message = new ErrorMessage(ex.getMessage(), 500, "http://link.com");
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(message).build();
    }
    

}
