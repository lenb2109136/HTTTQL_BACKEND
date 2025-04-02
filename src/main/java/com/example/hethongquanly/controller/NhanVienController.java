package com.example.hethongquanly.controller;

import com.example.hethongquanly.model.NhanVien;
import com.example.hethongquanly.service.NhanVienService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/nhanvien")
@CrossOrigin(origins = "http://localhost:3000")
public class NhanVienController {

    @Autowired
    private NhanVienService nhanVienService;

    @GetMapping
    public ResponseEntity<List<NhanVien>> getAllNhanVien() {
        try {
            List<NhanVien> nhanVienList = nhanVienService.getAllNhanVien();
            return ResponseEntity.ok(nhanVienList);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(null);
        }
    }
    @PutMapping("/{id}/change-password")
    public ResponseEntity<?> changePassword(
        @PathVariable int id,
        @RequestBody Map<String, String> passwordData) {
        try {
            String oldPassword = passwordData.get("oldPassword");
            String newPassword = passwordData.get("NV_PASSWORD");
            NhanVien updatedNhanVien = nhanVienService.changePassword(id, oldPassword, newPassword);
            return ResponseEntity.ok(updatedNhanVien);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Lỗi khi đổi mật khẩu: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Lỗi server: " + e.getMessage());
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<NhanVien> getNhanVienById(@PathVariable int id) {
        try {
            NhanVien nhanVien = nhanVienService.getNhanVienById(id);
            return ResponseEntity.ok(nhanVien);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping
    public ResponseEntity<?> createNhanVien(@RequestBody NhanVien nhanVien) {
        try {
            NhanVien createdNhanVien = nhanVienService.createNhanVien(nhanVien);
            return ResponseEntity.ok(createdNhanVien);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Lỗi khi tạo nhân viên: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Lỗi server: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateNhanVien(@PathVariable int id,
                                            @RequestBody NhanVien nhanVienDetails) {
        try {
            NhanVien updatedNhanVien = nhanVienService.updateNhanVien(id, nhanVienDetails);
            return ResponseEntity.ok(updatedNhanVien);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Nhân viên không tồn tại: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Lỗi server: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteNhanVien(@PathVariable int id) {
        try {
            nhanVienService.deleteNhanVien(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Nhân viên không tồn tại: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Lỗi server: " + e.getMessage());
        }
    }
}