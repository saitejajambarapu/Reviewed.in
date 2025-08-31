package com.example.Reviewed.serviceimpl;

import com.example.Reviewed.Dto.*;
import com.example.Reviewed.model.*;
import com.example.Reviewed.repository.*;
import com.example.Reviewed.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    @Autowired
    UserRepo userRepo;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    PasswordEncoder passwordEncoder;


    @Autowired
    ReviewLikesRepo reviewLikesRepo;

    @Autowired
    ContentRepository contentRepository;

    @Autowired
    ContentReviewsRepo contentReviewsRepo;

    @Autowired
    ReviewDisLikesRepo reviewDisLikesRepo;

    @Autowired
    ReviewRepliesRepo reviewRepliesRepo;

    @Autowired
    UserContentInteractionRepo userContentInteractionRepo;
    @Autowired
    CommentReplyRepo commentReplyRepo;

    @Override
    public UserEntity getUserById(Long userId) {
        Optional<UserEntity> userEntity = userRepo.findById(userId);
        return userEntity.get();
    }
    public UserDto signUp(SignUpDto signUpDto) {
        Optional<UserEntity> user = userRepo.findByEmail(signUpDto.getEmail());
        if(user.isPresent()) {
            throw new BadCredentialsException("User with email already exits "+ signUpDto.getEmail());
        }

        UserEntity toBeCreatedUser = modelMapper.map(signUpDto, UserEntity.class);
        toBeCreatedUser.setPassword(passwordEncoder.encode(toBeCreatedUser.getPassword()));

        UserEntity savedUser = userRepo.save(toBeCreatedUser);
        return modelMapper.map(savedUser, UserDto.class);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepo.findByEmail(username).orElseThrow();
    }

    public UserProfileDto getProfileById(Long id){
        Optional<UserEntity> user = userRepo.findById(id);
        UserProfileDto userProfileDto = new UserProfileDto();
        userProfileDto.setUser(user.get());
        // reviews by userid
        List<ReviewPageDto> reviewPageDtoList = new ArrayList<>();
        //likedList
        List<ReviewPageDto> likedReviewPageDtoList = new ArrayList<>();
        List<ReviewPageDto> watchedListPageDtoList = new ArrayList<>();
        List<UserContentInteraction> userContentInteractionsList = userContentInteractionRepo.findByuser_id(id);
        for(UserContentInteraction userContentInteraction: userContentInteractionsList){
            ReviewPageDto reviewPageDto = new ReviewPageDto();
            if(userContentInteraction.getReview()!=null){
            reviewPageDto.setId(userContentInteraction.getReview().getId());
            reviewPageDto.setRating(userContentInteraction.getRating());

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
                    List<CommentsDto> reivewRepliesDtoList = new ArrayList<>();
                    for(ReviewReplies reviewReplies: reviewRepliesList){
                        List<ReivewRepliesDto> commentRepliesDtoList = new ArrayList<>();
                        ReivewRepliesDto reviewRepliesDto = modelMapper.map(reviewReplies,ReivewRepliesDto.class);
                        reviewRepliesDto.setReply(reviewReplies.getReply());
                        UserDto userDto1 = modelMapper.map(reviewReplies.getRepliedUser(),UserDto.class);
                        reviewRepliesDto.setUserDto(userDto1);
                        List<CommentReplyEntity> commentReplyEntityList = commentReplyRepo.findByMasterComment_Id(reviewReplies.getId());
//                        List<ReviewReplies> commentRepliesList = new ArrayList<>();
//                        for(CommentReplyEntity commentReplyEntity :commentReplyEntityList){
//                            commentRepliesList.add(commentReplyEntity.getCommentedBy());
//                        }
                        CommentsDto commentsDto = new CommentsDto();
                        commentsDto.setCommentReply(reviewRepliesDto);
                        commentsDto.setReplies(commentReplyEntityList);
                        reivewRepliesDtoList.add(commentsDto);
                    }
                    reviewPageDto.setReviewReplies(reivewRepliesDtoList);
                }
            }

            Optional<ContentEntity> contentEntity = contentRepository.findById(userContentInteraction.getContentId());
            reviewPageDto.setContentName(contentEntity.get().getTitle());
            reviewPageDto.setContentPoster(contentEntity.get().getPoster());
            reviewPageDto.setId(contentEntity.get().getId());
            reviewPageDto.setImdbId(contentEntity.get().getImdbID());
            if(userContentInteraction.isLiked()){
                likedReviewPageDtoList.add(reviewPageDto);
            }
            if(userContentInteraction.isWatched()){
                watchedListPageDtoList.add(reviewPageDto);
            }
            if(userContentInteraction.getReview()!=null) reviewPageDtoList.add(reviewPageDto);

        }
        userProfileDto.setReviewpage(reviewPageDtoList);
        userProfileDto.setLikedList(likedReviewPageDtoList);
        userProfileDto.setWatchedList(watchedListPageDtoList);
        return userProfileDto;

    }
}
