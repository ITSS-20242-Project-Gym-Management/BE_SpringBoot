package com.example.itssprj_ver1.service;

import com.example.itssprj_ver1.model.exerciseSession;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.ResponseEntity;

public interface exerSessionI {

     List<Map<String,Object>> getAllSessions();
     boolean addSession(String cufirstname,String culastname, String ptfirstname,String ptlastname, String exerciseType);
     boolean updateSession(int sessionid,String cufirstname, String culastname, String ptfirstname, String ptlastname, String exerciseType);
     
     List<exerciseSession> getAllSession();
     Optional<exerciseSession> getSessionById(Integer id);
     List<exerciseSession> getSessionByCustomerId(Integer customerId);
     List<exerciseSession> getSessionsByTrainerId(Integer trainerId);

     ResponseEntity<?> createSession(Integer trainerId, Integer customerId, String exerciseType,
                                     LocalDateTime beginAt, LocalDateTime endAt, String description);
     ResponseEntity<?> updateSession(Integer sessionId, Integer trainerId, Integer customerId,
                                    String exerciseType, LocalDateTime beginAt, LocalDateTime endAt,
                                    String description);
     void deleteSession(Integer id);


}
