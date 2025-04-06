package com.example.hethongquanly.service;

import com.example.hethongquanly.model.BacLuong;
import com.example.hethongquanly.model.ChiTietBacLuong;
import com.example.hethongquanly.model.NhanVien;
import com.example.hethongquanly.repository.BacLuongRepository;
import com.example.hethongquanly.repository.ChiTietBacLuongRepository;
import com.example.hethongquanly.repository.NhanVienRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class ChiTietBacLuongService {
    @Autowired
    private final ChiTietBacLuongRepository chiTietBacLuongRepository;
    private final BacLuongRepository bacLuongRepository;
    private final NhanVienRepository nhanVienRepository;


    @Transactional
    public ChiTietBacLuong themChiTietBacLuong(int nhanVienId, int bacLuongId) {
        // Kiểm tra nhân viên tồn tại
        NhanVien nhanVien = nhanVienRepository.findById(nhanVienId)
                .orElseThrow(() -> new RuntimeException("Nhân viên không tồn tại với id: " + nhanVienId));

        // Kiểm tra bậc lương tồn tại
        BacLuong bacLuong = bacLuongRepository.findById(bacLuongId)
                .orElseThrow(() -> new RuntimeException("Bậc lương không tồn tại với id: " + bacLuongId));

        // Tạo mới chi tiết bậc lương
        ChiTietBacLuong chiTietBacLuong = new ChiTietBacLuong();
        chiTietBacLuong.setNV_ID(nhanVien);
        chiTietBacLuong.setBAC_ID(bacLuong);

        // Ngày áp dụng sẽ được tự động set bởi @PrePersist trong entity

        return chiTietBacLuongRepository.save(chiTietBacLuong);
    }

    public List<ChiTietBacLuong> getAll() {
        return chiTietBacLuongRepository.findAll();
    }
    // Lấy chi tiết bậc lương của nhân viên theo NV_ID
//    public List<ChiTietBacLuong> getChiTietBacLuongByNhanVienId(Integer nvId) {
//        return chiTietBacLuongRepository.findByNhanVien_NV_ID(nvId);  // Đảm bảo gọi phương thức đúng
//    }
    public List<ChiTietBacLuong> findByNhanVienId(int nhanVienId) {
        return chiTietBacLuongRepository.findByNhanVienId(nhanVienId);
    }
    public ChiTietBacLuong findLatestByNhanVienId(int nhanVienId) {
        // Kiểm tra nhân viên tồn tại
        if (!nhanVienRepository.existsById(nhanVienId)) {
            throw new NoSuchElementException("Nhân viên không tồn tại với id: " + nhanVienId);
        }

        return chiTietBacLuongRepository.findLatestByNhanVienId(nhanVienId)
                .orElseThrow(() -> new NoSuchElementException("Nhân viên chưa có bậc lương nào"));
    }


}
