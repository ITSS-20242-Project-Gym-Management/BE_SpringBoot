package com.example.itssprj_ver1.service;

import com.example.itssprj_ver1.model.roles;
import com.example.itssprj_ver1.model.users;
import com.example.itssprj_ver1.repository.roleRepository;
import com.example.itssprj_ver1.repository.userRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class userService implements userServiceI {

    private final userRepository userRepository;
    private final roleRepository roleRepository;

    @Override
    public boolean addUser(String username, String password, int roleid) {
        roles role = roleRepository.findById(roleid);
        if (role == null) {
            return false;
        }
        users user = userRepository.findByUsername(username);
        if (user == null) {
            user = new users();
            user.setUsername(username);
            user.setPassword(password);
            user.setRole(role);
            userRepository.save(user);
            return true;
        } else {
            return false;
        }
    }


    @Override
    public users getUserbyUsername(String username) {
        return null;

    }

    @Override
    public List<users> getUserbyRole(int roleid) {

        return null;

    }

    @Override
    public boolean checkUserbyRoleUsername(String username, int roleid) {

        return false;

    }

    @Override
    public void deleteUserbyUsername(String username) {

    }
}
