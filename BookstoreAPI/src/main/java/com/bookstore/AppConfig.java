package com.bookstore;

import java.util.*;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

@ApplicationPath("BookstoreAPI/api")
public class AppConfig extends Application {
    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> classes = new HashSet<>();
        // Register resources
        classes.add(com.bookstore.resources.BookResource.class);
        classes.add(com.bookstore.resources.AuthorResource.class);
        classes.add(com.bookstore.resources.CustomerResource.class);
        classes.add(com.bookstore.resources.CartResource.class);
        classes.add(com.bookstore.resources.OrderResource.class);
        // Register exception mapper
        classes.add(com.bookstore.exceptions.CustomExceptionMapper.class);
        return classes;
    }
}
