/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.musicexplorer.exception;

import com.musicexplorer.org.utils.ErrorMessage;
import javax.validation.ConstraintDeclarationException;
import javax.validation.ConstraintDefinitionException;
import javax.validation.GroupDefinitionException;
import javax.validation.ValidationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 *
 * @author Babak Tamjidi baboly@gmail.com
 */
@Provider
public class ValidationExceptionMapper implements ExceptionMapper<ValidationException> {

    @Override
    public Response toResponse(ValidationException ex) {
        ErrorMessage errorMessage = new ErrorMessage(ex.getMessage(), 3543, "http://docs.mysite.com/doc.html");
        if (ex instanceof ConstraintDeclarationException) {
            System.out.println("ConstraintDeclarationException ");
        } else if (ex instanceof ConstraintDefinitionException) {
            System.out.println("ConstraintDefinitionException");
        } else if (ex instanceof GroupDefinitionException) {
            System.out.println("GroupDefinitionException");
        } else  {
            System.out.println("ConstraintViolation");
        }

        return Response.status(Response.Status.BAD_REQUEST).entity(errorMessage).build();
    }

}
