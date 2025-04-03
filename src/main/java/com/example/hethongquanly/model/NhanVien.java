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
	public int getNV_ID() {
		return NV_ID;
	}
	public void setNV_ID(int nV_ID) {
		NV_ID = nV_ID;
	}
	public PhongBan getPB_ID() {
		return PB_ID;
	}
	public void setPB_ID(PhongBan pB_ID) {
		PB_ID = pB_ID;
	}
	public String getNV_HOTEN() {
		return NV_HOTEN;
	}
	public void setNV_HOTEN(String nV_HOTEN) {
		NV_HOTEN = nV_HOTEN;
	}
	public LocalDate getNV_NGAYSINH() {
		return NV_NGAYSINH;
	}
	public void setNV_NGAYSINH(LocalDate nV_NGAYSINH) {
		NV_NGAYSINH = nV_NGAYSINH;
	}
	public Boolean getNV_GIOITINH() {
		return NV_GIOITINH;
	}
	public void setNV_GIOITINH(Boolean nV_GIOITINH) {
		NV_GIOITINH = nV_GIOITINH;
	}
	public String getNV_EMAIL() {
		return NV_EMAIL;
	}
	public void setNV_EMAIL(String nV_EMAIL) {
		NV_EMAIL = nV_EMAIL;
	}
	public String getNV_SDT() {
		return NV_SDT;
	}
	public void setNV_SDT(String nV_SDT) {
		NV_SDT = nV_SDT;
	}
	public String getNV_USERNAME() {
		return NV_USERNAME;
	}
	public void setNV_USERNAME(String nV_USERNAME) {
		NV_USERNAME = nV_USERNAME;
	}
	public String getNV_PASSWORD() {
		return NV_PASSWORD;
	}
	public void setNV_PASSWORD(String nV_PASSWORD) {
		NV_PASSWORD = nV_PASSWORD;
	}
	public String getNV_DIACHI() {
		return NV_DIACHI;
	}
	public void setNV_DIACHI(String nV_DIACHI) {
		NV_DIACHI = nV_DIACHI;
	}
	 
	 

}
