package com.example.hethongquanly.repository;

import com.example.hethongquanly.model.NhanVien;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NhanVienRepository extends JpaRepository<NhanVien, Integer> {
    @Query(value = "SELECT COUNT(*) FROM nhan_vien WHERE PB_ID = :pbId", nativeQuery = true)
    long countByPhongBanId(@Param("pbId") int pbId);

    @Query("SELECT nv FROM NhanVien nv WHERE LOWER(nv.NV_EMAIL) = LOWER(:email)")
    Optional<NhanVien> findByNV_EMAIL(@Param("email") String NV_EMAIL);

    @Query("SELECT nv FROM NhanVien nv WHERE nv.NV_SDT = :sdt")
    Optional<NhanVien> findByNV_SDT(@Param("sdt") String NV_SDT);
}