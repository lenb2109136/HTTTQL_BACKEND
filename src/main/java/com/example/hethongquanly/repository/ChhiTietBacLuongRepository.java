package com.example.hethongquanly.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.hethongquanly.embeded.ChiTietBacLuong;

@Repository
public interface ChhiTietBacLuongRepository extends JpaRepository<com.example.hethongquanly.model.ChiTietBacLuong, ChiTietBacLuong>{

}
