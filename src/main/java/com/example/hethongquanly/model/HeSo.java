package com.example.hethongquanly.model;

import java.time.LocalDate;

import com.example.hethongquanly.embeded.HeSoId;
import com.example.hethongquanly.embeded.KhauTruId;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

@Table(name = "HE_SO")
@Entity
public class HeSo {
	@EmbeddedId
	private HeSoId id;
	
	@ManyToOne
	@MapsId("BAC_ID")
	@JoinColumn(name = "BAC_ID")
	private BacLuong BAC_ID;
	
	@ManyToOne
	@MapsId("NGACH_ID")
	@JoinColumn(name = "NGACH_ID")
	private NgachLuong NGACH_ID;
	
	private LocalDate NGAYAPDUNG  ;
	private Float HS_HESO;
	public HeSoId getId() {
		return id;
	}
	public void setId(HeSoId id) {
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
	public LocalDate getNGAYAPDUNG() {
		return NGAYAPDUNG;
	}
	public void setNGAYAPDUNG(LocalDate nGAYAPDUNG) {
		NGAYAPDUNG = nGAYAPDUNG;
	}
	public Float getHS_HESO() {
		return HS_HESO;
	}
	public void setHS_HESO(Float hS_HESO) {
		HS_HESO = hS_HESO;
	}
	
	
}
