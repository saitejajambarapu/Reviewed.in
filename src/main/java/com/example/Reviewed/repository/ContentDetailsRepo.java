package com.example.Reviewed.repository;

import com.example.Reviewed.model.ContentDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContentDetailsRepo extends JpaRepository<ContentDetails, Long> {
}
