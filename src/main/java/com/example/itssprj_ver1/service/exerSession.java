package com.example.itssprj_ver1.service;


import com.example.itssprj_ver1.dto.ErrorResponse;
import com.example.itssprj_ver1.model.customer;
import com.example.itssprj_ver1.model.exerciseSession;
import com.example.itssprj_ver1.model.staff;
import com.example.itssprj_ver1.repository.customerRepository;
import com.example.itssprj_ver1.repository.exerSessionRepository;
import com.example.itssprj_ver1.repository.staffRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
            sessionMap.put("sessionid", session.getId());

            // Thông tin khách hàng
            sessionMap.put("customer_name", session.getCustomer().getFirstname() + " " +
                    session.getCustomer().getLastname());

            // Thông tin huấn luyện viên
            sessionMap.put("trainer_name", session.getStaff().getFirstname() + " " +
                    session.getStaff().getLastname());

            // Thời gian
            LocalDateTime beginAt = session.getBeginAt();
            LocalDateTime endAt = session.getEndAt();

            sessionMap.put("begin_time", beginAt != null ? beginAt.format(formatter) : "Chưa xác định");
            sessionMap.put("end_time", endAt != null ? endAt.format(formatter) : "Chưa xác định");

            // Loại và mô tả
            sessionMap.put("exercise_type", session.getExerciseType());
            sessionMap.put("description", session.getDescription() != null ? session.getDescription() : "Chưa xác định");

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
                    .exerciseType(exerciseType)
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
        staff foundTrainer = staffRepository.findById(trainerId)
                .orElseThrow(() -> new RuntimeException("Trainer not found with id: " + trainerId));

        // Find all sessions
        List<exerciseSession> sessions = exerSessionRepository.findAll();
        List<exerciseSession> trainerSessions = new ArrayList<>();

        for (exerciseSession session : sessions) {
            if (session.getStaff().getId().equals(trainerId)) { // Filtering by trainer ID
                trainerSessions.add(session);
            }
        }

        return trainerSessions;
    }

    @Override
    public ResponseEntity<?> createSession(
            Integer trainerId, Integer customerId, String exerciseType,
            LocalDateTime beginAt, LocalDateTime endAt, String description) {

        // Find Trainer
        Optional<staff> trainerOpt = staffRepository.findById(trainerId);
        if (trainerOpt.isEmpty()) {
            return ResponseEntity.badRequest().body(new ErrorResponse("Trainer not found", 400));
        }

        // Find Customer
        Optional<customer> customerOpt = customerRepository.findById(customerId);
        if (customerOpt.isEmpty()) {
            return ResponseEntity.badRequest().body(new ErrorResponse("Customer not found", 400));
        }

        // Check for time conflicts
        boolean sessionExists = exerSessionRepository.existsByStaffAndTimeOverlap(trainerOpt.get(), beginAt, endAt);
        if (sessionExists) {
            return ResponseEntity.badRequest().body(new ErrorResponse("Trainer already has a session at this time", 400));
        }

        sessionExists = exerSessionRepository.existsByCustomerAndTimeOverlap(customerOpt.get(), beginAt, endAt);
        if (sessionExists) {
            return ResponseEntity.badRequest().body(new ErrorResponse("Customer already has a session at this time", 400));
        }

        try {
            // Create Workout Session - don't set ID to let it be auto-generated
            exerciseSession session = exerciseSession.builder()
                    .staff(trainerOpt.get())
                    .customer(customerOpt.get())
                    .exerciseType(exerciseType)
                    .beginAt(beginAt)
                    .endAt(endAt)
                    .description(description)
                    .build();

            // Save and return the session with generated ID
            exerciseSession savedSession = exerSessionRepository.save(session);
            return ResponseEntity.ok(savedSession);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500)
                    .body(new ErrorResponse("Failed to create exercise session: " + e.getMessage(), 500));
        }
    }

    @Override
    public ResponseEntity<?> updateSession(
            Integer sessionId, Integer trainerId, Integer customerId,
            String exerciseType, LocalDateTime beginAt, LocalDateTime endAt,
            String description
    ) {
        // Find the existing session
        Optional<exerciseSession> existingSessionOpt = exerSessionRepository.findById(sessionId);
        if (existingSessionOpt.isEmpty()) {
            return ResponseEntity.badRequest().body(new ErrorResponse("Workout session not found", 400));
        }

        exerciseSession existingSession = existingSessionOpt.get();

        System.out.println(existingSession);
        // Find Trainer
        Optional<staff> trainerOpt = staffRepository.findById(trainerId);
        if (trainerOpt.isEmpty()) {
            return ResponseEntity.badRequest().body(new ErrorResponse("Trainer not found", 400));
        }

        // Find Customer
        Optional<customer> customerOpt = customerRepository.findById(customerId);
        if (customerOpt.isEmpty()) {
            return ResponseEntity.badRequest().body(new ErrorResponse("Customer not found", 400));
        }

        // Check for time conflicts - không tính buổi tập hiện tại đang cập nhật
        boolean hasTrainerConflict = exerSessionRepository.findAll().stream()
            .filter(session -> !session.getId().equals(sessionId)) // Loại trừ buổi tập hiện tại
            .filter(session -> session.getStaff().getId().equals(trainerId))
            .anyMatch(session -> 
                (beginAt.isBefore(session.getEndAt()) && endAt.isAfter(session.getBeginAt()))
            );
            
        if (hasTrainerConflict) {
            return ResponseEntity.badRequest().body(new ErrorResponse("Trainer already has a session at this time", 400));
        }

        boolean hasCustomerConflict = exerSessionRepository.findAll().stream()
            .filter(session -> !session.getId().equals(sessionId)) // Loại trừ buổi tập hiện tại
            .filter(session -> session.getCustomer().getId().equals(customerId))
            .anyMatch(session -> 
                (beginAt.isBefore(session.getEndAt()) && endAt.isAfter(session.getBeginAt()))
            );
            
        if (hasCustomerConflict) {
            return ResponseEntity.badRequest().body(new ErrorResponse("Customer already has a session at this time", 400));
        }

        // Update fields
        existingSession.setStaff(trainerOpt.get());
        existingSession.setCustomer(customerOpt.get());
        existingSession.setExerciseType(exerciseType);
        existingSession.setBeginAt(beginAt);
        existingSession.setEndAt(endAt);
        existingSession.setDescription(description);

        // Save the updated session
        exerciseSession updatedSession = exerSessionRepository.save(existingSession);
        return ResponseEntity.ok(updatedSession);
    }

    @Override
    public void deleteSession(Integer id) {

    }
}
