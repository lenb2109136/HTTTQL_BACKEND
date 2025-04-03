package com.example.hethongquanly.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.hethongquanly.service.ExportPDF;
import com.example.hethongquanly.service.SendEmail;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping("/getphieuluongpdf")
public class PhieuLuongPDFController {
	@Autowired
	private ExportPDF exportPDF;
	
	@PostMapping("/getphieuluongpdf")
	public ResponseEntity<Object> getphieuluongpdf(@RequestBody Map<Object, Object> map) throws Exception{
		byte[] dulieu=exportPDF.createPdf(map);
		HttpHeaders header= new HttpHeaders();
		header.setContentType(MediaType.APPLICATION_PDF);
		header.setContentDisposition(ContentDisposition.attachment()
                .filename("sample.pdf")
                .build());
		return new ResponseEntity<Object>(dulieu,header,HttpStatus.OK);

	}

	@PostMapping("/sendemailphieuluong")
	public ResponseEntity<Object> ChuyenMailPhieuLuong(
	        @RequestParam("email") String email,
	        @RequestParam("noidung") String noidung,
	        @RequestParam("tieude") String tieude,
	        @RequestParam("map") String mapJson) throws Exception {
	    ObjectMapper objectMapper = new ObjectMapper();
	    Map<Object, Object> map = objectMapper.readValue(mapJson, Map.class);

	    SendEmail sendEmail = new SendEmail();
	    byte[] dulieu = exportPDF.createPdf(map);
	    
	    sendEmail.taomail(email, tieude, noidung, dulieu);

	    return new ResponseEntity<Object>(null, HttpStatus.OK);
	}

	
	
	
	
}
