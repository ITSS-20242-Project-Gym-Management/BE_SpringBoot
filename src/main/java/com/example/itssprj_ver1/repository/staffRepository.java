package com.example.itssprj_ver1.repository;

import com.example.itssprj_ver1.model.staff;
import com.example.itssprj_ver1.model.users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface staffRepository extends JpaRepository<staff, Integer> {
    staff findByUserid(users userid);
}
