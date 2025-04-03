package com.example.hethongquanly.service;

import com.example.hethongquanly.model.BacLuong;
import com.example.hethongquanly.repository.BacLuongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BacLuongService {
    @Autowired
    private BacLuongRepository bacLuongRepository;

    // Lấy danh sách tất cả bậc lương
    public List<BacLuong> getAllBacLuong() {
        return bacLuongRepository.findAll();
    }

    // Lấy bậc lương theo ID
    public Optional<BacLuong> getBacLuongById(int id) {
        return bacLuongRepository.findById(id);
    }

    // Thêm bậc lương
    public BacLuong createBacLuong(BacLuong bacLuong) {
        return bacLuongRepository.save(bacLuong);
    }

    // Cập nhật tên bậc lương
    public BacLuong updateBacLuong(int id, BacLuong updatedBacLuong) {
        return bacLuongRepository.findById(id).map(bacLuong -> {
            bacLuong.setBAC_TEN(updatedBacLuong.getBAC_TEN());
            return bacLuongRepository.save(bacLuong);
        }).orElse(null);
    }

    // Xóa bậc lương
    public boolean deleteBacLuong(int id) {
        if (bacLuongRepository.existsById(id)) {
            bacLuongRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
