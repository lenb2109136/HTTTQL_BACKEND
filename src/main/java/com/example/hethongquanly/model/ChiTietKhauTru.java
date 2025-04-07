package com.example.hethongquanly.model;

import java.time.LocalDate;

import com.example.hethongquanly.embeded.KhauTruId;

import jakarta.annotation.Generated;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

@Entity
@Table(name = "CHI_TIET_KHAU_TRU")
public class ChiTietKhauTru {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int ID;
	
	@ManyToOne
	@JoinColumn(name = "KT_ID")
	private KhauTru khauTru;
	
	@ManyToOne
	@JoinColumn(name = "NV_ID")
	private NhanVien nhanVien;
	
	private Float KT_PHIAPDUNG;
	 
	 
	public Float getKT_PHIAPDUNG() {
		return KT_PHIAPDUNG;
	}
	public void setKT_PHIAPDUNG(Float kT_PHIAPDUNG) {
		KT_PHIAPDUNG = kT_PHIAPDUNG;
	}
	
	private LocalDate CHI_TIET_KY_NGAYAPDUNG;

	

	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public KhauTru getKhauTru() {
		return khauTru;
	}

	public void setKhauTru(KhauTru khauTru) {
		this.khauTru = khauTru;
	}

	public NhanVien getNhanVien() {
		return nhanVien;
	}

	public void setNhanVien(NhanVien nhanVien) {
		this.nhanVien = nhanVien;
	}

	public LocalDate getCHI_TIET_KY_NGAYAPDUNG() {
		return CHI_TIET_KY_NGAYAPDUNG;
	}

	public void setCHI_TIET_KY_NGAYAPDUNG(LocalDate cHI_TIET_KY_NGAYAPDUNG) {
		CHI_TIET_KY_NGAYAPDUNG = cHI_TIET_KY_NGAYAPDUNG;
	}
	
	
}
