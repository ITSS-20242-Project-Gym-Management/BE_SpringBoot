package com.example.itssprj_ver1.service;

import com.example.itssprj_ver1.model.membership;
import com.example.itssprj_ver1.repository.membershipRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class membershipService implements membershipServiceI {
    @Autowired
    private membershipRepository membershipRepository;
    @Override
    public List<Map<String, Object>> getMembership() {
        List<membership> memberships = membershipRepository.findAll();
        if(memberships == null ||memberships.isEmpty()) {
            return null;
        }
        List<Map<String, Object>> mapList = new ArrayList<>();
        for(membership membership : memberships) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", membership.getId());
            map.put("namepackage", membership.getName());
            map.put("price", membership.getPrice());
            map.put("description", membership.getDescription());
            map.put("exercise_type", membership.getExerciseType());
            mapList.add(map);
        }
        return mapList;
    }
}
