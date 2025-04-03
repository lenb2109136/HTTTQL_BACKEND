package com.example.hethongquanly.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.hethongquanly.model.Response;
import com.example.hethongquanly.repository.NgachLuongRepository;

@RestController
@RequestMapping("nghachluong")
public class NgachLuongController {
	
	@Autowired
	private NgachLuongRepository ngachLuongRepository;
	@GetMapping("/getngachluong")
	public ResponseEntity<Response> getNghachLuong (){
		return new ResponseEntity<Response>(new Response(HttpStatus.OK,"",ngachLuongRepository.findAll()),HttpStatus.OK);
	}
}
