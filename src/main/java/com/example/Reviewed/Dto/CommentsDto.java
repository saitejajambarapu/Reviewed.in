package com.example.Reviewed.Dto;

import com.example.Reviewed.model.CommentReplyEntity;

import java.util.List;

public class CommentsDto {

    private Long id;

    private ReivewRepliesDto CommentReply;

    private List<CommentReplyEntity> replies;

    public ReivewRepliesDto getCommentReply() {
        return CommentReply;
    }

    public void setCommentReply(ReivewRepliesDto commentReply) {
        CommentReply = commentReply;
    }

    public List<CommentReplyEntity> getReplies() {
        return replies;
    }

    public void setReplies(List<CommentReplyEntity> replies) {
        this.replies = replies;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
