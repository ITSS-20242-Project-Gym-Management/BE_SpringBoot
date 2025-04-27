package com.example.itssprj_ver1.service;

import com.example.itssprj_ver1.model.memberRegister;

import java.sql.Date;
import java.util.List;
import java.util.Map;

public interface memRegServiceI {
    boolean addMemberReg(String cufirstname, String culastname, String namepackage, String status, Date beginAt, Date endAt);

    boolean updateMemberReg(int memberRegId, String status, Date beginAt, Date endAt);

    List<Map<String, Object>> getMemberRegByStatus(String status);

    List<Map<String, Object>> getMemberRegByCreateAt(Date createAt);

    List<Map<String, Object>> getAllMemberReg();

}