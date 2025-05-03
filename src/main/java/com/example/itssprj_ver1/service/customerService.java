package com.example.itssprj_ver1.service;

import com.example.itssprj_ver1.model.customer;
import com.example.itssprj_ver1.model.users;
import com.example.itssprj_ver1.repository.customerRepository;
import com.example.itssprj_ver1.repository.userRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class customerService implements customerServiceI {
    @Autowired
    private userRepository userRepository;
    @Autowired
    private customerRepository customerRepository;

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

    @Override
    public List<Map<String, Object>> getAllCustomers() {
        List<customer> customers = customerRepository.findAll();
        if (customers == null || customers.isEmpty()) {
            return new ArrayList<>();
        }
        List<Map<String, Object>> mappedResults = new ArrayList<>();
        for (customer result : customers) {
            Map<String, Object> response = new HashMap<>();
            if (result.getUserid().isDeleted() == true) {
                continue;
            } else {
                response.put("customerId", result.getId());
                response.put("customerName", result.getFirstname() + " " + result.getLastname());
                response.put("customerAge", result.getAge());
                response.put("customerGender", result.getGender());
                response.put("customerPhone", result.getPhone());
                response.put("customerEmail", result.getEmail());
                response.put("infoUpdateAt", result.getUpdateAt());
                mappedResults.add(response);
            }
        }
        return mappedResults;
    }

    @Override
    public boolean updateCustomer(int customerid, String firstname, String lastname, String email, String phone, int age) {
        Optional<customer> customer = customerRepository.findById(customerid);
        if (customer.isEmpty()) {
            return false;
        }
        try {
            customer.get().setFirstname(firstname);
            customer.get().setLastname(lastname);
            customer.get().setEmail(email);
            customer.get().setPhone(phone);
            customer.get().setAge(age);
            customerRepository.save(customer.get());
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
