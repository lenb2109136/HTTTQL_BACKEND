package com.example.hethongquanly.model;

import java.time.LocalDate;

import com.example.hethongquanly.embeded.KhauTruId;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

@Entity
@Table(name = "CHI_TIET_KHAU_TRU")
public class ChiTietKhauTru {
	@EmbeddedId
	private KhauTruId id;
	
	@ManyToOne
	@MapsId("KT_ID")
	@JoinColumn(name = "KT_ID")
	private KhauTru khauTru;
	
	@ManyToOne
	@MapsId("NV_ID")
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

	public KhauTruId getId() {
		return id;
	}

	public void setId(KhauTruId id) {
		this.id = id;
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
