package com.example.itssprj_ver1.repository;

import com.example.itssprj_ver1.model.customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface customerRepository extends JpaRepository<customer,Integer> {

}
