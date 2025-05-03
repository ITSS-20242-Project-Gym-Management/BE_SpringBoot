package com.example.itssprj_ver1.service;

import java.util.List;
import java.util.Map;

public interface roomEquipmentServiceI {
    boolean addRoomEquipment(String room_name, String equipment_name, int quantity, String status);

    boolean updateRoomEquipment(String room_name, String equipment_name, String status);

    boolean deleteRoomEquipment(String room_name, String equipment_name);

    List<Map<String, Object>> getRoomEquipmentByRoomName(String room_name);

    List<Map<String, Object>> getRoomEquipmentByNameDevice(String device_name);

    List<Map<String, Object>> getRoomEquipmentByStatus(String status);

    List<Map<String, Object>> getRoomEquipmentByRoomNameAndNameDevice(String roomname, String device_name);

    List<Map<String, Object>> getRoomEquipmentByRoomNameAndStatus(String roomname, String status);

    List<Map<String, Object>> getRoomEquipmentByRoomNameAndNameDeviceAndStatus(String roomname, String device_name, String status);

    List<Map<String, Object>> getAllRoomEquipment();
}