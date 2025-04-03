package com.example.hethongquanly.controller;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.hethongquanly.service.ExcelExport;
import com.example.hethongquanly.service.NhanVienService;

@Controller
@RequestMapping("/getexcel")
public class PhieuLuongExcelController {

    @Autowired
    private NhanVienService nhanVienService;

    @GetMapping("getexcel")
    public ResponseEntity<byte[]> getExcel(
            @RequestParam(name = "nvid", required = false, defaultValue = "0") int bophanid,
            @RequestParam(name = "nbd", required = false) LocalDate nbd,
            @RequestParam(name = "nkt", required = false) LocalDate nkt) {

        List<Map<Object, Object>> map = nhanVienService.getLuongByBoPhan(bophanid, nbd, nkt);
        
        // Kiểm tra dữ liệu
        if (map == null || map.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .body(null);
        }

        byte[] data = ExcelExport.exportToExcel(map);
        if (data == null || data.length == 0) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }

        // Kiểm tra ghi file ra ổ cứng có mở được không
        try (FileOutputStream fos = new FileOutputStream("test.xlsx")) {
            fos.write(data);
            System.out.println("File Excel đã được ghi thành công!");
        } catch (IOException e) {
            e.printStackTrace();
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"LuongNhanVien.xlsx\"");

        return new ResponseEntity<>(data, headers, HttpStatus.OK);
    }

}
