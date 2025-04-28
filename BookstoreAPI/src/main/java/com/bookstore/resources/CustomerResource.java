package com.bookstore.resources;

import com.bookstore.Database;
import com.bookstore.models.Customer;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

@Path("/customers")
public class CustomerResource {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createCustomer(Customer customer) {
        customer.setId(Database.customerId.getAndIncrement());
        Database.customers.put(customer.getId(), customer);
        return Response.status(201).entity(customer).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllCustomers() {
        return Response.ok(Database.customers.values()).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCustomer(@PathParam("id") Long id) {
        Customer customer = Database.customers.get(id);
        if(customer == null) throw new NotFoundException("Customer not found");
        return Response.ok(customer).build();
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateCustomer(@PathParam("id") Long id, Customer updatedCustomer) {
        if(!Database.customers.containsKey(id)) {
            throw new NotFoundException("Customer not found");
        }
        updatedCustomer.setId(id);
        Database.customers.put(id, updatedCustomer);
        return Response.ok(updatedCustomer).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteCustomer(@PathParam("id") Long id) {
        if(Database.customers.remove(id) == null) {
            throw new NotFoundException("Customer not found");
        }
        // Remove associated cart
        Database.carts.remove(id);
        return Response.noContent().build();
    }
}

