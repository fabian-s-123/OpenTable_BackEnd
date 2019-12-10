package at.opentable.api;

import at.opentable.controller.ReviewController;
import at.opentable.entity.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping(path = "/api/reviews")
public class ReviewApi {

    @Autowired
    private ReviewController reviewController;

    @PostMapping
    public ResponseEntity createReview(@RequestBody Review review) {
        boolean success = this.reviewController.createReview(review);
        if (success) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @GetMapping
    public Iterable<Review> findAll() {
        return this.reviewController.findAll();
    }

    @GetMapping(path = "/{id}")
    public Optional<Review> getReview(@PathVariable int id, String comment) {
        return this.reviewController.getReview(id);
    }

    @PutMapping
    public ResponseEntity updateReview(@RequestBody Review review) {
        boolean success = this.reviewController.updateReview(review);
        if (success) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @DeleteMapping
    public ResponseEntity deleteReview(@RequestBody Review review) {
        boolean success = this.reviewController.deleteReview(review);
        if (success) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity deleteReviewById(@PathVariable int id) {
        boolean success = this.reviewController.deleteReviewById(id);
        if (success) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }
}
