package com.example.hethongquanly.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.hethongquanly.model.NhanVien;
import com.example.hethongquanly.model.Response;
import com.example.hethongquanly.service.NhanVienService;

@RestController
@RequestMapping("/nhanvien")
public class NhanVienController {
	@Autowired
	private NhanVienService nhanVienService;
	@GetMapping("getluongnhanvien")
	public ResponseEntity<Response> getluongnhanvien(
			@RequestParam("nvid") int nvid,
			@RequestParam("nbd") LocalDate nbd,
			@RequestParam("nkt") LocalDate nkt
			){
		return new ResponseEntity<Response>(new Response(HttpStatus.OK,"",nhanVienService.TinhLuong(nvid, nbd, nkt)),HttpStatus.OK);
	
	}
	@GetMapping("getluongnhanvienbybophan")
	public ResponseEntity<Response> getluongnhanvienByBoPhan(
			@RequestParam(name="nvid",required = false,defaultValue ="0") int bophanid,
			@RequestParam(name="nbd",required = false) LocalDate nbd,
			@RequestParam(name="nkt",required = false) LocalDate nkt
			){
		return new ResponseEntity<Response>(new Response(HttpStatus.OK,"",nhanVienService.getLuongByBoPhan(bophanid, nbd, nkt)),HttpStatus.OK);
		
	}
	
	@GetMapping("/getPhongBanSoDienThoai")
	public ResponseEntity<Response> getPhongBanSoDienThoai(
			@RequestParam(name = "sodienthoai",required = false, defaultValue = "") String sodienthoai,
			@RequestParam(name = "idphongban",required = false, defaultValue="0") int idphongban
			) {
		List<NhanVien> nhanViens= nhanVienService.filterNhanVien(idphongban);
		if(sodienthoai!="") {
			nhanViens= nhanViens.stream().filter((d)->{
				return d.getNV_SDT().contains(sodienthoai);
			}).collect(Collectors.toList());
		}
		return new ResponseEntity<Response>(new Response(HttpStatus.OK,"",nhanViens), HttpStatus.OK);
	}
}
