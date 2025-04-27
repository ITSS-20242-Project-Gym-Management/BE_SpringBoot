package com.example.itssprj_ver1.service;

public interface paymentServiceI {
    boolean addPayment(String cufirstname, String culastname,String method, Float amount, Boolean paid);
}
