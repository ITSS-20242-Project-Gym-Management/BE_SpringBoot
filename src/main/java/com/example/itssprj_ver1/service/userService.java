package com.example.itssprj_ver1.service;

import com.example.itssprj_ver1.exceptions.UserNotFoundException;
import com.example.itssprj_ver1.model.users;
import com.example.itssprj_ver1.repository.userRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class userService implements userServiceI {

    private final userRepository userRepository;

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
