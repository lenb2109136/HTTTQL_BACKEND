package com.example.hethongquanly.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.hethongquanly.embeded.ChiTietBacLuong;

@Repository
public interface ChhiTietBacLuongRepository extends JpaRepository<com.example.hethongquanly.model.ChiTietBacLuong, ChiTietBacLuong>{
	@Query(value = "SELECT * FROM chi_tiet_bac_luong WHERE NV_ID=:nvid AND NGAYAPDUNG>=:nbd AND NGAYAPDUNG<=:nkt",nativeQuery = true)
	public List<com.example.hethongquanly.model.ChiTietBacLuong> getChiTietBacLuong(@RequestParam("nvid") int nvid, @RequestParam("nbd") LocalDate nbd, @RequestParam("nkt") LocalDate nkt);

}
