package com.example.hethongquanly.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.hethongquanly.embeded.HeSoId;
import com.example.hethongquanly.model.HeSo;

import java.util.List;
import java.util.Optional;

@Repository
public interface HeSoRepository extends JpaRepository<HeSo, HeSoId>{

}
