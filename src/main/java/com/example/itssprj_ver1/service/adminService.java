package com.example.itssprj_ver1.service;


import com.example.itssprj_ver1.model.*;
import com.example.itssprj_ver1.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class adminService implements adminServiceI {

    private final customerRepository customerRepository;
    private final memRegRepository memRegRepository;
    private final memRegService memRegService;
    private final reviewService reviewService;
    private final staffRepository staffRepository;
    private final userRepository userRepository;
    private final roleRepository roleRepository;

    @Override
    public List<Map<String, Object>> getAllCustomers() {
        List<customer> customerData = customerRepository.findAll();
        if (customerData.isEmpty()) {
            return null;
        }

        List<Map<String, Object>> customerList = new ArrayList<>();

        for (customer c : customerData) {
            Map<String, Object> customerMap = new HashMap<>();
            customerMap.put("customerId", c.getId());
            customerMap.put("name", c.getFirstname() + " " + c.getLastname());
            customerMap.put("age", c.getAge());
            customerMap.put("gender", c.getGender());
            customerMap.put("email", c.getEmail());
            customerMap.put("phone", c.getPhone());
            customerMap.put("updateAt", c.getUpdateAt());

            customerMap.put("membership", memRegService.getMembershipByCustomer(c));

            customerList.add(customerMap);
        }


        return customerList;

    }

    @Override
    public List<Map<String, Object>> getCustomersByMembershipId(Integer membershipId) {

        List<memberRegister> memRegList = memRegRepository.findAllByMembershipId(membershipId);

        if (memRegList.isEmpty()) {
            return null;
        }

        List<Map<String, Object>> customerList = new ArrayList<>();

        for (memberRegister memReg : memRegList) {

            Map<String, Object> customerMap = new HashMap<>();
            customerMap.put("customerId", memReg.getCustomer().getId());
            customerMap.put("name", memReg.getCustomer().getFirstname() + " " + memReg.getCustomer().getLastname());
            customerMap.put("age", memReg.getCustomer().getAge());
            customerMap.put("gender", memReg.getCustomer().getGender());
            customerMap.put("email", memReg.getCustomer().getEmail());
            customerMap.put("phone", memReg.getCustomer().getPhone());
            customerMap.put("updateAt", memReg.getCustomer().getUpdateAt());

            customerMap.put("membership", memRegService.getMembershipByCustomer(memReg.getCustomer()));

            customerList.add(customerMap);
        }

        return customerList;

    }

    @Override
    public List<customer> getCustomerByName(String firstName, String lastName) {
        return List.of();
    }

    @Override
    public customer getCustomerById(Integer id) {
        return null;
    }

    @Override
    public List<Map<String, Object>> getCustomerByPhone(String phone) {
        List<customer> customerData = customerRepository.findByPhone(phone);

        if (customerData.isEmpty()) {
            return null;
        }

        List<Map<String, Object>> customerList = new ArrayList<>();

        for (customer c : customerData) {
            Map<String, Object> customerMap = new HashMap<>();

            customerMap.put("customerId", c.getId());
            customerMap.put("name", c.getFirstname() + " " + c.getLastname());
            customerMap.put("age", c.getAge());
            customerMap.put("gender", c.getGender());
            customerMap.put("email", c.getEmail());
            customerMap.put("phone", c.getPhone());
            customerMap.put("updateAt", c.getUpdateAt());
            customerMap.put("membership", memRegService.getMembershipByCustomer(c));

            customerList.add(customerMap);

        }


        return customerList;
    }

    @Override
    public List<Map<String, Object>> getAllReviewsByCustomer() {

        List<review> reviews = reviewService.getReview();

        if (reviews.isEmpty()) {
            return null;
        }

        List<Map<String, Object>> reviewData = new ArrayList<>();

        int idx = 0;
        for (review review : reviews) {
            Map<String, Object> reviewMap = new HashMap<>();
            reviewMap.put("index", idx);
            reviewMap.put("reviewId", review.getId());
            reviewMap.put("customerId", review.getCustomer().getId());
            reviewMap.put("customer", review.getCustomer().getFirstname() + " " + review.getCustomer().getLastname());
            reviewMap.put("text", review.getText());
            reviewMap.put("date", review.getCreateAt());
            idx++;
            reviewData.add(reviewMap);
        }

        return reviewData;
    }

    @Override
    public List<Map<String, Object>> getAllManagers() {
        roles role = roleRepository.findByRolename("manager");

        //Tkhoan User cua Manager
        List<users> usersData = userRepository.findByRole(role);

        if (usersData.isEmpty()) {
            return new ArrayList<>();   //Nhan loi 404 status ko co quan ly nao
        }

        List<Map<String, Object>> managerData = new ArrayList<>();

        int idx = 0;
        for (users u : usersData) {
            Map<String, Object> managerMap = new HashMap<>();
            staff s = staffRepository.findByUserid(u);

            managerMap.put("index", idx);
            managerMap.put("managerId", s.getId());
            managerMap.put("name", s.getFirstname() + " " + s.getLastname());
            managerMap.put("age", s.getAge());
            managerMap.put("gender", s.getGender());
            managerMap.put("phone", s.getPhone());
            managerMap.put("email", s.getEmail());

            managerData.add(managerMap);

            idx++;
        }

        return managerData;
    }

    @Override
    public List<staff> getManagersByName(String firstName, String lastName) {
        return List.of();
    }

    @Override
    public staff getManagerById(Integer id) {
        return null;
    }

    @Override
    public List<Map<String, Object>> getManagerByPhone(String phone) {
        roles role = roleRepository.findByRolename("manager");

        //Tkhoan User cua Manager
        List<users> usersData = userRepository.findByRole(role);

        if (usersData.isEmpty()) {
            return new ArrayList<>();
        }

        List<Map<String, Object>> managerData = new ArrayList<>();

        int idx = 0;
        for (users u : usersData) {
            Map<String, Object> managerMap = new HashMap<>();
            staff s = staffRepository.findByUserid(u);

            if (s.getPhone().equals(phone)) {
                managerMap.put("index", idx);
                managerMap.put("managerId", s.getId());
                managerMap.put("name", s.getFirstname() + " " + s.getLastname());
                managerMap.put("age", s.getAge());
                managerMap.put("gender", s.getGender());
                managerMap.put("phone", s.getPhone());
                managerMap.put("email", s.getEmail());

                managerData.add(managerMap);

                idx++;
            }

        }

        return managerData;
    }

    @Override
    public List<Map<String, Object>> getAllPTs() {
        roles role = roleRepository.findByRolename("PT");

        //Tkhoan User cua PT
        List<users> usersData = userRepository.findByRole(role);
        if (usersData.isEmpty()) {
            return new ArrayList<>();   //Nhan loi 404 status ko co PT nao
        }

        List<Map<String, Object>> ptData = new ArrayList<>();

        int idx = 0;
        for(users u : usersData) {
            Map<String, Object> ptMap = new HashMap<>();

            staff s = staffRepository.findByUserid(u);

            ptMap.put("index", idx);
            ptMap.put("managerId", s.getId());
            ptMap.put("name", s.getFirstname() + " " + s.getLastname());
            ptMap.put("age", s.getAge());
            ptMap.put("gender", s.getGender());
            ptMap.put("phone", s.getPhone());
            ptMap.put("email", s.getEmail());

            ptData.add(ptMap);

            idx++;
        }

        return ptData;
    }

    @Override
    public List<staff> getPTsByName(String firstName, String lastName) {
        return List.of();
    }

    @Override
    public staff getPTById(Integer id) {
        return null;
    }

    @Override
    public List<Map<String, Object>> getPTByPhone(String phone) {
        roles role = roleRepository.findByRolename("PT");

        //Tkhoan User cua PT
        List<users> usersData = userRepository.findByRole(role);
        if (usersData.isEmpty()) {
            return new ArrayList<>();   //Nhan loi 404 status ko co PT nao
        }

        List<Map<String, Object>> ptData = new ArrayList<>();

        int idx = 0;
        for(users u : usersData) {
            Map<String, Object> ptMap = new HashMap<>();

            staff s = staffRepository.findByUserid(u);
            if(s.getPhone().equals(phone)) {
                ptMap.put("index", idx);
                ptMap.put("managerId", s.getId());
                ptMap.put("name", s.getFirstname() + " " + s.getLastname());
                ptMap.put("age", s.getAge());
                ptMap.put("gender", s.getGender());
                ptMap.put("phone", s.getPhone());
                ptMap.put("email", s.getEmail());

                ptData.add(ptMap);

                idx++;

            }
        }

        return ptData;

    }

    @Override
    public List<Map<String, Object>> getAllRooms() {

        return List.of();
    }

    @Override
    public List<room> getRoomsByStatus(String status) {
        return List.of();
    }

    @Override
    public List<roomEquipment> getRoomEquipmentByRoom(room room) {

        return List.of();
    }

    @Override
    public List<roomEquipment> getRoomEquipmentByStatus(String status) {



        return List.of();
    }

    @Override
    public List<roomEquipment> getRoomEquipmentByStatusAndRoomname(String roomname, String status) {


        return List.of();
    }

    @Override
    public boolean addRoom(room room) {
        return false;
    }

    @Override
    public boolean updateRoom(room room) {
        return false;
    }

    @Override
    public boolean deleteRoom(room room) {
        return false;
    }

    @Override
    public boolean addRoomEquipment(roomEquipment roomEquipment) {
        return false;
    }

    @Override
    public boolean updateRoomEquipment(roomEquipment roomEquipment) {
        return false;
    }

    @Override
    public boolean deleteRoomEquipment(roomEquipment roomEquipment) {
        return false;
    }


}
