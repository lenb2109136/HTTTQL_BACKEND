package com.example.hethongquanly.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Column;

@Entity
@Table(name = "khieu_nai")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "kn_ID")
public class KhieuNai {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "KN_ID")
    private int kn_ID;

    @ManyToOne
    @JoinColumn(name = "NV_ID", nullable = false)
    private NhanVien nv_ID;

    @Column(name = "KN_NOIDUNG")
    private String kn_NOIDUNG;

    @Column(name = "kn_ngaykn")
    private LocalDateTime kn_NGAYKN;

    @Column(name = "KN_TRANGTHAI")
    private String kn_TRANGTHAI;

    // Getters và Setters
    public int getKn_ID() {
        return kn_ID;
    }

    public void setKn_ID(int kn_ID) {
        this.kn_ID = kn_ID;
    }

    public NhanVien getNv_ID() {
        return nv_ID;
    }

    public void setNv_ID(NhanVien nv_ID) {
        this.nv_ID = nv_ID;
    }

    public String getKn_NOIDUNG() {
        return kn_NOIDUNG;
    }

    public void setKn_NOIDUNG(String kn_NOIDUNG) {
        this.kn_NOIDUNG = kn_NOIDUNG;
    }

    public LocalDateTime getKn_NGAYKN() {
        return kn_NGAYKN;
    }

    public void setKn_NGAYKN(LocalDateTime kn_NGAYKN) {
        this.kn_NGAYKN = kn_NGAYKN;
    }

    public String getKn_TRANGTHAI() {
        return kn_TRANGTHAI;
    }

    public void setKn_TRANGTHAI(String kn_TRANGTHAI) {
        this.kn_TRANGTHAI = kn_TRANGTHAI;
    }
}