package com.example.Reviewed.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.Data;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;

import java.util.ArrayList;
import java.util.List;

@Entity
public class ContentReviews extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String review;

    private long likes;
    private long dislikes;
    private long isReplied;
    private Long contentId;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private UserEntity reviewedBy;

    @OneToMany(mappedBy = "likedReview", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReviewLikes> likedList = new ArrayList<>();

    @OneToOne(mappedBy = "review", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private UserContentInteraction interaction;

    @OneToMany(mappedBy = "dislikedReview", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReviewDislikes> dislikeList = new ArrayList<>();

    @OneToMany(mappedBy = "review", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReviewReplies> reviewReplies = new ArrayList<>();





    public long getLikes() {
        return likes;
    }

    public void setLikes(long likes) {
        this.likes = likes;
    }

    public long getDislikes() {
        return dislikes;
    }

    public void setDislikes(long dislikes) {
        this.dislikes = dislikes;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public long isReplied() {
        return isReplied;
    }

    public void setReplied(long replied) {
        isReplied = replied;
    }

    public Long getContentId() {
        return contentId;
    }

    public void setContentId(Long contentId) {
        this.contentId = contentId;
    }

    public UserEntity getReviewedBy() {
        return reviewedBy;
    }

    public void setReviewedBy(UserEntity reviewedBy) {
        this.reviewedBy = reviewedBy;
    }

    @Override
    public String toString() {
        return "ContentReviews{" +
                "id=" + id +
                ", review='" + review + '\'' +
                ", likes=" + likes +
                ", dislikes=" + dislikes +
                ", isReplied=" + isReplied +
                ", contentId=" + contentId +
                ", reviewedById=" + (reviewedBy != null ? reviewedBy.getId() : null) +
                ", reviewRepliesCount=" + (reviewReplies != null ? reviewReplies.size() : 0) +
                '}';
    }

}

