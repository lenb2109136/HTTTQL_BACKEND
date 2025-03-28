package com.example.hethongquanly.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "PHONG_BAN")
public class PhongBan {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int PB_ID;
	
	private String PB_TEN;

	public int getPB_ID() {
		return PB_ID;
	}

	public void setPB_ID(int pB_ID) {
		PB_ID = pB_ID;
	}

	public String getPB_TEN() {
		return PB_TEN;
	}

	public void setPB_TEN(String pB_TEN) {
		PB_TEN = pB_TEN;
	}
	
	
}
