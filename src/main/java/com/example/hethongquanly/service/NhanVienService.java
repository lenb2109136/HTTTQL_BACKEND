package com.example.hethongquanly.service;


import com.example.hethongquanly.model.*;
import com.example.hethongquanly.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.hethongquanly.embeded.HeSoId;

import jakarta.persistence.EntityNotFoundException;

import java.util.List;

@Service
public class NhanVienService {

    private static final Logger logger = LoggerFactory.getLogger(NhanVienService.class);

		@Autowired
		private UngLuongRepository ungLuongRepository;
		@Autowired
		private ChiTietKhauTruRepository chiTietKhauTruRepository;
		@Autowired
		private ChiTietBacLuongRepository chhiTietBacLuongRepository;

		@Autowired
		private NhanVienRepository nhanVienRepository;
		
		@Autowired
		private ChiTietBacLuongService chiTietBacLuongService;

		public static long getNumberOfDays(LocalDate startDate, LocalDate endDate) {
	        return ChronoUnit.DAYS.between(startDate, endDate) + 1;
	    }
		private int getRandomTangCa() {
		    int[] values = {0, 1500, 2000};
		    return values[ThreadLocalRandom.current().nextInt(values.length)];
		}
		public Map<Object, Object> TinhLuong(int idnv, LocalDate nbd, LocalDate nkt) {
			long songay = ChronoUnit.DAYS.between(nbd, nkt) + 1;
				Map<Object, Object> map= new HashMap<Object, Object>();
				List<UngLuong> ul= ungLuongRepository.getUngLuong(idnv, nbd, nkt);
				List<ChiTietKhauTru> ctkt=chiTietKhauTruRepository.getChiTietKhauTru(idnv, nbd, nkt);
			ChiTietBacLuong latest = chiTietBacLuongService.findLatestByNhanVienId(idnv);
			Float tongungluong=ul.stream().map((u)-> u.getUL_TIEN()).reduce(0f,Float::sum);
			Float luongcoban=latest.getBAC_ID().getHeSo()* latest.getBAC_ID().getNgachLuong().getLuongCoSo();
			luongcoban=(float)(luongcoban/26)*songay;
			Float tongkhautru=ctkt.stream().map((u)-> u.getKhauTru().getKT_SOTIEN()).reduce(0f,Float::sum);
			map.put("luongcoban",luongcoban);
			map.put("luongtangca", getRandomTangCa());
			map.put("tongthunhap", getRandomTangCa()+luongcoban);
			map.put("tongungluong", tongungluong);
			map.put("tongkhautru", tongkhautru);

				map.put("danhsachkhautru", ctkt.stream().map((d)->{
					Map<Object, Object> khautru=new HashMap<Object, Object>();
					khautru.put("khautru", d.getKhauTru());
					khautru.put("tienung", d.getCHI_TIET_KY_NGAYAPDUNG());
					return khautru;
				}));
				map.put("danhsachbacluong",null);
				map.put("danhsachungluong",ul);
				map.put("luongnhan",getRandomTangCa()+luongcoban-tongkhautru-tongungluong);
				return map;
			}


		public List<NhanVien> filterNhanVien(int idPhongBan){
			List<NhanVien> nhanvien=nhanVienRepository.findAll();
			if(idPhongBan!=0) {
				return nhanvien.stream().filter((d)->{
					return d.getPB_ID().getPB_ID()==idPhongBan;
				}).collect(Collectors.toList());
			}
			else {
				return nhanvien;
			}
		}

		public List<Map<Object, Object>> getLuongByBoPhan(int idphongban, LocalDate ngaybd, LocalDate ngaykt) {
		    if (ngaybd == null) {
		        ngaybd = LocalDate.now().withDayOfMonth(1);
		    }
		    if (ngaykt == null) {
		        ngaykt = LocalDate.now();
		    }
		    final LocalDate finalNgaybd = ngaybd;
		    final LocalDate finalNgaykt = ngaykt;

		    List<NhanVien> nv = new ArrayList<>();
		    if (idphongban == 0) {
		        nv = nhanVienRepository.findAll();
		    } else {
		        nv = filterNhanVien(idphongban);
		    }

		    return nv.stream().map((d) -> {
		        Map<Object, Object> m = TinhLuong(d.getNV_ID(), finalNgaybd, finalNgaykt);
		        if (m == null) {
		            m = new HashMap<>();
		            m.put("error", "Lỗi tính lương cho nhân viên: " + d.getNV_ID());
		        }
		        m.put("thongtinnhanvien", d);
		        return m;
		    }).collect(Collectors.toList());
		}





