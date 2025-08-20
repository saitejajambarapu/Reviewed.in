package com.example.Reviewed.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
public class CommentReplyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // This is the parent reply being commented on
    @ManyToOne
    @JoinColumn(name = "commented_to_id")
    @JsonIgnore
    private ReviewReplies commentedTo;

    // The reply/author who made the comment
    @ManyToOne
    @JoinColumn(name = "commented_by_id")
    private ReviewReplies commentedBy;

    // The actual content of the reply
    @ManyToOne
    @JoinColumn(name = "content_id")
    @JsonIgnore
    private ContentEntity contentEntity;

    @ManyToOne
    @JoinColumn(name = "master_comment_id")
    @JsonIgnore
    private ReviewReplies masterComment;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ReviewReplies getCommentedTo() {
        return commentedTo;
    }

    public void setCommentedTo(ReviewReplies commentedTo) {
        this.commentedTo = commentedTo;
    }

    public ReviewReplies getCommentedBy() {
        return commentedBy;
    }

    public void setCommentedBy(ReviewReplies commentedBy) {
        this.commentedBy = commentedBy;
    }

    public ContentEntity getContentEntity() {
        return contentEntity;
    }

    public void setContentEntity(ContentEntity contentEntity) {
        this.contentEntity = contentEntity;
    }

    public ReviewReplies getMasterComment() {
        return masterComment;
    }

    public void setMasterComment(ReviewReplies masterComment) {
        this.masterComment = masterComment;
    }
}
