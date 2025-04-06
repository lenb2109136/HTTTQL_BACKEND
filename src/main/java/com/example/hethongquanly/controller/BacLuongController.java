//package com.example.hethongquanly.controller;
//
//import com.example.hethongquanly.dto.BacLuongResponse;
//import com.example.hethongquanly.model.BacLuong;
//import com.example.hethongquanly.model.NgachLuong;
//import com.example.hethongquanly.repository.BacLuongRepository;
//import com.example.hethongquanly.repository.NgachLuongRepository;
//import com.example.hethongquanly.service.BacLuongService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.Date;
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/bac-luong")
//public class BacLuongController {
//
//    @Autowired
//    private BacLuongService bacLuongService;
//    @Autowired
//    private NgachLuongRepository ngachLuongRepository;
//    @Autowired
//    private BacLuongRepository bacLuongRepository;
//
//    @GetMapping
//    public List<BacLuongResponse> getAllBacLuong() {
//        return bacLuongService.getAllBacLuong();
//    }
//    @PostMapping
//    public BacLuong themBacLuong(@RequestBody BacLuongResponse request) {
//        // Kiểm tra ngạch lương tồn tại
//        if (!ngachLuongRepository.existsById(request.getNgachId())) {
//            throw new RuntimeException("Ngạch lương không tồn tại");
//        }
//
//        // Tạo mới bậc lương
//        BacLuong bacLuong = new BacLuong();
//        bacLuong.setNgachLuong(new NgachLuong(request.getNgachId()));
//        bacLuong.setTen(request.getTen());
//        bacLuong.setHeSo(request.getHeSo());
//        bacLuong.setNgayApDung(new Date()); // Luôn tự động set ngày hiện tại
//
//        return bacLuongRepository.save(bacLuong);
//    }
//}
