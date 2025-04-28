package com.example.itssprj_ver1.service;

import com.example.itssprj_ver1.model.room;
import com.example.itssprj_ver1.model.roomEquipment;
import com.example.itssprj_ver1.repository.roomEquipmentRepository;
import com.example.itssprj_ver1.repository.roomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class roomEquipmentService implements roomEquipmentServiceI {
    @Autowired
    private roomEquipmentRepository roomEquipmentRepository;

    @Autowired
    private roomRepository roomRepository;

    @Override
    public boolean addRoomEquipment(String room_name, String equipment_name, int quantity, String status) {
        roomEquipment roomEquipment = new roomEquipment();
        room room = roomRepository.findByName(room_name);
        if (room == null) {
            return false;
        }
        roomEquipment.setRoom(room);
        roomEquipment.setEquipmentName(equipment_name);
        roomEquipment.setQuantity(quantity);
        roomEquipment.setStatus(status);
        roomEquipmentRepository.save(roomEquipment);
        return true;
    }

    @Override
    public boolean updateRoomEquipment(int roomid, String equipment_name, String status) {
        room room = roomRepository.findById(roomid);
        if (room == null) {
            return false;
        }
        roomEquipment roomEquipment = roomEquipmentRepository.findByRoom_IdAndEquipmentName(roomid, equipment_name);
        roomEquipment.setStatus(status);
        roomEquipmentRepository.save(roomEquipment);
        return true;
    }

    @Override
    public boolean deleteRoomEquipment(int roomid, String equipment_name, int quantity) {
        room room = roomRepository.findById(roomid);
        if (room == null) {
            return false;
        }
        roomEquipment roomEquipment = roomEquipmentRepository.findByRoom_IdAndEquipmentName(roomid, equipment_name);
        if (roomEquipment != null) {
            roomEquipmentRepository.delete(roomEquipment);
            return true;
        }
        return false;
    }

    @Override
    public List<Map<String, Object>> getRoomEquipmentByRoomName(String room_name) {
        List<roomEquipment> results = roomEquipmentRepository.findByRoom_Name(room_name);
        if (results == null || results.isEmpty()) {
            return new ArrayList<>();
        }

        List<Map<String, Object>> mappedResults = new ArrayList<>();
        for (roomEquipment result : results) {
            Map<String, Object> item = new HashMap<>();
            item.put("room_name", result.getRoom().getName() );
            item.put("equipment_name", result.getEquipmentName() );
            item.put("quantity", result.getQuantity() );
            item.put("status", result.getStatus() );
            mappedResults.add(item);
        }
        return mappedResults;
    }

    @Override
    public List<Map<String, Object>> getRoomEquipmentByNameDevice(String roomEquipment) {
        List<roomEquipment> results = roomEquipmentRepository.findByEquipmentName(roomEquipment);
        if (results == null || results.isEmpty()) {
            return new ArrayList<>();
        }
        List<Map<String, Object>> mappedResults = new ArrayList<>();
        for (roomEquipment result : results) {
            Map<String, Object> item = new HashMap<>();
            item.put("room_name", result.getRoom().getName() );
            item.put("equipment_name", result.getEquipmentName() );
            item.put("quantity", result.getQuantity() );
            item.put("status", result.getStatus() );
            mappedResults.add(item);
        }
        return mappedResults;
    }

    @Override
    public List<Map<String, Object>> getRoomEquipmentByStatus(String status) {
        List<roomEquipment> results = roomEquipmentRepository.findByStatus(status);
        if (results == null || results.isEmpty()) {
            return new ArrayList<>();
        }
        List<Map<String, Object>> mappedResults = new ArrayList<>();
        for (roomEquipment result : results) {
            Map<String, Object> item = new HashMap<>();
            item.put("room_name", result.getRoom().getName() );
            item.put("equipment_name", result.getEquipmentName() );
            item.put("quantity", result.getQuantity() );
            item.put("status", result.getStatus() );
            mappedResults.add(item);
        }
        return mappedResults;
    }

    @Override
    public List<Map<String, Object>> getRoomEquipmentByRoomNameAndNameDevice(String roomname, String roomEquipment) {
        List<roomEquipment> results = roomEquipmentRepository.findByRoom_NameAndEquipmentName(roomname, roomEquipment);
        if (results == null || results.isEmpty()) {
            return new ArrayList<>();
        }
        List<Map<String, Object>> mappedResults = new ArrayList<>();
        for (roomEquipment result : results) {
            Map<String, Object> item = new HashMap<>();
            item.put("room_name", result.getRoom().getName() );
            item.put("equipment_name", result.getEquipmentName() );
            item.put("quantity", result.getQuantity() );
            item.put("status", result.getStatus() );
            mappedResults.add(item);
        }
        return mappedResults;
    }

    @Override
    public List<Map<String, Object>> getRoomEquipmentByRoomNameAndStatus(String roomname, String status) {
        List<roomEquipment> results = roomEquipmentRepository.findByRoom_NameAndStatus(roomname, status);
        if (results == null || results.isEmpty()) {
            return new ArrayList<>();
        }
        List<Map<String, Object>> mappedResults = new ArrayList<>();
        for (roomEquipment result : results) {
            Map<String, Object> item = new HashMap<>();
            item.put("room_name", result.getRoom().getName() );
            item.put("equipment_name", result.getEquipmentName() );
            item.put("quantity", result.getQuantity() );
            item.put("status", result.getStatus() );
            mappedResults.add(item);
        }
        return mappedResults;
    }

    @Override
    public List<Map<String, Object>> getRoomEquipmentByRoomNameAndNameDeviceAndStatus(String roomname, String roomEquipment, String status) {
        List<roomEquipment> results = roomEquipmentRepository.findByRoom_NameAndEquipmentNameAndStatus(roomname,roomEquipment, status);
        if (results == null || results.isEmpty()) {
            return new ArrayList<>();
        }
        List<Map<String, Object>> mappedResults = new ArrayList<>();
        for (roomEquipment result : results) {
            Map<String, Object> item = new HashMap<>();
            item.put("room_name", result.getRoom().getName() );
            item.put("equipment_name", result.getEquipmentName() );
            item.put("quantity", result.getQuantity() );
            item.put("status", result.getStatus() );
            mappedResults.add(item);
        }
        return mappedResults;
    }

    @Override
    public List<Map<String, Object>> getAllRoomEquipment() {
        List<roomEquipment> allEquipments = roomEquipmentRepository.findAll();
        List<Map<String, Object>> result = new ArrayList<>();

        for (roomEquipment equipment : allEquipments) {
            Map<String, Object> item = new HashMap<>();
            item.put("room_name", equipment.getRoom().getName());
            item.put("equipment_name", equipment.getEquipmentName());
            item.put("quantity", equipment.getQuantity());
            item.put("status", equipment.getStatus());
            result.add(item);
        }
        return result;
    }


}
