package com.example.hethongquanly.model;

import java.time.LocalDate;

import com.example.hethongquanly.embeded.HeSoId;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Table(name = "HE_SO")
@Entity
public class HeSo {
    // Getter và Setter
    @Setter
    @Getter
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
	private Float HS_HESO;
	public HeSo() {} // Constructor không tham số

	public HeSo(HeSoId id, Float HS_HESO) {
		this.id = id;
		this.HS_HESO = HS_HESO;
	}
//	public HeSo(HeSoId id, Float HS_HESO) {
//		this.id = id;
//		this.HS_HESO = HS_HESO;
//	}

	public Float getHS_HESO() { return HS_HESO; }
	public void setHS_HESO(Float HS_HESO) { this.HS_HESO = HS_HESO; }

}
