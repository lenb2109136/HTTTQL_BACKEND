package com.example.hethongquanly.service;

import com.example.hethongquanly.model.PhieuLuong;
import com.example.hethongquanly.repository.PhieuLuongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PhieuLuongService {
    @Autowired
    private PhieuLuongRepository phieuLuongRepository;

    public List<PhieuLuong> getAllPhieuLuong() {
        return phieuLuongRepository.findAll();
    }

    public List<PhieuLuong> getPhieuLuongByEmployee(int employeeId) {
        if (employeeId <= 0) {
            throw new IllegalArgumentException("Employee ID không hợp lệ");
        }
        return phieuLuongRepository.findByNvIdNV_ID(employeeId);
    }
}