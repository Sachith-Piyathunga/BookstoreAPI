package com.bookstore.resources;

import com.bookstore.Database;
import com.bookstore.models.Book;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import jakarta.ws.rs.NotFoundException;

@Path("/books")
public class BookResource {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createBook(Book book) {
        book.setId(Database.bookId.getAndIncrement());
        Database.books.put(book.getId(), book);
        return Response.status(Response.Status.CREATED).entity(book).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllBooks() {
        return Response.ok(Database.books.values()).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBook(@PathParam("id") Long id) {
        Book book = Database.books.get(id);
        if(book == null) throw new NotFoundException("Book not found");
        return Response.ok(book).build();
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateBook(@PathParam("id") Long id, Book updatedBook) {
        if(!Database.books.containsKey(id)) {
            throw new NotFoundException("Book not found");
        }
        updatedBook.setId(id);
        Database.books.put(id, updatedBook);
        return Response.ok(updatedBook).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteBook(@PathParam("id") Long id) {
        if(Database.books.remove(id) == null) {
            throw new NotFoundException("Book not found");
        }
        return Response.noContent().build();
    }
}