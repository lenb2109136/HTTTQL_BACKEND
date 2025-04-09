package com.example.hethongquanly.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;


import jakarta.persistence.*;
import lombok.Data;
@Table(name = "CHI_TIET_BAC_LUONG")
@Entity
public class ChiTietBacLuong {
	@Id
	@Column(name="ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int Id;
	
	@ManyToOne
	@JoinColumn(name = "BAC_ID")
	private BacLuong BAC_ID;

	@ManyToOne
	@JoinColumn(name = "NV_ID")
	private NhanVien nhanVien;
	
//	private LocalDate NGAYAPDUNG ;

	@Column(name = "NGAYAPDUNG", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date ngayApDung;

	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}

	public BacLuong getBAC_ID() {
		return BAC_ID;
	}

	public void setBAC_ID(BacLuong BAC_ID) {
		this.BAC_ID = BAC_ID;
	}

	public NhanVien getNV_ID() {
		return nhanVien;
	}

	public void setNV_ID(NhanVien NV_ID) {
		this.nhanVien = NV_ID;
	}

	public Date getNgayApDung() {
		return ngayApDung;
	}

	public void setNgayApDung(Date ngayApDung) {
		this.ngayApDung = ngayApDung;
	}

	@PrePersist
	protected void onCreate() {
		if (this.ngayApDung == null) {
			// Lấy ngày giờ hiện tại với múi giờ UTC+7
			LocalDateTime localDateTime = LocalDateTime.now(ZoneOffset.of("+07:00"));
			// Chuyển thành Date
			this.ngayApDung = Date.from(localDateTime.atZone(ZoneOffset.of("+07:00")).toInstant());
		}
	}


	
}
