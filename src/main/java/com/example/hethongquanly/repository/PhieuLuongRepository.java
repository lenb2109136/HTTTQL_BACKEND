package com.example.hethongquanly.repository;

import com.example.hethongquanly.model.PhieuLuong;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PhieuLuongRepository extends JpaRepository<PhieuLuong, Integer> {
//    List<PhieuLuong> findByNvId_NV_ID(int nvId);
}