package com.example.itssprj_ver1.service;

import com.example.itssprj_ver1.model.review;
import com.example.itssprj_ver1.repository.reviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class reviewService implements reviewServiceI {
    @Autowired
    private reviewRepository reviewRepository;

    @Override
    public List<review> getReview() {
        return reviewRepository.findAll();
    }
}
