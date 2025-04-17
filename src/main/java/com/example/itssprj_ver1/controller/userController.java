package com.example.itssprj_ver1.controller;

import com.example.itssprj_ver1.service.userService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class userController {
    private final userService userService;

    @PostMapping("/addUser")
    public ResponseEntity<Map<String,Object>> addUser(@RequestBody Map<String, String> request) {
        Map<String,Object> response = new HashMap<>();
        try {
            String username = request.get("username");
            String password = request.get("password");
            int roleid = Integer.parseInt(request.get("roleid"));
            if(userService.addUser(username, password, roleid)) {
                response.put("status", "Thêm người dùng thành công");
                return ResponseEntity.ok(response);
            } else {
                response.put("status", "Thêm người dùng thất bại");
                return ResponseEntity.status(400).body(response);
            }
        }
        catch (Exception e) {
            response.put("message", "Đã xảy ra lỗi: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }
}
