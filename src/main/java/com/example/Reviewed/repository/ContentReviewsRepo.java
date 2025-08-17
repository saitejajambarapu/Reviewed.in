package com.example.Reviewed.repository;

import com.example.Reviewed.model.ContentReviews;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContentReviewsRepo extends JpaRepository<ContentReviews,Long> {
    @Query(value = "Select * from content_reviews where content_id=:contentId and user_id=:id",nativeQuery = true)
    ContentReviews findByContentIdAndUserId(long id, long contentId);
//    @Query(value = "Select * from content_reviews order by created_at desc",nativeQuery = true)
//    List<ContentReviews> getAllReview();
}
