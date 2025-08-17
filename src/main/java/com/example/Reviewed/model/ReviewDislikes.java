package com.example.Reviewed.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class ReviewDislikes extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "review_id")
    private ContentReviews dislikedReview;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity dislikedByUser;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ContentReviews getDislikedReview() {
        return dislikedReview;
    }

    public void setDislikedReview(ContentReviews dislikedReview) {
        this.dislikedReview = dislikedReview;
    }

    public UserEntity getDislikedByUser() {
        return dislikedByUser;
    }

    public void setDislikedByUser(UserEntity dislikedByUser) {
        this.dislikedByUser = dislikedByUser;
    }
}
