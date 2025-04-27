package com.example.itssprj_ver1.repository;

import com.example.itssprj_ver1.model.membership;
import org.springframework.data.jpa.repository.JpaRepository;

public interface membershipRepository extends JpaRepository<membership, Integer> {
}
