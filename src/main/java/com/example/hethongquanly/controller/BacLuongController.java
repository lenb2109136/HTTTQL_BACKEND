package com.example.hethongquanly.controller;

import com.example.hethongquanly.model.BacLuong;
import com.example.hethongquanly.service.BacLuongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/bacluong")
public class BacLuongController {
    @Autowired
    private BacLuongService bacLuongService;

    // 1️⃣ Lấy danh sách tất cả bậc lương
    @GetMapping
    public List<BacLuong> getAllBacLuong() {
        return bacLuongService.getAllBacLuong();
    }

    // 2️⃣ Lấy bậc lương theo ID
    @GetMapping("/{id}")
    public ResponseEntity<BacLuong> getBacLuongById(@PathVariable int id) {
        Optional<BacLuong> bacLuong = bacLuongService.getBacLuongById(id);
        return bacLuong.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // 3️⃣ Thêm bậc lương mới
    @PostMapping
    public BacLuong createBacLuong(@RequestBody BacLuong bacLuong) {
        return bacLuongService.createBacLuong(bacLuong);
    }

    // 4️⃣ Cập nhật tên bậc lương
    @PutMapping("/{id}")
    public ResponseEntity<BacLuong> updateBacLuong(@PathVariable int id, @RequestBody BacLuong updatedBacLuong) {
        BacLuong result = bacLuongService.updateBacLuong(id, updatedBacLuong);
        return (result != null) ? ResponseEntity.ok(result) : ResponseEntity.notFound().build();
    }

    // 5️⃣ Xóa bậc lương theo ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBacLuong(@PathVariable int id) {
        return bacLuongService.deleteBacLuong(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
