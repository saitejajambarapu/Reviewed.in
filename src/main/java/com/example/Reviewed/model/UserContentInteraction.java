package com.example.Reviewed.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class UserContentInteraction extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private boolean isLiked;
    private boolean isWatched;
    private float rating;

    @OneToOne
    @JoinColumn(name = "review_id")
    private ContentReviews review;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
    private Long contentId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isLiked() {
        return isLiked;
    }

    public void setLiked(boolean liked) {
        isLiked = liked;
    }

    public boolean isWatched() {
        return isWatched;
    }

    public void setWatched(boolean watched) {
        isWatched = watched;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public ContentReviews getReview() {
        return review;
    }

    public void setReview(ContentReviews review) {
        this.review = review;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public Long getContentId() {
        return contentId;
    }

    public void setContentId(Long contentId) {
        this.contentId = contentId;
    }

    @Override
    public String toString() {
        return "UserContentInteraction{" +
                "id=" + id +
                ", isLiked=" + isLiked +
                ", isWatched=" + isWatched +
                ", rating=" + rating +
                ", review=" + review +
                ", user=" + user +
                ", contentId=" + contentId +
                '}';
    }
}

