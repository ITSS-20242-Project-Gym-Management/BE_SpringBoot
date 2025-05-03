package com.example.itssprj_ver1.service;

import com.example.itssprj_ver1.model.customer;
import com.example.itssprj_ver1.model.memberRegister;
import com.example.itssprj_ver1.model.membership;
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

    @Override
    public List<Map<String, Object>> getMembershipByCustomer(customer customer) {
        try {
            List<memberRegister> memReg = memRegRepository.findByCustomer(customer);

            if (memReg.isEmpty()) {
                return null;
            } else {

                List<Map<String, Object>> memRegList = new ArrayList<>();

                for (memberRegister memRegItem : memReg) {
                    Map<String, Object> memRegMap = new HashMap<>();
                    memRegMap.put("registrationId", memRegItem.getId());
                    memRegMap.put("createAt", memRegItem.getCreateAt());
                    memRegMap.put("startAt", memRegItem.getBeginAt());
                    memRegMap.put("endAt", memRegItem.getEndAt());
                    memRegMap.put("status", memRegItem.getStatus());

                    membership membership = memRegItem.getMembership();
                    if (membership != null) {
                        memRegMap.put("membershipName", membership.getName());
                        memRegMap.put("membershipPrice", membership.getPrice());
                        memRegMap.put("membershipDescription", membership.getDescription());
                    }

                    memRegList.add(memRegMap);
                }

                return memRegList;


            }

        }catch(NullPointerException e){
            return null;

        }

    }

}
