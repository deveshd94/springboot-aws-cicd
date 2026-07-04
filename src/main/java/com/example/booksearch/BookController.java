package com.example.booksearch;

import java.util.List;
import java.util.Locale;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookController {

    private static final List<Book> BOOKS = List.of(
            new Book(1, "Designing Data-Intensive Applications", "Martin Kleppmann", "distributed-systems"),
            new Book(2, "Effective Java", "Joshua Bloch", "java"),
            new Book(3, "Release It!", "Michael T. Nygard", "production"),
            new Book(4, "Terraform: Up and Running", "Yevgeniy Brikman", "devops"),
            new Book(5, "Continuous Delivery", "Jez Humble and David Farley", "devops")
    );

    @GetMapping("/")
    public String home() {
        return "Book Search is running. Try /books or /books?q=java";
    }

    @GetMapping("/books")
    public List<Book> books(@RequestParam(defaultValue = "") String q) {
        String query = q.toLowerCase(Locale.ROOT).trim();
        if (query.isEmpty()) {
            return BOOKS;
        }

        return BOOKS.stream()
                .filter(book -> contains(book.title(), query)
                        || contains(book.author(), query)
                        || contains(book.category(), query))
                .toList();
    }

    private static boolean contains(String value, String query) {
        return value.toLowerCase(Locale.ROOT).contains(query);
    }
}

