package com.example.itssprj_ver1.repository;

import com.example.itssprj_ver1.model.exerciseSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface exerSessionRepository extends JpaRepository<exerciseSession, Integer> {
}
