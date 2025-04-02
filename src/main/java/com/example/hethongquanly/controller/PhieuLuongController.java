//package com.example.hethongquanly.controller;
//
//import com.example.hethongquanly.model.PhieuLuong;
//import com.example.hethongquanly.service.PhieuLuongService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api")
//@CrossOrigin(origins = "http://localhost:3000")
//public class PhieuLuongController {
//    @Autowired
//    private PhieuLuongService phieuLuongService;
//
//    @GetMapping("/phieu-luong")
//    public ResponseEntity<List<PhieuLuong>> getPhieuLuongByEmployee(@RequestParam("employeeId") int employeeId) {
//        try {
//            List<PhieuLuong> phieuLuongList = phieuLuongService.getPhieuLuongByEmployee(employeeId);
//            if (phieuLuongList.isEmpty()) {
//                return ResponseEntity.noContent().build();
//            }
//            return ResponseEntity.ok(phieuLuongList);
//        } catch (Exception e) {
//            return ResponseEntity.status(500).body(null);
//        }
//    }
//}