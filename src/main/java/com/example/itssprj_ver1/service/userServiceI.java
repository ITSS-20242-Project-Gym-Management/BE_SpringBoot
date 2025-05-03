package com.example.itssprj_ver1.service;

import com.example.itssprj_ver1.model.users;

import java.util.List;

public interface userServiceI {
    boolean addUser(String username, String password, int roleid);
    boolean login(String username, String password);
    boolean updateUser(String username, String password);
    boolean deleteUser(String username);
}
