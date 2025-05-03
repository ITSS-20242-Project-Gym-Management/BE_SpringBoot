package com.example.itssprj_ver1.repository;

import com.example.itssprj_ver1.model.customer;
import com.example.itssprj_ver1.model.memberRegister;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface memRegRepository extends JpaRepository<memberRegister, Integer> {
    List<memberRegister> findByCustomer(customer customer);

    List<memberRegister> findAllByMembershipId(Integer membershipId);
}
