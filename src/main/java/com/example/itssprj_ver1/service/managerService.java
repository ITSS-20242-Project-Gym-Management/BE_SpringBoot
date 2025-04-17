package com.example.itssprj_ver1.service;

import com.example.itssprj_ver1.model.users;
import com.example.itssprj_ver1.repository.userRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class managerService implements managerServiceI{
    @Autowired
    private userRepository userRepository;

    @Override
    public boolean loginManager(String username, String password) {
        users user = userRepository.findByUsername(username);
        if (user != null && user.getPassword().equals(password) && user.getRole().getRoleid() == 2) {
            return true;
        }
        return false;
    }
}
