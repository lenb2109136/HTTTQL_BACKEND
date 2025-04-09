package com.example.hethongquanly.model;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;


@Entity
@Table(name = "bac_luong")
@Data
public class BacLuong {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "BAC_ID")
	private Integer id;

	@ManyToOne
//	@JsonIgnore // ngăn không hiện bên api
	@JoinColumn(name = "NGACH_ID", nullable = false)
	private NgachLuong ngachLuong;

	@Column(name = "BAC_TEN", nullable = false)
	private String ten;

	@Column(name = "B_HS", nullable = false)
	private Float heSo;


	@Column(name = "NGAY", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date ngayApDung;



	public Integer getId() {
		return id;
	}



	public void setId(Integer id) {
		this.id = id;
	}



	public NgachLuong getNgachLuong() {
		return ngachLuong;
	}



	public void setNgachLuong(NgachLuong ngachLuong) {
		this.ngachLuong = ngachLuong;
	}



	public String getTen() {
		return ten;
	}



	public void setTen(String ten) {
		this.ten = ten;
	}



	public Float getHeSo() {
		return heSo;
	}



	public void setHeSo(Float heSo) {
		this.heSo = heSo;
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
