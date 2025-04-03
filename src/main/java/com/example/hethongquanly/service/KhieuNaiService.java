package com.example.hethongquanly.service;

import com.example.hethongquanly.model.KhieuNai;
import com.example.hethongquanly.repository.KhieuNaiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class KhieuNaiService {

    @Autowired
    private KhieuNaiRepository khieuNaiRepository;

    public List<KhieuNai> getAllKhieuNai() {
        return khieuNaiRepository.findAll();
    }

    public Optional<KhieuNai> getKhieuNaiById(int id) {
        return khieuNaiRepository.findById(id);
    }

    public KhieuNai saveKhieuNai(KhieuNai khieuNai) {
        return khieuNaiRepository.save(khieuNai);
    }

    public void deleteKhieuNai(int id) {
        khieuNaiRepository.deleteById(id);
    }
}