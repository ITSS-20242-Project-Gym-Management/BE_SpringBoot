package com.example.itssprj_ver1.service;

import com.example.itssprj_ver1.model.membership;
import com.example.itssprj_ver1.model.payment;

import java.util.List;
import java.util.Optional;

public interface paymentServiceI {

    List<payment> getPayment();
    Optional<payment> getPaymentById(Integer id);
    List<payment> getPaymentByCustomerId(Integer customerId);
    payment createPayment(payment newPayment);

}
