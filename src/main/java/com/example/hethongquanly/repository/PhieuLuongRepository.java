package com.example.hethongquanly.repository;

import com.example.hethongquanly.model.PhieuLuong;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface PhieuLuongRepository extends JpaRepository<PhieuLuong, Integer> {
    @Query("SELECT p FROM PhieuLuong p WHERE p.nvId.NV_ID = :nvId")
    List<PhieuLuong> findByNvIdNV_ID(@Param("nvId") int nvId);

    // Thêm phương thức để lọc theo ngày
    List<PhieuLuong> findByNgayPhatBetween(Date startDate, Date endDate);
}