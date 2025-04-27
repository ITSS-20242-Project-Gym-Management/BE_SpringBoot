package com.example.itssprj_ver1.repository;

import com.example.itssprj_ver1.model.customer;
import com.example.itssprj_ver1.model.memberRegister;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface memRegRepository extends JpaRepository<memberRegister, Integer> {
    List<memberRegister> findByCustomer(customer customer);
}
