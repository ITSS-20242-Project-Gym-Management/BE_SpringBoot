package com.example.itssprj_ver1.repository;

import com.example.itssprj_ver1.model.customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface customerRepository extends JpaRepository<customer, Integer> {
    List<customer> findByPhone(String phone);
    
    customer findByFirstnameAndLastname(String firstname, String lastname);

    customer findCustomerByUserid_Id(Integer id);

    customer findAllByPhone(String phone);
}
