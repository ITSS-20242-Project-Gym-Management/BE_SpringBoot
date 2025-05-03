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
    List<roomEquipment> findByRoom_Name(String roomName);

    // tìm theo equipment name
    List<roomEquipment> findByEquipmentName(String equipmentName);

    //tìm theo status
    List<roomEquipment> findByStatus(String status);

    //tìm theo roomname + equipment name
    List<roomEquipment>findByRoom_NameAndEquipmentName(String roomName, String equipmentName);

    //tìm theo roomname + status
    List<roomEquipment> findByRoom_NameAndStatus(String roomName, String equipmentName);

    //tìm theo roomname + equipment name + status
    List<roomEquipment> findByRoom_NameAndEquipmentNameAndStatus(String roomName, String equipmentName, String status);

    // Phương thức hỗ trợ cho việc cập nhật và xóa
    roomEquipment findByRoom_IdAndEquipmentName(int roomid, String equipmentName);
}
