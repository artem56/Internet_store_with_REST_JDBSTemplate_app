package com.example.application_dev.controller;

import com.example.application_dev.Entity.ReviewEntity;
import com.example.application_dev.service.ReviewService;
import com.example.application_dev.service.UserService;
import io.jsonwebtoken.JwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<String> createOrUpdateReview(
            @RequestHeader("Authorization") String authorizationHeader,
            @RequestBody ReviewRequest reviewRequest
    ) {
        String[] parts = authorizationHeader.split(" ");
        String token = null;
        if (parts.length == 2) {
            token = parts[1];
        }
        if (token == null) {
            return ResponseEntity.badRequest().body("auth token is null");
        }
        try {
            userService.verify_token(token);
        } catch (JwtException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
        }

        if (reviewRequest.getRating() < 1 || reviewRequest.getRating() > 5) {
            return ResponseEntity.badRequest().body("Рейтинг должен быть от 1 до 5");
        }

        try {
            reviewService.createOrUpdateReview(reviewRequest.getUserId(), reviewRequest.getDeviceId(), reviewRequest.getRating(), reviewRequest.getComment());
            return ResponseEntity.ok("Review saved successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to save review");
        }
    }

    @GetMapping("/{deviceId}")
    public ResponseEntity<?> getReviewsByDeviceId(@PathVariable Long deviceId) {
        try {
            List<ReviewEntity> reviews = reviewService.getReviewsByDeviceId(deviceId);
            return ResponseEntity.ok(reviews);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to fetch reviews");
        }
    }
}
