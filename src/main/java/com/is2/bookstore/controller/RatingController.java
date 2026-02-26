package com.is2.bookstore.Controller;

import com.is2.bookstore.model.Rating;
import com.is2.bookstore.repository.RatingRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rate")
public class RatingController {

    private final RatingRepository ratingRepository;

    public RatingController(RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }

    @PostMapping
    public ResponseEntity<?> rateBook(@RequestBody Rating rating) {

        // Validar score
        if (rating.getScore() < 1 || rating.getScore() > 5) {
            return ResponseEntity.badRequest()
                    .body("Score tiene que ser entre 1 y 5");
        }

        // Guardar rating
        ratingRepository.save(rating);

        // Calcular promedio
        List<Rating> ratings = ratingRepository.findByBookId(rating.getBookId());

        double average = ratings.stream()
                .mapToInt(Rating::getScore)
                .average()
                .orElse(0.0);

        return ResponseEntity.ok("Rating saved. Average: " + average);
    }
}