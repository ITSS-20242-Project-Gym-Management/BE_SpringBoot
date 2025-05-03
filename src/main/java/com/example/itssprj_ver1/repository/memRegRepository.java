package com.example.itssprj_ver1.repository;

import com.example.itssprj_ver1.model.memberRegister;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;
import java.util.Map;

@Repository
public interface memRegRepository extends JpaRepository<memberRegister, Integer> {
    List<memberRegister> findByStatus(String status);

    List<memberRegister> findByCreateAt(Date createAt);

    List<memberRegister> findByStatusAndCreateAt(String status, Date createAt);
}
