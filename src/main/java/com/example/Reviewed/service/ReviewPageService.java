package com.example.Reviewed.service;

import com.example.Reviewed.Dto.ReviewPageDto;
import com.example.Reviewed.Dto.SingleReviewPageDto;
import com.example.Reviewed.Dto.UserDto;
import com.example.Reviewed.model.CommentReplyEntity;

import java.util.List;


public interface ReviewPageService {
    List<ReviewPageDto> AllReviews();

    SingleReviewPageDto getReviewByID(long id);

    List<UserDto> setReviewLike(long reviewId);
    List<UserDto> setReviewDisLike(long reviewId);

    SingleReviewPageDto setReviewReply(long reviewId, String comment);

    List<UserDto> removeReviewLike(long reviewId);

    List<UserDto> removeReviewDisLike(long reviewId);

    CommentReplyEntity setCommentReply(long contentId, long reviewId, long commentId, String comment);

    boolean deleteReivew(long reviewId);

    boolean deleteComment(long commentId);
}
