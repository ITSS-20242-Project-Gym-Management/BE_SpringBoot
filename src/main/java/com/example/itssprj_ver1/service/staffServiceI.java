package com.example.itssprj_ver1.service;

public interface staffServiceI {
    boolean updateStaff(int staffid, String firstname, String lastname, String email, String phone, int age, String rank);
}
