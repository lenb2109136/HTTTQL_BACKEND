package com.example.hethongquanly.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.hethongquanly.model.Response;
import com.example.hethongquanly.repository.PhongBanRepository;

@RestController()
@RequestMapping("/phongban")
public class PhongBanController {
	@Autowired
	private PhongBanRepository phongBanRepository;
		@GetMapping("/getPhongBan")
		public  ResponseEntity<Response> getPhongBan(){
			return new ResponseEntity<Response>(new Response(HttpStatus.OK,"",phongBanRepository.findAll()),HttpStatus.OK);
		}
}
