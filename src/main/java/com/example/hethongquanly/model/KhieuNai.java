package com.example.hethongquanly.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "KHIEU_NAI")
public class KhieuNai {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int KN_ID ;
	@ManyToOne
	@JoinColumn(name = "NV_ID")
	 private NhanVien  NV_ID ;
	 private String  KN_NOIDUNG ;
	 private LocalDateTime  KN_NGAYKN ;
	public int getKN_ID() {
		return KN_ID;
	}
	public void setKN_ID(int kN_ID) {
		KN_ID = kN_ID;
	}
	public NhanVien getNV_ID() {
		return NV_ID;
	}
	public void setNV_ID(NhanVien nV_ID) {
		NV_ID = nV_ID;
	}
	public String getKN_NOIDUNG() {
		return KN_NOIDUNG;
	}
	public void setKN_NOIDUNG(String kN_NOIDUNG) {
		KN_NOIDUNG = kN_NOIDUNG;
	}
	public LocalDateTime getKN_NGAYKN() {
		return KN_NGAYKN;
	}
	public void setKN_NGAYKN(LocalDateTime kN_NGAYKN) {
		KN_NGAYKN = kN_NGAYKN;
	}
	 
}

