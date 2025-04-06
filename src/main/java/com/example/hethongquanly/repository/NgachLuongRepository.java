package com.example.hethongquanly.repository;

import com.example.hethongquanly.model.NgachLuong;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NgachLuongRepository extends JpaRepository<NgachLuong, Integer> {
    @Query("SELECT n FROM NgachLuong n WHERE n.ngayApDung = (SELECT MAX(n2.ngayApDung) FROM NgachLuong n2 WHERE n2.ten = n.ten)")
    List<NgachLuong> findAllDistinctLatest();
//    @Query("SELECT nl.ngachTen, MAX(nl.ngay) as ngay, nl.ngachLuongCoSo " +
//            "FROM NgachLuong nl " +
//            "GROUP BY nl.ngachTen, nl.ngachLuongCoSo " +
//            "ORDER BY ngay DESC")
//    List<Object[]> findUniqueNgachLuongByLatestDate();
}
