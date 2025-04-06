package com.example.hethongquanly.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.hethongquanly.model.BacLuong;

import java.util.List;

@Repository
public interface BacLuongRepository extends JpaRepository<BacLuong, Integer> {
    @Query("SELECT bl FROM BacLuong bl WHERE bl.id = (SELECT MAX(b.id) FROM BacLuong b WHERE b.ngachLuong.id = bl.ngachLuong.id AND b.ten = bl.ten)")
    List<BacLuong> findLatestBacLuongByNgachIdAndBacTen();

//    @Query("SELECT b FROM BacLuong b WHERE b.ngachLuong.id = :ngachId AND b.ngayApDung = (SELECT MAX(b2.ngayApDung) FROM BacLuong b2 WHERE b2.ngachLuong.id = :ngachId)")
//    List<BacLuong> findLatestBacLuongByNgachId(@Param("ngachId") Integer ngachId);


    @Query("SELECT b FROM BacLuong b WHERE b.ngachLuong.id = :ngachId AND b.ngayApDung = (SELECT MAX(b2.ngayApDung) FROM BacLuong b2 WHERE b2.ten = b.ten AND b2.ngachLuong.id = :ngachId)")
    List<BacLuong> findLatestBacLuongByNgachId(@Param("ngachId") Integer ngachId);


}
