package com.example.itssprj_ver1.service;

import com.example.itssprj_ver1.model.review;
import com.example.itssprj_ver1.repository.reviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class reviewService implements reviewServiceI {

    private final reviewRepository reviewRepository;

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
