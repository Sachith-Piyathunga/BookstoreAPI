package com.bookstore.resources;

import com.bookstore.Database;
import com.bookstore.models.*;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;
import java.util.ArrayList;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Path("/customers/{customerId}/orders")
public class OrderResource {

    @POST
    public Response createOrder(@PathParam("customerId") Long customerId) {
        // Validate customer exists
        if(!Database.customers.containsKey(customerId)) {
            throw new NotFoundException("Customer not found");
        }
        
        // Get cart
        Cart cart = Database.carts.get(customerId);
        if(cart == null || cart.getItems().isEmpty()) {
            throw new BadRequestException("Cart is empty");
        }
        
        // Create order
        Order order = new Order();
        order.setId(Database.orderId.getAndIncrement());
        order.setCustomerId(customerId);
        order.setOrderDate(new Date());
        
        // Calculate total and update stock
        double total = 0;
        for(CartItem item : cart.getItems()) {
            Book book = Database.books.get(item.getBookId());
            if(book == null) {
                throw new NotFoundException("Book not found: " + item.getBookId());
            }
            if(book.getStockQuantity() < item.getQuantity()) {
                throw new BadRequestException("Out of stock: " + book.getTitle());
            }
            
            // Update stock
            book.setStockQuantity(book.getStockQuantity() - item.getQuantity());
            total += book.getPrice() * item.getQuantity();
        }
        
        order.setTotal(total);
        order.setItems(new ArrayList<>(cart.getItems()));
        Database.orders.put(order.getId(), order);
        
        // Clear cart
        cart.getItems().clear();
        
        return Response.status(201).entity(order).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOrders(@PathParam("customerId") Long customerId) {
        List<Order> customerOrders = Database.orders.values().stream()
                .filter(order -> customerId.equals(order.getCustomerId()))
                .collect(Collectors.toList());
        return Response.ok(customerOrders).build();
    }

    @GET
    @Path("/{orderId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOrder(@PathParam("customerId") Long customerId,
                             @PathParam("orderId") Long orderId) {
        Order order = Database.orders.get(orderId);
        if(order == null || !customerId.equals(order.getCustomerId())) {
            throw new NotFoundException("Order not found");
        }
        return Response.ok(order).build();
    }
}

