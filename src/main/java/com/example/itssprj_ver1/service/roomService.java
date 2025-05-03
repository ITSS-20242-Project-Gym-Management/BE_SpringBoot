package com.example.itssprj_ver1.service;

import com.example.itssprj_ver1.dto.roomdto;
import com.example.itssprj_ver1.model.room;
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
public class roomService implements roomServiceI {

    private final roomRepository roomRepository;

    @Override
    public List<Map<String, Object>> getRoom() {
        List<room> rooms = roomRepository.findAll();
        if(rooms == null || rooms.isEmpty()) {
            return null;
        }
        List<Map<String, Object>> mapList = new ArrayList<>();
        for(room room : rooms) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", room.getId());
            map.put("name", room.getName());
            map.put("status", room.getStatus());
            mapList.add(map);
        }
        return mapList;
    }

    @Override
    public List<Map<String, Object>> getRoomByStatus(String status) {
        List<room> rooms = roomRepository.findByStatus(status);

        if(rooms == null || rooms.isEmpty()) {
            return null;
        }

        List<Map<String, Object>> mapList = new ArrayList<>();
        for(room room : rooms) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", room.getId());
            map.put("name", room.getName());
            map.put("status", room.getStatus());
            mapList.add(map);
        }

        return mapList;
    }


    @Override
    public boolean addRoom(String name, String status) {

        room roomtoCheck = roomRepository.findByName(name);
        if(roomtoCheck != null) {
            return false;
        }

        room roomtoAdd = new room();

        roomtoAdd.setName(name);
        roomtoAdd.setStatus(status);

        roomRepository.save(roomtoAdd);

        return true;

    }

    @Override
    public boolean updateRoom(String roomname, String status) {

        room room = roomRepository.findByName(roomname);
        if(room == null) {
            return false;
        }
        room.setStatus(status);
        roomRepository.save(room);

        return true;
    }

    @Override
    public boolean deleteRoom(String roomname) {

        room room = roomRepository.findByName(roomname);
        if(room == null) {
            return false;
        }
        roomRepository.delete(room);

        return true;
    }

    @Override
    public room findRoomById(int id) {
        return null;
    }
}