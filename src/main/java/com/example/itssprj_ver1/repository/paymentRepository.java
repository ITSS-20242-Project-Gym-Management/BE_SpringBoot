package com.example.itssprj_ver1.repository;

import com.example.itssprj_ver1.model.payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface paymentRepository extends JpaRepository<payment, Integer> {

    List<payment> findByCustomerId(Integer customerId);

}
