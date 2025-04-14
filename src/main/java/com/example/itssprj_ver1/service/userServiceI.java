package com.example.itssprj_ver1.service;

import com.example.itssprj_ver1.model.users;

import java.util.List;

public interface userServiceI {
    users addUser(users User);

    users getUserbyId(int id);
    users getUserbyUsername(String username);
    List<users> getUserbyRole(int roleid);

    boolean checkUserbyRoleUsername(String username,int roleid);

    void deleteUserbyId(int id);
    void deleteUserbyUsername(String username);



}
