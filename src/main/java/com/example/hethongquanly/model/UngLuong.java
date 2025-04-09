package com.example.hethongquanly.model;

import java.time.LocalDate;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "ung_luong")
public class UngLuong {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UL_ID")
    @JsonProperty("ul_ID")
    private int UL_ID;

    @Column(name = "NV_ID")
    @JsonProperty("nv_ID")
    private int NV_ID;

    @Column(name = "UL_NGAYUL")
    @JsonProperty("ul_NGAYUL")
    private LocalDate UL_NGAYUL;

    @Column(name = "UL_TIEN")
    @JsonProperty("ul_TIEN")
    private Float UL_TIEN;

    @Column(name = "UL_TRANGTHAI")
    @JsonProperty("ul_TRANGTHAI")
    private String UL_TRANGTHAI;

    // Getters và Setters
    public int getUL_ID() {
        return UL_ID;
    }

    public void setUL_ID(int UL_ID) {
        this.UL_ID = UL_ID;
    }

    public int getNV_ID() {
        return NV_ID;
    }

    public void setNV_ID(int NV_ID) {
        this.NV_ID = NV_ID;
    }

    public LocalDate getUL_NGAYUL() {
        return UL_NGAYUL;
    }

    public void setUL_NGAYUL(LocalDate UL_NGAYUL) {
        this.UL_NGAYUL = UL_NGAYUL;
    }

    public Float getUL_TIEN() {
        return UL_TIEN;
    }

    public void setUL_TIEN(Float UL_TIEN) {
        this.UL_TIEN = UL_TIEN;
    }

    public String getUL_TRANGTHAI() {
        return UL_TRANGTHAI;
    }

    public void setUL_TRANGTHAI(String UL_TRANGTHAI) {
        this.UL_TRANGTHAI = UL_TRANGTHAI;
    }
}