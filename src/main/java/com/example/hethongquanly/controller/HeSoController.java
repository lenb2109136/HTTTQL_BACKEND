package com.example.hethongquanly.controller;

import com.example.hethongquanly.dto.HeSoRequest;
import com.example.hethongquanly.model.BacLuong;
import com.example.hethongquanly.model.HeSo;
import com.example.hethongquanly.model.NgachLuong;
import com.example.hethongquanly.embeded.HeSoId;
import com.example.hethongquanly.repository.BacLuongRepository;
import com.example.hethongquanly.repository.HeSoRepository;
import com.example.hethongquanly.repository.NgachLuongRepository;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/heso")
public class HeSoController {

    private final HeSoRepository heSoRepository;
    private final BacLuongRepository bacLuongRepository;
    private final NgachLuongRepository ngachLuongRepository;

    public HeSoController(HeSoRepository heSoRepository, BacLuongRepository bacLuongRepository, NgachLuongRepository ngachLuongRepository) {
        this.heSoRepository = heSoRepository;
        this.bacLuongRepository = bacLuongRepository;
        this.ngachLuongRepository = ngachLuongRepository;
    }

    // 1️⃣ Lấy danh sách hệ số
    @GetMapping
    public List<HeSo> getAllHeSo() {
        return heSoRepository.findAll();
    }

    // 2️⃣ Tìm hệ số theo Bac_ID và Ngach_ID
    @GetMapping("/{bacId}/{ngachId}")
    public ResponseEntity<HeSo> getHeSoById(@PathVariable Integer bacId, @PathVariable Integer ngachId, @PathVariable LocalDate NGAYAPDUNG) {
        HeSoId heSoId = new HeSoId(bacId, ngachId, NGAYAPDUNG);
        return heSoRepository.findById(heSoId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 3️⃣ Thêm hệ số mới
//    @PostMapping
//    public ResponseEntity<String> createHeSo(@RequestBody HeSoRequest request) {
//        BacLuong bacLuong = bacLuongRepository.findById(request.getBacId())
//                .orElseThrow(() -> new RuntimeException("BacLuong không tồn tại"));
//        NgachLuong ngachLuong = ngachLuongRepository.findById(request.getNgachId())
//                .orElseThrow(() -> new RuntimeException("NgachLuong không tồn tại"));
//
//        HeSoId heSoId = new HeSoId(request.getBacId(), request.getNgachId());
//        if (heSoRepository.existsById(heSoId)) {
//            return ResponseEntity.badRequest().body("Hệ số đã tồn tại");
//        }
//
//        HeSo heSo = new HeSo();
//        heSo.setId(heSoId);
//        heSo.setBAC_ID(bacLuong);
//        heSo.setNGACH_ID(ngachLuong);
//        heSo.setNGAYAPDUNG(request.getNgayApDung());
//        heSo.setHS_HESO(request.getHsHeSo());
//
//        heSoRepository.save(heSo);
//        return ResponseEntity.ok("Thêm hệ số thành công");
//    }
    @PostMapping
    public ResponseEntity<String> createHeSo(@RequestBody HeSoRequest request) {

        if (request.getBac_Id() == null || request.getNgach_Id() == null) {
            return ResponseEntity.badRequest().body("Bac ID hoặc Ngach ID không được null");
        }

        Optional<BacLuong> bacLuongOpt = bacLuongRepository.findById(request.getBac_Id());
        Optional<NgachLuong> ngachLuongOpt = ngachLuongRepository.findById(request.getNgach_Id());

        if (bacLuongOpt.isEmpty() || ngachLuongOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("BacLuong hoặc NgachLuong không tồn tại");
        }

        HeSoId heSoId = new HeSoId(request.getBac_Id(), request.getNgach_Id(),request.getNgayApDung());
        HeSo heSo = new HeSo(heSoId,  request.getHs_HeSo());
        heSo.setBAC_ID(bacLuongOpt.get());
        heSo.setNGACH_ID(ngachLuongOpt.get());
//        heSo.setNGAYAPDUNG(request.getNgayApDung());
        heSo.setHS_HESO(request.getHs_HeSo());

        heSoRepository.save(heSo);

        return ResponseEntity.ok("Hệ số được thêm thành công");
    }

    // 4️⃣ Cập nhật hệ số (chỉ cập nhật HS_HESO)
    @PutMapping("/{bacId}/{ngachId}")
    public ResponseEntity<String> updateHeSo(@PathVariable Integer bacId, @PathVariable Integer ngachId, @RequestBody Float newHsHeSo, @PathVariable LocalDate NGAYAPDUNG) {
        HeSoId heSoId = new HeSoId(bacId, ngachId, NGAYAPDUNG);
        return heSoRepository.findById(heSoId).map(heSo -> {
            heSo.setHS_HESO(newHsHeSo);
            heSoRepository.save(heSo);
            return ResponseEntity.ok("Cập nhật hệ số thành công");
        }).orElse(ResponseEntity.notFound().build());
    }

    // 5️⃣ Xóa hệ số
    @DeleteMapping("/{bacId}/{ngachId}")
    public ResponseEntity<String> deleteHeSo(@PathVariable Integer bacId, @PathVariable Integer ngachId,@PathVariable LocalDate NGAYAPDUNG) {
        HeSoId heSoId = new HeSoId(bacId, ngachId,NGAYAPDUNG);
        if (heSoRepository.existsById(heSoId)) {
            heSoRepository.deleteById(heSoId);
            return ResponseEntity.ok("Xóa hệ số thành công");
        }
        return ResponseEntity.notFound().build();
    }
}
