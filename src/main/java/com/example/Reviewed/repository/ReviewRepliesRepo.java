package com.example.Reviewed.repository;

import com.example.Reviewed.model.ReviewReplies;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepliesRepo  extends JpaRepository<ReviewReplies, Long> {

    List<ReviewReplies> findByReview_Id(Long id);
}
