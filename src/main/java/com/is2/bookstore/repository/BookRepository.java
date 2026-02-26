
package com.is2.bookstore.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.is2.bookstore.model.Book;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    @Query("""
        SELECT b FROM Book b
        WHERE (:author IS NULL OR LOWER(b.author) LIKE LOWER(CONCAT('%', :author, '%')))
        AND (:title IS NULL OR LOWER(b.title) LIKE LOWER(CONCAT('%', :title, '%')))
        AND (:isbn IS NULL OR b.isbn = :isbn)
    """)
    List<Book> advancedSearch(
            @Param("author") String author,
            @Param("title") String title,
            @Param("isbn") String isbn
    );

    List<Book> simpleSearch
            (String title,
             String author
    );
}

