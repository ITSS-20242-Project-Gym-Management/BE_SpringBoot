package com.example.itssprj_ver1.service;

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
    @Autowired
    private roomRepository roomRepository;

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
}
