package com.example.itssprj_ver1.service;

import com.example.itssprj_ver1.model.customer;

public interface customerServiceI {
    boolean loginCustomer(String username, String password);

    boolean addCustomer(String firstname, String lastname, String email, String phone, String gender, int age, int userid);
}
