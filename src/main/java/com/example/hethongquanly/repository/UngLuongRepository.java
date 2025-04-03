package com.example.hethongquanly.repository;

import com.example.hethongquanly.model.UngLuong;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

public interface UngLuongRepository extends JpaRepository<UngLuong, Integer> {
    @Modifying
    @Query("DELETE FROM UngLuong u WHERE u.NV_ID = :nvId")
    void deleteByNvId(@Param("nvId") int nvId);
    @Query(value = "SELECT * FROM ung_luong WHERE NV_ID=:nvid AND UL_NGAYUL>=:nbd AND UL_NGAYUL<=:nkt AND UL_TRANGTHAI='Đã duyệt';",nativeQuery = true)
    public List<UngLuong> getUngLuong(@RequestParam("nvid") int nvid, @RequestParam("nbd") LocalDate nbd, @RequestParam("nkt") LocalDate nkt);
}