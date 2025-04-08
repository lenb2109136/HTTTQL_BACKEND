package com.example.hethongquanly.controller;

import com.example.hethongquanly.model.PhieuLuong;
import com.example.hethongquanly.service.PhieuLuongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class PhieuLuongController {

    @Autowired
    private PhieuLuongService phieuLuongService;

    // API lấy tất cả phiếu lương
    @GetMapping("/phieu-luong/all")
    public ResponseEntity<?> getAllPhieuLuong() {
        try {
            List<PhieuLuong> phieuLuongList = phieuLuongService.getAllPhieuLuong();
            if (phieuLuongList.isEmpty()) {
                return new ResponseEntity<>("Không có dữ liệu phiếu lương", HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(phieuLuongList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Lỗi server: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // API lấy phiếu lương theo ID nhân viên
    @GetMapping("/phieu-luong")
    public ResponseEntity<?> getPhieuLuongByEmployee(@RequestParam("employeeId") int employeeId) {
        try {
            List<PhieuLuong> phieuLuongList = phieuLuongService.getPhieuLuongByEmployee(employeeId);
            if (phieuLuongList.isEmpty()) {
                return new ResponseEntity<>("Không tìm thấy phiếu lương cho nhân viên có ID: " + employeeId, HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(phieuLuongList, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>("ID nhân viên không hợp lệ: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Lỗi server: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}