package com.example.itssprj_ver1.controller;

import com.example.itssprj_ver1.repository.userRepository;
import com.example.itssprj_ver1.service.customerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

import static com.example.itssprj_ver1.config.GenToken.generateToken;

@RestController
@RequestMapping("/customer")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class customerController {
    private final customerService customerService;
    private final userRepository userRepository;

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, String> request) {
        Map<String, Object> response = new HashMap<>();
        try {
            String username = request.get("username");
            String password = request.get("password");
            if (customerService.loginCustomer(username, password)) {
                String token = generateToken(username, password);
                response.put("status", "Đăng nhập thành công");
                response.put("role", "customer");
                response.put("token", token);
                response.put("userid", userRepository.findByUsername(username).getId());
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
}
