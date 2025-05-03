package com.example.itssprj_ver1.service;

import java.util.List;
import java.util.Map;

public interface exerSessionI {
    List<Map<String,Object>> getAllSessions();
    boolean addSession(String cufirstname,String culastname, String ptfirstname,String ptlastname, String exerciseType);
    boolean updateSession(int sessionid,String cufirstname, String culastname, String ptfirstname, String ptlastname, String exerciseType);
}
