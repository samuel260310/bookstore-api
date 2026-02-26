package com.is2.bookstore.ui;

import com.is2.bookstore.model.Book;
import com.is2.bookstore.model.Rating;
import com.is2.bookstore.model.Review;
import com.is2.bookstore.repository.BookRepository;
import com.is2.bookstore.repository.RatingRepository;
import com.is2.bookstore.repository.ReviewRepository;
import com.is2.bookstore.service.BookService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

@Component
public class BookstoreUI implements CommandLineRunner {

    private final BookService bookService;
    private final BookRepository bookRepository;
    private final RatingRepository ratingRepository;
    private final ReviewRepository reviewRepository;
    private final Scanner scanner;

    public BookstoreUI(BookService bookService, BookRepository bookRepository,
                       RatingRepository ratingRepository, ReviewRepository reviewRepository) {
        this.bookService = bookService;
        this.bookRepository = bookRepository;
        this.ratingRepository = ratingRepository;
        this.reviewRepository = reviewRepository;
        this.scanner = new Scanner(System.in);
    }

    @Override
    public void run(String... args) throws Exception {
        boolean running = true;

        while (running) {
            mostrarMenu();
            String opcion = scanner.nextLine().trim();

            switch (opcion) {
                case "1":
                    listarTodosLosLibros();
                    break;
                case "2":
                    buscarLibros();
                    break;
                case "3":
                    busquedaSimple();
                    break;
                case "4":
                    mostrarDetallesLibro();
                    break;
                case "5":
                    agregarCalificacion();
                    break;
                case "6":
                    agregarResena();
                    break;
                case "7":
                    running = false;
                    System.out.println("¡Gracias por usar Bookstore!");
                    break;
                default:
                    System.out.println("Opción no válida. Intenta de nuevo.");
            }

            if (running) {
                System.out.println("\nPresiona Enter para continuar...");
                scanner.nextLine();
            }
        }
    }

    private void mostrarMenu() {
        System.out.println("\n" + "═".repeat(50));
        System.out.println("          BOOKSTORE - MENÚ PRINCIPAL ");
        System.out.println("═".repeat(50));
        System.out.println("1.Listar todos los libros");
        System.out.println("2.Búsqueda avanzada (autor, título, ISBN)");
        System.out.println("3.Búsqueda simple");
        System.out.println("4.Ver detalles de un libro");
        System.out.println("5.Agregar calificación");
        System.out.println("6.Agregar reseña");
        System.out.println("7.Salir");
        System.out.println("═".repeat(50));
        System.out.print("Selecciona una opción (1-7): ");
    }

    private void listarTodosLosLibros() {
        List<Book> libros = bookRepository.findAll();

        if (libros.isEmpty()) {
            System.out.println("No hay libros en la base de datos.");
            return;
        }

        System.out.println("\n LISTA DE TODOS LOS LIBROS:");
        System.out.println("─".repeat(100));
        System.out.printf("%-3s | %-30s | %-20s | %-15s\n", "ID", "TÍTULO", "AUTOR", "ISBN");
        System.out.println("─".repeat(100));

        for (Book libro : libros) {
            System.out.printf("%-3d | %-30s | %-20s | %-15s\n",
                    libro.getId(),
                    truncar(libro.getTitle(), 28),
                    truncar(libro.getAuthor(), 18),
                    libro.getIsbn());
        }

        System.out.println("─".repeat(100));
        System.out.printf("Total: %d libro(s)%n", libros.size());
    }

    private void buscarLibros() {
        System.out.println("\n BÚSQUEDA AVANZADA");
        System.out.println("(Deja en blanco para no filtrar por un campo)");

        System.out.print("Autor: ");
        String autor = scanner.nextLine().trim().isEmpty() ? null : scanner.nextLine().trim();

        System.out.print("Título: ");
        String titulo = scanner.nextLine().trim().isEmpty() ? null : scanner.nextLine().trim();

        System.out.print("ISBN: ");
        String isbn = scanner.nextLine().trim().isEmpty() ? null : scanner.nextLine().trim();

        List<Book> resultados = bookService.advancedSearch(autor, titulo, isbn);

        mostrarResultados(resultados, "Búsqueda avanzada");
    }

