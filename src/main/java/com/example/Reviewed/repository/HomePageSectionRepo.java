package com.example.Reviewed.repository;

import com.example.Reviewed.model.HomePageSectionsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HomePageSectionRepo extends JpaRepository<HomePageSectionsEntity,Long> {
}
