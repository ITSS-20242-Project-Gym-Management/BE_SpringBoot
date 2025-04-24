package com.example.itssprj_ver1.service;

import com.example.itssprj_ver1.model.customer;
import com.example.itssprj_ver1.model.users;
import com.example.itssprj_ver1.repository.customerRepository;
import com.example.itssprj_ver1.repository.userRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class customerService implements customerServiceI {
    @Autowired
    private userRepository userRepository;
    @Autowired
    private customerRepository customerRepository;

    @Override
    public boolean loginCustomer(String username, String password) {
        users user = userRepository.findByUsername(username);
        if (user != null && user.getPassword().equals(password) && user.getRole().getRolename().equals("customer")) {
            return true;
        }
        return false;
    }

    @Override
    public boolean addCustomer(String firstname, String lastname, String email, String phone, String gender, int age, int userid) {
        Optional<users> users = userRepository.findById(userid);

        if (users.isEmpty()) {
            return false;
        }
        customer customer = new customer();
        customer.setFirstname(firstname);
        customer.setLastname(lastname);
        customer.setAge(age);
        customer.setEmail(email);
        customer.setGender(gender);
        customer.setPhone(phone);
        customer.setUserid(users.get());
        try {
            customerRepository.save(customer);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
