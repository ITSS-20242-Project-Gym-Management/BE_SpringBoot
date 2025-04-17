package com.example.itssprj_ver1.controller;

import com.example.itssprj_ver1.service.managerService;
import com.example.itssprj_ver1.service.reviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

import static com.example.itssprj_ver1.config.GenToken.generateToken;

@RestController
@RequestMapping("/manager")
@RequiredArgsConstructor
public class managerController {
    private final managerService managerService;
    private final reviewService reviewService;

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, String> request) {
        Map<String, Object> response = new HashMap<>();
        try {
            String username = request.get("username");
            String password = request.get("password");
            if (managerService.loginManager(username, password)) {
                String token = generateToken(username, password);
                response.put("status", "Đăng nhập thành công");
                response.put("token", token);
                return ResponseEntity.ok(response);
            } else {
                response.put("status", "Thông tin đăng nhập không đúng");
                return ResponseEntity.status(401).body(response);
            }
        } catch (Exception e) {
            response.put("message", "Đã xảy ra lỗi: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }

    @GetMapping("/getReview")
    public ResponseEntity<Map<String, Object>> getReview() {
        Map<String, Object> response = new HashMap<>();
        try {
            if (reviewService.getReview() != null) {
                response.put("status", "Lấy danh sách review thành công");
                response.put("data", reviewService.getReview());
                return ResponseEntity.ok(response);
            } else {
                response.put("status", "Không có review nào");
                return ResponseEntity.status(404).body(response);
            }
        } catch (Exception e) {
            response.put("message", "Đã xảy ra lỗi: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }
}
