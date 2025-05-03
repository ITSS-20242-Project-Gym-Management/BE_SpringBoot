package com.example.itssprj_ver1.service;

import com.example.itssprj_ver1.model.customer;
import com.example.itssprj_ver1.model.payment;
import com.example.itssprj_ver1.repository.customerRepository;
import com.example.itssprj_ver1.repository.paymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.sql.Date;
@Service
@RequiredArgsConstructor
public class paymentService implements paymentServiceI {
    @Autowired
    private customerRepository customerRepository;
    @Autowired
    private paymentRepository paymentRepository;
    @Override
    public boolean addPayment(String phone, String method, Float amount, Boolean paid) {
        customer customer = customerRepository.findByPhone(phone);
        if (customer == null) {
            return false; // Customer not found
        }
        try{
            payment newpayment = payment.builder()
                    .customer(customer)
                    .method(method)
                    .amount(amount)
                    .paid(paid)
                    .createAt(new Date(System.currentTimeMillis())) // Sử dụng ngày hiện tại
                    .build();
            paymentRepository.save(newpayment);
            return true; // Payment added successfully
        }
        catch (Exception e){
            return false; // Error occurred while adding payment
        }
    }
}
