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

@Entity
@Table(name = "KHIEU_NAI")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "kn_ID")
public class KhieuNai {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int kn_ID;

    @ManyToOne
    @JoinColumn(name = "NV_ID")
    private NhanVien nv_ID;

    private String kn_NOIDUNG;
    private LocalDateTime kn_NGAYKN;

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
}