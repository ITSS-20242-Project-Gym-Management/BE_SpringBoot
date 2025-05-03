package com.example.itssprj_ver1.service;

import com.example.itssprj_ver1.model.exerciseSession;

import java.util.List;
import java.util.Optional;

public interface exerSessionI {

     List<Map<String,Object>> getAllSessions();
    boolean addSession(String cufirstname,String culastname, String ptfirstname,String ptlastname, String exerciseType);
    boolean updateSession(int sessionid,String cufirstname, String culastname, String ptfirstname, String ptlastname, String exerciseType);
     
     List<exerciseSession> getAllSessions();
     Optional<exerciseSession> getSessionById(Integer id);
     List<exerciseSession> getSessionByCustomerId(Integer customerId);
     List<exerciseSession> getSessionsByTrainerId(Integer trainerId);

     exerciseSession createSession(exerciseSession newSession);
     exerciseSession updateSession(Integer id, exerciseSession updatedSession);

     void deleteSession(Integer id);


}
