package com.example.hethongquanly.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public class HeSoRequest {
    private Integer bac_Id;
    private Integer ngach_Id;
    private LocalDate ngayApDung;
    private Float hs_HeSo;

}

