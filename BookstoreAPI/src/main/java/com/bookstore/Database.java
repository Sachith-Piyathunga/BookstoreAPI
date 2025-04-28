package com.bookstore;

import com.bookstore.models.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;

public class Database {
    public static ConcurrentHashMap<Long, Book> books = new ConcurrentHashMap<>();
    public static ConcurrentHashMap<Long, Author> authors = new ConcurrentHashMap<>();
    public static ConcurrentHashMap<Long, Customer> customers = new ConcurrentHashMap<>();
    public static ConcurrentHashMap<Long, Cart> carts = new ConcurrentHashMap<>();
    public static ConcurrentHashMap<Long, Order> orders = new ConcurrentHashMap<>();
    
    public static AtomicLong bookId = new AtomicLong(1);
    public static AtomicLong authorId = new AtomicLong(1);
    public static AtomicLong customerId = new AtomicLong(1);
    public static AtomicLong orderId = new AtomicLong(1);
}