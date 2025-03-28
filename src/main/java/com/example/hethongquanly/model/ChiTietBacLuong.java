package com.example.hethongquanly.model;

import java.time.LocalDate;

import com.example.hethongquanly.embeded.HeSoId;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

@Table(name = "CHI_TIET_BAC_LUONG")
@Entity
public class ChiTietBacLuong {
	@EmbeddedId
	private com.example.hethongquanly.embeded.ChiTietBacLuong id;
	
	@ManyToOne
	@MapsId("BAC_ID")
	@JoinColumn(name = "BAC_ID")
	private BacLuong BAC_ID;
	
	@ManyToOne
	@MapsId("NGACH_ID")
	@JoinColumn(name = "NGACH_ID")
	private NgachLuong NGACH_ID;
	
	
	@ManyToOne
	@MapsId("NV_ID")
	@JoinColumn(name = "NV_ID")
	private NhanVien NV_ID;
	
	private LocalDate NGAYAPDUNG ;

	public com.example.hethongquanly.embeded.ChiTietBacLuong getId() {
		return id;
	}

	public void setId(com.example.hethongquanly.embeded.ChiTietBacLuong id) {
		this.id = id;
	}

	public BacLuong getBAC_ID() {
		return BAC_ID;
	}

	public void setBAC_ID(BacLuong bAC_ID) {
		BAC_ID = bAC_ID;
	}

	public NgachLuong getNGACH_ID() {
		return NGACH_ID;
	}

	public void setNGACH_ID(NgachLuong nGACH_ID) {
		NGACH_ID = nGACH_ID;
	}

	public NhanVien getNV_ID() {
		return NV_ID;
	}

	public void setNV_ID(NhanVien nV_ID) {
		NV_ID = nV_ID;
	}

	public LocalDate getNGAYAPDUNG() {
		return NGAYAPDUNG;
	}

	public void setNGAYAPDUNG(LocalDate nGAYAPDUNG) {
		NGAYAPDUNG = nGAYAPDUNG;
	}
	
	
}
