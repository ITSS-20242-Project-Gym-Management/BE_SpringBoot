package com.example.itssprj_ver1.repository;

import com.example.itssprj_ver1.model.roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface roleRepository extends JpaRepository<roles, Integer> {
    roles findById(int id);

    roles findByRolename(String rolename);
}