package com.example.hethongquanly.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "PHIEU_LUONG")
public class PhieuLuong {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int	PL_ID ;
	@ManyToOne
	@JoinColumn(name = "NV_ID")
	private NhanVien   NV_ID;
	private LocalDate   PL_NGAY;
	private Float   PL_LUONGCOBAN;
	private Float   PL_LUONGTANGCA;
	private Float   PL_TONGTHUNHAP;
	private Float   PL_TONGKHAUTRU;
	private Float   PL_UNGLUONG;
	private Float   PL_LUONGNHAN;
	private Float   PL_NO;
	public int getPL_ID() {
		return PL_ID;
	}
	public void setPL_ID(int pL_ID) {
		PL_ID = pL_ID;
	}
	public NhanVien getNV_ID() {
		return NV_ID;
	}
	public void setNV_ID(NhanVien nV_ID) {
		NV_ID = nV_ID;
	}
	public LocalDate getPL_NGAY() {
		return PL_NGAY;
	}
	public void setPL_NGAY(LocalDate pL_NGAY) {
		PL_NGAY = pL_NGAY;
	}
	public Float getPL_LUONGCOBAN() {
		return PL_LUONGCOBAN;
	}
	public void setPL_LUONGCOBAN(Float pL_LUONGCOBAN) {
		PL_LUONGCOBAN = pL_LUONGCOBAN;
	}
	public Float getPL_LUONGTANGCA() {
		return PL_LUONGTANGCA;
	}
	public void setPL_LUONGTANGCA(Float pL_LUONGTANGCA) {
		PL_LUONGTANGCA = pL_LUONGTANGCA;
	}
	public Float getPL_TONGTHUNHAP() {
		return PL_TONGTHUNHAP;
	}
	public void setPL_TONGTHUNHAP(Float pL_TONGTHUNHAP) {
		PL_TONGTHUNHAP = pL_TONGTHUNHAP;
	}
	public Float getPL_TONGKHAUTRU() {
		return PL_TONGKHAUTRU;
	}
	public void setPL_TONGKHAUTRU(Float pL_TONGKHAUTRU) {
		PL_TONGKHAUTRU = pL_TONGKHAUTRU;
	}
	public Float getPL_UNGLUONG() {
		return PL_UNGLUONG;
	}
	public void setPL_UNGLUONG(Float pL_UNGLUONG) {
		PL_UNGLUONG = pL_UNGLUONG;
	}
	public Float getPL_LUONGNHAN() {
		return PL_LUONGNHAN;
	}
	public void setPL_LUONGNHAN(Float pL_LUONGNHAN) {
		PL_LUONGNHAN = pL_LUONGNHAN;
	}
	public Float getPL_NO() {
		return PL_NO;
	}
	public void setPL_NO(Float pL_NO) {
		PL_NO = pL_NO;
	}
	
	
}

