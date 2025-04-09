package com.example.hethongquanly.controller;

import com.example.hethongquanly.model.PhongBan;
import com.example.hethongquanly.model.Response;
import com.example.hethongquanly.repository.PhongBanRepository;
import com.example.hethongquanly.service.PhongBanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/phongban")
@CrossOrigin(origins = "http://localhost:3000")
public class PhongBanController {

    @Autowired
    private PhongBanService phongBanService;
    
    @Autowired
    private PhongBanRepository phongBanRepository;


//    private PhongBanRepository phongBanRepository;

    @GetMapping
    public ResponseEntity<?> getAllPhongBan() {
        try {
            List<PhongBan> phongBanList = phongBanService.getAllPhongBan();
            return ResponseEntity.ok(phongBanList);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Lỗi khi lấy danh sách phòng ban: " + e.getMessage());
        }
    }
    
//    @GetMapping("/getPhongBan")
//    public  ResponseEntity<Response> getPhongBan(){
//        return new ResponseEntity<Response>(new Response(HttpStatus.OK,"",phongBanRepository.findAll()),HttpStatus.OK);
//    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPhongBanById(@PathVariable int id) {
        try {
            PhongBan phongBan = phongBanService.getPhongBanById(id);
            return ResponseEntity.ok(phongBan);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Phòng ban không tồn tại: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Lỗi server: " + e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> createPhongBan(@RequestBody PhongBan phongBan) {
        try {
            PhongBan createdPhongBan = phongBanService.createPhongBan(phongBan);
            return ResponseEntity.ok(createdPhongBan);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Lỗi khi tạo phòng ban: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Lỗi server: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePhongBan(@PathVariable int id, @RequestBody PhongBan phongBanDetails) {
        try {
            PhongBan updatedPhongBan = phongBanService.updatePhongBan(id, phongBanDetails);
            return ResponseEntity.ok(updatedPhongBan);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Phòng ban không tồn tại: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Lỗi server: " + e.getMessage());
        }
    }
    @GetMapping("/getPhongBan")
    public  ResponseEntity<Response> getPhongBan(){
        return new ResponseEntity<Response>(new Response(HttpStatus.OK,"",phongBanRepository.findAll()),HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePhongBan(@PathVariable int id) {
        try {
            phongBanService.deletePhongBan(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Phòng ban không tồn tại: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Lỗi server: " + e.getMessage());
        }
    }
}