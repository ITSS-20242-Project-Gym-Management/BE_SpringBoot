package com.example.itssprj_ver1.service;

import com.example.itssprj_ver1.model.customer;
import com.example.itssprj_ver1.model.payment;
import com.example.itssprj_ver1.repository.customerRepository;
import com.example.itssprj_ver1.repository.paymentRepository;
import com.example.itssprj_ver1.service.paymentServiceI;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class paymentService implements paymentServiceI {

    private final paymentRepository paymentRepository;
    private final customerRepository customerRepository;

    @Override
     public List<payment> getPayment() {
         // Your implementation here

         return paymentRepository.findAll();

     }

     @Override
     public Optional<payment> getPaymentById(Integer id) {
         // Your implementation here
            return paymentRepository.findById(id);
     }

     @Override
     public List<payment> getPaymentByCustomerId(Integer customerId) {
         // Your implementation here
         //Check customer ID
         customer foundCustomer = customerRepository.findById(customerId)
                 .orElseThrow(() -> new RuntimeException("Customer not found with id: " + customerId));

         List<payment> payments = paymentRepository.findAll();
         List<payment> customerPayments = new ArrayList<>();

            for (payment payment : payments) {
                if (payment.getCustomer().equals(foundCustomer)) {
                    customerPayments.add(payment);
                }
            }

            System.out.println("Customer ID: " + customerId);
            System.out.println("Payments size: " + payments.size());

            return customerPayments;

     }

     @Override
     public payment createPayment(payment newPayment) {
         // Your implementation here
            return null;

     }
}
