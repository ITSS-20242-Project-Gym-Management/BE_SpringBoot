package com.example.itssprj_ver1.repository;

import com.example.itssprj_ver1.model.memberRegister;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface memRegRepository extends JpaRepository<memberRegister, Integer> {
}
