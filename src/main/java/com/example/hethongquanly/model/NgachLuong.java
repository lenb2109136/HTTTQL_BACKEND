package com.example.hethongquanly.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "NGACH_LUONG")
public class NgachLuong {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int NGACH_ID  ;
	private Float  NGACH_LUONGCOSO    ;
	private String NGACH_TEN    ;
	public int getNGACH_ID() {
		return NGACH_ID;
	}
	public void setNGACH_ID(int nGACH_ID) {
		NGACH_ID = nGACH_ID;
	}
	public Float getNGACH_LUONGCOSO() {
		return NGACH_LUONGCOSO;
	}
	public void setNGACH_LUONGCOSO(Float nGACH_LUONGCOSO) {
		NGACH_LUONGCOSO = nGACH_LUONGCOSO;
	}
	public String getNGACH_TEN() {
		return NGACH_TEN;
	}
	public void setNGACH_TEN(String nGACH_TEN) {
		NGACH_TEN = nGACH_TEN;
	}
	
	
}