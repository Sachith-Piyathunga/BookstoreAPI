package com.bookstore.models;

public class Author {
    private Long id;
    private String name;
    private String biography;

    public Author() {}  // Required for JSON binding

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getBiography() { return biography; }
    public void setBiography(String biography) { this.biography = biography; }
}