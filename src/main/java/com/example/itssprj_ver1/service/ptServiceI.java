package com.example.itssprj_ver1.service;


import java.util.List;
import java.util.Map;

import com.example.itssprj_ver1.model.customer;

public interface ptServiceI {
    boolean addPT(String firstname, String lastname, String email, String phone, String gender, int age, int userid);
    List<Map<String,Object>> findAll();
    List<customer> customerListByTrainer(Integer trainerId);

}
