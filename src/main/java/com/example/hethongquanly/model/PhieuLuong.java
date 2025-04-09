package com.example.hethongquanly.model;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "phieu_luong")
public class PhieuLuong {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PL_ID")
    private int id;

    @ManyToOne
    @JoinColumn(name = "NV_ID", nullable = false)
    private NhanVien nvId;

    @Column(name = "PL_NGAY")
    private Date ngayPhat;

    @Column(name = "PL_LUONGCOBAN")
    private double luongCoBan;

    @Column(name = "PL_LUONGTANGCA")
    private double luongTangCa;

    @Column(name = "PL_TONGTHUNHAP")
    private double tongThuNhap;

    @Column(name = "PL_TONGKHAUTRU")
    private double tongKhauTru;

    @Column(name = "PL_UNGLUONG")
    private double ungLuong;

    @Column(name = "PL_LUONGNHAN")
    private double luongNhan;

    @Column(name = "PL_NO")
    private double no;

    @Column(name = "PL_TRANGTHAI")
    private String trangThai;

    // Getters và Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public NhanVien getNvId() {
        return nvId;
    }

    public void setNvId(NhanVien nvId) {
        this.nvId = nvId;
    }

    public Date getNgayPhat() {
        return ngayPhat;
    }

    public void setNgayPhat(Date ngayPhat) {
        this.ngayPhat = ngayPhat;
    }

    public double getLuongCoBan() {
        return luongCoBan;
    }

    public void setLuongCoBan(double luongCoBan) {
        this.luongCoBan = luongCoBan;
    }

    public double getLuongTangCa() {
        return luongTangCa;
    }

    public void setLuongTangCa(double luongTangCa) {
        this.luongTangCa = luongTangCa;
    }

    public double getTongThuNhap() {
        return tongThuNhap;
    }

    public void setTongThuNhap(double tongThuNhap) {
        this.tongThuNhap = tongThuNhap;
    }

    public double getTongKhauTru() {
        return tongKhauTru;
    }

    public void setTongKhauTru(double tongKhauTru) {
        this.tongKhauTru = tongKhauTru;
    }

    public double getUngLuong() {
        return ungLuong;
    }

    public void setUngLuong(double ungLuong) {
        this.ungLuong = ungLuong;
    }

    public double getLuongNhan() {
        return luongNhan;
    }

    public void setLuongNhan(double luongNhan) {
        this.luongNhan = luongNhan;
    }

    public double getNo() {
        return no;
    }

    public void setNo(double no) {
        this.no = no;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }
}