package com.example.hethongquanly.repository;

import com.example.hethongquanly.model.ChiTietBacLuong;
import com.example.hethongquanly.model.BacLuong;
import com.example.hethongquanly.model.NhanVien;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChiTietBacLuongRepository extends JpaRepository<ChiTietBacLuong, Integer> {
    @Query("SELECT c FROM ChiTietBacLuong c WHERE c.nhanVien.NV_ID = :nhanVienId")
    List<ChiTietBacLuong> findByNhanVienId(@Param("nhanVienId") int nhanVienId);

    // Tìm bậc lương mới nhất của nhân viên
    @Query("SELECT c FROM ChiTietBacLuong c WHERE c.nhanVien.NV_ID = :nhanVienId " +
            "ORDER BY c.ngayApDung DESC LIMIT 1")
    Optional<ChiTietBacLuong> findLatestByNhanVienId(@Param("nhanVienId") int nhanVienId);

    // Kiểm tra bản ghi tồn tại dựa trên NV_ID và BAC_ID
    @Query("SELECT c FROM ChiTietBacLuong c WHERE c.nhanVien.NV_ID = :nhanVienId AND c.BAC_ID.id = :bacLuongId")
    Optional<ChiTietBacLuong> findByNV_IDAndBAC_ID(@Param("nhanVienId") int nhanVienId, @Param("bacLuongId") int bacLuongId);
}