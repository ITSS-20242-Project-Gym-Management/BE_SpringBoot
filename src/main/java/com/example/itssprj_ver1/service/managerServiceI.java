package com.example.itssprj_ver1.service;

import com.example.itssprj_ver1.model.users;

public interface managerServiceI {
    boolean addManager(String firstname, String lastname, String email, String phone, String gender, int age, int userid);

    boolean deleteManager(int id);
}
