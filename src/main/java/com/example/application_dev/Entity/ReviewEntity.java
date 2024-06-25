package com.example.application_dev.Entity;

import java.util.Date;

public class ReviewEntity {
    private Long id;
    private Long userId;
    private Long deviceId;
    private Integer rating;
    private String comment;
    private Date reviewDate;

    public ReviewEntity() {
    }

    public ReviewEntity(Long id, Long userId, Long deviceId, Integer rating, String comment, Date reviewDate) {
        this.id = id;
        this.userId = userId;
        this.deviceId = deviceId;
        this.rating = rating;
        this.comment = comment;
        this.reviewDate = reviewDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Long deviceId) {
        this.deviceId = deviceId;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(Date reviewDate) {
        this.reviewDate = reviewDate;
    }

    @Override
    public String toString() {
        return "ReviewEntity{" +
                "id=" + id +
                ", userId=" + userId +
                ", deviceId=" + deviceId +
                ", rating=" + rating +
                ", comment='" + comment + '\'' +
                ", reviewDate=" + reviewDate +
                '}';
    }
}
