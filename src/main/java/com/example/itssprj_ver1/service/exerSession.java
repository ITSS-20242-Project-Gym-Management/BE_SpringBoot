package com.example.itssprj_ver1.service;

import com.example.itssprj_ver1.model.exerciseSession;
import com.example.itssprj_ver1.repository.exerSessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class exerSession implements exerSessionI {
    @Autowired
    private exerSessionRepository exerSessionRepository;

    @Override
    public List<Map<String, Object>> getAllSessions() {
        List<exerciseSession> sessions = exerSessionRepository.findByStaff_Userid_Role_Roleid(3);
        List<Map<String, Object>> sessionsList = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        for (exerciseSession session : sessions) {
            Map<String, Object> sessionMap = new HashMap<>();

            // Thông tin khách hàng
            sessionMap.put("customer_name", session.getCustomer().getFirstname() + " " +
                    session.getCustomer().getLastname());

            // Thông tin huấn luyện viên
            sessionMap.put("trainer_name", session.getStaff().getFirstname() + " " +
                    session.getStaff().getLastname());

            // Thời gian
            sessionMap.put("begin_time", session.getBeginAt().format(formatter));
            sessionMap.put("end_time", session.getEndAt().format(formatter));

            // Loại và mô tả
            sessionMap.put("exercise_type", session.getExerciseType());
            sessionMap.put("description", session.getDescription());

            sessionsList.add(sessionMap);
        }

        return sessionsList;
    }
}
