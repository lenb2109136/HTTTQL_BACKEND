package com.example.hethongquanly.controller;

import com.example.hethongquanly.model.UngLuong;
import com.example.hethongquanly.service.UngLuongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ung-luong")
@CrossOrigin(origins = "http://localhost:3000")
public class UngLuongController {
    @Autowired
    private UngLuongService ungLuongService;

    @GetMapping
    public ResponseEntity<?> getAllUngLuong(@RequestParam(required = false) Integer nvId) {
        try {
            List<UngLuong> ungLuongList = ungLuongService.getAllUngLuong();
            if (nvId != null) {
                ungLuongList = ungLuongList.stream()
                    .filter(u -> u.getNV_ID() == nvId) // Lọc chính xác theo NV_ID
                    .toList();
            }
            if (ungLuongList.isEmpty()) {
                return ResponseEntity.ok("Không có yêu cầu ứng lương nào cho NV_ID: " + nvId);
            }
            return ResponseEntity.ok(ungLuongList);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Lỗi khi lấy danh sách ứng lương: " + e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> createUngLuong(@RequestBody UngLuong ungLuong) {
        try {
            UngLuong createdUngLuong = ungLuongService.createUngLuong(ungLuong);
            return ResponseEntity.ok(createdUngLuong);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Lỗi khi tạo yêu cầu ứng lương: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUngLuong(
        @PathVariable int id,
        @RequestBody UngLuong ungLuongDetails
    ) {
        try {
            UngLuong updatedUngLuong = ungLuongService.updateUngLuong(id, ungLuongDetails);
            return ResponseEntity.ok(updatedUngLuong);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Yêu cầu ứng lương không tồn tại với ID: " + id);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Lỗi khi cập nhật yêu cầu ứng lương: " + e.getMessage());
        }
    }

    @DeleteMapping("/nv/{nvId}")
    public ResponseEntity<?> deleteUngLuongByNvId(@PathVariable int nvId) {
        try {
            ungLuongService.deleteUngLuongByNvId(nvId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Lỗi khi xóa yêu cầu ứng lương theo NV_ID: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUngLuong(@PathVariable int id) {
        try {
            ungLuongService.deleteUngLuong(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Yêu cầu ứng lương không tồn tại với ID: " + id);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Lỗi khi xóa yêu cầu ứng lương: " + e.getMessage());
        }
    }
}