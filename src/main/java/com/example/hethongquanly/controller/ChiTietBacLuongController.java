package com.example.hethongquanly.controller;


import com.example.hethongquanly.model.ChiTietBacLuong;
import com.example.hethongquanly.service.ChiTietBacLuongService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/api/chi-tiet-bac-luong")
@RequiredArgsConstructor
public class ChiTietBacLuongController {

    @Autowired
    private ChiTietBacLuongService chiTietBacLuongService;
    @PostMapping
    public ResponseEntity<ChiTietBacLuong> themChiTietBacLuong(
            @RequestParam int nhanVienId,
            @RequestParam int bacLuongId) {
        ChiTietBacLuong newChiTietBacLuong = chiTietBacLuongService.themChiTietBacLuong(
                nhanVienId,
                bacLuongId);
        return new ResponseEntity<>(newChiTietBacLuong, HttpStatus.CREATED);
    }

    @GetMapping()
    public List<ChiTietBacLuong> getAll() {
        return chiTietBacLuongService.getAll();
    }

    @GetMapping("/nhan-vien/{nhanVienId}")
    public ResponseEntity<List<ChiTietBacLuong>> getByNhanVienId(@PathVariable int nhanVienId) {
        List<ChiTietBacLuong> danhSach = chiTietBacLuongService.findByNhanVienId(nhanVienId);
        return ResponseEntity.ok(danhSach);
    }
    @GetMapping("/nhan-vien/{nhanVienId}/latest")
    public ResponseEntity<ChiTietBacLuong> getLatestByNhanVienId(@PathVariable int nhanVienId) {
        ChiTietBacLuong latest = chiTietBacLuongService.findLatestByNhanVienId(nhanVienId);
        return ResponseEntity.ok(latest);
    }


}
