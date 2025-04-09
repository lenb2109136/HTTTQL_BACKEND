package com.example.hethongquanly.model;

import java.time.LocalDate;



import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "CHI_TIET_KHAU_TRU")
public class ChiTietKhauTru {

    @Id
	@Column(name="ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int Id;

	

    @ManyToOne
//	@MapsId("KT_ID")
	@JoinColumn(name = "KT_ID")
	private KhauTru khauTru;
	
	@ManyToOne
//	@MapsId("NV_ID")
	@JoinColumn(name = "NV_ID")
	private NhanVien nhanVien;

	private LocalDate 	CHI_TIET_KY_NGAYAPDUNG;
	private Float KT_PHIAPDUNG;

	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}

	public KhauTru getKhauTru() {
		return khauTru;
	}

	public void setKhauTru(KhauTru khauTru) {
		this.khauTru = khauTru;
	}

	public NhanVien getNhanVien() {
		return nhanVien;
	}

	public void setNhanVien(NhanVien nhanVien) {
		this.nhanVien = nhanVien;
	}

	public LocalDate getCHI_TIET_KY_NGAYAPDUNG() {
		return CHI_TIET_KY_NGAYAPDUNG;
	}

	public void setCHI_TIET_KY_NGAYAPDUNG(LocalDate CHI_TIET_KY_NGAYAPDUNG) {
		this.CHI_TIET_KY_NGAYAPDUNG = CHI_TIET_KY_NGAYAPDUNG;
	}

	public Float getKT_PHIAPDUNG() {
		return KT_PHIAPDUNG;
	}

	public void setKT_PHIAPDUNG(Float KT_PHIAPDUNG) {
		this.KT_PHIAPDUNG = KT_PHIAPDUNG;
	}
}
