package com.example.itssprj_ver1.service;

import com.example.itssprj_ver1.model.customer;
import com.example.itssprj_ver1.model.memberRegister;
import com.example.itssprj_ver1.model.membership;

import java.util.List;
import java.util.Map;

public interface memRegServiceI {

    memberRegister addMemRegistration(memberRegister newMemReg);

//    static boolean checkExpiration(memberRegister MemReg);

    void updateMemRegExpiredStatus(memberRegister newMemReg);

    memberRegister findMemRegById(Integer id);

    List<Map<String, Object>> findMemRegByCustomerId(Integer customerId);

    void requestExtendMembership(memberRegister memReg);

    List<Map<String, Object>> getMembershipByCustomer(customer customer);
}
