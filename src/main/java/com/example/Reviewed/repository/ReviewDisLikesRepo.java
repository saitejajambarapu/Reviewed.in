package com.example.Reviewed.repository;

import com.example.Reviewed.model.ReviewDislikes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ReviewDisLikesRepo extends JpaRepository<ReviewDislikes, Long> { ;

    List<ReviewDislikes> findByDislikedReview_Id(Long id);

    @Modifying
    @Transactional
    @Query(value = "delete from review_dislikes where review_id=:reviewId and user_id=:id",nativeQuery = true)
    void deleteByUserIdAndReviewId(long reviewId, Long id);
    @Query(value = "select * from review_dislikes where review_id=:reviewId and user_id=:id",nativeQuery = true)
    ReviewDislikes findByUserIdAndReviewId(long reviewId, Long id);
}
