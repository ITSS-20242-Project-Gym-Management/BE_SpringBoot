package com.example.itssprj_ver1.service;

import com.example.itssprj_ver1.exceptions.UserNotFoundException;
import com.example.itssprj_ver1.model.customer;
import com.example.itssprj_ver1.model.roles;
import com.example.itssprj_ver1.model.staff;
import com.example.itssprj_ver1.model.users;
import com.example.itssprj_ver1.repository.customerRepository;
import com.example.itssprj_ver1.repository.roleRepository;
import com.example.itssprj_ver1.repository.staffRepository;
import com.example.itssprj_ver1.repository.userRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class userService implements userServiceI {

    private final userRepository userRepository;
    @Autowired
    private roleRepository roleRepository;
    @Autowired
    private customerRepository customerRepository;
    @Autowired
    private staffRepository staffRepository;
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
    public List<Map<String, Object>> getAllUsers() {
        List<users> users = userRepository.findAll();
        if (users == null || users.isEmpty()) {
            return null;
        }

        List<Map<String, Object>> mappedResults = new java.util.ArrayList<>();
        for (users result : users) {
            if (result.isDeleted()) {
                continue;
            }

            Map<String, Object> response = new java.util.HashMap<>();
            response.put("userId", result.getId());
            response.put("username", result.getUsername());
            response.put("password", result.getPassword());
            response.put("role", result.getRole().getRolename());
            response.put("roleId", result.getRole().getRoleid());
            response.put("createAt", result.getCreatedAt());

            // Add additional user information based on role
            int roleId = result.getRole().getRoleid();
            if (roleId == 4) { // Assuming 4 is for customers
                customer customerInfo = customerRepository.findCustomerByUserid_Id(result.getId());
                if (customerInfo != null) {
                    response.put("userType", "customer");
                    response.put("firstName", customerInfo.getFirstname());
                    response.put("lastName", customerInfo.getLastname());
                    response.put("email", customerInfo.getEmail());
                    response.put("phone", customerInfo.getPhone());
                    response.put("age", customerInfo.getAge());
                    response.put("gender", customerInfo.getGender());
                    response.put("customerId", customerInfo.getId());
                }
            } else if (roleId == 2 || roleId == 3) { // Assuming 2,3 are for staff
                staff staffInfo = staffRepository.findStaffByUserid_Id(result.getId());
                if (staffInfo != null) {
                    response.put("userType", "staff");
                    response.put("firstName", staffInfo.getFirstname());
                    response.put("lastName", staffInfo.getLastname());
                    response.put("email", staffInfo.getEmail());
                    response.put("phone", staffInfo.getPhone());
                    response.put("age", staffInfo.getAge());
                    response.put("gender", staffInfo.getGender());
                    response.put("rank", staffInfo.getRank());
                    response.put("staffId", staffInfo.getId());
                }
            }

            mappedResults.add(response);
        }

        return mappedResults;
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
