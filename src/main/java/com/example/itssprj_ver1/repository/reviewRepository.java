package com.example.itssprj_ver1.repository;

import com.example.itssprj_ver1.model.review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface reviewRepository extends JpaRepository<review, Integer> {



}
