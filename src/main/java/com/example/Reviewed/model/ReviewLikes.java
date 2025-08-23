package com.example.Reviewed.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class ReviewLikes extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id", nullable = false)
    private ContentReviews likedReview;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity likedByUser;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ContentReviews getLikedReview() {
        return likedReview;
    }

    public void setLikedReview(ContentReviews likedReview) {
        this.likedReview = likedReview;
    }

    public UserEntity getLikedByUser() {
        return likedByUser;
    }

    public void setLikedByUser(UserEntity likedByUser) {
        this.likedByUser = likedByUser;
    }
}

