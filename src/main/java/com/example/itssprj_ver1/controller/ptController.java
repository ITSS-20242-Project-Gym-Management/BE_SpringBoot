package com.example.itssprj_ver1.controller;

import com.example.itssprj_ver1.repository.userRepository;
import com.example.itssprj_ver1.service.ptService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

import static com.example.itssprj_ver1.config.GenToken.generateToken;

@RestController
@RequestMapping("/pt")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class ptController {

}
