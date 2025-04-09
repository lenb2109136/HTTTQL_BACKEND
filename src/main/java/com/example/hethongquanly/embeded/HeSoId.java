package com.example.hethongquanly.embeded;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class HeSoId implements Serializable {
	private Integer BAC_ID;
	private Integer NGACH_ID;
	private LocalDate NGAYAPDUNG; // Thêm vào khóa chính

	public HeSoId() {} // Constructor không tham số

	public HeSoId(Integer BAC_ID, Integer NGACH_ID, LocalDate NGAYAPDUNG) {
		this.BAC_ID = BAC_ID;
		this.NGACH_ID = NGACH_ID;
		this.NGAYAPDUNG = NGAYAPDUNG;
	}

	// Getter và Setter
	public Integer getBAC_ID() { return BAC_ID; }
	public void setBAC_ID(Integer BAC_ID) { this.BAC_ID = BAC_ID; }

	public LocalDate getNGAYAPDUNG() {
		return NGAYAPDUNG;
	}

	public void setNGAYAPDUNG(LocalDate NGAYAPDUNG) {
		this.NGAYAPDUNG = NGAYAPDUNG;
	}

	public Integer getNGACH_ID() { return NGACH_ID; }
	public void setNGACH_ID(Integer NGACH_ID) { this.NGACH_ID = NGACH_ID; }

	@Override
	public boolean equals(Object o) {
	    if (this == o) return true;
	    if (o == null || getClass() != o.getClass()) return false;
	    HeSoId that = (HeSoId) o;
	    return BAC_ID == that.BAC_ID && NGACH_ID == that.NGACH_ID;
	}

	@Override
	public int hashCode() {
	    return Objects.hash(BAC_ID, NGACH_ID);
	}
}
