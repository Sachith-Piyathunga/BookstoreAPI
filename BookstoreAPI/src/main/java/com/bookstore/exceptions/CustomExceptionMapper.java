package com.bookstore.exceptions;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class CustomExceptionMapper implements ExceptionMapper<Exception> {
    
    @Override
    public Response toResponse(Exception ex) {
        return Response.status(Response.Status.BAD_REQUEST)
                .entity("{\"error\": \"" + ex.getMessage() + "\"}")
                .type("application/json")
                .build();
    }
}