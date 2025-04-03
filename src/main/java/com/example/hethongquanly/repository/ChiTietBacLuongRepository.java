package com.example.hethongquanly.repository;

import com.example.hethongquanly.model.ChiTietBacLuong;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

public interface ChiTietBacLuongRepository extends JpaRepository<ChiTietBacLuong, Integer> {
    @Query(value = "SELECT * FROM chi_tiet_bac_luong WHERE NV_ID=:nvid AND NGAYAPDUNG>=:nbd AND NGAYAPDUNG<=:nkt",nativeQuery = true)
    public List<ChiTietBacLuong> getChiTietBacLuong(@RequestParam("nvid") int nvid, @RequestParam("nbd") LocalDate nbd, @RequestParam("nkt") LocalDate nkt);
}