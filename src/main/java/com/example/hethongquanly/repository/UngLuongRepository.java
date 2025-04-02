package com.example.hethongquanly.repository;

import com.example.hethongquanly.model.UngLuong;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UngLuongRepository extends JpaRepository<UngLuong, Integer> {
    @Modifying
    @Query("DELETE FROM UngLuong u WHERE u.NV_ID = :nvId")
    void deleteByNvId(@Param("nvId") int nvId);
}