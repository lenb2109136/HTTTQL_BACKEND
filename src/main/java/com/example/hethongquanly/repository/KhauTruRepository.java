package com.example.hethongquanly.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.hethongquanly.model.KhauTru;

@Repository
public interface KhauTruRepository extends JpaRepository<KhauTru, Integer>{
	
}
