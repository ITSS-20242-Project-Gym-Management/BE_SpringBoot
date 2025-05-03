package com.example.itssprj_ver1.repository;

import com.example.itssprj_ver1.model.payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface paymentRepository extends JpaRepository<payment, Integer> {

    List<payment> findByCustomerId(Integer customerId);

}
