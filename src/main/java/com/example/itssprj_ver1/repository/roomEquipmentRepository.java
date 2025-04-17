package com.example.itssprj_ver1.repository;

import com.example.itssprj_ver1.model.roomEquipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface roomEquipmentRepository extends JpaRepository<roomEquipment, Integer> {
}
