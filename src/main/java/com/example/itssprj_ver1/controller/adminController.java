package com.example.itssprj_ver1.controller;

import com.example.itssprj_ver1.repository.userRepository;
import com.example.itssprj_ver1.service.adminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

import static com.example.itssprj_ver1.config.GenToken.generateToken;

@RequestMapping("/admin")
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class adminController {
    private final adminService adminService;
    private final userRepository userRepository;


}