    private void busquedaSimple() {
        System.out.println("\n BÚSQUEDA SIMPLE");
        System.out.println("─".repeat(50));
        System.out.println("1. Buscar por autor");
        System.out.println("2. Buscar por título");
        System.out.println("3. Buscar por ISBN");
        System.out.println("─".repeat(50));
        System.out.print("Selecciona una opción (1-3): ");
        
        String opcion = scanner.nextLine().trim();
        
        switch (opcion) {
            case "1":
                System.out.print("\n👤 Ingresa el nombre del autor: ");
                String autor = scanner.nextLine().trim();
                if (autor.isEmpty()) {
                    System.out.println("El autor no puede estar vacío.");
                    return;
                }
                List<Book> resultadosAutor = bookService.advancedSearch(autor, null, null);
                mostrarResultados(resultadosAutor, "Búsqueda por autor: " + autor);
                break;
                
            case "2":
                System.out.print("\n Ingresa el título del libro: ");
                String titulo = scanner.nextLine().trim();
                if (titulo.isEmpty()) {
                    System.out.println("El título no puede estar vacío.");
                    return;
                }
                List<Book> resultadosTitulo = bookService.advancedSearch(null, titulo, null);
                mostrarResultados(resultadosTitulo, "Búsqueda por título: " + titulo);
                break;
                
            case "3":
                System.out.print("\n Ingresa el ISBN: ");
                String isbn = scanner.nextLine().trim();
                if (isbn.isEmpty()) {
                    System.out.println("El ISBN no puede estar vacío.");
                    return;
                }
                List<Book> resultadosISBN = bookService.advancedSearch(null, null, isbn);
                mostrarResultados(resultadosISBN, "Búsqueda por ISBN: " + isbn);
                break;
                
            default:
                System.out.println("Opción no válida.");
        }
    }

    private void mostrarResultados(List<Book> libros, String titulo) {
        if (libros.isEmpty()) {
            System.out.println("\n No se encontraron libros para: " + titulo);
            return;
        }

        System.out.println("\n RESULTADOS: " + titulo);
        System.out.println("─".repeat(100));
        System.out.printf("%-3s | %-30s | %-20s | %-15s\n", "ID", "TÍTULO", "AUTOR", "ISBN");
        System.out.println("─".repeat(100));

        for (Book libro : libros) {
            System.out.printf("%-3d | %-30s | %-20s | %-15s\n",
                    libro.getId(),
                    truncar(libro.getTitle(), 28),
                    truncar(libro.getAuthor(), 18),
                    libro.getIsbn());
        }

        System.out.println("─".repeat(100));
        System.out.printf("Se encontraron %d libro(s)%n", libros.size());
    }

