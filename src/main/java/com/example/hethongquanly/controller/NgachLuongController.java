package com.example.hethongquanly.controller;

import com.example.hethongquanly.model.NgachLuong;
import com.example.hethongquanly.service.NgachLuongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/ngachluong")
public class NgachLuongController {
    @Autowired
    private NgachLuongService service;

    // Lấy tất cả dữ liệu
    @GetMapping
    public List<NgachLuong> getAll() {
        return service.getAll();
    }
    public NgachLuongController(NgachLuongService ngachLuongService) {
        this.service = ngachLuongService;
    }

    @GetMapping("/distinct")
    public List<NgachLuong> getDistinctNgachLuong() {
        return service.getDistinctNgachLuong();
    }

    // Lấy theo ID
    @GetMapping("/{id}")
    public ResponseEntity<NgachLuong> getById(@PathVariable int id) {
        Optional<NgachLuong> result = service.getById(id);
        return result.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Thêm mới hoặc cập nhật
    @PostMapping
    public NgachLuong create(@RequestBody NgachLuong ngachLuong) {
        return service.save(ngachLuong);
    }

    // Cập nhật theo ID
    @PutMapping("/{id}")
    public ResponseEntity<NgachLuong> update(@PathVariable int id, @RequestBody NgachLuong newData) {
        return service.getById(id).map(existing -> {
            existing.setNGACH_LUONGCOSO(newData.getNGACH_LUONGCOSO());
            existing.setNGACH_TEN(newData.getNGACH_TEN());
            return ResponseEntity.ok(service.save(existing));
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Xóa theo ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        if (service.getById(id).isPresent()) {
            service.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    // Cập nhật ngạch lương theo tên ngạch
    @PutMapping("/{id}/luongcoso")
    public ResponseEntity<NgachLuong> updateNgachLuongCoSo(@PathVariable int id, @RequestBody Float newLuongCoSo) {
        NgachLuong updatedNgachLuong = service.updateNgachLuongCoSo(id, newLuongCoSo);
        return ResponseEntity.ok(updatedNgachLuong);
    }
}
