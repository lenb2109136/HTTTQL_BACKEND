package com.example.hethongquanly.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.NONE, getterVisibility = JsonAutoDetect.Visibility.NONE)
@Entity
@Table(name = "phong_ban")
public class PhongBan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PB_ID")
    @JsonProperty("PB_ID") // Chỉ serialize thành "PB_ID"
    private int PB_ID;

    @Column(name = "PB_TEN")
    @JsonProperty("PB_TEN") // Chỉ serialize thành "PB_TEN"
    private String PB_TEN;

    @Transient
    @JsonProperty("soNhanSu") // Chỉ serialize thành "soNhanSu"
    private long soNhanSu;

    // Getters và Setters
    public int getPB_ID() { return PB_ID; }
    public void setPB_ID(int PB_ID) { this.PB_ID = PB_ID; }
    public String getPB_TEN() { return PB_TEN; }
    public void setPB_TEN(String PB_TEN) { this.PB_TEN = PB_TEN; }
    public long getSoNhanSu() { return soNhanSu; }
    public void setSoNhanSu(long soNhanSu) { this.soNhanSu = soNhanSu; }

    @Override
    public String toString() {
        return "PhongBan{PB_ID=" + PB_ID + ", PB_TEN='" + PB_TEN + "', soNhanSu=" + soNhanSu + "}";
    }
}