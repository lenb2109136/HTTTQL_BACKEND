package com.example.hethongquanly.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "NGACH_LUONG")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NgachLuong {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int NGACH_ID;

	public int getNGACH_ID() {
		return NGACH_ID;
	}

	public void setNGACH_ID(int NGACH_ID) {
		this.NGACH_ID = NGACH_ID;
	}

	public Float getNGACH_LUONGCOSO() {
		return NGACH_LUONGCOSO;
	}

	public void setNGACH_LUONGCOSO(Float NGACH_LUONGCOSO) {
		this.NGACH_LUONGCOSO = NGACH_LUONGCOSO;
	}

	public String getNGACH_TEN() {
		return NGACH_TEN;
	}

	public void setNGACH_TEN(String NGACH_TEN) {
		this.NGACH_TEN = NGACH_TEN;
	}

	private Float NGACH_LUONGCOSO;
	private String NGACH_TEN;
}