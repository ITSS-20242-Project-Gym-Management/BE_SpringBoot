package com.example.itssprj_ver1.service;

import com.example.itssprj_ver1.model.users;
import com.example.itssprj_ver1.repository.userRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ptService implements ptServiceI {
    @Autowired
    private userRepository userRepository;
}
