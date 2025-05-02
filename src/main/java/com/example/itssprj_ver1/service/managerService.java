package com.example.itssprj_ver1.service;

import com.example.itssprj_ver1.model.staff;
import com.example.itssprj_ver1.model.users;
import com.example.itssprj_ver1.repository.staffRepository;
import com.example.itssprj_ver1.repository.userRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class managerService implements managerServiceI{
    @Autowired
    private userRepository userRepository;
    @Autowired
    private staffRepository staffRepository;

    @Override
    public boolean addManager(String firstname, String lastname, String email, String phone, String gender, int age, int userid) {
        Optional<users> user = userRepository.findById(userid);
        if (user.isEmpty()) {
            return false;
        }
        staff staff = new staff();
        staff.setFirstname(firstname);
        staff.setLastname(lastname);
        staff.setEmail(email);
        staff.setPhone(phone);
        staff.setGender(gender);
        staff.setAge(age);
        staff.setRank("Iron");
        staff.setUserid(user.get());
        try{
            staffRepository.save(staff);
            return true;
        }catch (Exception e) {
            return false;
        }
    }
}
