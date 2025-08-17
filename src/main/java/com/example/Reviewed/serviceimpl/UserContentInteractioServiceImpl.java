package com.example.Reviewed.serviceimpl;

import com.example.Reviewed.Dto.ContentDtoWithUserInteractions;
import com.example.Reviewed.Dto.UserContentInteractionDto;
import com.example.Reviewed.model.ContentReviews;
import com.example.Reviewed.model.UserContentInteraction;
import com.example.Reviewed.model.UserEntity;
import com.example.Reviewed.repository.ContentReviewsRepo;
import com.example.Reviewed.repository.UserContentInteractionRepo;
import jakarta.persistence.RollbackException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserContentInteractioServiceImpl {

    @Autowired
    ContentReviewsRepo contentReviewsRepo;

    @Autowired
    UserContentInteractionRepo userContentInteractionRepo;

    @Autowired
    AuthServiceImpl authService;

    @Transactional(rollbackFor = RollbackException.class)
    public ContentDtoWithUserInteractions saveUserContentInteraction(ContentDtoWithUserInteractions userContentInteractionDto){
        UserEntity user = authService.getCurrentUser();
        UserContentInteraction userContentInteraction = userContentInteractionRepo.findByContentIdAndUserId(user.getId(),userContentInteractionDto.getId());
        if(userContentInteraction==null){
            userContentInteraction = new UserContentInteraction();
        }

        if(userContentInteractionDto.isLiked()){
            userContentInteraction.setLiked(true);
        }
        if(!userContentInteractionDto.isLiked()){
            userContentInteraction.setLiked(false);
        }
        if(userContentInteractionDto.isWatched()){
            userContentInteraction.setWatched(true);
        }
        if(!userContentInteractionDto.isWatched()){
            userContentInteraction.setWatched(false);
        }
        if(userContentInteractionDto.getReview()!=null){
            ContentReviews contentReview = contentReviewsRepo.findByContentIdAndUserId(user.getId(),userContentInteractionDto.getId());
            if(contentReview==null){
               contentReview= new ContentReviews();
            }
            contentReview.setContentId(userContentInteractionDto.getId());
            contentReview.setReview(userContentInteractionDto.getReview());
            contentReview.setReviewedBy(user);
            ContentReviews contentReviews1 = contentReviewsRepo.save(contentReview);
            userContentInteraction.setReview(contentReviews1);
        }if(userContentInteractionDto.getRating()>0){
            userContentInteraction.setRating(userContentInteractionDto.getRating());
        }
        userContentInteraction.setContentId(userContentInteractionDto.getId());
        userContentInteraction.setUser(user);
        userContentInteractionRepo.save(userContentInteraction);
        return userContentInteractionDto;
    }


}
