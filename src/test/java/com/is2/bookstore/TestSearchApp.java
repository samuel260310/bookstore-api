package com.is2.bookstore;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.is2.bookstore.service.BookService;
import com.is2.bookstore.model.Book;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.List;

@SpringBootTest
class BookstoreApplicationTests {

	@Test
	void contextLoads() {
	}

}


@SpringBootApplication
public class TestSearchApp {

	public static void main(String[] args) {
		// Levanta el contexto de Spring Boot
		ApplicationContext context = SpringApplication.run(TestSearchApp.class, args);

		// Obtén tu servicio de búsqueda
		BookService bookService = context.getBean(BookService.class);

		// Llama a tu método de búsqueda
		List<Book> results = bookService.advancedSearch("Rowling", "Harry", "1234");

		// Muestra los resultados en consola
		System.out.println("Resultados de la búsqueda:");
		results.forEach(System.out::println);
	}
}
