package com.example.itssprj_ver1.service;

import com.example.itssprj_ver1.model.room;

import java.util.List;
import java.util.Map;

public interface roomServiceI {
    List<Map<String, Object>> getRoom();

    List<Map<String, Object>> getRoomByStatus(String status);

    boolean addRoom(String roomname, String status);

    boolean updateRoom(String roomname, String status);

    boolean deleteRoom(String roomname);

    room findRoomById(int id);


}