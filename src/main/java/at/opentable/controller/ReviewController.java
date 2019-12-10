package at.opentable.controller;

import at.opentable.entity.Review;
import at.opentable.Repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.Optional;

@Controller
public class ReviewController {

    @Autowired
    private ReviewRepository reviewRepository;

    public boolean createReview (Review review) {
        this.reviewRepository.save(review);
        System.out.println("Review successfully created.");
        return true;
    }

    public Iterable<Review> findAll() {
        return this.reviewRepository.findAll();
    }

    public Optional<Review> getReview(int id) {
        return this.reviewRepository.findById(id);
    }

    public boolean updateReview (Review review) {
        this.reviewRepository.saveAndFlush(review);
        System.out.println("Review successfully updated.");
        return true;
    }

    public boolean deleteReview(Review review) {
        this.reviewRepository.delete(review);
        System.out.println("Review successfully deleted.");
        return true;
    }

    public boolean deleteReviewById(int id) {
        this.reviewRepository.deleteById(id);
        System.out.println("Review successfully deleted.");
        return true;
    }

}
