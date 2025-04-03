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
@Table(name = "UNG_LUONG")
public class UngLuong {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int   UL_ID  ;
	@ManyToOne
	@JoinColumn(name = "NV_ID")
	private NhanVien  NV_ID   ;
	private LocalDateTime   UL_NGAYUL    ;
	private Float   UL_TIEN      ;
	private String   UL_TRANGTHAI     ;
	public int getL_ID() {
		return UL_ID;
	}
	public void setL_ID(int l_ID) {
		UL_ID = l_ID;
	}
	public NhanVien getNV_ID() {
		return NV_ID;
	}
	public void setNV_ID(NhanVien nV_ID) {
		NV_ID = nV_ID;
	}
	public LocalDateTime getUL_NGAYUL() {
		return UL_NGAYUL;
	}
	public void setUL_NGAYUL(LocalDateTime uL_NGAYUL) {
		UL_NGAYUL = uL_NGAYUL;
	}
	public Float getUL_TIEN() {
		return UL_TIEN;
	}
	public void setUL_TIEN(Float uL_TIEN) {
		UL_TIEN = uL_TIEN;
	}
	public int getUL_ID() {
		return UL_ID;
	}
	public void setUL_ID(int uL_ID) {
		UL_ID = uL_ID;
	}
	public String getUL_TRANGTHAI() {
		return UL_TRANGTHAI;
	}
	public void setUL_TRANGTHAI(String uL_TRANGTHAI) {
		UL_TRANGTHAI = uL_TRANGTHAI;
	}
	
	
}