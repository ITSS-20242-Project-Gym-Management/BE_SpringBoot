package com.example.itssprj_ver1.repository;

import com.example.itssprj_ver1.model.roomEquipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface roomEquipmentRepository extends JpaRepository<roomEquipment, Integer> {
    //tìm theo room name
    List<roomEquipment> findAllByRoom_Name(String roomName);

    // tìm theo equipment name
    List<roomEquipment> findAllByEquipmentName(String equipmentName);

    //tìm theo status
    List<roomEquipment> findAllByStatus(String status);

    //tìm theo roomname + equipment name
    List<roomEquipment>findAllByRoom_NameAndEquipmentName(String roomName, String equipmentName);

    //tìm theo roomname + status
    List<roomEquipment> findAllByRoom_NameAndStatus(String roomName, String equipmentName);

    //tìm theo roomname + equipment name + status
    List<roomEquipment> findAllByRoom_NameAndEquipmentNameAndStatus(String roomName, String equipmentName, String status);

    // Phương thức hỗ trợ cho việc cập nhật và xóa
    List<roomEquipment> findByRoom_NameAndEquipmentName(String room_name, String equipmentName);
}