package com.example.hethongquanly.embeded;

import java.util.Objects;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class KhauTruId {
	@Column(name = "KT_ID") 
	 private int KT_ID; 
	
	@Column(name = "NV_ID") 
	private int NV_ID;

	

	public int getKT_ID() {
		return KT_ID;
	}

	public void setKT_ID(int kT_ID) {
		KT_ID = kT_ID;
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
	    KhauTruId that = (KhauTruId) o;
	    return KT_ID == that.KT_ID && NV_ID == that.NV_ID;
	}

	@Override
	public int hashCode() {
	    return Objects.hash(KT_ID, NV_ID);
	}
}
