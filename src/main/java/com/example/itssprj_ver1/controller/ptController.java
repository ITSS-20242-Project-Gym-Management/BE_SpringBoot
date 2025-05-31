package com.example.itssprj_ver1.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.itssprj_ver1.model.exerciseSession;
import com.example.itssprj_ver1.service.exerSession;
import com.example.itssprj_ver1.model.customer;
import com.example.itssprj_ver1.service.ptService;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/pt")
@RequiredArgsConstructor
public class ptController {

    @Autowired
    private final exerSession exerSession;
    @Autowired
    private ptService ptService;

    @GetMapping("/all-schedule")
    public ResponseEntity<List<Map<String, Object>>> getAllWorkoutSchedule(){
        List<Map<String, Object>> sessions = exerSession.getAllSessions();
        return ResponseEntity.ok(sessions);
    }

    @GetMapping("/schedule/{trainerId}")
    public ResponseEntity<List<exerciseSession>> getSessionsByTrainer(@PathVariable Integer trainerId) {
        List<exerciseSession> sessions = exerSession.getSessionsByTrainerId(trainerId);
        return ResponseEntity.ok(sessions);
    }

    @PostMapping("/add-schedule")
    public ResponseEntity<ResponseEntity<?>> addWorkoutSession(@RequestBody Map<String, Object> request) {
        Integer trainerId = (Integer) request.get("trainerId");
        Integer customerId = (Integer) request.get("customerId");
        
        // Kiểm tra cả hai tham số có thể có
        String exerciseType = (String) request.get("exerciseType");
        if (exerciseType == null) {
            exerciseType = (String) request.get("excerciseType");
        }
        
        LocalDateTime beginAt = LocalDateTime.parse((String) request.get("beginAt"));
        LocalDateTime endAt = LocalDateTime.parse((String) request.get("endAt"));
        String description = (String) request.get("description");
        System.out.println("exerciseType: " + exerciseType);
        ResponseEntity<?> newSession = exerSession.createSession(trainerId, customerId, exerciseType, beginAt, endAt, description);

        return ResponseEntity.ok(newSession);
    }

    @PostMapping("/update-schedule/{sessionId}")
    public ResponseEntity<?> editWorkoutSession(
            @PathVariable Integer sessionId,
            @RequestBody Map<String, Object> request) {

        if (sessionId == null) {
            return ResponseEntity.badRequest().body("Session ID cannot be null.");
        }
        Integer trainerId = (Integer) request.get("trainerId");
        Integer customerId = (Integer) request.get("customerId");
        
        // Kiểm tra cả hai tham số có thể có
        String exerciseType = (String) request.get("exerciseType");
        if (exerciseType == null) {
            exerciseType = (String) request.get("excerciseType");
        }
        
        LocalDateTime beginAt = LocalDateTime.parse((String) request.get("beginAt"));
        LocalDateTime endAt = LocalDateTime.parse((String) request.get("endAt"));
        String description = (String) request.get("description");

        return exerSession.updateSession(sessionId, trainerId, customerId,
                exerciseType, beginAt, endAt, description);
    }

    @PostMapping("/customer-list")
    public ResponseEntity<Map<String, Object>> getCustomerList(
            @RequestBody Map<String, Object> request
    ) {
        Map<String, Object> response = new HashMap<>();

        Integer trainerId = (Integer) request.get("trainerId");
        System.out.println("CHECKING TRAINER ID: " + trainerId);
        List<customer> customerList = ptService.customerListByTrainer(trainerId);

        response.put("customerList", customerList);


        return ResponseEntity.ok(response);
    }

}
