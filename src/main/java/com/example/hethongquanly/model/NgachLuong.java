package com.example.hethongquanly.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
	private Integer id;

	@Column(name = "NGACH_LUONGCOSO", nullable = false)
	private Float luongCoSo;

	@Column(name = "NGACH_TEN", nullable = false)
	private String ten;

	@Column(name = "NGAY", nullable = false)
	@Temporal(TemporalType.DATE)
	private Date ngayApDung;

	@JsonIgnore
    @OneToMany(mappedBy = "ngachLuong", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<BacLuong> bacLuongs;

}