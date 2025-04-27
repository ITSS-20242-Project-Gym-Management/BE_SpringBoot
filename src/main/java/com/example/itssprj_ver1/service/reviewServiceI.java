package com.example.itssprj_ver1.service;


import com.example.itssprj_ver1.model.review;

import java.util.List;
import java.util.Optional;

public interface reviewServiceI {

    List<review> getReview();
    Optional<review> getReviewById(Integer id);
    review createReview(review newReview);
    review updateReview(Integer id, review updatedReview);
    void deleteReview(Integer id);


}
