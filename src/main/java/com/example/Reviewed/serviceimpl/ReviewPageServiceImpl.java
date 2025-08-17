package com.example.Reviewed.serviceimpl;

import com.example.Reviewed.Dto.ReivewRepliesDto;
import com.example.Reviewed.Dto.ReviewAndUserDto;
import com.example.Reviewed.Dto.ReviewPageDto;
import com.example.Reviewed.Dto.UserDto;
import com.example.Reviewed.model.*;
import com.example.Reviewed.repository.*;
import com.example.Reviewed.service.ReviewPageService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ReviewPageServiceImpl implements ReviewPageService {

    @Autowired
    UserContentInteractionRepo userContentInteractionRepo;

    @Autowired
    ReviewLikesRepo reviewLikesRepo;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    ContentRepository contentRepository;

    @Autowired
    ContentReviewsRepo contentReviewsRepo;

    @Autowired
    AuthServiceImpl authService;

    @Autowired
    ReviewDisLikesRepo reviewDisLikesRepo;

    @Autowired
    ReviewRepliesRepo reviewRepliesRepo;

    @Override
    public List<ReviewPageDto> AllReviews() {
        List<ReviewPageDto> reviewPageDtoList = new ArrayList<>();
        List<UserContentInteraction> userContentInteractionList = userContentInteractionRepo.getAllInteractions();
        if(userContentInteractionList!=null){
            for(UserContentInteraction userContentInteraction : userContentInteractionList) {
                if (userContentInteraction.getReview() != null) {
                ReviewPageDto reviewPageDto = new ReviewPageDto();
                reviewPageDto.setId(userContentInteraction.getReview().getId());
                reviewPageDto.setRating(userContentInteraction.getRating());
                Optional<ContentEntity> contentEntity = contentRepository.findById(userContentInteraction.getContentId());
                reviewPageDto.setContentName(contentEntity.get().getTitle());
                reviewPageDto.setContentPoster(contentEntity.get().getPoster());
                    ContentReviews contentReview = userContentInteraction.getReview();
                    ReviewAndUserDto reviewAndUserDto = modelMapper.map(contentReview, ReviewAndUserDto.class);
                    UserDto userDto = modelMapper.map(contentReview.getReviewedBy(), UserDto.class);
                    reviewAndUserDto.setUserDto(userDto);
                    reviewPageDto.setContentReviews(reviewAndUserDto);
                    if (contentReview.getLikes() > 0) {
                        List<ReviewLikes> reviewLikes = reviewLikesRepo.findBylikedReview_Id(contentReview.getId());
                        List<UserDto> likedUserList = new ArrayList<>();
                        for (ReviewLikes reviewLikes1 : reviewLikes) {
                            UserEntity userEntity = reviewLikes1.getLikedByUser();
                            userDto = modelMapper.map(userEntity, UserDto.class);
                            likedUserList.add(userDto);
                        }
                        reviewPageDto.setIsLiked(likedUserList);
                    }
                    if (contentReview.getDislikes() > 0) {
                        List<ReviewDislikes> reviewDisLikes = reviewDisLikesRepo.findByDislikedReview_Id(contentReview.getId());
                        List<UserDto> disLikedUserList = new ArrayList<>();
                        for (ReviewDislikes reviewDisLike : reviewDisLikes) {
                            UserEntity userEntity = reviewDisLike.getDislikedByUser();
                            userDto = modelMapper.map(userEntity, UserDto.class);
                            disLikedUserList.add(userDto);
                        }
                        reviewPageDto.setDisLiked(disLikedUserList);
                    }
                reviewPageDtoList.add(reviewPageDto);
            }

            }
        }
        return reviewPageDtoList;
    }

    @Override
    public ReviewPageDto getReviewByID(long id) {
        ReviewPageDto reviewPageDto = new ReviewPageDto();
        UserContentInteraction userContentInteraction= userContentInteractionRepo.findByReview_Id(id);
        reviewPageDto.setId(userContentInteraction.getReview().getId());
        reviewPageDto.setRating(userContentInteraction.getRating());
        Optional<ContentEntity> contentEntity = contentRepository.findById(userContentInteraction.getContentId());
        reviewPageDto.setContentName(contentEntity.get().getTitle());
        reviewPageDto.setContentPoster(contentEntity.get().getPoster());
        UserEntity userEntity1 = authService.getCurrentUser();
        UserContentInteraction checkInteractionWithContent = userContentInteractionRepo.findByContentIdAndUserId(userEntity1.getId(),userContentInteraction.getContentId());
        if(checkInteractionWithContent!=null){
            reviewPageDto.setLikedList(checkInteractionWithContent.isLiked());
            reviewPageDto.setWatchList(checkInteractionWithContent.isWatched());
        }


        if(userContentInteraction.getReview()!=null){
            ContentReviews contentReview = userContentInteraction.getReview();
            ReviewAndUserDto reviewAndUserDto = modelMapper.map(contentReview,ReviewAndUserDto.class);
            UserDto userDto = modelMapper.map(contentReview.getReviewedBy(),UserDto.class);
            reviewAndUserDto.setUserDto(userDto);
            reviewPageDto.setContentReviews(reviewAndUserDto);
            if (contentReview.getLikes()>0){
                List<ReviewLikes> reviewLikes = reviewLikesRepo.findBylikedReview_Id(contentReview.getId());
                List<UserDto> likedUserList = new ArrayList<>();
                for (ReviewLikes reviewLikes1 : reviewLikes){
                    UserEntity userEntity = reviewLikes1.getLikedByUser();
                     userDto = modelMapper.map(userEntity,UserDto.class);
                    likedUserList.add(userDto);
                }
                reviewPageDto.setIsLiked(likedUserList);
            }
             if (contentReview.getDislikes()>0){
                List<ReviewDislikes> reviewDisLikes = reviewDisLikesRepo.findByDislikedReview_Id(contentReview.getId());
                List<UserDto> disLikedUserList = new ArrayList<>();
                for (ReviewDislikes reviewDisLike : reviewDisLikes){
                    UserEntity userEntity = reviewDisLike.getDislikedByUser();
                    userDto = modelMapper.map(userEntity,UserDto.class);
                    disLikedUserList.add(userDto);
                }
                reviewPageDto.setDisLiked(disLikedUserList);
            }
             if(contentReview.isReplied()>0){
                 List<ReviewReplies> reviewRepliesList = reviewRepliesRepo.findByReview_Id(contentReview.getId());
                 List<ReivewRepliesDto> reivewRepliesDtoList = new ArrayList<>();
                 for(ReviewReplies reviewReplies: reviewRepliesList){
                     ReivewRepliesDto reviewRepliesDto = modelMapper.map(reviewReplies,ReivewRepliesDto.class);
                     reviewRepliesDto.setReply(reviewReplies.getReply());
                     UserDto user = modelMapper.map(reviewReplies.getRepliedUser(),UserDto.class);
                     reviewRepliesDto.setUserDto(user);
                     reivewRepliesDtoList.add(reviewRepliesDto);
                 }
                 reviewPageDto.setReviewReplies(reivewRepliesDtoList);
             }
        }


        return  reviewPageDto;
    }

    @Override
    @Transactional
    public List<UserDto> setReviewLike(long reviewId) {
        UserEntity user = authService.getCurrentUser();
        ReviewLikes reviewLikes2= reviewLikesRepo.findByUserIdAndReviewId(reviewId,user.getId());
        if(reviewLikes2!=null){

            return removeReviewLike(reviewId);
        }
        Optional<ContentReviews> contentReviews = contentReviewsRepo.findById(reviewId);
        List<ReviewLikes> reviewLikesList = reviewLikesRepo.findBylikedReview_Id(reviewId);
        long like = reviewLikesList.size()+1;
        contentReviews.get().setLikes(like);
        ReviewLikes reviewLikes = new ReviewLikes();
        reviewLikes.setLikedByUser(user);
        reviewLikes.setLikedReview(contentReviews.get());
        reviewLikesRepo.save(reviewLikes);
        contentReviewsRepo.save(contentReviews.get());
        List<UserDto> userDtoList = new ArrayList<>();
        for(ReviewLikes reviewLikes1 : reviewLikesList){
           UserDto userDto = modelMapper.map(reviewLikes1.getLikedByUser(),UserDto.class);
           userDtoList.add(userDto);;
        }
        UserDto userDto = modelMapper.map(user,UserDto.class);
        userDtoList.add(userDto);
        return userDtoList;

    }

    @Override
    @Transactional
    public List<UserDto> setReviewDisLike(long reviewId) {
        UserEntity user = authService.getCurrentUser();
        ReviewDislikes reviewDislikes2 = reviewDisLikesRepo.findByUserIdAndReviewId(reviewId,user.getId());
        if(reviewDislikes2!=null){
            return removeReviewDisLike(reviewId);
        }

        Optional<ContentReviews> contentReviews = contentReviewsRepo.findById(reviewId);

        long like = contentReviews.get().getDislikes()+1;
        contentReviews.get().setDislikes(like);
        ReviewDislikes reviewLikes = new ReviewDislikes();
        reviewLikes.setDislikedByUser(user);
        reviewLikes.setDislikedReview(contentReviews.get());
        reviewDisLikesRepo.save(reviewLikes);
        contentReviewsRepo.save(contentReviews.get());
        List<ReviewDislikes> reviewLikesList = reviewDisLikesRepo.findByDislikedReview_Id(reviewId);
        List<UserDto> userDtoList = new ArrayList<>();
        for(ReviewDislikes reviewLikes1 : reviewLikesList){
            UserDto userDto = modelMapper.map(reviewLikes1.getDislikedByUser(),UserDto.class);
            userDtoList.add(userDto);;
        }
        return userDtoList;
    }

    @Override
    public ReviewPageDto setReviewReply(long reviewId, String comment) {
        UserEntity user = authService.getCurrentUser();
        Optional<ContentReviews> contentReviews = contentReviewsRepo.findById(reviewId);
        long reviewRepliesCount = contentReviews.get().isReplied()+1;
        contentReviews.get().setReplied(reviewRepliesCount);
        ReviewReplies reviewReplies = new ReviewReplies();
        reviewReplies.setReply(comment);
        reviewReplies.setRepliedUser(user);
        reviewReplies.setReview(contentReviews.get());
        reviewRepliesRepo.save(reviewReplies);
        contentReviewsRepo.save(contentReviews.get());
        return getReviewByID(reviewId);

    }

    @Override
    public List<UserDto> removeReviewLike(long reviewId) {
        UserEntity user = authService.getCurrentUser();
        Optional<ContentReviews> contentReviews = contentReviewsRepo.findById(reviewId);
        long like = contentReviews.get().getLikes()-1;
        contentReviews.get().setLikes(like);
        reviewLikesRepo.deleteByUserIdAndReviewId(reviewId,user.getId());
        contentReviewsRepo.save(contentReviews.get());
        List<ReviewLikes> reviewLikesList = reviewLikesRepo.findBylikedReview_Id(reviewId);
        List<UserDto> userDtoList = new ArrayList<>();
        for(ReviewLikes reviewLikes1 : reviewLikesList){
            UserDto userDto = modelMapper.map(reviewLikes1.getLikedByUser(),UserDto.class);
            userDtoList.add(userDto);;
        }
        return userDtoList;
    }

    @Override
    public List<UserDto> removeReviewDisLike(long reviewId) {
        UserEntity user = authService.getCurrentUser();
        Optional<ContentReviews> contentReviews = contentReviewsRepo.findById(reviewId);
        long like = reviewDisLikesRepo.findByDislikedReview_Id(reviewId).size()-1;
        contentReviews.get().setDislikes(like);
        reviewDisLikesRepo.deleteByUserIdAndReviewId(reviewId,user.getId());
        contentReviewsRepo.save(contentReviews.get());
        List<ReviewDislikes> reviewLikesList = reviewDisLikesRepo.findByDislikedReview_Id(reviewId);
        List<UserDto> userDtoList = new ArrayList<>();
        for(ReviewDislikes reviewLikes1 : reviewLikesList){
            UserDto userDto = modelMapper.map(reviewLikes1.getDislikedByUser(),UserDto.class);
            userDtoList.add(userDto);;
        }
        return userDtoList;
    }
}
