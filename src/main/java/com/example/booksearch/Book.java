package com.example.booksearch;

public record Book(
        long id,
        String title,
        String author,
        String category
) {
}

