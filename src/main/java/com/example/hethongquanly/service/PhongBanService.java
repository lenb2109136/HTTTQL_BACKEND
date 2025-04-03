package com.example.hethongquanly.service;

import com.example.hethongquanly.model.PhongBan;
import com.example.hethongquanly.repository.NhanVienRepository;
import com.example.hethongquanly.repository.PhongBanRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PhongBanService {
    private static final Logger logger = LoggerFactory.getLogger(PhongBanService.class);

    @Autowired
    private PhongBanRepository phongBanRepository;

    @Autowired
    private NhanVienRepository nhanVienRepository;

    public List<PhongBan> getAllPhongBan() {
        logger.info("Lấy danh sách phòng ban với số nhân sự");
        List<PhongBan> phongBans = phongBanRepository.findAll();
        for (PhongBan pb : phongBans) {
            long soNhanSu = nhanVienRepository.countByPhongBanId(pb.getPB_ID());
            logger.info("Phòng ban ID: {}, Số nhân sự: {}", pb.getPB_ID(), soNhanSu);
            pb.setSoNhanSu(soNhanSu);
        }
        return phongBans;
    }

    public PhongBan getPhongBanById(int id) {
        logger.info("Lấy phòng ban với ID: {}", id);
        PhongBan phongBan = phongBanRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Phòng ban không tồn tại với ID: " + id));
        long soNhanSu = nhanVienRepository.countByPhongBanId(id);
        logger.info("Phòng ban ID: {}, Số nhân sự: {}", id, soNhanSu);
        phongBan.setSoNhanSu(soNhanSu);
        return phongBan;
    }

    public PhongBan createPhongBan(PhongBan phongBan) {
        logger.info("Tạo phòng ban mới: {}", phongBan);
        if (phongBan.getPB_TEN() == null || phongBan.getPB_TEN().trim().isEmpty()) {
            throw new RuntimeException("Tên phòng ban là bắt buộc");
        }
        PhongBan savedPhongBan = phongBanRepository.save(phongBan);
        savedPhongBan.setSoNhanSu(0); // Phòng ban mới tạo sẽ có 0 nhân sự
        return savedPhongBan;
    }

    public PhongBan updatePhongBan(int id, PhongBan phongBanDetails) {
        logger.info("Cập nhật phòng ban ID: {} với dữ liệu: {}", id, phongBanDetails);
        PhongBan phongBan = phongBanRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Phòng ban không tồn tại với ID: " + id));

        if (phongBanDetails.getPB_TEN() == null || phongBanDetails.getPB_TEN().trim().isEmpty()) {
            throw new RuntimeException("Tên phòng ban là bắt buộc");
        }
        phongBan.setPB_TEN(phongBanDetails.getPB_TEN());
        PhongBan updatedPhongBan = phongBanRepository.save(phongBan);
        long soNhanSu = nhanVienRepository.countByPhongBanId(id);
        updatedPhongBan.setSoNhanSu(soNhanSu);
        return updatedPhongBan;
    }

    public void deletePhongBan(int id) {
        logger.info("Xóa phòng ban với ID: {}", id);
        PhongBan phongBan = phongBanRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Phòng ban không tồn tại với ID: " + id));
        phongBanRepository.delete(phongBan);
    }
}