package com.example.hethongquanly.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.hethongquanly.model.UngLuong;

@Repository
public interface UngLuongRepository extends JpaRepository<UngLuong, Integer>{

}
