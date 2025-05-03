package com.example.itssprj_ver1.controller;


import com.example.itssprj_ver1.model.customer;
import com.example.itssprj_ver1.model.membership;
import com.example.itssprj_ver1.repository.membershipRepository;
import com.example.itssprj_ver1.repository.roomEquipmentRepository;
import com.example.itssprj_ver1.service.adminService;
import com.example.itssprj_ver1.service.membershipService;
import com.example.itssprj_ver1.service.roomEquipmentService;
import com.example.itssprj_ver1.service.roomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class adminController {

    private final adminService adminService;
    private final membershipService membershipService;
    private final membershipRepository membershipRepository;
    private final roomService roomService;
    private final roomEquipmentService roomEquipmentService;

    @GetMapping("/getCustomerList")
    public ResponseEntity<Object> getCustomerList() {
        Map<String, Object> response = new HashMap<>();

        try {
            List<Map<String, Object>> customerData = adminService.getAllCustomers();

            if (customerData.isEmpty()) {
                response.put("status", "Danh sach Khanh hang trong");
                return ResponseEntity.status(404).body(response);

            }

            response.put("status", "Lay danh sach Khach hang thanh cong");
            response.put("data", adminService.getAllCustomers());

            return ResponseEntity.ok(response);

        } catch (Exception e) {

            return ResponseEntity.status(500).body("Error: " + e.getMessage());

        }


    }


    @PostMapping("/getCustomerListByMembership")
    public ResponseEntity<Object> getCustomerListByMembership(@RequestBody Map<String, String> request) {
        Map<String, Object> response = new HashMap<>();

        try {

            String membershipName = request.get("membershipName");

            //Nen o trong membershipService
            Integer membershipId = membershipRepository.findByName(membershipName).getId();
            //

            List<Map<String, Object>> customerData = adminService.getCustomersByMembershipId(membershipId);

            if (customerData.isEmpty()) {
                response.put("status", "Khong co khach hang nao");
                return ResponseEntity.status(404).body(response);

            }

            response.put("status", "Lay danh sach Khach hang thanh cong");
            response.put("data", customerData);

            return ResponseEntity.ok(response);

        } catch (Exception e) {

            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }


    }


    @PostMapping("/getCustomerListByPhone")
    public ResponseEntity<Object> getCustomerListByPhone(@RequestBody Map<String, String> request) {
        Map<String, Object> response = new HashMap<>();

        try {

            String phone = request.get("phone");

            List<Map<String, Object>> customerData = adminService.getCustomerByPhone(phone);

            if (customerData == null) {
                response.put("status", "Khong co khach hang nao");
                return ResponseEntity.status(404).body(response);

            }

            response.put("status", "Lay danh sach Khach hang thanh cong");
            response.put("data", customerData);

            return ResponseEntity.ok(response);
        } catch (Exception e) {

            return ResponseEntity.status(500).body("Error: " + e.getMessage());


        }

    }

    @GetMapping("/getCustomerReviews")
    public ResponseEntity<Object> getCustomerReviews() {
        Map<String, Object> response = new HashMap<>();

        try {

            List<Map<String, Object>> reviewData = adminService.getAllReviewsByCustomer();

            if (reviewData.isEmpty()) {
                response.put("status", "Khong co review nao");
                return ResponseEntity.status(404).body(response);

            }

            response.put("status", "Lay danh sach review thanh cong");
            response.put("data", reviewData);

            return ResponseEntity.ok(response);
        } catch (Exception e) {

            return ResponseEntity.status(500).body("Error: " + e.getMessage());

        }

    }


    @GetMapping("/getManagerList")
    public ResponseEntity<Object> getManagerList() {
        Map<String, Object> response = new HashMap<>();

        try {

            List<Map<String, Object>> managerData = adminService.getAllManagers();

            if (managerData.isEmpty()) {
                response.put("status", "Khong co quan ly nao");
                return ResponseEntity.status(404).body(response);

            }

            response.put("status", "Lay danh sach Quan ly thanh cong");
            response.put("data", managerData);

            return ResponseEntity.ok(response);

        } catch (Exception e) {

            return ResponseEntity.status(500).body("Error: " + e.getMessage());

        }

    }


    @PostMapping("/getManagerListByPhone")
    public ResponseEntity<Object> getManagerListByPhone(@RequestBody Map<String, String> request) {
        Map<String, Object> response = new HashMap<>();

        try {

            String phone = request.get("phone");

            List<Map<String, Object>> managerData = adminService.getManagerByPhone(phone);

            if (managerData == null) {
                response.put("status", "Khong co quan ly nao");
                return ResponseEntity.status(404).body(response);

            }

            response.put("status", "Lay danh sach Quan ly thanh cong");
            response.put("data", managerData);

            return ResponseEntity.ok(response);

        } catch (Exception e) {

            return ResponseEntity.status(500).body("Error: " + e.getMessage());

        }
    }

    //Testing
    @GetMapping("/getPTList")
    public ResponseEntity<Object> getPTList() {
        Map<String, Object> response = new HashMap<>();
        try {

            List<Map<String, Object>> ptData = adminService.getAllPTs();
            if (ptData.isEmpty()) {
                response.put("status", "Khong co PT nao");
                return ResponseEntity.status(404).body(response);

            }
            response.put("status", "Lay danh sach PT thanh cong");
            response.put("data", ptData);

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }


    }

    //Testing
    @PostMapping("/getPTListByPhone")
    public ResponseEntity<Object> getPTListByPhone(@RequestBody Map<String, String> request) {
        Map<String, Object> response = new HashMap<>();

        try {

            String phone = request.get("phone");

            List<Map<String, Object>> ptData = adminService.getPTByPhone(phone);

            if (ptData.isEmpty()) {

                response.put("status", "Khong co PT nao");
                return ResponseEntity.status(404).body(response);

            }

            response.put("status", "Lay danh sach PT thanh cong");
            response.put("data", ptData);

            return ResponseEntity.ok(response);

        } catch (Exception e) {

            return ResponseEntity.status(500).body("Error: " + e.getMessage());

        }
    }

    //Testing
    @GetMapping("/getRoomList")
    public ResponseEntity<Object> getRoomList() {
        Map<String, Object> response = new HashMap<>();

        try {
            List<Map<String, Object>> roomData = roomService.getRoom();

            if (roomData == null) {
                response.put("status", "Khong co phong nao");
                return ResponseEntity.status(404).body(response);

            }
            response.put("status", "Lay danh sach phong thanh cong");
            response.put("data", roomData);

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());

        }

    }

    //Testing
    @PostMapping("/getRoomListByStatus")
    public ResponseEntity<Object> getRoomListByStatus(@RequestBody Map<String, String> request) {
        Map<String, Object> response = new HashMap<>();

        try {

            String status = request.get("status");

            List<Map<String, Object>> roomData = roomService.getRoomByStatus(status);

            if (roomData == null) {
                response.put("status", "Khong co phong nao");
                return ResponseEntity.status(404).body(response);

            }

            response.put("status", "Lay danh sach phong thanh cong");
            response.put("data", roomData);

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());

        }

    }

    //Testing
    @GetMapping("/getRoomEquipmentList")
    public ResponseEntity<Object> getRoomEquipmentList() {
        Map<String, Object> response = new HashMap<>();

        try {
            List<Map<String, Object>> roomEquipmentData = roomEquipmentService.getAllRoomEquipment();

            if (roomEquipmentData.isEmpty()) {
                response.put("status", "Khong co thiet bi nao");
                return ResponseEntity.status(404).body(response);

            }
            response.put("status", "Lay danh sach thiet bi thanh cong");
            response.put("data", roomEquipmentData);

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());

        }

    }

    //Testing
    @PostMapping("/getRoomEquipmentListByRoomName")
    public ResponseEntity<Object> getRoomEquipmentListByRoomName(@RequestBody Map<String, String> request) {
        Map<String, Object> response = new HashMap<>();

        try {

            String roomName = request.get("roomName");

            List<Map<String, Object>> roomEquipmentData = roomEquipmentService.getRoomEquipmentByRoomName(roomName);

            if (roomEquipmentData.isEmpty()) {
                response.put("status", "Khong co thiet bi nao hoac sai ten phong");
                return ResponseEntity.status(404).body(response);

            }
            response.put("status", "Lay danh sach thiet bi thanh cong");
            response.put("data", roomEquipmentData);

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());

        }

    }

    //Testing
    @PostMapping("/getRoomEquipmentListByStatus")
    public ResponseEntity<Object> getRoomEquipmentListByStatus(@RequestBody Map<String, String> request) {
        Map<String, Object> response = new HashMap<>();

        try {

            String status = request.get("status");

            List<Map<String, Object>> roomEquipmentData = roomEquipmentService.getRoomEquipmentByStatus(status);

            if (roomEquipmentData.isEmpty()) {
                response.put("status", "Khong co thiet bi nao");
                return ResponseEntity.status(404).body(response);

            }
            response.put("status", "Lay danh sach thiet bi thanh cong");
            response.put("data", roomEquipmentData);

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());

        }

    }

    //Testing
    @PostMapping("/addRoom")
    public ResponseEntity<Object> addRoom(@RequestBody Map<String, String> request) {
        Map<String, Object> response = new HashMap<>();

        try {

            String roomName = request.get("roomName");
            String status = request.get("status");

            boolean result = roomService.addRoom(roomName, status);

            if (result) {
                response.put("status", "Them phong thanh cong");
                return ResponseEntity.ok(response);
            } else {
                response.put("status", "Phong da ton tai");
                return ResponseEntity.status(400).body(response);
            }

        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());

        }


    }

    //Testing
    @PostMapping("/updateRoom")
    public ResponseEntity<Object> updateRoom(@RequestBody Map<String, String> request) {
        Map<String, Object> response = new HashMap<>();

        try {

            String roomName = request.get("roomName");
            String status = request.get("status");

            boolean result = roomService.updateRoom(roomName, status);

            if (result) {
                response.put("status", "Cap nhat trang thai phong thanh cong");
                return ResponseEntity.ok(response);
            } else {
                response.put("status", "Phong khong ton tai");
                return ResponseEntity.status(400).body(response);
            }

        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());

        }
    }

    //Testing
    @PostMapping("/deleteRoom")
    public ResponseEntity<Object> deleteRoom(@RequestBody Map<String, String> request) {
        Map<String, Object> response = new HashMap<>();

        try {

            String roomName = request.get("roomName");

            boolean result = roomService.deleteRoom(roomName);

            if (result) {
                response.put("status", "Xoa phong " + roomName + " thanh cong");
                return ResponseEntity.ok(response);
            } else {
                response.put("status", "Phong khong ton tai");
                return ResponseEntity.status(400).body(response);
            }

        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());

        }
    }

}