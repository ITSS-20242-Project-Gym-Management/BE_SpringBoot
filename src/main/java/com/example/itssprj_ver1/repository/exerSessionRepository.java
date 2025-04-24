package com.example.itssprj_ver1.repository;

import com.example.itssprj_ver1.model.exerciseSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface exerSessionRepository extends JpaRepository<exerciseSession, Integer> {
    // Custom query methods can be defined here if needed
    // For example, you can add methods to find exercise sessions by customer ID or date
    List<exerciseSession> findByStaff_Userid_Role_Roleid(Integer roleId);
}
