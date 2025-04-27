package com.example.itssprj_ver1.service;


import com.example.itssprj_ver1.model.customer;
import com.example.itssprj_ver1.model.exerciseSession;
import com.example.itssprj_ver1.repository.customerRepository;
import com.example.itssprj_ver1.repository.exerSessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class exerSession implements exerSessionI{

    @Autowired
    private exerSessionRepository exerSessionRepository;
    @Autowired
    private customerRepository customerRepository;

    @Override
    public List<exerciseSession> getAllSessions() {
        return exerSessionRepository.findAll();
    }

    @Override
    public Optional<exerciseSession> getSessionById(Integer id) {
        return exerSessionRepository.findById(id);
    }


    @Override
    public List<exerciseSession> getSessionByCustomerId(Integer customerId) {

        //Check customer ID
        customer foundCustomer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found with id: " + customerId));

        //find all
        List<exerciseSession> sessions = exerSessionRepository.findAll();
        List<exerciseSession> customerSessions = new ArrayList<>();

        for (exerciseSession session : sessions) {
            if (session.getCustomer().getId().equals(customerId)) {
                customerSessions.add(session);
            }
        }

        System.out.println("Customer ID: " + customerId);
        System.out.println("Sessions size: " + sessions.size());

        return customerSessions;

    }

    @Override
    public List<exerciseSession> getSessionsByTrainerId(Integer trainerId) {
        return null;
    }

    @Override
    public exerciseSession createSession(exerciseSession newSession) {
        return null;
    }

    @Override
    public exerciseSession updateSession(Integer id, exerciseSession updatedSession) {
        return null;
    }

    @Override
    public void deleteSession(Integer id) {

    }
}
