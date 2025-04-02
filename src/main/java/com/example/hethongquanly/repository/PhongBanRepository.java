package com.example.hethongquanly.repository;

import com.example.hethongquanly.model.PhongBan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhongBanRepository extends JpaRepository<PhongBan, Integer> {
}