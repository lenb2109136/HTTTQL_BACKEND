package com.example.hethongquanly.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.hethongquanly.model.NgachLuong;

import java.util.List;

@Repository
public interface NgachLuongRepository extends JpaRepository<NgachLuong, Integer>{
//    @Query("SELECT DISTINCT n.NGACH_TEN FROM NgachLuong n")
//    List<String> findDistinctNgachTen();
    @Query("SELECT nl FROM NgachLuong nl WHERE nl.NGACH_ID IN " +
            "(SELECT MAX(n.NGACH_ID) FROM NgachLuong n GROUP BY n.NGACH_TEN) " +
            "ORDER BY nl.NGACH_ID DESC")
    List<NgachLuong> findDistinctNgachLuong();
}
