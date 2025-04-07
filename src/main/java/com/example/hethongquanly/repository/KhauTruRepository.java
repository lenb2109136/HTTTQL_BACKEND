package com.example.hethongquanly.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.hethongquanly.model.KhauTru;

@Repository
public interface KhauTruRepository extends JpaRepository<KhauTru, Integer>{
	@Query(value = "SELECT k.*, \r\n"
			+ "       COALESCE(SUM(c.ID), 0) AS tong_id \r\n"
			+ "FROM khau_tru k \r\n"
			+ "LEFT JOIN chi_tiet_khau_tru c \r\n"
			+ "    ON c.KT_ID = k.KT_ID \r\n"
			+ "    AND c.CHI_TIET_KY_NGAYAPDUNG >= :nbd\r\n"
			+ "    AND c.CHI_TIET_KY_NGAYAPDUNG <= :nkt\r\n"
			+ "GROUP BY k.KT_ID HAVING k.KT_THUONGNIEN = 1;\r\n"
			+ "",nativeQuery = true)
	public List<Map<Object, Object>> getThongKe (LocalDateTime nbd, LocalDateTime nkt);
	@Query(value = "SELECT k.*, \r\n"
			+ "       COALESCE(SUM(c.ID), 0) AS tong_id \r\n"
			+ "FROM khau_tru k \r\n"
			+ "LEFT JOIN chi_tiet_khau_tru c \r\n"
			+ "    ON c.KT_ID = k.KT_ID \r\n"
			+ "    AND c.CHI_TIET_KY_NGAYAPDUNG >= :nbd\r\n"
			+ "    AND c.CHI_TIET_KY_NGAYAPDUNG <= :nkt \r\n"
			+ "GROUP BY k.KT_ID HAVING k.KT_THUONGNIEN = 0; \r\n"
			+ "",nativeQuery = true)
	public List<Map<Object , Object>> getThongKenotthuongnien (LocalDateTime nbd, LocalDateTime nkt);
}
