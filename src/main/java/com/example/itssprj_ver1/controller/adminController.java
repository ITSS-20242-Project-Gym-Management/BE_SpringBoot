package com.example.itssprj_ver1.controller;

import com.example.itssprj_ver1.repository.userRepository;
import com.example.itssprj_ver1.service.adminService;
import com.example.itssprj_ver1.service.ptService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.itssprj_ver1.config.GenToken.generateToken;

@RequestMapping("/admin")
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class adminController {
    private final ptService ptService;

    @GetMapping("/getAllPT")
    public ResponseEntity<Map<String, Object>> getAllPT(@RequestHeader(value = "token", required = false) String token) {
        Map<String, Object> response = new HashMap<>();
        try {
            if (token == null || token.isEmpty()) {
                response.put("status", "error");
                response.put("message", "Token is missing");
                return ResponseEntity.badRequest().body(response);
            }
            List<Map<String, Object>> ptList = ptService.findAll();
            if (ptList != null && !ptList.isEmpty()) {
                response.put("status", "success");
                response.put("list", ptList);
                return ResponseEntity.ok(response);
            }else {
                response.put("status", "error");
                response.put("message", "Invalid token");
                return ResponseEntity.status(401).body(response);
            }
        }catch (Exception e){
            response.put("status", "error");
            response.put("message", "An error occurred: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }

}
