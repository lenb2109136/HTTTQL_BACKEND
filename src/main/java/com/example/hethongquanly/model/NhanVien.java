package com.example.hethongquanly.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.NONE, getterVisibility = JsonAutoDetect.Visibility.NONE)
@Entity
@Table(name = "nhan_vien")
public class NhanVien {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "NV_ID")
    @JsonProperty("NV_ID")
    private int NV_ID;

    @ManyToOne
    @JoinColumn(name = "PB_ID")
    @JsonProperty("PB_ID")
    private PhongBan PB_ID;

    @Column(name = "NV_HOTEN")
    @JsonProperty("NV_HOTEN")
    private String NV_HOTEN;

    @Column(name = "NV_NGAYSINH")
    @JsonProperty("NV_NGAYSINH")
    private LocalDate NV_NGAYSINH;

    @Column(name = "NV_GIOITINH")
    @JsonProperty("NV_GIOITINH")
    private Short NV_GIOITINH; // Thay từ short thành Short

    @Column(name = "NV_EMAIL")
    @JsonProperty("NV_EMAIL")
    private String NV_EMAIL;

    @Column(name = "NV_SDT")
    @JsonProperty("NV_SDT")
    private String NV_SDT;

    @Column(name = "NV_USERNAME")
    @JsonProperty("NV_USERNAME")
    private String NV_USERNAME;

    @Column(name = "NV_PASSWORD")
    @JsonProperty("NV_PASSWORD")
    private String NV_PASSWORD;

    @Column(name = "NV_DIACHI")
    @JsonProperty("NV_DIACHI")
    private String NV_DIACHI;

    @Transient
    @JsonProperty("pbIdTemp")
    private Integer pbIdTemp;

    // Getters và Setters
    public int getNV_ID() { return NV_ID; }
    public void setNV_ID(int NV_ID) { this.NV_ID = NV_ID; }
    public PhongBan getPB_ID() { return PB_ID; }
    public void setPB_ID(PhongBan PB_ID) { this.PB_ID = PB_ID; }
    public String getNV_HOTEN() { return NV_HOTEN; }
    public void setNV_HOTEN(String NV_HOTEN) { this.NV_HOTEN = NV_HOTEN; }
    public LocalDate getNV_NGAYSINH() { return NV_NGAYSINH; }
    public void setNV_NGAYSINH(LocalDate NV_NGAYSINH) { this.NV_NGAYSINH = NV_NGAYSINH; }
    public Short getNV_GIOITINH() { return NV_GIOITINH; }
    public void setNV_GIOITINH(Short NV_GIOITINH) { this.NV_GIOITINH = NV_GIOITINH; }
    public String getNV_EMAIL() { return NV_EMAIL; }
    public void setNV_EMAIL(String NV_EMAIL) { this.NV_EMAIL = NV_EMAIL; }
    public String getNV_SDT() { return NV_SDT; }
    public void setNV_SDT(String NV_SDT) { this.NV_SDT = NV_SDT; }
    public String getNV_USERNAME() { return NV_USERNAME; }
    public void setNV_USERNAME(String NV_USERNAME) { this.NV_USERNAME = NV_USERNAME; }
    public String getNV_PASSWORD() { return NV_PASSWORD; }
    public void setNV_PASSWORD(String NV_PASSWORD) { this.NV_PASSWORD = NV_PASSWORD; }
    public String getNV_DIACHI() { return NV_DIACHI; }
    public void setNV_DIACHI(String NV_DIACHI) { this.NV_DIACHI = NV_DIACHI; }
    public Integer getPbIdTemp() { return pbIdTemp; }
    public void setPbIdTemp(Integer pbIdTemp) { this.pbIdTemp = pbIdTemp; }

    @PostLoad
    @PrePersist
    @PreUpdate
    public void syncPbId() {
        if (pbIdTemp != null && PB_ID == null) {
            PB_ID = new PhongBan();
            PB_ID.setPB_ID(pbIdTemp);
        }
    }
}