package com.example.itssprj_ver1.service;

import com.example.itssprj_ver1.model.review;
import com.example.itssprj_ver1.repository.reviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class reviewService implements reviewServiceI {

    private final reviewRepository reviewRepository;

    @Override
    public List<Map<String, Object>> getReviews() {
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

    @Override
    public List<review> getReview() {
        return reviewRepository.findAll();

    }

    @Override
    public Optional<review> getReviewById(Integer id) {
        return reviewRepository.findById(id);

    }

    @Override
    public review createReview(review newReview) {

        return reviewRepository.save(newReview);

    }

    @Override
    public review updateReview(Integer id, review updatedReview) {
        return reviewRepository.findById(id)
                .map(review -> {
                    review.setText(updatedReview.getText());
                    return reviewRepository.save(review);
                })
                .orElseGet(() -> {
                    updatedReview.setId(id);
                    return reviewRepository.save(updatedReview);
                });
    }

    @Override
    public void deleteReview(Integer id) {
        reviewRepository.deleteById(id);

    }

}
