package com.example.hethongquanly.repository;

import com.example.hethongquanly.model.UngLuong;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface UngLuongRepository extends JpaRepository<UngLuong, Integer> {
    @Modifying
    @Query("DELETE FROM UngLuong u WHERE u.NV_ID = :nvId")
    void deleteByNvId(@Param("nvId") int nvId);

    @Query("SELECT u FROM UngLuong u WHERE u.NV_ID = :nvId AND u.UL_NGAYUL >= :startDate AND u.UL_NGAYUL <= :endDate AND u.UL_TRANGTHAI = 'Đã duyệt'")
    List<UngLuong> getUngLuong(@Param("nvId") int nvId, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    // Thay thế findByUL_NGAYULBetween bằng @Query
    @Query("SELECT u FROM UngLuong u WHERE u.UL_NGAYUL BETWEEN :startDate AND :endDate")
    List<UngLuong> findByDateRange(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
}