package com.example.Reviewed.repository;

import com.example.Reviewed.model.ContentEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DynamicQueryRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public List<ContentEntity> executeDynamicUserQuery(String sql) {
        Query query = entityManager.createNativeQuery(sql, ContentEntity.class);
        return query.getResultList();
    }
}
