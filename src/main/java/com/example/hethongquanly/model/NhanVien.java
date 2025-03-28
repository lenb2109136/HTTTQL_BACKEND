package com.example.hethongquanly.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Table(name = "NHAN_VIEN")
@Entity
public class NhanVien {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int	NV_ID              ;
	
	@ManyToOne
	@JoinColumn(name = "PB_ID")
	 private PhongBan  PB_ID ;
	 private String  NV_HOTEN ;
	 private LocalDate  NV_NGAYSINH ;
	 private Boolean  NV_GIOITINH  ;
	 private String  NV_EMAIL     ;
	 private String  NV_SDT           ;
	 private String  NV_USERNAME     ;
	 private String  NV_PASSWORD     ;
	 private String  NV_DIACHI        ;
	 
	 

}
