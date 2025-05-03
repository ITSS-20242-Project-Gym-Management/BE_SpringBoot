package com.example.itssprj_ver1.service;

import com.example.itssprj_ver1.model.review;
import com.example.itssprj_ver1.repository.reviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class reviewService implements reviewServiceI {
    @Autowired
    private reviewRepository reviewRepository;

    @Override
    public List<Map<String, Object>> getReview() {
        List<review> reviews = reviewRepository.findAll();

        List<Map<String, Object>> reviewData = new ArrayList<>();

        for (review review : reviews) {
            Map<String, Object> reviewMap = new HashMap<>();
            reviewMap.put("reviewId", review.getId());
            reviewMap.put("customer", review.getCustomer().getFirstname() + review.getCustomer().getLastname());
            reviewMap.put("text", review.getText());
            reviewMap.put("date", review.getCreateAt());
            reviewData.add(reviewMap);
        }
        return reviewData;
    }
}
