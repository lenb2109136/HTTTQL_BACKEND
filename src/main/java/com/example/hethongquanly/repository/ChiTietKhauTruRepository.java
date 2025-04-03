package com.example.hethongquanly.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.hethongquanly.embeded.KhauTruId;
import com.example.hethongquanly.model.ChiTietKhauTru;

@Repository
public interface ChiTietKhauTruRepository extends JpaRepository<ChiTietKhauTru, KhauTruId>{
	@Query(value = "SELECT * FROM chi_tiet_khau_tru WHERE NV_ID=:nvid AND CHI_TIET_KY_NGAYAPDUNG>=:nbd AND CHI_TIET_KY_NGAYAPDUNG<=:nkt "
			,nativeQuery = true)
	public List<ChiTietKhauTru> getChiTietKhauTru(@RequestParam("nvid") int nvid, @RequestParam("nbd") LocalDate nbd, @RequestParam("nkt") LocalDate nkt);
}
