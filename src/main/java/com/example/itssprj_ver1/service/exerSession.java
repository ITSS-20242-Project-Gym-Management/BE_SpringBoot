package com.example.itssprj_ver1.service;


import com.example.itssprj_ver1.model.customer;
import com.example.itssprj_ver1.model.exerciseSession;
import com.example.itssprj_ver1.model.staff;
import com.example.itssprj_ver1.repository.customerRepository;
import com.example.itssprj_ver1.repository.exerSessionRepository;
import com.example.itssprj_ver1.repository.staffRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@RequiredArgsConstructor
public class exerSession implements exerSessionI{

    @Autowired
    private exerSessionRepository exerSessionRepository;
    @Autowired
    private customerRepository customerRepository;
    @Autowired
    private staffRepository staffRepository;
    
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

    @Override
    public boolean addSession(String cufirstname, String culastname, String ptfirstname, String ptlastname,String exerciseType) {
        customer customer = customerRepository.findByFirstnameAndLastname(cufirstname, culastname);
        if (customer == null) {
            return false; // Không tìm thấy khách hàng
        }

        staff staff = staffRepository.findByFirstnameAndLastname(ptfirstname, ptlastname);
        if (staff == null) {
            return false; // Không tìm thấy huấn luyện viên
        }
        try {

            // Tạo đối tượng exerciseSession sử dụng builder pattern
            exerciseSession session = exerciseSession.builder()
                    .customer(customer)
                    .staff(staff)
                    .ExerciseType(exerciseType)
                    .build();

            // Lưu buổi tập vào database
            exerSessionRepository.save(session);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateSession(int sessionid,String cufirstname, String culastname, String ptfirstname, String ptlastname, String exerciseType) {
        exerciseSession session = exerSessionRepository.findById(sessionid).orElse(null);
        if (session == null) {
            return false;
        }
        customer customer = customerRepository.findByFirstnameAndLastname(cufirstname, culastname);
        if (customer == null) {
            return false; // Không tìm thấy khách hàng
        }
        staff staff = staffRepository.findByFirstnameAndLastname(ptfirstname, ptlastname);
        if (staff == null) {
            return false; // Không tìm thấy huấn luyện viên
        }
        try {
            // Cập nhật các thuộc tính của đối tượng hiện có
            session.setCustomer(customer);
            session.setStaff(staff);
            session.setExerciseType(exerciseType);

            // Lưu buổi tập vào database
            exerSessionRepository.save(session);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<exerciseSession> getAllSession() {
        return exerSessionRepository.findAll();
    }

    @Override
    public Optional<exerciseSession> getSessionById(Integer id) {
        return exerSessionRepository.findById(id);
    }


    @Override
    public List<exerciseSession> getSessionByCustomerId(Integer customerId) {

        //Check customer ID
        customer foundCustomer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found with id: " + customerId));

        //find all
        List<exerciseSession> sessions = exerSessionRepository.findAll();
        List<exerciseSession> customerSessions = new ArrayList<>();

        for (exerciseSession session : sessions) {
            if (session.getCustomer().getId().equals(customerId)) {
                customerSessions.add(session);
            }
        }

        System.out.println("Customer ID: " + customerId);
        System.out.println("Sessions size: " + sessions.size());

        return customerSessions;

    }

    @Override
    public List<exerciseSession> getSessionsByTrainerId(Integer trainerId) {
        return null;
    }

    @Override
    public exerciseSession createSession(exerciseSession newSession) {
        return null;
    }

    @Override
    public exerciseSession updateSession(Integer id, exerciseSession updatedSession) {
        return null;
    }

    @Override
    public void deleteSession(Integer id) {

    }
}
