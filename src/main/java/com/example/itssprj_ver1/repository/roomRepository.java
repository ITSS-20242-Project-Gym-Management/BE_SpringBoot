package com.example.itssprj_ver1.repository;

import com.example.itssprj_ver1.model.room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface roomRepository extends JpaRepository<room, Integer> {
    room findByName(String name);

    room findById(int id);

}
