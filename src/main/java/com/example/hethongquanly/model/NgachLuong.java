package com.example.hethongquanly.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "ngach_luong")
@Data
public class NgachLuong {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "NGACH_ID")
	private Long id;

	@Column(name = "NGACH_LUONGCOSO", nullable = false)
	private Float luongCoSo;

	@Column(name = "NGACH_TEN", nullable = false, length = 255)
	private String tenNgach;

	@Column(name = "NGAY")
	@Temporal(TemporalType.DATE)
	private Date ngayApDung;

	@OneToMany(mappedBy = "ngachLuong", cascade = CascadeType.ALL)
	private List<BacLuong> bacLuongs;
}