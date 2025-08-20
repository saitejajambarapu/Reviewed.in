package com.example.Reviewed.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class ReviewReplies extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String reply;

    @ManyToOne
    @JoinColumn(name = "review_id")
    @JsonIgnore
    private ContentReviews review;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity repliedUser;



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    public ContentReviews getReview() {
        return review;
    }

    public void setReview(ContentReviews review) {
        this.review = review;
    }

    public UserEntity getRepliedUser() {
        return repliedUser;
    }

    public void setRepliedUser(UserEntity repliedUser) {
        this.repliedUser = repliedUser;
    }
}
