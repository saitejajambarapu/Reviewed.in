package com.example.Reviewed.repository;

import com.example.Reviewed.model.CommentReplyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentReplyRepo extends JpaRepository<CommentReplyEntity,Long> {
    List<CommentReplyEntity> findByMasterComment_Id(Long id);
}
