package com.example.hethongquanly.embeded;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class ChiTietBacLuong {
	@Column(name = "BAC_ID") 
	 private int BAC_ID; 
	
	@Column(name = "NGACH_ID") 
	private int NGACH_ID;
	
	@Column(name = "NV_ID") 
	private int NV_ID;
	
	public int getBAC_ID() {
		return BAC_ID;
	}

	public void setBAC_ID(int bAC_ID) {
		BAC_ID = bAC_ID;
	}

	public int getNGACH_ID() {
		return NGACH_ID;
	}

	public void setNGACH_ID(int nGACH_ID) {
		NGACH_ID = nGACH_ID;
	}

	public int getNV_ID() {
		return NV_ID;
	}

	public void setNV_ID(int nV_ID) {
		NV_ID = nV_ID;
	}

	@Override
	public boolean equals(Object o) {
	    if (this == o) return true;
	    if (o == null || getClass() != o.getClass()) return false;
	    ChiTietBacLuong that = (ChiTietBacLuong) o;
	    return BAC_ID == that.BAC_ID && NGACH_ID == that.NGACH_ID && NV_ID == that.NV_ID;
	}

	@Override
	public int hashCode() {
	    return Objects.hash(BAC_ID, NGACH_ID,NV_ID);
	}
}
