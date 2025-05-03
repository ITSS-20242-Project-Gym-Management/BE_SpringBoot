package com.example.itssprj_ver1.service;

import com.example.itssprj_ver1.exceptions.UserNotFoundException;
import com.example.itssprj_ver1.model.roles;
import com.example.itssprj_ver1.model.users;
import com.example.itssprj_ver1.repository.roleRepository;
import com.example.itssprj_ver1.repository.userRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class userService implements userServiceI {

    private final userRepository userRepository;
    @Autowired
    private roleRepository roleRepository;

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
    public boolean login(String username, String password) {
        users user = userRepository.findByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            return true;
        }
        return false;
    }

    @Override
    public boolean updateUser(String username, String password) {
        users user = userRepository.findByUsername(username);
        if (user != null) {
            user.setPassword(password);
            userRepository.save(user);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteUser(String username) {
        users user = userRepository.findByUsername(username);
        if (user != null ) {
            user.setDeleted(true);
            userRepository.save(user);
            return true;
        }
        return false;
    }
    @Override
    public users addUser(users User) {
        return null;
    }

    @Override
    public users getUserbyId(int id) {
        return userRepository.findById(id)
                .orElseThrow(()-> new UserNotFoundException("User not found"));

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
    public void deleteUserbyId(int id) {
        userRepository.findById(id)
                .ifPresentOrElse(userRepository::delete
                        ,()-> {throw new UserNotFoundException("User not found");});
    }

    @Override
    public void deleteUserbyUsername(String username) {

    }
}
