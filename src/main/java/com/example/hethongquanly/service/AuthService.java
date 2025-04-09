package com.example.hethongquanly.service;

import com.example.hethongquanly.model.NhanVien;
import com.example.hethongquanly.repository.NhanVienRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class AuthService {
    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

    @Autowired
    private NhanVienRepository nhanVienRepository;

    public NhanVien login(String identifier, String password) {
        logger.info("Đăng nhập với identifier: {}", identifier);
        String trimmedIdentifier = identifier != null ? identifier.trim() : "";
        String trimmedPassword = password != null ? password.trim() : "";
        logger.info("Identifier sau trim: {}", trimmedIdentifier);
        logger.info("Password sau trim: {}", trimmedPassword);

        // Kiểm tra identifier trống
        if (trimmedIdentifier.isEmpty()) {
            logger.warn("Email hoặc số điện thoại không được để trống");
            throw new RuntimeException("Email hoặc số điện thoại không được để trống");
        }

        // Trường hợp đặc biệt: Nếu identifier là admin@gmail.com, luôn trả về thành công
        if ("admin@gmail.com".equalsIgnoreCase(trimmedIdentifier)) {
            logger.info("Đăng nhập đặc biệt cho admin@gmail.com - Bỏ qua kiểm tra mật khẩu");
            // Tìm hoặc tạo một đối tượng NhanVien giả định cho admin
            NhanVien admin = nhanVienRepository.findByNV_EMAIL(trimmedIdentifier)
                    .orElseGet(() -> {
                        // Nếu không tìm thấy, tạo mới hoặc xử lý theo logic của bạn
                        NhanVien newAdmin = new NhanVien();
                        newAdmin.setNV_EMAIL("admin@gmail.com");
                        newAdmin.setNV_HOTEN("Admin");
                        // Thêm các trường khác nếu cần
                        return newAdmin;
                    });
            return admin; // Trả về ngay mà không kiểm tra mật khẩu
        }

        // Kiểm tra mật khẩu trống cho các tài khoản khác
        if (trimmedPassword.isEmpty()) {
            logger.warn("Mật khẩu không được để trống cho identifier: {}", trimmedIdentifier);
            throw new RuntimeException("Mật khẩu không được để trống");
        }

        // Tìm nhân viên theo email hoặc số điện thoại cho các tài khoản khác
        NhanVien nhanVien;
        if (trimmedIdentifier.contains("@")) {
            logger.info("Tìm nhân viên bằng email: {}", trimmedIdentifier);
            nhanVien = nhanVienRepository.findByNV_EMAIL(trimmedIdentifier)
                    .orElseThrow(() -> {
                        logger.warn("Không tìm thấy nhân viên với email: {}", trimmedIdentifier);
                        return new RuntimeException("Email không tồn tại");
                    });
        } else {
            logger.info("Tìm nhân viên bằng số điện thoại: {}", trimmedIdentifier);
            nhanVien = nhanVienRepository.findByNV_SDT(trimmedIdentifier)
                    .orElseThrow(() -> {
                        logger.warn("Không tìm thấy nhân viên với số điện thoại: {}", trimmedIdentifier);
                        return new RuntimeException("Số điện thoại không tồn tại");
                    });
        }

        // Kiểm tra mật khẩu cho các tài khoản khác
        if (!trimmedPassword.equals(nhanVien.getNV_PASSWORD())) {
            logger.warn("Mật khẩu không khớp cho identifier: {}", trimmedIdentifier);
            throw new RuntimeException("Mật khẩu không đúng");
        }

        logger.info("Đăng nhập thành công cho nhân viên: {}", nhanVien.getNV_HOTEN());
        return nhanVien;
    }
}