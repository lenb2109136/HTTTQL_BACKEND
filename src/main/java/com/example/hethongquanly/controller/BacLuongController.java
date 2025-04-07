package com.example.hethongquanly.controller;


import com.example.hethongquanly.model.BacLuong;
import com.example.hethongquanly.model.NgachLuong;
import com.example.hethongquanly.repository.BacLuongRepository;
import com.example.hethongquanly.repository.NgachLuongRepository;
import com.example.hethongquanly.service.BacLuongService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/bac-luong")
public class BacLuongController {

    @Autowired
    private BacLuongService bacLuongService;
    @Autowired
    private NgachLuongRepository ngachLuongRepository;
    @Autowired
    private BacLuongRepository bacLuongRepository;

    @GetMapping
    public ResponseEntity<List<BacLuong>> getAllBacLuong(@RequestParam("ten") String ten) {
        List<BacLuong> bacLuongList = bacLuongService.getAllBacLuong();
        bacLuongList=bacLuongList.stream().filter((d)->{
            return d.getTen().equals(ten);
        }).collect(Collectors.toList());
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
    @PostMapping("/save")
    public boolean edit(@RequestBody Map<Object,Object> map){
        String ten =(String)map.get("ten");
        float t = Float.parseFloat((String) map.get("luongCoSo"));
        Integer nghachId = Integer.parseInt((String) map.get("ngachId"));
        BacLuong b= new BacLuong();
        NgachLuong n= ngachLuongRepository.findById(nghachId).orElseThrow(()-> new EntityNotFoundException());
        b.setNgachLuong(n);
        b.setTen(ten);
        b.setHeSo(t);
        b.setNgayApDung(new Date());
        bacLuongRepository.save(b);
        return true;

    }
}