    private void mostrarDetallesLibro() {
        System.out.print("\n Ingresa el título del libro: ");
        String titulo = scanner.nextLine().trim();
        
        if (titulo.isEmpty()) {
            System.out.println("El título no puede estar vacío.");
            return;
        }
        
        List<Book> resultados = bookService.advancedSearch(null, titulo, null);
        
        if (resultados.isEmpty()) {
            System.out.println("No se encontró un libro con el título: " + titulo);
            return;
        }
        
        if (resultados.size() == 1) {
            Book b = resultados.get(0);
            mostrarDetallesDelLibro(b);
        } else {
            System.out.println("\nSe encontraron múltiples coincidencias:");
            System.out.println("─".repeat(100));
            System.out.printf("%-3s | %-30s | %-20s | %-15s\n", "ID", "TÍTULO", "AUTOR", "ISBN");
            System.out.println("─".repeat(100));
            
            for (int i = 0; i < resultados.size(); i++) {
                Book libro = resultados.get(i);
                System.out.printf("%-3d | %-30s | %-20s | %-15s\n",
                        i + 1,
                        truncar(libro.getTitle(), 28),
                        truncar(libro.getAuthor(), 18),
                        libro.getIsbn());
            }
            
            System.out.print("\nSelecciona el número del libro: ");
            try {
                int indice = Integer.parseInt(scanner.nextLine().trim()) - 1;
                if (indice >= 0 && indice < resultados.size()) {
                    mostrarDetallesDelLibro(resultados.get(indice));
                } else {
                    System.out.println("Selección inválida.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Debes ingresar un número válido.");
            }
        }
    }
    
    private void mostrarDetallesDelLibro(Book b) {
        System.out.println("\n" + "═".repeat(50));
        System.out.println("DETALLES DEL LIBRO");
        System.out.println("═".repeat(50));
        System.out.println("ID:     " + b.getId());
        System.out.println("Título: " + b.getTitle());
        System.out.println("Autor:  " + b.getAuthor());
        System.out.println("ISBN:   " + b.getIsbn());
        System.out.println("═".repeat(50));
    }

    private void agregarCalificacion() {
        System.out.println("\n⭐ AGREGAR CALIFICACIÓN");
        System.out.println("─".repeat(100));
        
        List<Book> libros = bookRepository.findAll();
        
        if (libros.isEmpty()) {
            System.out.println("❌ No hay libros disponibles.");
            return;
        }
        
        // Mostrar todos los libros
        System.out.printf("%-3s | %-30s | %-20s | %-15s\n", "#", "TÍTULO", "AUTOR", "ISBN");
        System.out.println("─".repeat(100));
        
        for (int i = 0; i < libros.size(); i++) {
            Book libro = libros.get(i);
            System.out.printf("%-3d | %-30s | %-20s | %-15s\n",
                    i + 1,
                    truncar(libro.getTitle(), 28),
                    truncar(libro.getAuthor(), 18),
                    libro.getIsbn());
        }
        
        System.out.print("\nSelecciona el número del libro (1-" + libros.size() + "): ");
        
        Book libroSeleccionado;
        try {
            int indice = Integer.parseInt(scanner.nextLine().trim()) - 1;
            if (indice < 0 || indice >= libros.size()) {
                System.out.println("❌ Selección inválida.");
                return;
            }
            libroSeleccionado = libros.get(indice);
        } catch (NumberFormatException e) {
            System.out.println("❌ Debes ingresar un número válido.");
            return;
        }
        
        try {
            System.out.print("Ingresa el ID del usuario: ");
            long userId = Long.parseLong(scanner.nextLine().trim());

            System.out.print("Ingresa la calificación (1-5): ");
            int score = Integer.parseInt(scanner.nextLine().trim());

            if (score < 1 || score > 5) {
                System.out.println("❌ La puntuación debe estar entre 1 y 5.");
                return;
            }

            Rating rating = new Rating();
            rating.setBookId(libroSeleccionado.getId());
            rating.setUserId(userId);
            rating.setScore(score);

            ratingRepository.save(rating);

            // Calcular promedio
            List<Rating> ratings = ratingRepository.findByBookId(libroSeleccionado.getId());
            double average = ratings.stream()
                    .mapToInt(Rating::getScore)
                    .average()
                    .orElse(0.0);

            System.out.println("\n Calificación guardada exitosamente!");
            System.out.printf("Promedio de calificaciones para este libro: %.2f%n", average);
        } catch (NumberFormatException e) {
            System.out.println("Debes ingresar números válidos.");
        }
    }

    private void agregarResena() {
        System.out.print("\nIngresa el título del libro: ");
        String titulo = scanner.nextLine().trim();
        
        if (titulo.isEmpty()) {
            System.out.println("El título no puede estar vacío.");
            return;
        }
        
        List<Book> resultados = bookService.advancedSearch(null, titulo, null);
        
        if (resultados.isEmpty()) {
            System.out.println("No se encontró un libro con ese título.");
            return;
        }
        
        Book libroSeleccionado;
        
        if (resultados.size() == 1) {
            libroSeleccionado = resultados.get(0);
        } else {
            System.out.println("\n Se encontraron múltiples coincidencias:");
            System.out.println("─".repeat(100));
            System.out.printf("%-3s | %-30s | %-20s | %-15s\n", "#", "TÍTULO", "AUTOR", "ISBN");
            System.out.println("─".repeat(100));
            
            for (int i = 0; i < resultados.size(); i++) {
                Book libro = resultados.get(i);
                System.out.printf("%-3d | %-30s | %-20s | %-15s\n",
                        i + 1,
                        truncar(libro.getTitle(), 28),
                        truncar(libro.getAuthor(), 18),
                        libro.getIsbn());
            }
            
            System.out.print("\nSelecciona el número del libro: ");
            try {
                int indice = Integer.parseInt(scanner.nextLine().trim()) - 1;
                if (indice < 0 || indice >= resultados.size()) {
                    System.out.println("Selección inválida.");
                    return;
                }
                libroSeleccionado = resultados.get(indice);
            } catch (NumberFormatException e) {
                System.out.println("Debes ingresar un número válido.");
                return;
            }
        }
        
        try {
            System.out.print("Ingresa el ID del usuario: ");
            long userId = Long.parseLong(scanner.nextLine().trim());

            System.out.print("Ingresa tu reseña: ");
            String content = scanner.nextLine().trim();

            if (content.isEmpty()) {
                System.out.println("El contenido de la reseña no puede estar vacío.");
                return;
            }

            Review review = new Review();
            review.setBookId(libroSeleccionado.getId());
            review.setUserId(userId);
            review.setContent(content);

            reviewRepository.save(review);

            System.out.println("\n Reseña guardada exitosamente!");
            System.out.println("Contenido: " + content);
        } catch (NumberFormatException e) {
            System.out.println("Debes ingresar números válidos para el ID del usuario.");
        }
    }

    private String truncar(String texto, int longitud) {
        if (texto == null) return "";
        return texto.length() > longitud ? texto.substring(0, longitud - 3) + "..." : texto;
    }
}
