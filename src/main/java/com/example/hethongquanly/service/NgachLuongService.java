package com.example.hethongquanly.service;


import com.example.hethongquanly.model.NgachLuong;
import com.example.hethongquanly.repository.NgachLuongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class NgachLuongService {

    @Autowired
    private NgachLuongRepository ngachLuongRepository;

    public List<NgachLuong> getAllNgachLuong() {
        return ngachLuongRepository.findAll();
    }

    public NgachLuong getNgachLuongById(Integer id) {
        return ngachLuongRepository.findById(id).orElse(null);
    }

    // Thêm mới ngạch lương với ngày tự động là ngày hiện tại
    public NgachLuong createNgachLuong(NgachLuong ngachLuong) {
        // Tự động set ngày hiện tại
        ngachLuong.setNgayApDung(new Date());
        return ngachLuongRepository.save(ngachLuong);
    }

    // Lấy tất cả ngạch lương không trùng tên (chỉ lấy bản ghi mới nhất)
    public List<NgachLuong> getAllNgachLuongLatest() {
        return ngachLuongRepository.findAllDistinctLatest();
    }
    public NgachLuong updateNgachLuong(Integer id, NgachLuong ngachLuongDetails) {
        NgachLuong ngachLuong = ngachLuongRepository.findById(id).orElse(null);
        if (ngachLuong != null) {
            ngachLuong.setLuongCoSo(ngachLuongDetails.getLuongCoSo());
            ngachLuong.setTen(ngachLuongDetails.getTen());
            ngachLuong.setNgayApDung(ngachLuongDetails.getNgayApDung());
            return ngachLuongRepository.save(ngachLuong);
        }
        return null;
    }

    public void deleteNgachLuong(Integer id) {
        ngachLuongRepository.deleteById(id);
    }
}