package com.example.itssprj_ver1.service;

import com.example.itssprj_ver1.model.customer;
import com.example.itssprj_ver1.model.memberRegister;
import com.example.itssprj_ver1.model.membership;
import com.example.itssprj_ver1.repository.customerRepository;
import com.example.itssprj_ver1.repository.memRegRepository;
import com.example.itssprj_ver1.repository.membershipRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class memRegService implements memRegServiceI {

    @Autowired
    private memRegRepository memRegRepository;

    @Autowired
    private customerRepository customerRepository;

    @Autowired
    private membershipRepository membershipRepository;

    @Override
    public boolean addMemberReg(String cufirstname, String culastname, String namepackage, String status, Date beginAt, Date endAt) {
        try {
            // Find customer by firstname and lastname
            customer customer = customerRepository.findByFirstnameAndLastname(cufirstname, culastname);
            if (customer == null) {
                return false; // Customer not found
            }

            // Find membership by name
            membership membership = membershipRepository.findByName(namepackage);
            if (membership == null) {
                return false; // Membership package not found
            }

            // Create new memberRegister using builder pattern
            memberRegister newMemberReg = memberRegister.builder()
                    .customer(customer)
                    .membership(membership)
                    .createAt(new Date(System.currentTimeMillis())) // Current date
                    .status(status)
                    .beginAt(beginAt)
                    .endAt(endAt)
                    .build();

            // Save to database
            memRegRepository.save(newMemberReg);

            return true;
        } catch (Exception e) {
            e.printStackTrace(); // Log error for debugging
            return false;
        }
    }

    @Override
    public boolean updateMemberReg(int memberRegId, String status, Date beginAt, Date endAt) {
        try {
            // Find existing memberRegister by ID
            memberRegister existingMemberReg = memRegRepository.findById(memberRegId).orElse(null);
            if (existingMemberReg == null) {
                return false; // Member register not found
            }

            // Update fields
            existingMemberReg.setStatus(status);
            existingMemberReg.setBeginAt(beginAt);
            existingMemberReg.setEndAt(endAt);

            // Save updated memberRegister to database
            memRegRepository.save(existingMemberReg);

            return true;
        } catch (Exception e) {
            e.printStackTrace(); // Log error for debugging
            return false;
        }
    }

    @Override
    public List<Map<String, Object>> getMemberRegByStatus(String status) {
        try {
            // Get member registrations by status
            List<memberRegister> memberRegs = memRegRepository.findByStatus(status);
            
            // Convert to list of maps with specific values
            List<Map<String, Object>> result = new ArrayList<>();
            
            for (memberRegister memReg : memberRegs) {
                Map<String, Object> regInfo = new HashMap<>();
                regInfo.put("id", memReg.getId());
                regInfo.put("customerFirstname", memReg.getCustomer().getFirstname());
                regInfo.put("customerLastname", memReg.getCustomer().getLastname());
                regInfo.put("membershipName", memReg.getMembership().getName());
                regInfo.put("status", memReg.getStatus());
                regInfo.put("createAt", memReg.getCreateAt());
                regInfo.put("beginAt", memReg.getBeginAt());
                regInfo.put("endAt", memReg.getEndAt());
                
                result.add(regInfo);
            }
            
            return result;
        } catch (Exception e) {
            e.printStackTrace(); // Log error for debugging
            return null;
        }
    }

    @Override
    public List<Map<String, Object>> getMemberRegByCreateAt(Date createAt) {
        try {
            List<memberRegister> memberRegs = memRegRepository.findByCreateAt(createAt);
            // Convert to list of maps with specific values
            List<Map<String, Object>> result = new ArrayList<>();

            for (memberRegister memReg : memberRegs) {
                Map<String, Object> regInfo = new HashMap<>();
                regInfo.put("id", memReg.getId());
                regInfo.put("customerName", memReg.getCustomer().getFirstname() + memReg.getCustomer().getLastname());
                regInfo.put("membershipName", memReg.getMembership().getName());
                regInfo.put("status", memReg.getStatus());
                regInfo.put("createAt", memReg.getCreateAt());
                regInfo.put("beginAt", memReg.getBeginAt());
                regInfo.put("endAt", memReg.getEndAt());

                result.add(regInfo);
            }

            return result;
        } catch (Exception e) {
            e.printStackTrace(); // Log error for debugging
            return null;
        }
    }

    @Override
    public List<Map<String, Object>> getAllMemberReg() {
        try {
            List<memberRegister> allMemberRegs = memRegRepository.findAll();
            List<Map<String, Object>> memberRegisters = new ArrayList<>();
            if (allMemberRegs != null) {
                memberRegisters = allMemberRegs.stream().map(memReg -> {
                    Map<String, Object> result = new HashMap<>();
                    result.put("id", memReg.getId());
                    result.put("customerName", memReg.getCustomer().getFirstname() + memReg.getCustomer().getLastname());
                    result.put("membershipName", memReg.getMembership().getName());
                    result.put("status", memReg.getStatus());
                    result.put("createAt", memReg.getCreateAt());
                    result.put("beginAt", memReg.getBeginAt());
                    result.put("endAt", memReg.getEndAt());
                    return result;
                }).collect(Collectors.toList());
            }
            return memberRegisters;
        } catch (Exception e) {
            e.printStackTrace(); // Log error for debugging
            return null;
        }
    }
}