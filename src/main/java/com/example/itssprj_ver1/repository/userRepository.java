package com.example.itssprj_ver1.repository;

import com.example.itssprj_ver1.model.users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface userRepository extends JpaRepository<users, Integer> {
    users findByUsername(String username);
}
