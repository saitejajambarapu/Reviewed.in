package com.example.Reviewed.repository;

import com.example.Reviewed.model.ReviewLikes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ReviewLikesRepo extends JpaRepository<ReviewLikes,Long> {
    List<ReviewLikes> findBylikedReview_Id(Long id);

    @Modifying
    @Transactional
    @Query(value = "delete from review_likes where review_id=:reviewId and user_id=:id",nativeQuery = true)
    void deleteByUserIdAndReviewId(long reviewId, Long id);
    @Query(value = "select * from review_likes where review_id=:reviewId and user_id=:id",nativeQuery = true)
    ReviewLikes findByUserIdAndReviewId(long reviewId, Long id);
}
