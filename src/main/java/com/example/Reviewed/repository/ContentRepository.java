package com.example.Reviewed.repository;

import com.example.Reviewed.model.ContentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ContentRepository extends JpaRepository<ContentEntity, Long> {
    ContentEntity findByImdbID(String imdbID);

    @Query(value = "SELECT * \n" +
            "FROM content_db \n" +
            "WHERE title LIKE CONCAT('%', :title, '%')\n" +
            "ORDER BY year DESC;\n", nativeQuery = true)
    List<ContentEntity> findByTitleLike(@Param("title") String title);

}
