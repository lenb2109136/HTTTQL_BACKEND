package com.example.hethongquanly.repository;

import com.example.hethongquanly.model.ChiTietKhauTru;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ChiTietKhauTruRepository extends JpaRepository<ChiTietKhauTru, Integer> {

}