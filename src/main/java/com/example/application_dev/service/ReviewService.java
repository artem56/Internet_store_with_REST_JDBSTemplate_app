package com.example.application_dev.service;

import com.example.application_dev.Entity.ReviewEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ReviewService {

    @Autowired
    JdbcTemplate jdbcTemplate;

    private final RowMapper<ReviewEntity> reviewRowMapper = (rs, rowNum) -> {
        ReviewEntity review = new ReviewEntity();
        review.setId(rs.getLong("review_id"));
        review.setUserId(rs.getLong("user_user_id"));
        review.setDeviceId(rs.getLong("device_device_id"));
        review.setRating(rs.getInt("rating"));
        review.setComment(rs.getString("comment"));
        review.setReviewDate(rs.getDate("review_date"));
        return review;
    };

    public void createOrUpdateReview(Long userId, Long deviceId, Integer rating, String comment) {
        Integer reviewCount = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM reviews WHERE user_user_id = ? AND device_device_id = ?",
                new Object[]{userId, deviceId},
                Integer.class
        );

        if (reviewCount != null && reviewCount > 0) {
            // Update existing review
            jdbcTemplate.update(
                    "UPDATE reviews SET rating = ?, comment = ?, review_date = ? WHERE user_user_id = ? AND device_device_id = ?",
                    rating, comment, new Date(), userId, deviceId
            );
        } else {
            // Create new review
            jdbcTemplate.update(
                    "INSERT INTO reviews (user_user_id, device_device_id, rating, comment, review_date) VALUES (?, ?, ?, ?, ?)",
                    userId, deviceId, rating, comment, new Date()
            );
        }
    }

    public List<ReviewEntity> getReviewsByDeviceId(Long deviceId) {
        return jdbcTemplate.query(
                "SELECT * FROM reviews WHERE device_device_id = ?",
                new Object[]{deviceId},
                reviewRowMapper
        );
    }
}
