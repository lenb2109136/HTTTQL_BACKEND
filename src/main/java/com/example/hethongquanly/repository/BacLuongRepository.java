package com.example.hethongquanly.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.hethongquanly.model.BacLuong;

@Repository
public interface BacLuongRepository extends JpaRepository<BacLuong, Integer> {
}
