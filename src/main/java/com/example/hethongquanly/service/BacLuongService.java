//package com.example.hethongquanly.service;
//
//import com.example.hethongquanly.dto.BacLuongResponse;
//import com.example.hethongquanly.model.BacLuong;
//import com.example.hethongquanly.repository.BacLuongRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Service
//public class BacLuongService {
//
//    @Autowired
//    private BacLuongRepository bacLuongRepository;
//
//    public List<BacLuongResponse> getAllBacLuong() {
//        List<BacLuong> bacLuongs = bacLuongRepository.findAll();
//
//        return bacLuongs.stream()
//                .map(this::convertToResponse)
//                .collect(Collectors.toList());
//    }
//
//    private BacLuongResponse convertToResponse(BacLuong bacLuong) {
//        BacLuongResponse response = new BacLuongResponse();
//        response.setId(bacLuong.getId());
//        response.setNgachId(bacLuong.getNgachLuong().getId()); // Chỉ lấy ID
//        response.setTen(bacLuong.getTen());
//        response.setHeSo(bacLuong.getHeSo());
//        response.setNgayApDung(bacLuong.getNgayApDung());
//        return response;
//    }
//}