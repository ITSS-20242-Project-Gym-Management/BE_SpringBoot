package com.example.itssprj_ver1.repository;

import com.example.itssprj_ver1.model.customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface customerRepository extends JpaRepository<customer, Integer> {
}
