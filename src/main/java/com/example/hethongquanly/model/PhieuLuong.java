package com.example.hethongquanly.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "PHIEU_LUONG")
public class PhieuLuong {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PL_ID")
    @JsonProperty("id")
    private int PL_ID;

    @ManyToOne
    @JoinColumn(name = "NV_ID")
    @JsonProperty("nvId")
    private NhanVien nvId;

    @Column(name = "PL_NGAY")
    @JsonProperty("ngayPhat")
    private LocalDate PL_NGAY;

    @Column(name = "PL_LUONGCOBAN")
    @JsonProperty("luongCoBan")
    private Float PL_LUONGCOBAN;

    @Column(name = "PL_LUONGTANGCA")
    @JsonProperty("luongTangCa")
    private Float PL_LUONGTANGCA;

    @Column(name = "PL_TONGTHUNHAP")
    @JsonProperty("tongThuNhap")
    private Float PL_TONGTHUNHAP;

    @Column(name = "PL_TONGKHAUTRU")
    @JsonProperty("tongKhauTru")
    private Float PL_TONGKHAUTRU;

    @Column(name = "PL_UNGLUONG")
    @JsonProperty("ungLuong")
    private Float PL_UNGLUONG;

    @Column(name = "PL_LUONGNHAN")
    @JsonProperty("luongNhan")
    private Float PL_LUONGNHAN;

    @Column(name = "PL_NO")
    @JsonProperty("no")
    private Float PL_NO;

    // Getters và Setters
    public int getPL_ID() {
        return PL_ID;
    }

    public void setPL_ID(int PL_ID) {
        this.PL_ID = PL_ID;
    }

    public NhanVien getNvId() {
        return nvId;
    }

    public void setNvId(NhanVien nvId) {
        this.nvId = nvId;
    }

    public LocalDate getPL_NGAY() {
        return PL_NGAY;
    }

    public void setPL_NGAY(LocalDate PL_NGAY) {
        this.PL_NGAY = PL_NGAY;
    }

    public Float getPL_LUONGCOBAN() {
        return PL_LUONGCOBAN;
    }

    public void setPL_LUONGCOBAN(Float PL_LUONGCOBAN) {
        this.PL_LUONGCOBAN = PL_LUONGCOBAN;
    }

    public Float getPL_LUONGTANGCA() {
        return PL_LUONGTANGCA;
    }

    public void setPL_LUONGTANGCA(Float PL_LUONGTANGCA) {
        this.PL_LUONGTANGCA = PL_LUONGTANGCA;
    }

    public Float getPL_TONGTHUNHAP() {
        return PL_TONGTHUNHAP;
    }

    public void setPL_TONGTHUNHAP(Float PL_TONGTHUNHAP) {
        this.PL_TONGTHUNHAP = PL_TONGTHUNHAP;
    }

    public Float getPL_TONGKHAUTRU() {
        return PL_TONGKHAUTRU;
    }

    public void setPL_TONGKHAUTRU(Float PL_TONGKHAUTRU) {
        this.PL_TONGKHAUTRU = PL_TONGKHAUTRU;
    }

    public Float getPL_UNGLUONG() {
        return PL_UNGLUONG;
    }

    public void setPL_UNGLUONG(Float PL_UNGLUONG) {
        this.PL_UNGLUONG = PL_UNGLUONG;
    }

    public Float getPL_LUONGNHAN() {
        return PL_LUONGNHAN;
    }

    public void setPL_LUONGNHAN(Float PL_LUONGNHAN) {
        this.PL_LUONGNHAN = PL_LUONGNHAN;
    }

    public Float getPL_NO() {
        return PL_NO;
    }

    public void setPL_NO(Float PL_NO) {
        this.PL_NO = PL_NO;
    }
}