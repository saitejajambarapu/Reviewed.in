package com.example.Reviewed.serviceimpl;

import com.example.Reviewed.model.ContentDetails;
import com.example.Reviewed.repository.ContentDetailsRepo;
import com.example.Reviewed.service.ContentDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContentDetailsServiceImpl implements ContentDetailsService {

    @Autowired
    ContentDetailsRepo contentDetailsRepo;

    @Override
    public List<ContentDetails> getContentDetails() {
        List<ContentDetails> contentDetails = contentDetailsRepo.findAll();
        return contentDetails;
    }
}

