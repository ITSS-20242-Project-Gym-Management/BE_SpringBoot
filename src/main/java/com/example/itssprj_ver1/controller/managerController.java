package com.example.itssprj_ver1.controller;

import com.example.itssprj_ver1.service.exerSession;
import com.example.itssprj_ver1.service.roomEquipmentService;
import com.example.itssprj_ver1.repository.userRepository;
import com.example.itssprj_ver1.service.managerService;
import com.example.itssprj_ver1.service.reviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.itssprj_ver1.config.GenToken.generateToken;

@RestController
@RequestMapping("/manager")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5174", allowCredentials = "true")
public class managerController {
    private final managerService managerService;
    private final reviewService reviewService;
    private final userRepository userRepository;
    private final roomEquipmentService roomEquipmentService;
    private final exerSession exerSession;
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, String> request) {
        Map<String, Object> response = new HashMap<>();
        try {
            String username = request.get("username");
            String password = request.get("password");
            if (managerService.loginManager(username, password)) {
                String token = generateToken(username, password);
                response.put("status", "Đăng nhập thành công");
                response.put("role", "manager");
                response.put("token", token);
                response.put("userid", userRepository.findByUsername(username).getId());
                return ResponseEntity.ok(response);
            } else {
                response.put("status", "Thông tin đăng nhập không đúng");
                return ResponseEntity.status(401).body(response);
            }
        } catch (Exception e) {
            response.put("message", "Đã xảy ra lỗi: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }

    @GetMapping("/getReview")
    public ResponseEntity<Map<String, Object>> getReview() {
        Map<String, Object> response = new HashMap<>();
        try {
            if (reviewService.getReview() != null) {
                response.put("status", "Lấy danh sách review thành công");
                response.put("data", reviewService.getReview());
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

    @PostMapping("/addDevice")
    public ResponseEntity<Map<String, Object>> addDevice(@RequestHeader(value = "token", required = false) String token,
                                                         @RequestBody Map<String, String> request) {
        Map<String, Object> response = new HashMap<>();
        try {
            // Kiểm tra token
            if (token == null || token.isEmpty()) {
                response.put("success", false);
                response.put("message", "Token is missing or invalid");
                return ResponseEntity.badRequest().body(response);
            }

            int roomid = Integer.parseInt(request.get("roomid"));
            String equipment_name = request.get("equipment_name");
            int quantity = Integer.parseInt(request.get("quantity"));
            String status = request.get("status");

            if (roomEquipmentService.addRoomEquipment(roomid, equipment_name, quantity, status)) {
                response.put("status", "Thêm thiết bị thành công");
                return ResponseEntity.ok(response);
            } else {
                response.put("status", "Thêm thiết bị không thành công");
                return ResponseEntity.status(400).body(response);
            }
        } catch (Exception e) {
            response.put("message", "Đã xảy ra lỗi: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }

    @PostMapping("/updateDevice")
    public ResponseEntity<Map<String, Object>> updateDevice(@RequestHeader(value = "token", required = false) String token,
                                                            @RequestBody Map<String, String> request) {
        Map<String, Object> response = new HashMap<>();
        try {
            // Kiểm tra token
            if (token == null || token.isEmpty()) {
                response.put("success", false);
                response.put("message", "Token is missing or invalid");
                return ResponseEntity.badRequest().body(response);
            }

            int roomid = Integer.parseInt(request.get("roomid"));
            String equipment_name = request.get("equipment_name");
            String status = request.get("status");

            if (roomEquipmentService.updateRoomEquipment(roomid, equipment_name, status)) {
                response.put("status", "Cập nhật thiết bị thành công");
                return ResponseEntity.ok(response);
            } else {
                response.put("status", "Cập nhật thiết bị không thành công");
                return ResponseEntity.status(400).body(response);
            }
        } catch (Exception e) {
            response.put("message", "Đã xảy ra lỗi: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }

    @PostMapping("/deleteDevice")
    public ResponseEntity<Map<String, Object>> deleteDevice(@RequestHeader(value = "token", required = false) String token,
                                                            @RequestBody Map<String, String> request) {
        Map<String, Object> response = new HashMap<>();
        try {
            // Kiểm tra token
            if (token == null || token.isEmpty()) {
                response.put("success", false);
                response.put("message", "Token is missing or invalid");
                return ResponseEntity.badRequest().body(response);
            }

            int roomid = Integer.parseInt(request.get("roomid"));
            String equipment_name = request.get("equipment_name");
            int quantity = Integer.parseInt(request.get("quantity"));

            if (roomEquipmentService.deleteRoomEquipment(roomid, equipment_name, quantity)) {
                response.put("status", "Xóa thiết bị thành công");
                return ResponseEntity.ok(response);
            } else {
                response.put("status", "Xóa thiết bị không thành công");
                return ResponseEntity.status(400).body(response);
            }
        } catch (Exception e) {
            response.put("message", "Đã xảy ra lỗi: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }

    @GetMapping("/findDevice")
    public ResponseEntity<Map<String, Object>> findDevice(
            @RequestHeader(value = "token", required = false) String token,
            @RequestParam(value = "room_name", required = false) String room_name,
            @RequestParam(value = "roomEquipment", required = false) String roomEquipment,
            @RequestParam(value = "status", required = false) String status) {

        Map<String, Object> response = new HashMap<>();
        try {
            // Kiểm tra token
            if (token == null || token.isEmpty()) {
                response.put("success", false);
                response.put("message", "Token is missing or invalid");
                return ResponseEntity.badRequest().body(response);
            }

            // Xử lý các giá trị rỗng
            boolean hasRoomName = room_name != null && !room_name.trim().isEmpty();
            boolean hasEquipment = roomEquipment != null && !roomEquipment.trim().isEmpty();
            boolean hasStatus = status != null && !status.trim().isEmpty();

            List<Map<String, Object>> roomEquipments;

            // Xử lý các trường hợp tìm kiếm
            if (hasRoomName && hasEquipment && hasStatus) {
                // Trường hợp 1: Tất cả 3 tham số
                roomEquipments = roomEquipmentService.getRoomEquipmentByRoomNameAndNameDeviceAndStatus(
                        room_name, roomEquipment, status);
            } else if (hasRoomName && hasEquipment) {
                // Trường hợp 2: room_name + roomEquipment
                roomEquipments = roomEquipmentService.getRoomEquipmentByRoomNameAndNameDevice(
                        room_name, roomEquipment);
            } else if (hasRoomName && hasStatus) {
                // Trường hợp 3: room_name + status
                roomEquipments = roomEquipmentService.getRoomEquipmentByRoomNameAndStatus(
                        room_name, status);
            } else if (hasRoomName) {
                // Trường hợp 4: Chỉ room_name
                roomEquipments = roomEquipmentService.getRoomEquipmentByRoomName(room_name);
            } else if (hasEquipment) {
                // Trường hợp 5: Chỉ roomEquipment
                roomEquipments = roomEquipmentService.getRoomEquipmentByNameDevice(roomEquipment);
            } else if (hasStatus) {
                // Trường hợp 6: Chỉ status
                roomEquipments = roomEquipmentService.getRoomEquipmentByStatus(status);
            }else {
                // Default case: No parameters provided - initialize with empty list
                roomEquipments = new ArrayList<>();
            }

            // Trả về kết quả
            if (roomEquipments != null && !roomEquipments.isEmpty()) {
                response.put("status", "Lấy danh sách thiết bị thành công");
                response.put("list", roomEquipments);
                return ResponseEntity.ok(response);
            } else {
                response.put("status", "Không có thiết bị nào");
                return ResponseEntity.ok(response); // Sửa thành 200 OK với danh sách rỗng
            }
        } catch (Exception e) {
            response.put("message", "Đã xảy ra lỗi: " + e.getMessage());
            e.printStackTrace(); // Log lỗi để debug
            return ResponseEntity.status(500).body(response);
        }
    }

    @GetMapping("/getFullDevice")
    public ResponseEntity<Map<String, Object>> getFullDevice(@RequestHeader(value = "token", required = false) String token) {
        Map<String, Object> response = new HashMap<>();
        try {
            // Kiểm tra token
            if (token == null || token.isEmpty()) {
                response.put("success", false);
                response.put("message", "Token is missing or invalid");
                return ResponseEntity.badRequest().body(response);
            }
            List<Map<String, Object>> roomEquipments = roomEquipmentService.getAllRoomEquipment();
            if (roomEquipments != null) {
                response.put("status", "Lấy danh sách thiết bị thành công");
                response.put("list", roomEquipments);
                return ResponseEntity.ok(response);
            } else {
                response.put("status", "Không có thiết bị nào");
                return ResponseEntity.status(404).body(response);
            }
        } catch (Exception e) {
            response.put("message", "Đã xảy ra lỗi: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }

    @GetMapping("/getExercise")
    public ResponseEntity<Map<String, Object>> getExercise(@RequestHeader(value = "token", required = false) String token){
        Map<String, Object> response = new HashMap<>();
        try {
            // Kiểm tra token
            if (token == null || token.isEmpty()) {
                response.put("success", false);
                response.put("message", "Token is missing or invalid");
                return ResponseEntity.badRequest().body(response);
            }
            List<Map<String, Object>> exercise = exerSession.getAllSessions();
            if (exercise != null) {
                response.put("status", "Lấy danh sách thiết bị thành công");
                response.put("list", exercise);
                return ResponseEntity.ok(response);
            } else {
                response.put("status", "Không có thiết bị nào");
                return ResponseEntity.status(404).body(response);
            }
        } catch (Exception e) {
            response.put("message", "Đã xảy ra lỗi: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }
}
