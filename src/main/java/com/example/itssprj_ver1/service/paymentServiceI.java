package com.example.itssprj_ver1.service;

public interface paymentServiceI {
    boolean addPayment(String phone,String method, Float amount, Boolean paid);
}
