package com.example.itssprj_ver1.service;

import com.example.itssprj_ver1.model.*;

import java.util.List;
import java.util.Map;

public interface adminServiceI {
    //Customer
    List<Map<String,Object>>  getAllCustomers();

    List<Map<String,Object>> getCustomersByMembershipId(Integer membershipId);

    List<customer> getCustomerByName(String firstName, String lastName);

    customer getCustomerById(Integer id);

    List<Map<String, Object>> getCustomerByPhone(String phone);

    List<Map<String, Object>>  getAllReviewsByCustomer();


    //Manager
    List<Map<String, Object>> getAllManagers();

    List<staff> getManagersByName(String firstName, String lastName);

    staff getManagerById(Integer id);

    List<Map<String, Object>> getManagerByPhone(String phone);

    //PT
    List<Map<String, Object>> getAllPTs();

    List<staff> getPTsByName(String firstName, String lastName);

    staff getPTById(Integer id);

    List<Map<String, Object>> getPTByPhone(String phone);


    //Room-Equipment
    List<Map<String, Object>> getAllRooms();//Loc lam r

    List<room> getRoomsByStatus(String status);

    List<roomEquipment> getRoomEquipmentByRoom(room room);

    List<roomEquipment> getRoomEquipmentByStatus(String status);

    List<roomEquipment> getRoomEquipmentByStatusAndRoomname(String roomname, String status);

    boolean addRoom(room room);
    boolean updateRoom(room room);
    boolean deleteRoom(room room);

    boolean addRoomEquipment(roomEquipment roomEquipment);
    boolean updateRoomEquipment(roomEquipment roomEquipment);
    boolean deleteRoomEquipment(roomEquipment roomEquipment);





}
