package com.example.hethongquanly.controller;

import com.example.hethongquanly.model.NgachLuong;
import com.example.hethongquanly.service.NgachLuongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/ngach-luong")
public class NgachLuongController {

    @Autowired
    private NgachLuongService ngachLuongService;

    @GetMapping
    public List<NgachLuong> getAllNgachLuong() {
        return ngachLuongService.getAllNgachLuong();
    }
    @GetMapping("/latest")
    public List<NgachLuong> getAllNgachLuongLatest() {
        return ngachLuongService.getAllNgachLuongLatest();
    }

    @GetMapping("/{id}")
    public ResponseEntity<NgachLuong> getNgachLuongById(@PathVariable Integer id) {
        NgachLuong ngachLuong = ngachLuongService.getNgachLuongById(id);
        return ngachLuong != null ? ResponseEntity.ok(ngachLuong) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public NgachLuong createNgachLuong(@RequestBody NgachLuong ngachLuong) {
        return ngachLuongService.createNgachLuong(ngachLuong);
    }

    @PutMapping("/{id}")
    public ResponseEntity<NgachLuong> updateNgachLuong(@PathVariable Integer id, @RequestBody NgachLuong ngachLuong) {
        NgachLuong updatedNgachLuong = ngachLuongService.updateNgachLuong(id, ngachLuong);
        return updatedNgachLuong != null ? ResponseEntity.ok(updatedNgachLuong) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNgachLuong(@PathVariable Integer id) {
        ngachLuongService.deleteNgachLuong(id);
        return ResponseEntity.noContent().build();
    }
}