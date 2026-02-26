package com.is2.bookstore.Controller;

import com.is2.bookstore.model.Book;
import com.is2.bookstore.service.BookService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/search-simple")
    public List<Book> searchSimple(@RequestParam("q") String query) {
        return bookService.searchSimple(query);
    }

    @GetMapping("/search")
    public List<Book> advancedSearch(
            @RequestParam(required = false) String author,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String isbn) {

        return bookService.advancedSearch(author, title, isbn);
    }
}
