package com.example.hethongquanly.service;

import com.example.hethongquanly.model.HeSo;
import com.example.hethongquanly.embeded.HeSoId;
import com.example.hethongquanly.repository.HeSoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HeSoService {
    @Autowired
    private HeSoRepository heSoRepository;

    public List<HeSo> getAllHeSo() {
        return heSoRepository.findAll();
    }

    public Optional<HeSo> getHeSoById(HeSoId id) {
        return heSoRepository.findById(id);
    }

    public HeSo saveHeSo(HeSo heSo) {
        return heSoRepository.save(heSo);
    }

    public void deleteHeSo(HeSoId id) {
        heSoRepository.deleteById(id);
    }
}
