package com.example.hethongquanly.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.hethongquanly.model.UngLuong;

@Repository
public interface UngLuongRepository extends JpaRepository<UngLuong, Integer>{
	@Query(value = "SELECT * FROM ung_luong WHERE NV_ID=:nvid AND UL_NGAYUL>=:nbd AND UL_NGAYUL<=:nkt AND UL_TRANGTHAI='Đã duyệt';",nativeQuery = true)
	public List<UngLuong> getUngLuong(@RequestParam("nvid") int nvid, @RequestParam("nbd") LocalDate nbd, @RequestParam("nkt") LocalDate nkt);
}
