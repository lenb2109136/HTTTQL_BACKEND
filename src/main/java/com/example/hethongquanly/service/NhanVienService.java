package com.example.hethongquanly.service;

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
import com.example.hethongquanly.model.ChiTietBacLuong;
import com.example.hethongquanly.model.ChiTietKhauTru;
import com.example.hethongquanly.model.HeSo;
import com.example.hethongquanly.model.NhanVien;
import com.example.hethongquanly.model.UngLuong;
import com.example.hethongquanly.repository.ChhiTietBacLuongRepository;
import com.example.hethongquanly.repository.ChiTietKhauTruRepository;
import com.example.hethongquanly.repository.HeSoRepository;
import com.example.hethongquanly.repository.NhanVienRepository;
import com.example.hethongquanly.repository.UngLuongRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class NhanVienService {
		@Autowired
		private UngLuongRepository ungLuongRepository;
		@Autowired
		private ChiTietKhauTruRepository chiTietKhauTruRepository;
		@Autowired
		private ChhiTietBacLuongRepository chhiTietBacLuongRepository;
		@Autowired
		private HeSoRepository heSoRepository;
		
		@Autowired
		private NhanVienRepository nhanVienRepository;
		
		public static long getNumberOfDays(LocalDate startDate, LocalDate endDate) {
	        return ChronoUnit.DAYS.between(startDate, endDate) + 1;
	    }
		private int getRandomTangCa() {
		    int[] values = {0, 1500, 2000};
		    return values[ThreadLocalRandom.current().nextInt(values.length)];
		}
		public Map<Object, Object> TinhLuong(int idnv, LocalDate nbd, LocalDate nkt) {
			Map<Object, Object> map= new HashMap<Object, Object>();
			List<UngLuong> ul= ungLuongRepository.getUngLuong(idnv, nbd, nkt);
			List<ChiTietKhauTru> ctkt=chiTietKhauTruRepository.getChiTietKhauTru(idnv, nbd, nkt);
			List<ChiTietBacLuong> ctbl= chhiTietBacLuongRepository.getChiTietBacLuong(idnv, nbd, nkt);
			Float tongungluong=ul.stream().map((u)-> u.getUL_TIEN()).reduce(0f,Float::sum);
			Float tongkhautru=ctkt.stream().map((u)-> u.getKhauTru().getKT_SOTIEN()).reduce(0f,Float::sum);
			map.put("luongcoban",20000);
			map.put("luongtangca", getRandomTangCa());
			map.put("tongthunhap", getRandomTangCa());
			map.put("tongungluong", tongungluong);
			map.put("tongkhautru", tongkhautru);
			map.put("luongnhan", tongkhautru);
			map.put("danhsachkhautru", ctkt.stream().map((d)->{
				Map<Object, Object> khautru=new HashMap<Object, Object>();
				khautru.put("khautru", d.getKhauTru());
				khautru.put("tienung", d.getCHI_TIET_KY_NGAYAPDUNG());
				return khautru;
			}));
			map.put("danhsachbacluong",null);
			map.put("danhsachungluong",ul);
			map.put("luongnhan",3000);
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


}
