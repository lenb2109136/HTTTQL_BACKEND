package com.example.hethongquanly.service;

import com.example.hethongquanly.model.NgachLuong;
import com.example.hethongquanly.repository.NgachLuongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NgachLuongService {
    @Autowired
    private NgachLuongRepository repository;

    public NgachLuongService(NgachLuongRepository ngachLuongRepository) {
        this.repository = ngachLuongRepository;
    }

    public List<NgachLuong> getDistinctNgachLuong() {
        return repository.findDistinctNgachLuong();
    }
//    List<NgachLuong> danhSachNgachLuong = repository.findDistinctNgachLuong();


    // Lấy tất cả danh sách
    public List<NgachLuong> getAll() {
        return repository.findAll();
    }

    // Tìm theo ID
    public Optional<NgachLuong> getById(int id) {
        return repository.findById(id);
    }

    // Thêm hoặc cập nhật
    public NgachLuong save(NgachLuong ngachLuong) {
        return repository.save(ngachLuong);
    }

    // Xóa theo ID
    public void deleteById(int id) {
        repository.deleteById(id);
    }

    // Câập nhật ngạch lương cơ sở theo id
    public NgachLuong updateNgachLuongCoSo(int id, Float newLuongCoSo) {
        Optional<NgachLuong> optionalNgachLuong = repository.findById(id);
        if (optionalNgachLuong.isPresent()) {
            NgachLuong ngachLuong = optionalNgachLuong.get();
            ngachLuong.setNGACH_LUONGCOSO(newLuongCoSo);
            return repository.save(ngachLuong);
        } else {
            throw new RuntimeException("Ngạch Lương không tồn tại với ID: " + id);
        }
    }
}
