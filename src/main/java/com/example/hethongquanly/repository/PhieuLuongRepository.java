package com.example.hethongquanly.repository;

import com.example.hethongquanly.model.PhieuLuong;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


@Repository
public interface PhieuLuongRepository extends JpaRepository<PhieuLuong, Integer> {

    @Query("SELECT p FROM PhieuLuong p WHERE p.nvId.NV_ID = :nvId")
    List<PhieuLuong> findByNvIdNV_ID(@Param("nvId") int nvId);
}