package com.example.hethongquanly.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name ="BAC_LUONG")
public class BacLuong {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int BAC_ID  ;
	private String BAC_TEN ;
	public int getBAC_ID() {
		return BAC_ID;
	}
	public void setBAC_ID(int bAC_ID) {
		BAC_ID = bAC_ID;
	}
	public String getBAC_TEN() {
		return BAC_TEN;
	}
	public void setBAC_TEN(String bAC_TEN) {
		BAC_TEN = bAC_TEN;
	}


}

