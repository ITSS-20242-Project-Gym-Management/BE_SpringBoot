package com.example.itssprj_ver1.controller;

import com.example.itssprj_ver1.model.*;
import com.example.itssprj_ver1.repository.customerRepository;
import com.example.itssprj_ver1.repository.memRegRepository;
import com.example.itssprj_ver1.repository.membershipRepository;
import com.example.itssprj_ver1.repository.userRepository;
import com.example.itssprj_ver1.service.*;
import org.hibernate.service.NullServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

@RestController
@RequestMapping("/customer")
public class    customerController {


    @Autowired
    private reviewService reviewService;
    @Autowired
    private paymentService paymentService;
    @Autowired
    private customerRepository customerRepository;
    @Autowired
    private exerSession exerSession;
    @Autowired
    private membershipService membershipService;
    @Autowired
    private memRegService memRegService;
    @Autowired
    private memRegRepository memRegRepository;
    @Autowired
    private membershipRepository membershipRepository;
    @Autowired
    private userRepository userRepository;


    //Khach hang add review
    @PostMapping("/addReview")
    public ResponseEntity<Object> createReview(@RequestBody Map<String, String> request) {

        Map<String, Object> response = new HashMap<>();

        try {

            int userId = Integer.parseInt(request.get("userId"));
            int customerId = customerRepository.findCustomerByUserid_Id(userId).getId();

            Optional<customer> customerOpt = customerRepository.findById(customerId);
            if (customerOpt.isPresent()) {

                review newReview = new review();
                // Set the review details
                newReview.setText(request.get("text"));

                // Set the customer for the review
                newReview.setCustomer(customerOpt.get());

                //Set the current Date/Time
                Date currentDate = new Date(System.currentTimeMillis());
                newReview.setCreateAt(currentDate);

                review createdReview = reviewService.createReview(newReview);

                response.put("status", "Thêm review thành công");
                response.put("customerid", customerId);
                response.put("reviewId", createdReview.getId());
                response.put("text", createdReview.getText());
                response.put("date", createdReview.getCreateAt());

                return ResponseEntity.ok(response);

            } else {
                return new ResponseEntity<>("Customer not found", HttpStatus.NOT_FOUND);

            }

        } catch (Exception e) {
            return new ResponseEntity<>("Error creating review: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Lay danh sach review
    @GetMapping("/getReviews")
    public ResponseEntity<Map<String, Object>> getReview() {
        Map<String, Object> response = new HashMap<>();
        try {
            if (reviewService.getReview() != null) {

                response.put("status", "Lấy danh sách review thành công");

                List<review> reviews = reviewService.getReview();

                List<Map<String, Object>> reviewData = new ArrayList<>();

                for (review review : reviews) {
                    Map<String, Object> reviewMap = new HashMap<>();
                    reviewMap.put("reviewId", review.getId());
                    reviewMap.put("customer", review.getCustomer().getFirstname() + review.getCustomer().getLastname());
                    reviewMap.put("text", review.getText());
                    reviewMap.put("date", review.getCreateAt());
                    reviewData.add(reviewMap);
                }

                response.put("data", reviewData);

                return ResponseEntity.ok(response);
            } else {
                response.put("status", "Không có review nào");
                return ResponseEntity.status(404).body(response);
            }
        } catch (Exception e) {
            response.put("message", "Đã xảy ra lỗi: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }

    //Lay danh sach payment
    //Can kiem tra the token cung dung voi customerID moi thuc hien duoc
    @PostMapping("/getPayments")
    public ResponseEntity<Object> getPayments(@RequestBody Map<String, String> request) {
        Map<String, Object> response = new HashMap<>();

        try {
            // Check if the customer exists

            int userId = Integer.parseInt(request.get("userId"));
            int customerId = customerRepository.findCustomerByUserid_Id(userId).getId();

            Optional<customer> customerOpt = customerRepository.findById(customerId);
            if (customerOpt.isPresent()) {
                // Get the payments for the customer
                List<payment> customerPayments = paymentService.getPaymentByCustomerId(customerId);

                List<Map<String, Object>> paymentData = new ArrayList<>();

                if (customerPayments.isEmpty()) {
                    response.put("status", "Không có payment nào");
                    return ResponseEntity.status(404).body(response);

                } else {
                    for (payment customerPayment : customerPayments) {
                        Map<String, Object> paymentMap = new HashMap<>();
                        paymentMap.put("paymentId", customerPayment.getId());
                        paymentMap.put("customer", customerOpt.get().getFirstname() + " " + customerOpt.get().getLastname());
                        paymentMap.put("amount", customerPayment.getAmount());
                        paymentMap.put("method", customerPayment.getMethod());
                        paymentMap.put("paid", customerPayment.getPaid());
                        paymentMap.put("date", customerPayment.getCreateAt());
                        paymentData.add(paymentMap);

                    }

                    response.put("data", paymentData);
                    response.put("status", "Lấy danh sách payment thành công");
                    return ResponseEntity.ok(response);
                }

            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Customer not found");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error retrieving payments: " + e.getMessage());
        }
    }

    //Lay lich tap voi PT
    //Can them TOKEN
    @PostMapping("/getSessionsWithPT")
    public ResponseEntity<Object> getSessions(@RequestBody Map<String, String> request) {

        Map<String, Object> response = new HashMap<>();

        try {
            // Check if the customer exists
            int userId = Integer.parseInt(request.get("userId"));
            int customerId = customerRepository.findCustomerByUserid_Id(userId).getId();
            Optional<customer> customerOpt = customerRepository.findById(customerId);

            if (customerOpt.isPresent()) {
                // Get the payments for the customer

                List<exerciseSession> customerSessions = exerSession.getSessionByCustomerId(customerId);

                if (customerSessions.isEmpty()) {
                    response.put("status", "Không có session nào");
                    return ResponseEntity.status(404).body(response);

                } else {

                    List<Map<String, Object>> sessionData = new ArrayList<>();

                    for (exerciseSession customerSession : customerSessions) {

                        Map<String, Object> sessionMap = new HashMap<>();
                        sessionMap.put("sessionId", customerSession.getId());
                        sessionMap.put("customer", customerOpt.get().getFirstname() + " " + customerOpt.get().getLastname());
                        sessionMap.put("trainerId", customerSession.getStaff().getId());
                        sessionMap.put("trainer", customerSession.getStaff().getFirstname() + " " + customerSession.getStaff().getLastname());
                        sessionMap.put("sessionBeginTime", customerSession.getBeginAt());
                        sessionMap.put("sessionEndTime", customerSession.getEndAt());
                        sessionMap.put("description", customerSession.getDescription());
                        sessionData.add(sessionMap);

                    }

                    response.put("status", "Lấy danh sách session thành công");
                    response.put("data", sessionData);

                    return ResponseEntity.ok(response);
                }
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Customer not found");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error retrieving sessions: " + e.getMessage());

        }
    }

    //Lay thong tin khach hang
    @PostMapping("/getCustomerInfo")
    public ResponseEntity<Object> getCustomerInfo(@RequestBody Map<String, String> request) {
        Map<String, Object> response = new HashMap<>();

        try {
            // Check if the customer exists
            int userId = Integer.parseInt(request.get("userId"));
            int customerId = customerRepository.findCustomerByUserid_Id(userId).getId();
            Optional<customer> customerOpt = customerRepository.findById(customerId);

            if (customerOpt.isPresent()) {
                response.put("customerId", customerId);
                response.put("customerName", customerOpt.get().getFirstname() + " " + customerOpt.get().getLastname());
                response.put("customerAge", customerOpt.get().getAge());
                response.put("customerGender", customerOpt.get().getGender());
                response.put("customerPhone", customerOpt.get().getPhone());
                response.put("customerEmail", customerOpt.get().getEmail());
                response.put("infoUpdateAt", customerOpt.get().getUpdateAt());
                response.put("status", "Lấy thông tin khách hàng thành công");

                return ResponseEntity.ok(response);

            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Customer not found");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error retrieving customer info: " + e.getMessage());
        }
    }

    //Xem thong tin cac goi tap
    @GetMapping("/getExercisePackages")
    public ResponseEntity<Object> getExercisePackages() {
        Map<String, Object> response = new HashMap<>();

        try {

            // Get the payments for the customer
            response.put("status", "Lấy thông tin các gói tập thành công");
            response.put("data", membershipService.getAllMemberships());

            return ResponseEntity.ok(response);


        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error retrieving exercise packages: " + e.getMessage());
        }
    }

    //Theo doi thoi han goi tap cua Khach
    @PostMapping("/getMemberRegistration")
    public ResponseEntity<Object> getMemberRegistration(@RequestBody Map<String, String> request) {
        Map<String, Object> response = new HashMap<>();
        try {
            // Check if the customer exists
            int userId = Integer.parseInt(request.get("userId"));
            int customerId = customerRepository.findCustomerByUserid_Id(userId).getId();
            Optional<customer> customerOpt = customerRepository.findById(customerId);

            if (customerOpt.isPresent()) {
                List<Map<String, Object>> memRegData = memRegService.findMemRegByCustomerId(customerId);

                if (memRegData.isEmpty()) {

                    response.put("status", "Khách hàng không có gói tập nào hoặc gói tập đang được yêu cầu Gia hạn");

                    return ResponseEntity.status(404).body(response);
                } else {

                    response.put("status", "Lấy thông tin gói tập được đăng ký thành công");
                    response.put("data", memRegData);

                    return ResponseEntity.ok(response);
                }

            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Customer not found");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error retrieving member registration: " + e.getMessage());
        }
    }

    //Gia han goi tap
    @PostMapping("/extendMembership")
    public ResponseEntity<Object> extendMembership(@RequestBody Map<String, String> request) {
        Map<String, Object> response = new HashMap<>();

        try {
            // Check if the customer exists
            int userId = Integer.parseInt(request.get("userId"));

            int customerId = customerRepository.findCustomerByUserid_Id(userId).getId();
            int memRegId = memRegRepository.findById(customerId).get().getId();

            Optional<customer> customerOpt = customerRepository.findById(customerId);
            Optional<memberRegister> memRegOpt = memRegRepository.findById(memRegId);

            if (customerOpt.isPresent()) {

                if (memRegOpt.isPresent()) {

                    memRegService.requestExtendMembership(memRegOpt.get());

                    //Set extend info
                    Map<String, Object> extendData = new HashMap<>();

                    extendData.put("customerId", customerId);
                    extendData.put("memRegId", memRegOpt.get().getId());
                    extendData.put("membershipExtend", memRegOpt.get().getMembership());
                    extendData.put("createAt", memRegOpt.get().getCreateAt());

                    //Set response
                    response.put("status", "Yêu cầu gia hạn gói tập thành công");
                    response.put("data", extendData);

                    return ResponseEntity.ok(response);
                } else {
                    response.put("status", "Gói tập của khách không tồn tại");
                    response.put("customerId", customerId);

                    return ResponseEntity.status(404).body(response);

                }
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Customer not found");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error requesting to extend membership: " + e.getMessage());
        }


    }


}
