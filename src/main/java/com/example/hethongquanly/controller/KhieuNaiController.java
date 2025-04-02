package com.example.hethongquanly.controller;

import com.example.hethongquanly.model.KhieuNai;
import com.example.hethongquanly.model.NhanVien;
import com.example.hethongquanly.repository.NhanVienRepository;
import com.example.hethongquanly.service.KhieuNaiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/khieu-nai")
@CrossOrigin(origins = "http://localhost:3000") // Cho phép frontend truy cập
public class KhieuNaiController {

    @Autowired
    private KhieuNaiService khieuNaiService;

    @Autowired
    private NhanVienRepository nhanVienRepository;

    @GetMapping
    public List<KhieuNai> getAllKhieuNai() {
        return khieuNaiService.getAllKhieuNai();
    }

    @GetMapping("/{id}")
    public ResponseEntity<KhieuNai> getKhieuNaiById(@PathVariable int id) {
        Optional<KhieuNai> khieuNai = khieuNaiService.getKhieuNaiById(id);
        return khieuNai.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public KhieuNai createKhieuNai(@RequestBody KhieuNai khieuNai) {
        // Ánh xạ nv_ID từ số sang đối tượng NhanVien
        if (khieuNai.getNv_ID() != null && khieuNai.getNv_ID().getNV_ID() > 0) {
            NhanVien nhanVien = nhanVienRepository.findById(khieuNai.getNv_ID().getNV_ID())
                    .orElseThrow(() -> new RuntimeException("Nhân viên không tồn tại: " + khieuNai.getNv_ID().getNV_ID()));
            khieuNai.setNv_ID(nhanVien);
        }
        return khieuNaiService.saveKhieuNai(khieuNai);
    }

    @PutMapping("/{id}")
    	public ResponseEntity<KhieuNai> updateKhieuNai(@PathVariable int id, @RequestBody KhieuNai khieuNaiDetails) {
        Optional<KhieuNai> optionalKhieuNai = khieuNaiService.getKhieuNaiById(id);
        if (optionalKhieuNai.isPresent()) {
            KhieuNai khieuNai = optionalKhieuNai.get();
            // Chỉ cập nhật nội dung và ngày tháng
            khieuNai.setKn_NOIDUNG(khieuNaiDetails.getKn_NOIDUNG());
            khieuNai.setKn_NGAYKN(khieuNaiDetails.getKn_NGAYKN());
            // Giữ nguyên nv_ID và kn_ID
            return ResponseEntity.ok(khieuNaiService.saveKhieuNai(khieuNai));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteKhieuNai(@PathVariable int id) {
        if (khieuNaiService.getKhieuNaiById(id).isPresent()) {
            khieuNaiService.deleteKhieuNai(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}