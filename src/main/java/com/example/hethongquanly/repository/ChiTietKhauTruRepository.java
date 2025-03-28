package com.example.hethongquanly.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.hethongquanly.embeded.KhauTruId;
import com.example.hethongquanly.model.ChiTietKhauTru;

@Repository
public interface ChiTietKhauTruRepository extends JpaRepository<ChiTietKhauTru, KhauTruId>{

}
