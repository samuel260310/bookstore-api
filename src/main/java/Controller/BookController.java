package Controller;

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

    @GetMapping("/search")
    public List<Book> searchBooks(
        @RequestParam(required = false) String author,
        @RequestParam(required = false) String title,
        @RequestParam(required = false) String isbn
    ) {
        return bookService.searchBooks(author, title, isbn);
    }
}