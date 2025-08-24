package com.example.Reviewed.repository;

import com.example.Reviewed.model.UserContentInteraction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserContentInteractionRepo extends JpaRepository<UserContentInteraction,Long> {
    @Query(value = "Select * from user_content_interaction order by created_at desc",nativeQuery = true)
    List<UserContentInteraction> getAllInteractions();
//    @Query(value = "Select * from user_content_interaction where review_id=:id",nativeQuery = true)
    UserContentInteraction findByReview_Id(long id);

    @Query(value = "Select * from user_content_interaction where user_id=:id and content_id=:contentId",nativeQuery = true)
    UserContentInteraction findByContentIdAndUserId(long id, long contentId);

    List<UserContentInteraction> findByuser_id(Long id);

    List<UserContentInteraction> findByContentId(Long contentId);
}
