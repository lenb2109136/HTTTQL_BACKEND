package com.example.hethongquanly.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.hethongquanly.embeded.KhauTruId;
import com.example.hethongquanly.model.ChiTietKhauTru;
import com.example.hethongquanly.model.KhauTru;
import com.example.hethongquanly.model.NhanVien;
import com.example.hethongquanly.model.Response;
import com.example.hethongquanly.repository.ChiTietKhauTruRepository;
import com.example.hethongquanly.repository.KhauTruRepository;
import com.example.hethongquanly.repository.NhanVienRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/khautru")
public class KhauTruController {

	@Autowired
	private KhauTruRepository khauTruRepository;
	
	@Autowired
	private NhanVienRepository nhanVienRepository;
	
	@Autowired
	private ChiTietKhauTruRepository chiTietKhauTruRepository;

	@GetMapping("/getkhautruthuongnien")
	public ResponseEntity<Object> getKhauTruthuongNien() {
		return new ResponseEntity<Object>(new Response(HttpStatus.OK, "",
				khauTruRepository.findAll().stream().filter(d -> d.isKT_THUONGNIEN()).collect(Collectors.toList())),
				HttpStatus.OK);
	}

	@GetMapping("/getkhautrunoibo")
	public ResponseEntity<Object> getkhautrunoibo() {
		return new ResponseEntity<Object>(new Response(HttpStatus.OK, "", khauTruRepository.findAll().stream()
				.filter(d -> d.isKT_THUONGNIEN() == false).collect(Collectors.toList())), HttpStatus.OK);
	}

	@PostMapping("/create")
	public ResponseEntity<Response> CreateNew(@RequestBody KhauTru khauTru) {
		khauTruRepository.save(khauTru);
		return new ResponseEntity<Response>(new Response(HttpStatus.OK, "Tạo khấu trừ thành công", null),
				HttpStatus.OK);
	}

	@PostMapping("thietlapkhautru")
	public ResponseEntity<Response> thietlapkhautru(@RequestBody String requestBody) {
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			Map<Object, Object> dataMap = objectMapper.readValue(requestBody, new TypeReference<Map<Object, Object>>() {
			});

			List<Map<Object, Object>> nhanvien = (List<Map<Object, Object>>) dataMap.get("dsnhanvien");
			Map<Object, Object> me = (Map<Object, Object>) dataMap.get("khautru");
			KhauTru k= khauTruRepository.findById((Integer)me.get("kt_ID")).orElseThrow(()-> new EntityNotFoundException());
			nhanvien.forEach(nb->{
				NhanVien nv= nhanVienRepository.findById((Integer)nb.get("nv_ID")).orElseThrow(()-> new EntityNotFoundException());
				ChiTietKhauTru kk= new ChiTietKhauTru();
				KhauTruId id= new KhauTruId();
				id.setKT_ID(k.getKT_ID());
				id.setNV_ID(nv.getNV_ID());
				kk.setId(id);
				kk.setCHI_TIET_KY_NGAYAPDUNG(LocalDate.now());
				kk.setKhauTru(k);
				kk.setNhanVien(nv);
				kk.setKT_PHIAPDUNG(k.getKT_SOTIEN());
				chiTietKhauTruRepository.save(kk);
			});
			return new ResponseEntity<Response>(new Response(HttpStatus.OK, "Tạo khấu trừ thành công",null), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Response>(new Response(HttpStatus.OK, "Tạo khấu trừ thành công",null), HttpStatus.BAD_REQUEST);
		}
		
	}

}
