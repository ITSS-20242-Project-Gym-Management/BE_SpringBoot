package com.example.itssprj_ver1.service;

import com.example.itssprj_ver1.model.membership;
import com.example.itssprj_ver1.model.payment;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface paymentServiceI {
   boolean addPayment(String phone,String method, Float amount, Boolean paid);
    List<payment> getPayment();
    Optional<payment> getPaymentById(Integer id);
    List<payment> getPaymentByCustomerId(Integer customerId);
    payment createPayment(payment newPayment);
   //boolean updatePaymentFields(Integer paymentId, Boolean paid);
//    List<Map<String, Object>> totalRevenueByMonth(int month);

    List<Map<String, Object>> totalRevenueByYear(int year);

}