    @Autowired
    private PhongBanRepository phongBanRepository;

    public List<NhanVien> getAllNhanVien() {
        return nhanVienRepository.findAll();
    }

    public NhanVien getNhanVienById(int id) {
        return nhanVienRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Nhân viên không tồn tại với ID: " + id));
    }

    public NhanVien createNhanVien(NhanVien nhanVien) {
        logger.info("Dữ liệu nhân viên nhận được: {}", nhanVien);
        if (nhanVien.getPbIdTemp() != null) {
            PhongBan phongBan = phongBanRepository.findById(nhanVien.getPbIdTemp())
                .orElseThrow(() -> new RuntimeException("Phòng ban không tồn tại với ID: " + nhanVien.getPbIdTemp()));
            nhanVien.setPB_ID(phongBan);
        } else {
            throw new RuntimeException("Phòng ban là bắt buộc");
        }
        return nhanVienRepository.save(nhanVien);
    }

    public NhanVien changePassword(int id, String oldPassword, String newPassword) {
        NhanVien nhanVien = nhanVienRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Nhân viên không tồn tại với ID: " + id));

        if (!nhanVien.getNV_PASSWORD().equals(oldPassword)) {
            throw new RuntimeException("Mật khẩu cũ không đúng");
        }

        nhanVien.setNV_PASSWORD(newPassword);
        return nhanVienRepository.save(nhanVien);
    }

    public NhanVien updateNhanVien(int id, NhanVien nhanVienDetails) {
        logger.info("Dữ liệu nhân viên cập nhật nhận được: {}", nhanVienDetails);
        NhanVien nhanVien = nhanVienRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Nhân viên không tồn tại với ID: " + id));

        // Chỉ cập nhật các trường không null từ nhanVienDetails
        if (nhanVienDetails.getNV_HOTEN() != null) {
            nhanVien.setNV_HOTEN(nhanVienDetails.getNV_HOTEN());
        }
        if (nhanVienDetails.getNV_NGAYSINH() != null) {
            nhanVien.setNV_NGAYSINH(nhanVienDetails.getNV_NGAYSINH());
        }
        if (nhanVienDetails.getNV_GIOITINH() != null) {
            nhanVien.setNV_GIOITINH(nhanVienDetails.getNV_GIOITINH());
        }
        if (nhanVienDetails.getNV_EMAIL() != null) {
            nhanVien.setNV_EMAIL(nhanVienDetails.getNV_EMAIL());
        }
        if (nhanVienDetails.getNV_SDT() != null) {
            nhanVien.setNV_SDT(nhanVienDetails.getNV_SDT());
        }
        if (nhanVienDetails.getNV_USERNAME() != null) {
            nhanVien.setNV_USERNAME(nhanVienDetails.getNV_USERNAME());
        }
        if (nhanVienDetails.getNV_PASSWORD() != null) {
            nhanVien.setNV_PASSWORD(nhanVienDetails.getNV_PASSWORD());
        }
        if (nhanVienDetails.getNV_DIACHI() != null) {
            nhanVien.setNV_DIACHI(nhanVienDetails.getNV_DIACHI());
        }
        if (nhanVienDetails.getPbIdTemp() != null) {
            PhongBan phongBan = phongBanRepository.findById(nhanVienDetails.getPbIdTemp())
                .orElseThrow(() -> new RuntimeException("Phòng ban không tồn tại với ID: " + nhanVienDetails.getPbIdTemp()));
            nhanVien.setPB_ID(phongBan);
        }

        return nhanVienRepository.save(nhanVien);
    }

    public void deleteNhanVien(int id) {
        NhanVien nhanVien = nhanVienRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Nhân viên không tồn tại với ID: " + id));
        nhanVienRepository.delete(nhanVien);
    }
    
    
    
    
    
    
    // MỚI BỔ SUNG THÊM
    public void getHeSoLuong() {
    	
    }
}