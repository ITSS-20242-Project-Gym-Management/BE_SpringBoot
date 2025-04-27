package com.example.itssprj_ver1.service;

import com.example.itssprj_ver1.model.membership;
import com.example.itssprj_ver1.repository.membershipRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class membershipService implements membershipServiceI {

    @Autowired
    private membershipRepository membershipRepository;

    @Override
    public List<membership> getAllMemberships() {
        // Implementation to retrieve all memberships
        return membershipRepository.findAll();

    }
}
