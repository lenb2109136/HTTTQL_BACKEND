package com.example.hethongquanly.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Table(name = "KHAU_TRU")
@Entity
public class KhauTru {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int KT_ID ;
	 private String  KT_DIENGIAI   ;
	 private Float  KT_SOTIEN    ;
	 private String  KT_LOAITIENKHAUTRU  ;
	public int getKT_ID() {
		return KT_ID;
	}
	public void setKT_ID(int kT_ID) {
		KT_ID = kT_ID;
	}
	public String getKT_DIENGIAI() {
		return KT_DIENGIAI;
	}
	public void setKT_DIENGIAI(String kT_DIENGIAI) {
		KT_DIENGIAI = kT_DIENGIAI;
	}
	public Float getKT_SOTIEN() {
		return KT_SOTIEN;
	}
	public void setKT_SOTIEN(Float kT_SOTIEN) {
		KT_SOTIEN = kT_SOTIEN;
	}
	public String getKT_LOAITIENKHAUTRU() {
		return KT_LOAITIENKHAUTRU;
	}
	public void setKT_LOAITIENKHAUTRU(String kT_LOAITIENKHAUTRU) {
		KT_LOAITIENKHAUTRU = kT_LOAITIENKHAUTRU;
	}
	 
	 
}

