package com.example.itssprj_ver1.service;

import com.example.itssprj_ver1.model.membership;

import java.util.List;
import java.util.Map;

public interface membershipServiceI {
    List<Map<String, Object>> getMembership();
    List<membership> getAllMemberships();

}
