package com.example.hethongquanly.service;


import com.example.hethongquanly.model.BacLuong;
import com.example.hethongquanly.model.NgachLuong;
import com.example.hethongquanly.repository.BacLuongRepository;
import com.example.hethongquanly.repository.NgachLuongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BacLuongService {

    @Autowired
    private BacLuongRepository bacLuongRepository;

    @Autowired
    private NgachLuongRepository ngachLuongRepository;

    public List<BacLuong> getAllBacLuong() {
        return bacLuongRepository.findAll();
    }

    public BacLuong addBacLuong(Integer ngachId, BacLuong bacLuong) {
        // Kiểm tra xem ngạch lương có tồn tại không
        Optional<NgachLuong> ngachLuongOptional = ngachLuongRepository.findById(ngachId);
        if (!ngachLuongOptional.isPresent()) {
            throw new RuntimeException("Ngạch lương với ID " + ngachId + " không tồn tại");
        }

        // Kiểm tra các trường bắt buộc của bậc lương
        if (bacLuong.getTen() == null || bacLuong.getTen().trim().isEmpty()) {
            throw new RuntimeException("Tên bậc lương không được để trống");
        }
        if (bacLuong.getHeSo() == null || bacLuong.getHeSo() <= 0) {
            throw new RuntimeException("Hệ số lương phải lớn hơn 0");
        }

        // Liên kết bậc lương với ngạch lương
        NgachLuong ngachLuong = ngachLuongOptional.get();
        bacLuong.setNgachLuong(ngachLuong);

        // Trường ngay sẽ tự động được đặt trong entity (không cần truyền vào)

        // Lưu bậc lương
        return bacLuongRepository.save(bacLuong);
    }
    public List<BacLuong> getLatestBacLuongByNgachIdAndBacTen() {
        return bacLuongRepository.findLatestBacLuongByNgachIdAndBacTen();
    }

    public List<BacLuong> getBacLuongByNgachId(Integer ngachId) {
        Optional<NgachLuong> ngachLuongOptional = ngachLuongRepository.findById(ngachId);
        return ngachLuongOptional.map(NgachLuong::getBacLuongs).orElse(Collections.emptyList());
    }
    public List<BacLuong> getLatestBacLuongByNgachId(Integer ngachId) {
        return bacLuongRepository.findLatestBacLuongByNgachId(ngachId);
    }
}
