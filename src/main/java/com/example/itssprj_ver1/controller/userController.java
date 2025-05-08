package com.example.itssprj_ver1.controller;

import com.example.itssprj_ver1.model.customer;
import com.example.itssprj_ver1.model.staff;
import com.example.itssprj_ver1.model.users;
import com.example.itssprj_ver1.repository.customerRepository;
import com.example.itssprj_ver1.repository.staffRepository;
import com.example.itssprj_ver1.repository.userRepository;
import com.example.itssprj_ver1.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.example.itssprj_ver1.config.GenToken.generateToken;

@RestController
@RequestMapping
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class userController {
    private final userService userService;
    private final customerService customerService;
    private final managerService managerService;
    private final ptService ptService;
    private final staffService staffService;

    private final userRepository userRepository;
    private final staffRepository staffRepository;
    private final customerRepository customerRepository;

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, String> request) {
        Map<String, Object> response = new HashMap<>();
        try {
            String username = request.get("username");
            String password = request.get("password");
            users user = userRepository.findByUsername(username);
            if(user.isDeleted() == true){
                response.put("status", "Tài khoản đã bị xóa");
                return ResponseEntity.status(400).body(response);
            } else {
                if (userService.login(username, password)) {
                    String token = generateToken(username, password);
                    response.put("status", "Đăng nhập thành công");
                    response.put("role", userRepository.findByUsername(username).getRole().getRolename());
                    response.put("token", token);
                    response.put("userid", userRepository.findByUsername(username).getId());
                    return ResponseEntity.ok(response);
                } else {
                    response.put("status", "Thông tin đăng nhập không đúng");
                    return ResponseEntity.status(401).body(response);
                }
            }
        } catch (Exception e) {
            response.put("message", "Đã xảy ra lỗi: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }

    @PostMapping("/addUser")
    public ResponseEntity<Map<String, Object>> addUser(@RequestBody Map<String, String> request,
                                                       @RequestHeader(value = "token", required = false) String token) {
        Map<String, Object> response = new HashMap<>();
        try {
            if (token == null || token.isEmpty()) {
                response.put("success", false);
                response.put("message", "Token is missing or invalid");
                return ResponseEntity.badRequest().body(response);
            }

            // đăng ký tài khoản
            String username = request.get("username");
            String password = request.get("password");
            int roleid = Integer.parseInt(request.get("roleid"));

            //đăng ký thông tin cá nhân
            String firstname = request.get("firstname");
            String lastname = request.get("lastname");
            String email = request.get("email");
            String phone = request.get("phone");
            String gender = request.get("gender");
            int age = Integer.parseInt(request.get("age"));

            if (userService.addUser(username, password, roleid)) {
                response.put("status", "Thêm tài khoản thành công");
            }
            users user = userRepository.findByUsername(username);

            boolean checkadd = true;
            if (roleid == 4) {
                checkadd = customerService.addCustomer(firstname, lastname, email, phone, gender, age, user.getId());
            } else if (roleid == 3) {
                checkadd = ptService.addPT(firstname, lastname, email, phone, gender, age, user.getId());
            } else if (roleid == 2) {
                checkadd = managerService.addManager(firstname, lastname, email, phone, gender, age, user.getId());
            }

            if (checkadd) {
                response.put("status", "Thêm người dùng thành công");
                return ResponseEntity.ok(response);
            } else {
                response.put("status", "Thêm người dùng không thành công");
                return ResponseEntity.status(400).body(response);
            }

        } catch (Exception e) {
            response.put("message", "Đã xảy ra lỗi: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }

    @PostMapping("/updateAccount")
    public ResponseEntity<Map<String, Object>> updateUser(@RequestBody Map<String, String> request,
                                                          @RequestHeader(value = "token", required = false) String token) {
        Map<String, Object> response = new HashMap<>();
        try {
            if (token == null || token.isEmpty()) {
                response.put("success", false);
                response.put("message", "Token is missing or invalid");
                return ResponseEntity.badRequest().body(response);
            }

            String username = request.get("username");
            String password = request.get("password");

            users user = userRepository.findByUsername(username);

            if (user.isDeleted() == true) {
                response.put("status", "Tài khoản đã bị xóa");
                return ResponseEntity.status(400).body(response);
            } else {
                if (userService.updateUser(username, password)) {
                    response.put("status", "Cập nhật tài khoản thành công");
                    return ResponseEntity.ok(response);
                } else {
                    response.put("status", "Cập nhật tài khoản không thành công");
                    return ResponseEntity.status(400).body(response);
                }
            }
        } catch (Exception e) {
            response.put("message", "Đã xảy ra lỗi: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }

    @PostMapping("/updateInfoCustomer")
    public ResponseEntity<Map<String, Object>> updateInfo(@RequestBody Map<String, String> request,
                                                          @RequestHeader(value = "token", required = false) String token) {
        Map<String, Object> response = new HashMap<>();
        try {
            if (token == null || token.isEmpty()) {
                response.put("success", false);
                response.put("message", "Token is missing or invalid");
                return ResponseEntity.badRequest().body(response);
            }
            int id = Integer.parseInt(request.get("id"));
            String firstname = request.get("firstname");
            String lastname = request.get("lastname");
            String email = request.get("email");
            String phone = request.get("phone");
            int age = Integer.parseInt(request.get("age"));

            Optional<customer> customer = customerRepository.findById(id);
            if (customer.isEmpty()) {
                response.put("status", "Người dùng không tồn tại");
                return ResponseEntity.status(400).body(response);
            }

            if(customer.get().getUserid().isDeleted() == true){
                response.put("status", "Tài khoản đã bị xóa, người dùng không tồn tại");
                return ResponseEntity.status(400).body(response);
            }else {
                if (customerService.updateCustomer(id, firstname, lastname, email, phone, age)) {
                    response.put("status", "Cập nhật thông tin thành công");
                    return ResponseEntity.ok(response);
                } else {
                    response.put("status", "Cập nhật thông tin không thành công");
                    return ResponseEntity.status(400).body(response);
                }
            }
        } catch (Exception e) {
            response.put("message", "Đã xảy ra lỗi: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }

    @PostMapping("/updateInfostaff")
    public ResponseEntity<Map<String, Object>> updateInfostaff(@RequestBody Map<String, String> request,
                                                               @RequestHeader(value = "token", required = false) String token) {
        Map<String, Object> response = new HashMap<>();
        try {
            if (token == null || token.isEmpty()) {
                response.put("success", false);
                response.put("message", "Token is missing or invalid");
                return ResponseEntity.badRequest().body(response);
            }
            int id = Integer.parseInt(request.get("id"));
            String firstname = request.get("firstname");
            String lastname = request.get("lastname");
            String email = request.get("email");
            String phone = request.get("phone");
            int age = Integer.parseInt(request.get("age"));
            String rank = request.get("rank");

            Optional<staff> staff = staffRepository.findById(id);
            if (staff.isEmpty()) {
                response.put("status", "Người dùng không tồn tại");
                return ResponseEntity.status(400).body(response);
            }
            if(staff.get().getUserid().isDeleted() == true){
                response.put("status", "Tài khoản đã bị xóa, người dùng không tồn tại");
                return ResponseEntity.status(400).body(response);
            }else {
                if (staffService.updateStaff(id, firstname, lastname, email, phone, age, rank)) {
                    response.put("status", "Cập nhật thông tin thành công");
                    return ResponseEntity.ok(response);
                } else {
                    response.put("status", "Cập nhật thông tin không thành công");
                    return ResponseEntity.status(400).body(response);
                }
            }
        } catch (Exception e) {
            response.put("message", "Đã xảy ra lỗi: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }

    @PostMapping("/deleteUser")
    public ResponseEntity<Map<String, Object>> deleteUser(@RequestBody Map<String, String> request,
                                                          @RequestHeader(value = "token", required = false) String token) {
        Map<String, Object> response = new HashMap<>();
        try {
            if (token == null || token.isEmpty()) {
                response.put("success", false);
                response.put("message", "Token is missing or invalid");
                return ResponseEntity.badRequest().body(response);
            }

            String username = request.get("username");
            users user = userRepository.findByUsername(username);
            int roleid = user.getRole().getRoleid();
            boolean checkdelete = true;
            if (roleid == 4 || roleid == 3) {
                checkdelete = userService.deleteUser(username);
            } else if (roleid == 2) {
                staff staff = staffRepository.findStaffByUserid_Id(user.getId());
                checkdelete = managerService.deleteManager(staff.getId());
                userService.deleteUser(username);
            }
            if (checkdelete) {
                response.put("status", "Xóa người dùng thành công");
                return ResponseEntity.ok(response);
            } else {
                response.put("status", "Xóa người dùng không thành công");
                return ResponseEntity.status(400).body(response);
            }
        } catch (Exception e) {
            response.put("message", "Đã xảy ra lỗi: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }

    @GetMapping("/getUserInfo")
    public ResponseEntity<Map<String, Object>> getUserInfo() {
        Map<String, Object> response = new HashMap<>();
        try {
            List<Map<String, Object>> userList = userService.getAllUsers();
            if (userList != null && !userList.isEmpty()) {
                response.put("status", "success");
                response.put("list", userList);
                return ResponseEntity.ok(response);
            } else {
                response.put("status", "error");
                response.put("message", "Không có thông tin khách hàng nào");
                return ResponseEntity.status(404).body(response);
            }
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", "Đã xảy ra lỗi: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }
}
