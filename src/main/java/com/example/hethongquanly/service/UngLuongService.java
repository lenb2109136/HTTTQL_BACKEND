package com.example.hethongquanly.service;

import com.example.hethongquanly.model.UngLuong;
import com.example.hethongquanly.repository.UngLuongRepository;
import com.example.hethongquanly.config.SalaryAdvanceWebSocketHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UngLuongService {
    @Autowired
    private SalaryAdvanceWebSocketHandler webSocketHandler; // Trì hoãn khởi tạo

    @Autowired
    private UngLuongRepository ungLuongRepository;

    // Lấy danh sách tất cả yêu cầu ứng lương
    public List<UngLuong> getAllUngLuong() {
        return ungLuongRepository.findAll();
    }

    // Tạo mới yêu cầu ứng lương
    public UngLuong createUngLuong(UngLuong ungLuong) {
        // Kiểm tra và đặt giá trị mặc định nếu cần
        if (ungLuong.getNV_ID() == 0) {
            throw new IllegalArgumentException("NV_ID không được là 0");
        }
        if (ungLuong.getUL_NGAYUL() == null) {
            throw new IllegalArgumentException("UL_NGAYUL không được null");
        }
        if (ungLuong.getUL_TIEN() == null) {
            throw new IllegalArgumentException("UL_TIEN không được null");
        }
        // Nếu UL_TRANGTHAI null, đặt mặc định là "Chờ duyệt"
        if (ungLuong.getUL_TRANGTHAI() == null) {
            ungLuong.setUL_TRANGTHAI("Chờ duyệt");
        }

        UngLuong savedUngLuong = ungLuongRepository.save(ungLuong);
        return savedUngLuong;
    }

    // Cập nhật yêu cầu ứng lương
    public UngLuong updateUngLuong(int id, UngLuong ungLuongDetails) {
        UngLuong ungLuong = ungLuongRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Yêu cầu ứng lương không tồn tại với ID: " + id));
        
        boolean statusChanged = false;
        if (ungLuongDetails.getNV_ID() != 0 && ungLuong.getNV_ID() != ungLuongDetails.getNV_ID()) {
            ungLuong.setNV_ID(ungLuongDetails.getNV_ID());
        }
        if (ungLuongDetails.getUL_NGAYUL() != null && !ungLuong.getUL_NGAYUL().equals(ungLuongDetails.getUL_NGAYUL())) {
            ungLuong.setUL_NGAYUL(ungLuongDetails.getUL_NGAYUL());
        }
        if (ungLuongDetails.getUL_TIEN() != null && !ungLuong.getUL_TIEN().equals(ungLuongDetails.getUL_TIEN())) {
            ungLuong.setUL_TIEN(ungLuongDetails.getUL_TIEN());
        }
        if (ungLuongDetails.getUL_TRANGTHAI() != null && !ungLuong.getUL_TRANGTHAI().equals(ungLuongDetails.getUL_TRANGTHAI())) {
            ungLuong.setUL_TRANGTHAI(ungLuongDetails.getUL_TRANGTHAI());
            statusChanged = true;
        }
        
        UngLuong updatedUngLuong = ungLuongRepository.save(ungLuong);
        
        if (statusChanged) {
            webSocketHandler.notifyStatusUpdate(ungLuong.getNV_ID(), ungLuongDetails.getUL_TRANGTHAI());
        }
        
        return updatedUngLuong;
    }

    // Xóa yêu cầu ứng lương theo NV_ID
    public void deleteUngLuongByNvId(int nvId) {
        ungLuongRepository.deleteByNvId(nvId);
    }

    // Xóa yêu cầu ứng lương theo UL_ID
    public void deleteUngLuong(int id) {
        UngLuong ungLuong = ungLuongRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Yêu cầu ứng lương không tồn tại với ID: " + id));
        ungLuongRepository.delete(ungLuong);
    }
}