package com.example.hethongquanly.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.hethongquanly.model.PhieuLuong;

@Repository
public interface PhieuLuongRepository extends JpaRepository<PhieuLuong, Integer>{

}
