package com.example.hethongquanly.model;

import java.util.Date;

import jakarta.persistence.*;
import lombok.Data;
import java.util.Date;

@Entity
@Table(name = "bac_luong")
@Data
public class BacLuong {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "BAC_ID")
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "NGACH_ID", nullable = false)
	private NgachLuong ngachLuong;

	@Column(name = "BAC_TEN", nullable = false)
	private String ten;

	@Column(name = "B_HS", nullable = false)
	private Float heSo;

	@Column(name = "NGAY", nullable = false)
	@Temporal(TemporalType.DATE)
	private Date ngayApDung;
}
