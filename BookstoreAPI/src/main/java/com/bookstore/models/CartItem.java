package com.bookstore.models;

public class CartItem {
    private Long bookId;
    private int quantity;

    public CartItem() {}  // Required for JSON binding

    // Getters and Setters
    public Long getBookId() { return bookId; }
    public void setBookId(Long bookId) { this.bookId = bookId; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
}
