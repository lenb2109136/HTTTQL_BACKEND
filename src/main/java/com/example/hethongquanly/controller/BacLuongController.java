package com.example.hethongquanly.controller;


import com.example.hethongquanly.model.BacLuong;
import com.example.hethongquanly.service.BacLuongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/bac-luong")
public class BacLuongController {

    @Autowired
    private BacLuongService bacLuongService;

    @GetMapping
    public ResponseEntity<List<BacLuong>> getAllBacLuong() {
        List<BacLuong> bacLuongList = bacLuongService.getAllBacLuong();
        return ResponseEntity.ok(bacLuongList);
    }

    @PostMapping("/{ngachId}")
    public ResponseEntity<Map<String, Object>> addBacLuong(@PathVariable Integer ngachId, @RequestBody BacLuong bacLuong) {
        Map<String, Object> response = new HashMap<>();
        try {
            // Thêm bậc lương (ngay sẽ tự động được đặt)
            BacLuong savedBacLuong = bacLuongService.addBacLuong(ngachId, bacLuong);

            response.put("message", "Thêm bậc lương thành công");
            response.put("data", savedBacLuong);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
    @GetMapping("/latest")
    public ResponseEntity<List<BacLuong>> getLatestBacLuongByNgachIdAndBacTen() {
        List<BacLuong> latestBacLuongs = bacLuongService.getLatestBacLuongByNgachIdAndBacTen();
        return ResponseEntity.ok(latestBacLuongs);
    }

    @GetMapping("/ngach/{ngachId}")
    public ResponseEntity<List<BacLuong>> getBacLuongByNgachId(@PathVariable Integer ngachId) {
        List<BacLuong> bacLuongs = bacLuongService.getBacLuongByNgachId(ngachId);
        if (bacLuongs == null || bacLuongs.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(bacLuongs);
    }
    @GetMapping("/ngach/{ngachId}/latest")
    public ResponseEntity<List<BacLuong>> getBacLuongByNgach(@PathVariable Integer ngachId) {
        List<BacLuong> result = bacLuongService.getLatestBacLuongByNgachId(ngachId);
        return ResponseEntity.ok(result);
    }
}

