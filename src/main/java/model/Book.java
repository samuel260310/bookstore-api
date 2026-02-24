package model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.*;
package com.is2.bookstore.bookstoreApplication;

public class Book {




    @Entity
    @Table(name = "books")
    public class Book {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private String author;

        private String title;

        private String isbn;

        // public Book() {}

        // Getters y setters
        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }

        public String getAuthor() { return author; }
        public void setAuthor(String author) { this.author = author; }

        public String getTitle() { return title; }
        public void setTitle(String title) { this.title = title; }

        public String getIsbn() { return isbn; }
        public void setIsbn(String isbn) { this.isbn = isbn; }
    }
}
