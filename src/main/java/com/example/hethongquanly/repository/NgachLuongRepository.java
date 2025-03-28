package com.example.hethongquanly.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.hethongquanly.model.NgachLuong;

@Repository
public interface NgachLuongRepository extends JpaRepository<NgachLuong, Integer>{

}
