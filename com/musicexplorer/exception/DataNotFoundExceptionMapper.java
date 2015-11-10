/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.musicexplorer.exception;

import com.musicexplorer.org.utils.ErrorMessage;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 *
 * @author Babak Tamjidi  baboly@gmail.com
 */
@Provider
public class DataNotFoundExceptionMapper implements ExceptionMapper<DataNotFoundException>{

    @Override
    public Response toResponse(DataNotFoundException ex) {
        ErrorMessage message = new ErrorMessage(ex.getMessage(), 404 , "http://musicExplorer.se/api/documentation");
        return Response.status(Status.NOT_FOUND).entity(message).build();
    }

}
