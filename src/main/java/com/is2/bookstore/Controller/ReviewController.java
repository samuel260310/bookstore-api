package com.is2.bookstore.Controller;

import com.is2.bookstore.model.Review;
import com.is2.bookstore.repository.ReviewRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/review")
public class ReviewController {

    private final ReviewRepository reviewRepository;

    public ReviewController(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    // POST /review → guarda la reseña
    @PostMapping
    public ResponseEntity<?> saveReview(@RequestBody Review request) {
        if (request.getContent() == null || request.getContent().isBlank()) {
            return ResponseEntity.badRequest().body("El contenido no puede estar vacío");
        }
        Review saved = reviewRepository.save(request);
        return ResponseEntity.ok(saved);
    }

    // POST /review/preview → muestra sin guardar
    @PostMapping("/preview")
    public ResponseEntity<?> previewReview(@RequestBody Review request) {
        if (request.getContent() == null || request.getContent().isBlank()) {
            return ResponseEntity.badRequest().body("El contenido no puede estar vacío");
        }
        return ResponseEntity.ok(Map.of(
                "preview", true,
                "bookId", request.getBookId(),
                "userId", request.getUserId(),
                "content", request.getContent(),
                "mensaje", "Vista previa. La reseña NO ha sido guardada."
        ));
    }
}

