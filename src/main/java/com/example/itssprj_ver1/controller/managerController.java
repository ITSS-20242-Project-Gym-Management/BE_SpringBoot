package com.example.itssprj_ver1.controller;

import com.example.itssprj_ver1.service.*;
import com.example.itssprj_ver1.repository.userRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.*;
import java.sql.Date;

@RestController
@RequestMapping("/manager")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class managerController {
    private final reviewService reviewService;
    private final roomEquipmentService roomEquipmentService;
    private final exerSession exerSession;
    private final paymentService paymentService;
    private final memRegService memRegService;
    private final customerService customerService;
    private final membershipService membershipService;
    private final roomService roomService;

    @GetMapping("/getReviews")
    public ResponseEntity<Map<String, Object>> getReview(@RequestHeader(value = "token", required = false) String token) {
        Map<String, Object> response = new HashMap<>();
        try {
            // Kiểm tra token
            if (token == null || token.isEmpty()) {
                response.put("success", false);
                response.put("message", "Token is missing or invalid");
                return ResponseEntity.badRequest().body(response);
            }

            List<Map<String, Object>> reviews = reviewService.getReviews();
            if (reviewService.getReview() != null) {
                response.put("status", "Lấy danh sách review thành công");
                response.put("data", reviews);
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

    @GetMapping("/getInfoCustomer")
    public ResponseEntity<Map<String, Object>> getInfoCustomer(@RequestHeader(value = "token", required = false) String token) {
        Map<String, Object> response = new HashMap<>();
        try {
            // Kiểm tra token
            if (token == null || token.isEmpty()) {
                response.put("success", false);
                response.put("message", "Token is missing or invalid");
                return ResponseEntity.badRequest().body(response);
            }

            List<Map<String, Object>> customers = customerService.getAllCustomers();
            if (customers != null) {
                response.put("status", "Lấy danh sách khách hàng thành công");
                response.put("list", customers);
                return ResponseEntity.ok(response);
            } else {
                response.put("status", "Không có khách hàng nào");
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

            String room_name = request.get("room_name");
            String equipment_name = request.get("equipment_name");
            int quantity = Integer.parseInt(request.get("quantity"));
            String status = request.get("status");

            if (roomEquipmentService.addRoomEquipment(room_name, equipment_name, quantity, status)) {
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

            String room_name = request.get("room_name");
            String equipment_name = request.get("equipment_name");
            String status = request.get("status");

            if (roomEquipmentService.updateRoomEquipment(room_name, equipment_name, status)) {
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

            String room_name = request.get("room_name");
            String equipment_name = request.get("equipment_name");

            if (roomEquipmentService.deleteRoomEquipment(room_name, equipment_name)) {
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

    @PostMapping("/findDevice")
    public ResponseEntity<Map<String, Object>> findDevice(
            @RequestHeader(value = "token", required = false) String token,
            @RequestBody Map<String, String> request) {

        Map<String, Object> response = new HashMap<>();
        try {
            // Kiểm tra token
            if (token == null || token.isEmpty()) {
                response.put("success", false);
                response.put("message", "Token is missing or invalid");
                return ResponseEntity.badRequest().body(response);
            }
            String room_name = request.get("room_name");
            String roomEquipment = request.get("roomEquipment");
            String status = request.get("status");
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
            } else {
                // Trường hợp 7: Không có tham số nào
                roomEquipments = roomEquipmentService.getAllRoomEquipment();
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

    @GetMapping("/getExercise")
    public ResponseEntity<Map<String, Object>> getExercise(@RequestHeader(value = "token", required = false) String token) {
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

    @PostMapping("/addExercise")// thêm quan hệ khách hàng với pt
    public ResponseEntity<Map<String, Object>> addExerciseSession(@RequestHeader(value = "token", required = false) String token,
                                                                  @RequestBody Map<String, String> request) {
        Map<String, Object> response = new HashMap<>();
        try {
            // Kiểm tra token
            if (token == null || token.isEmpty()) {
                response.put("success", false);
                response.put("message", "Token is missing or invalid");
                return ResponseEntity.badRequest().body(response);
            }

            // Kiểm tra các trường bắt buộc
            String cufirstname = request.get("cufirstname");
            String culastname = request.get("culastname");
            String ptfirstname = request.get("ptfirstname");
            String ptlastname = request.get("ptlastname");
            String exerciseType = request.get("exerciseType");

            if (exerSession.addSession(cufirstname, culastname, ptfirstname, ptlastname, exerciseType)) {
                response.put("status", "Thêm buổi tập thành công");
                return ResponseEntity.ok(response);
            } else {
                response.put("status", "Thêm buổi tập không thành công");
                return ResponseEntity.status(400).body(response);
            }
        } catch (Exception e) {
            response.put("message", "Đã xảy ra lỗi: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }

    @PostMapping("/updateExercise")
    public ResponseEntity<Map<String, Object>> updateExerciseSession(@RequestHeader(value = "token", required = false) String token,
                                                                             @RequestBody Map<String, String> request) {
        Map<String, Object> response = new HashMap<>();
        try {
            // Kiểm tra token
            if (token == null || token.isEmpty()) {
                response.put("success", false);
                response.put("message", "Token is missing or invalid");
                return ResponseEntity.badRequest().body(response);
            }

            // Kiểm tra sessionid có trong request không
            if (!request.containsKey("sessionid")) {
                response.put("status", "Thiếu sessionid");
                return ResponseEntity.status(400).body(response);
            }

            int sessionid = Integer.parseInt(request.get("sessionid"));
            String cufirstname = request.get("cufirstname");
            String culastname = request.get("culastname");
            String ptfirstname = request.get("ptfirstname");
            String ptlastname = request.get("ptlastname");
            String exerciseType = request.get("exerciseType");

            if (exerSession.updateSession(sessionid, cufirstname, culastname, ptfirstname, ptlastname, exerciseType)) {
                response.put("status", "Sửa buổi tập thành công");
                return ResponseEntity.ok(response);
            } else {
                response.put("status", "Sửa buổi tập không thành công");
                return ResponseEntity.status(400).body(response);
            }
        } catch (Exception e) {
            response.put("message", "Đã xảy ra lỗi: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }

   @PostMapping("addPayment")
    public ResponseEntity<Map<String, Object>> addPayment(@RequestHeader(value = "token", required = false) String token,
                                                          @RequestBody Map<String, String> request) {
        Map<String, Object> response = new HashMap<>();
        try {
            // Kiểm tra token
            if (token == null || token.isEmpty()) {
                response.put("success", false);
                response.put("message", "Token is missing or invalid");
                return ResponseEntity.badRequest().body(response);
            }
            String phone = request.get("phone");
            String paymentMethod = request.get("paymentMethod");
            Float amount = Float.parseFloat(request.get("amount"));
            Boolean paid = Boolean.parseBoolean(request.get("paid"));

            if (paymentService.addPayment(phone , paymentMethod, amount, paid)) {
                response.put("status", "Thêm thanh toán thành công");
                return ResponseEntity.ok(response);
            } else {
                response.put("status", "Thêm thanh toán không thành công");
                return ResponseEntity.status(400).body(response);
            }
        } catch (Exception e) {
            response.put("message", "Đã xảy ra lỗi: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }
 @GetMapping("/getPayments")
    public ResponseEntity<Map<String, Object>> getPayments(@RequestHeader(value = "token", required = false) String token) {
        Map<String, Object> response = new HashMap<>();
        try {
            // Kiểm tra token
            if (token == null || token.isEmpty()) {
                response.put("success", false);
                response.put("message", "Token is missing or invalid");
                return ResponseEntity.badRequest().body(response);
            }

            List<Map<String, Object>> paymentList = new ArrayList<>();
            List<payment> payments = paymentService.getPayment();
            
            if (payments != null && !payments.isEmpty()) {
                for (payment payment : payments) {
                    Map<String, Object> paymentMap = new HashMap<>();
                    paymentMap.put("payment_id", payment.getId());
                    paymentMap.put("customer_id", payment.getCustomer().getId());
                    paymentMap.put("customer_name", payment.getCustomer().getFirstname() + " " + payment.getCustomer().getLastname());
                    paymentMap.put("amount", payment.getAmount());
                    paymentMap.put("payment_method", payment.getMethod());
                    paymentMap.put("paid", payment.getPaid());
                    paymentMap.put("created_at", payment.getCreateAt());
                    
                    paymentList.add(paymentMap);
                }
                
                response.put("status", "Lấy danh sách thanh toán thành công");
                response.put("list", paymentList);
                return ResponseEntity.ok(response);
            } else {
                response.put("status", "Không có dữ liệu thanh toán");
                response.put("list", new ArrayList<>());
                return ResponseEntity.ok(response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.put("message", "Đã xảy ra lỗi: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }
    @PostMapping("/addMemberRegister")
    public ResponseEntity<Map<String, Object>> addMemberRegister(
            @RequestHeader(value = "token", required = false) String token,
            @RequestBody Map<String, String> request) {

        Map<String, Object> response = new HashMap<>();
        try {
            // Kiểm tra token
            if (token == null || token.isEmpty()) {
                response.put("success", false);
                response.put("message", "Token is missing or invalid");
                return ResponseEntity.badRequest().body(response);
            }

            // Lấy thông tin từ request
            String phone = request.get("phone");
            String namepackage = request.get("namepackage");
            String status = request.get("status");

            // Parse dates from request
            Date beginAt = null;
            Date endAt = null;
            try {
                if (request.containsKey("beginAt") && !request.get("beginAt").isEmpty()) {
                    String beginAtStr = request.get("beginAt");
                    // Nếu cần chuyển đổi định dạng từ dd/MM/yyyy sang yyyy-MM-dd
                    SimpleDateFormat inputFormat = new SimpleDateFormat("dd-MM-yyyy");
                    SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");
                    java.util.Date parsedDate = inputFormat.parse(beginAtStr);
                    beginAt = Date.valueOf(outputFormat.format(parsedDate));
                }
                if (request.containsKey("endAt") && !request.get("endAt").isEmpty()) {
                    // Tương tự cho endAt
                    String endAtStr = request.get("endAt");
                    SimpleDateFormat inputFormat = new SimpleDateFormat("dd-MM-yyyy");
                    SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");
                    java.util.Date parsedDate = inputFormat.parse(endAtStr);
                    endAt = Date.valueOf(outputFormat.format(parsedDate));
                }
            } catch (Exception e) {
                throw new IllegalArgumentException("Định dạng ngày tháng không hợp lệ: " + e.getMessage());
            }

            // Gọi service để thêm member register
            if (memRegService.addMemberReg(phone, namepackage, status, beginAt, endAt)) {
                response.put("status", "Đăng ký gói tập thành công");
                return ResponseEntity.ok(response);
            } else {
                response.put("status", "Đăng ký gói tập không thành công");
                return ResponseEntity.status(400).body(response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.put("message", "Đã xảy ra lỗi: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }

    @PostMapping("/updateMemberRegister")
    public ResponseEntity<Map<String, Object>> updateMemberRegister(@RequestHeader(value = "token", required = false) String token,
                                                                    @RequestBody Map<String, String> request) {
        Map<String, Object> response = new HashMap<>();
        try {
            // Kiểm tra token
            if (token == null || token.isEmpty()) {
                response.put("success", false);
                response.put("message", "Token is missing or invalid");
                return ResponseEntity.badRequest().body(response);
            }

            // Lấy thông tin từ request
            int memberRegId = Integer.parseInt(request.get("memberRegId"));
            String status = request.get("status");

            // Parse dates from request
            Date beginAt = null;
            Date endAt = null;
            try {
                if (request.containsKey("beginAt") && !request.get("beginAt").isEmpty()) {
                    String beginAtStr = request.get("beginAt");
                    // Nếu cần chuyển đổi định dạng từ dd/MM/yyyy sang yyyy-MM-dd
                    SimpleDateFormat inputFormat = new SimpleDateFormat("dd-MM-yyyy");
                    SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");
                    java.util.Date parsedDate = inputFormat.parse(beginAtStr);
                    beginAt = Date.valueOf(outputFormat.format(parsedDate));
                }
                if (request.containsKey("endAt") && !request.get("endAt").isEmpty()) {
                    // Tương tự cho endAt
                    String endAtStr = request.get("endAt");
                    SimpleDateFormat inputFormat = new SimpleDateFormat("dd-MM-yyyy");
                    SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");
                    java.util.Date parsedDate = inputFormat.parse(endAtStr);
                    endAt = Date.valueOf(outputFormat.format(parsedDate));
                }
            } catch (Exception e) {
                throw new IllegalArgumentException("Định dạng ngày tháng không hợp lệ: " + e.getMessage());
            }

            // Gọi service để thêm member register
            if (memRegService.updateMemberReg(memberRegId, status, beginAt, endAt)) {
                response.put("status", "Gia hạn gói tập cho khách thành công");
                return ResponseEntity.ok(response);
            } else {
                response.put("status", "Gia hạn gói tập cho khách không thành công");
                return ResponseEntity.status(400).body(response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.put("message", "Đã xảy ra lỗi: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }

    @GetMapping("/getMemberRegister")
    public ResponseEntity<Map<String, Object>> getMemberRegister(@RequestHeader(value = "token", required = false) String token,
                                                                 @RequestParam(value = "status", required = false) String status,
                                                                 @RequestParam(value = "createAt", required = false) String createAtStr) {
        Map<String, Object> response = new HashMap<>();
        try {
            // Kiểm tra token
            if (token == null || token.isEmpty()) {
                response.put("success", false);
                response.put("message", "Token is missing or invalid");
                return ResponseEntity.badRequest().body(response);
            }

            // Parse dates from request
            Date createAt = null;
            if (createAtStr != null && !createAtStr.isEmpty()) {
                try {
                    // Chuyển đổi định dạng từ dd-MM-yyyy sang yyyy-MM-dd
                    SimpleDateFormat inputFormat = new SimpleDateFormat("dd-MM-yyyy");
                    SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");
                    java.util.Date parsedDate = inputFormat.parse(createAtStr);
                    createAt = Date.valueOf(outputFormat.format(parsedDate));
                } catch (Exception e) {
                    throw new IllegalArgumentException("Định dạng ngày tháng không hợp lệ: " + e.getMessage());
                }
            }

            List<Map<String, Object>> memberRegisters = new ArrayList<>();

            if (status != null && !status.isEmpty() && createAt == null) {
                memberRegisters = memRegService.getMemberRegByStatus(status);
            } else if (createAt != null && status == null) {
                memberRegisters = memRegService.getMemberRegByCreateAt(createAt);
            } else if (createAt == null && status == null) {
                memberRegisters = memRegService.getAllMemberReg();
            }

            if (memberRegisters != null && !memberRegisters.isEmpty()) {
                response.put("status", "Lấy danh sách gia hạn thành công");
                response.put("list", memberRegisters);
                return ResponseEntity.ok(response);
            } else {
                response.put("status", "Không có dữ liệu gia hạn");
                response.put("list", new ArrayList<>());  // Return empty list instead of null
                return ResponseEntity.ok(response);  // Return 200 OK with empty list
            }
        } catch (Exception e) {
            e.printStackTrace();  // For debugging
            response.put("message", "Đã xảy ra lỗi: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }

    @GetMapping("/getMembership")
    public ResponseEntity<Map<String, Object>> getMembership(@RequestHeader(value = "token", required = false) String token) {
        Map<String, Object> response = new HashMap<>();
        try {
            // Kiểm tra token
            if (token == null || token.isEmpty()) {
                response.put("success", false);
                response.put("message", "Token is missing or invalid");
                return ResponseEntity.badRequest().body(response);
            }

            List<Map<String, Object>> memberships = membershipService.getMembership();
            if (memberships != null) {
                response.put("status", "Lấy danh sách gói tập thành công");
                response.put("list", memberships);
                return ResponseEntity.ok(response);
            } else {
                response.put("status", "Không có gói tập nào");
                return ResponseEntity.status(404).body(response);
            }
        } catch (Exception e) {
            response.put("message", "Đã xảy ra lỗi: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }

    @GetMapping("/getRoom")
    public ResponseEntity<Map<String, Object>> getRoom(@RequestHeader(value = "token", required = false) String token) {
        Map<String, Object> response = new HashMap<>();
        try {
            // Kiểm tra token
            if (token == null || token.isEmpty()) {
                response.put("success", false);
                response.put("message", "Token is missing or invalid");
                return ResponseEntity.badRequest().body(response);
            }

            List<Map<String, Object>> rooms = roomService.getRoom();
            if (rooms != null) {
                response.put("status", "Lấy danh sách phòng tập thành công");
                response.put("list", rooms);
                return ResponseEntity.ok(response);
            } else {
                response.put("status", "Không có phòng nào");
                return ResponseEntity.status(404).body(response);
            }
        } catch (Exception e) {
            response.put("message", "Đã xảy ra lỗi: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }
}
