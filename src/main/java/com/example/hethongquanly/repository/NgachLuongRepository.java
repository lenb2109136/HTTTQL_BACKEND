package com.example.hethongquanly.repository;

import com.example.hethongquanly.model.NgachLuong;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NgachLuongRepository extends JpaRepository<NgachLuong, Integer> {
//    @Query("SELECT n FROM NgachLuong n WHERE n.ngayApDung = (SELECT MAX(n2.ngayApDung) FROM NgachLuong n2 WHERE n2.ten = n.ten)")
//    List<NgachLuong> findAllDistinctLatest();
    @Query("SELECT DISTINCT n FROM NgachLuong n WHERE n.ngayApDung = (SELECT MAX(n2.ngayApDung) FROM NgachLuong n2 WHERE n2.ten = n.ten)")
    List<NgachLuong> findAllDistinctLatest();

    Optional<NgachLuong> findByTen(String ten);




}
