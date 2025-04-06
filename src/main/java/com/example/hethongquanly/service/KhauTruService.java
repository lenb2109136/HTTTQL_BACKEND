package com.example.hethongquanly.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.example.hethongquanly.embeded.KhauTruId;
import com.example.hethongquanly.model.ChiTietKhauTru;
import com.example.hethongquanly.model.KhauTru;
import com.example.hethongquanly.model.NhanVien;
import com.example.hethongquanly.repository.ChiTietKhauTruRepository;
import com.example.hethongquanly.repository.KhauTruRepository;
import com.example.hethongquanly.repository.NhanVienRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class KhauTruService {
	@Autowired
	private KhauTruRepository khauTruRepository;
	
	@Autowired
	private NhanVienRepository nhanVienRepository;
	
	@Autowired
	private ChiTietKhauTruRepository chiTietKhauTruRepository;
	
	
	@Scheduled(cron = "0 0 0 1 * ?")
	public void tuDongKhauTru() {
		List<KhauTru> kt =khauTruRepository.findAll();
		List<NhanVien> nv=nhanVienRepository.findAll();
		kt.forEach((d)->{
			if(d.isKT_TUDONG()==true &&d.isKT_THUONGNIEN()==true) {
				for(int i=0;i<nv.size();i++){ChiTietKhauTru kk= new ChiTietKhauTru();
					KhauTruId id= new KhauTruId();
					id.setKT_ID(d.getKT_ID());
					id.setNV_ID(nv.get(i).getNV_ID());
					kk.setId(id);
					kk.setCHI_TIET_KY_NGAYAPDUNG(LocalDate.now());
					kk.setKhauTru(d);
					kk.setNhanVien(nv.get(i));
					kk.setKT_PHIAPDUNG(d.getKT_SOTIEN());
					chiTietKhauTruRepository.save(kk);
		}
			}
		});
	}
	
//	@Scheduled(cron = "0/10 * * * * ?")
//	public void sys() {
//		System.out.println("có sự kiện xảy ra");
//	}
}
