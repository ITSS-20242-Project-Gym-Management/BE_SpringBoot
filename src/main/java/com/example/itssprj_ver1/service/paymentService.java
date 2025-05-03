package com.example.itssprj_ver1.service;

import com.example.itssprj_ver1.dto.monthlyRevenueDto;
import com.example.itssprj_ver1.model.customer;
import com.example.itssprj_ver1.model.payment;
import com.example.itssprj_ver1.repository.customerRepository;
import com.example.itssprj_ver1.repository.paymentRepository;
import com.example.itssprj_ver1.service.paymentServiceI;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

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


    @Override
    public List<Map<String, Object>> totalRevenueByYear(int year) {

        List<monthlyRevenueDto> monthlyRevenues = paymentRepository.findMonthlyRevenueByYear(year);
        List<Map<String, Object>> result = new ArrayList<>();

        if (monthlyRevenues == null || monthlyRevenues.isEmpty()) {
            for (int i = 1; i <= 12; i++) {
                Map<String, Object> item = new HashMap<>();
                item.put("month", i);
                item.put("revenue", 0f);
                result.add(item);
            }
            return result;
        }

        for (monthlyRevenueDto monthlyRevenue : monthlyRevenues) {
            Map<String, Object> item = new HashMap<>();
            item.put("month", monthlyRevenue.getMonth());
            item.put("revenue", monthlyRevenue.getTotalAmount());
            result.add(item);
        }


        return result;
    }
}
