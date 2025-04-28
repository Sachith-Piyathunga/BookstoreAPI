package com.bookstore.resources;

import com.bookstore.Database;
import com.bookstore.models.*;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

@Path("/customers/{customerId}/cart")
public class CartResource {

    @PUT
    @Path("/items/{bookId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateCartItem(
            @PathParam("customerId") Long customerId,
            @PathParam("bookId") Long bookId,
            CartItem updatedItem) {
        
        Cart cart = Database.carts.get(customerId);
        if(cart == null) throw new NotFoundException("Cart not found");
        
        // Find existing item
        CartItem existingItem = cart.getItems().stream()
                .filter(item -> item.getBookId().equals(bookId))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Item not found"));
        
        // Update quantity
        existingItem.setQuantity(updatedItem.getQuantity());
        return Response.ok(cart).build();
    }

    @DELETE
    @Path("/items/{bookId}")
    public Response removeCartItem(
            @PathParam("customerId") Long customerId,
            @PathParam("bookId") Long bookId) {
        
        Cart cart = Database.carts.get(customerId);
        if(cart == null) throw new NotFoundException("Cart not found");
        
        boolean removed = cart.getItems().removeIf(item -> 
            item.getBookId().equals(bookId));
        
        if(!removed) throw new NotFoundException("Item not found");
        return Response.noContent().build();
    }
}