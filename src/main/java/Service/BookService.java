package Service;

import com.is2.bookstore.model.Book;
import com.is2.bookstore.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> searchBooks(String author, String title, String isbn) {
        return bookRepository.search(author, title, isbn);
    }
}