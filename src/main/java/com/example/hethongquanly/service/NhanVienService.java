package com.example.hethongquanly.service;

import com.example.hethongquanly.model.NhanVien;
import com.example.hethongquanly.model.PhongBan;
import com.example.hethongquanly.repository.NhanVienRepository;
import com.example.hethongquanly.repository.PhongBanRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NhanVienService {
    private static final Logger logger = LoggerFactory.getLogger(NhanVienService.class);

    @Autowired
    private NhanVienRepository nhanVienRepository;

    @Autowired
    private PhongBanRepository phongBanRepository;

    public List<NhanVien> getAllNhanVien() {
        return nhanVienRepository.findAll();
    }

    public NhanVien getNhanVienById(int id) {
        return nhanVienRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Nhân viên không tồn tại với ID: " + id));
    }

    public NhanVien createNhanVien(NhanVien nhanVien) {
        logger.info("Dữ liệu nhân viên nhận được: {}", nhanVien);
        if (nhanVien.getPbIdTemp() != null) {
            PhongBan phongBan = phongBanRepository.findById(nhanVien.getPbIdTemp())
                .orElseThrow(() -> new RuntimeException("Phòng ban không tồn tại với ID: " + nhanVien.getPbIdTemp()));
            nhanVien.setPB_ID(phongBan);
        } else {
            throw new RuntimeException("Phòng ban là bắt buộc");
        }
        return nhanVienRepository.save(nhanVien);
    }

    public NhanVien changePassword(int id, String oldPassword, String newPassword) {
        NhanVien nhanVien = nhanVienRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Nhân viên không tồn tại với ID: " + id));
        
        if (!nhanVien.getNV_PASSWORD().equals(oldPassword)) {
            throw new RuntimeException("Mật khẩu cũ không đúng");
        }
        
        nhanVien.setNV_PASSWORD(newPassword);
        return nhanVienRepository.save(nhanVien);
    }

    public NhanVien updateNhanVien(int id, NhanVien nhanVienDetails) {
        logger.info("Dữ liệu nhân viên cập nhật nhận được: {}", nhanVienDetails);
        NhanVien nhanVien = nhanVienRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Nhân viên không tồn tại với ID: " + id));

        // Chỉ cập nhật các trường không null từ nhanVienDetails
        if (nhanVienDetails.getNV_HOTEN() != null) {
            nhanVien.setNV_HOTEN(nhanVienDetails.getNV_HOTEN());
        }
        if (nhanVienDetails.getNV_NGAYSINH() != null) {
            nhanVien.setNV_NGAYSINH(nhanVienDetails.getNV_NGAYSINH());
        }
        if (nhanVienDetails.getNV_GIOITINH() != null) {
            nhanVien.setNV_GIOITINH(nhanVienDetails.getNV_GIOITINH());
        }
        if (nhanVienDetails.getNV_EMAIL() != null) {
            nhanVien.setNV_EMAIL(nhanVienDetails.getNV_EMAIL());
        }
        if (nhanVienDetails.getNV_SDT() != null) {
            nhanVien.setNV_SDT(nhanVienDetails.getNV_SDT());
        }
        if (nhanVienDetails.getNV_USERNAME() != null) {
            nhanVien.setNV_USERNAME(nhanVienDetails.getNV_USERNAME());
        }
        if (nhanVienDetails.getNV_PASSWORD() != null) {
            nhanVien.setNV_PASSWORD(nhanVienDetails.getNV_PASSWORD());
        }
        if (nhanVienDetails.getNV_DIACHI() != null) {
            nhanVien.setNV_DIACHI(nhanVienDetails.getNV_DIACHI());
        }
        if (nhanVienDetails.getPbIdTemp() != null) {
            PhongBan phongBan = phongBanRepository.findById(nhanVienDetails.getPbIdTemp())
                .orElseThrow(() -> new RuntimeException("Phòng ban không tồn tại với ID: " + nhanVienDetails.getPbIdTemp()));
            nhanVien.setPB_ID(phongBan);
        }

        return nhanVienRepository.save(nhanVien);
    }

    public void deleteNhanVien(int id) {
        NhanVien nhanVien = nhanVienRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Nhân viên không tồn tại với ID: " + id));
        nhanVienRepository.delete(nhanVien);
    }
}