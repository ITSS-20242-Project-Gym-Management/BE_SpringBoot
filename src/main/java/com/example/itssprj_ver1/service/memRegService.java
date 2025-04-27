package com.example.itssprj_ver1.service;

import com.example.itssprj_ver1.model.customer;
import com.example.itssprj_ver1.model.memberRegister;
import com.example.itssprj_ver1.repository.customerRepository;
import com.example.itssprj_ver1.repository.memRegRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class memRegService implements memRegServiceI {

    private final customerRepository customerRepository;
    private final memRegRepository memRegRepository;

    @Override
    public memberRegister addMemRegistration(memberRegister newMemReg) {
        return memRegRepository.save(newMemReg);
    }

    public static boolean checkExpired(memberRegister MemReg) {
        LocalDate currentDate = LocalDate.now();
        LocalDate endDate = MemReg.getEndAt();

        try {
            if (endDate.isBefore(currentDate)) {
                return true;
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    @Override
    public void updateMemRegExpiredStatus(memberRegister MemReg) {

        if (checkExpired(MemReg)) {
            MemReg.setStatus("Hết hạn");
            memRegRepository.save(MemReg);

        }

    }

    @Override
    public memberRegister findMemRegById(Integer id) {
        return null;
    }

    @Override
    public List<Map<String, Object>> findMemRegByCustomerId(Integer customerId) {

        //Check customerID
        customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found with id: " + customerId));

        List<memberRegister> memRegList = memRegRepository.findByCustomer(customer);

//        List<memberRegister> customerMemRegList = new ArrayList<>();

//        for(memberRegister memReg : memRegList) {
//            if(memReg.getCustomer().getId().equals(customerId)) {
//                customerMemRegList.add(memReg);
//            }
//        }

        List<Map<String, Object>> memRegData = new ArrayList<>();

        if (memRegList.isEmpty()) {
            return null;
        }
        for (memberRegister memReg : memRegList) {
            // Check expiration status and update status
            if (!memReg.getStatus().equalsIgnoreCase("Gia hạn")) {

                updateMemRegExpiredStatus(memReg);

                //Debug console

                System.out.println("Customer ID: " + memReg.getCustomer().getId());
                System.out.println("MemReg ID: " + memReg.getId());

                // Add data to the list
                Map<String, Object> memRegMap = new HashMap<>();

                memRegMap.put("registrationId", memReg.getId());
                memRegMap.put("customerId", memReg.getCustomer().getId());
                memRegMap.put("createAt", memReg.getCreateAt());
                memRegMap.put("startAt", memReg.getBeginAt());
                memRegMap.put("endAt", memReg.getEndAt());
                memRegMap.put("status", memReg.getStatus());

                memRegData.add(memRegMap);
            }
        }

        return memRegData;
    }

    @Override
    public void requestExtendMembership(memberRegister memReg) {

        if (memReg.getStatus().equalsIgnoreCase("Hết hạn")) {
            // Update the status of the membership
            memReg.setStatus("Gia hạn");

            Date currentDate = Date.valueOf(LocalDate.now());

            memReg.setCreateAt(currentDate);

            // Save the updated membership registration
            memRegRepository.save(memReg);
        } else {
            throw new RuntimeException("Gói tập chưa hết hạn hoặc đang được yêu cầu gia hạn");

        }
    }


}
