package com.bookstore.resources;

import com.bookstore.Database;
import com.bookstore.models.Author;
import com.bookstore.models.Book;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import java.util.List;
import java.util.stream.Collectors;

@Path("/authors")
public class AuthorResource {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createAuthor(Author author) {
        author.setId(Database.authorId.getAndIncrement());
        Database.authors.put(author.getId(), author);
        return Response.status(201).entity(author).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllAuthors() {
        return Response.ok(Database.authors.values()).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAuthor(@PathParam("id") Long id) {
        Author author = Database.authors.get(id);
        if(author == null) throw new NotFoundException("Author not found");
        return Response.ok(author).build();
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateAuthor(@PathParam("id") Long id, Author updatedAuthor) {
        if(!Database.authors.containsKey(id)) {
            throw new NotFoundException("Author not found");
        }
        updatedAuthor.setId(id);
        Database.authors.put(id, updatedAuthor);
        return Response.ok(updatedAuthor).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteAuthor(@PathParam("id") Long id) {
        if(Database.authors.remove(id) == null) {
            throw new NotFoundException("Author not found");
        }
        return Response.noContent().build();
    }

    @GET
    @Path("/{id}/books")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Book> getAuthorBooks(@PathParam("id") Long authorId) {
        return Database.books.values().stream()
                .filter(book -> authorId.equals(book.getAuthorId()))
                .collect(Collectors.toList());
    }
}
