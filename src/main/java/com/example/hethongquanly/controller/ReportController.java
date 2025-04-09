package com.example.hethongquanly.controller;

import com.example.hethongquanly.model.PhieuLuong;
import com.example.hethongquanly.model.UngLuong;
import com.example.hethongquanly.repository.PhieuLuongRepository;
import com.example.hethongquanly.repository.UngLuongRepository;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ReportController {

    @Autowired
    private PhieuLuongRepository phieuLuongRepository;

    @Autowired
    private UngLuongRepository ungLuongRepository;

    // 1. Tổng Lương Theo Phòng Ban
    @GetMapping("/report/salary-by-department")
    public ResponseEntity<byte[]> generateSalaryByDepartmentReport(
            @RequestParam("month") int month,
            @RequestParam("year") int year) throws Exception {
        Calendar cal = Calendar.getInstance();
        cal.set(year, month - 1, 1, 0, 0, 0);
        Date startDate = cal.getTime();
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        Date endDate = cal.getTime();

        List<PhieuLuong> phieuLuongList = phieuLuongRepository.findByNgayPhatBetween(startDate, endDate);

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Salary By Department");

        // Thêm cột STT vào header
        Row headerRow = sheet.createRow(0);
        String[] columns = {"STT", "Phòng Ban", "Tổng Lương Cơ Bản", "Tổng Lương Tăng Ca", "Tổng Thu Nhập", 
                           "Tổng Khấu Trừ", "Tổng Ứng Lương", "Tổng Lương Thực Nhận"};
        for (int i = 0; i < columns.length; i++) {
            headerRow.createCell(i).setCellValue(columns[i]);
        }

        Map<String, double[]> totalsByDepartment = new HashMap<>();
        for (PhieuLuong pl : phieuLuongList) {
            String pbTen = pl.getNvId().getPB_ID().getPB_TEN();
            double[] totals = totalsByDepartment.getOrDefault(pbTen, new double[6]);
            totals[0] += pl.getLuongCoBan();
            totals[1] += pl.getLuongTangCa();
            totals[2] += pl.getTongThuNhap();
            totals[3] += pl.getTongKhauTru();
            totals[4] += pl.getUngLuong();
            totals[5] += pl.getLuongNhan();
            totalsByDepartment.put(pbTen, totals);
        }

        int rowNum = 1;
        int stt = 1; // Biến đếm STT
        for (Map.Entry<String, double[]> entry : totalsByDepartment.entrySet()) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(stt++); // Thêm STT
            row.createCell(1).setCellValue(entry.getKey());
            for (int i = 0; i < 6; i++) {
                row.createCell(i + 2).setCellValue(entry.getValue()[i]);
            }
        }

        for (int i = 0; i < columns.length; i++) {
            sheet.autoSizeColumn(i);
        }

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        workbook.write(out);
        workbook.close();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", "SalaryByDepartment_" + month + "-" + year + ".xlsx");
        return ResponseEntity.ok().headers(headers).body(out.toByteArray());
    }

    // 2. Chi Tiết Phiếu Lương Theo Nhân Viên
    @GetMapping("/report/salary-details")
    public ResponseEntity<byte[]> generateSalaryDetailsReport(
            @RequestParam("month") int month,
            @RequestParam("year") int year) throws Exception {
        Calendar cal = Calendar.getInstance();
        cal.set(year, month - 1, 1, 0, 0, 0);
        Date startDate = cal.getTime();
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        Date endDate = cal.getTime();

        List<PhieuLuong> phieuLuongList = phieuLuongRepository.findByNgayPhatBetween(startDate, endDate);

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Salary Details");

        // Thêm cột STT vào header
        Row headerRow = sheet.createRow(0);
        String[] columns = {"STT", "Mã NV", "Họ Tên", "Phòng Ban", "Ngày Tính Lương", "Lương Cơ Bản", 
                           "Lương Tăng Ca", "Tổng Thu Nhập", "Tổng Khấu Trừ", "Ứng Lương", 
                           "Lương Thực Nhận", "Trạng Thái"};
        for (int i = 0; i < columns.length; i++) {
            headerRow.createCell(i).setCellValue(columns[i]);
        }

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        int rowNum = 1;
        int stt = 1; // Biến đếm STT
        for (PhieuLuong pl : phieuLuongList) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(stt++); // Thêm STT
            row.createCell(1).setCellValue(pl.getNvId().getNV_ID());
            row.createCell(2).setCellValue(pl.getNvId().getNV_HOTEN());
            row.createCell(3).setCellValue(pl.getNvId().getPB_ID().getPB_TEN());
            row.createCell(4).setCellValue(sdf.format(pl.getNgayPhat()));
            row.createCell(5).setCellValue(pl.getLuongCoBan());
            row.createCell(6).setCellValue(pl.getLuongTangCa());
            row.createCell(7).setCellValue(pl.getTongThuNhap());
            row.createCell(8).setCellValue(pl.getTongKhauTru());
            row.createCell(9).setCellValue(pl.getUngLuong());
            row.createCell(10).setCellValue(pl.getLuongNhan());
            row.createCell(11).setCellValue(pl.getTrangThai());
        }

        for (int i = 0; i < columns.length; i++) {
            sheet.autoSizeColumn(i);
        }

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        workbook.write(out);
        workbook.close();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", "SalaryDetails_" + month + "-" + year + ".xlsx");
        return ResponseEntity.ok().headers(headers).body(out.toByteArray());
    }

    // 3. Tổng Hợp Khấu Trừ
    @GetMapping("/report/deduction-summary")
    public ResponseEntity<byte[]> generateDeductionSummaryReport(
            @RequestParam("month") int month,
            @RequestParam("year") int year) throws Exception {
        Calendar cal = Calendar.getInstance();
        cal.set(year, month - 1, 1, 0, 0, 0);
        Date startDate = cal.getTime();
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        Date endDate = cal.getTime();

        List<PhieuLuong> phieuLuongList = phieuLuongRepository.findByNgayPhatBetween(startDate, endDate);

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Deduction Summary");

        // Thêm cột STT vào header
        Row headerRow = sheet.createRow(0);
        String[] columns = {"STT", "Loại Khấu Trừ", "Tổng Số Tiền Khấu Trừ", "Số Nhân Viên Áp Dụng"};
        for (int i = 0; i < columns.length; i++) {
            headerRow.createCell(i).setCellValue(columns[i]);
        }

        Map<Double, Integer> deductionCount = new HashMap<>();
        for (PhieuLuong pl : phieuLuongList) {
            deductionCount.put(pl.getTongKhauTru(), 
                deductionCount.getOrDefault(pl.getTongKhauTru(), 0) + 1);
        }

        int rowNum = 1;
        int stt = 1; // Biến đếm STT
        for (Map.Entry<Double, Integer> entry : deductionCount.entrySet()) {
            Row row = sheet.createRow(rowNum++);
            double totalDeduction = entry.getKey() * entry.getValue();
            row.createCell(0).setCellValue(stt++); // Thêm STT
            row.createCell(1).setCellValue("Khấu trừ " + entry.getKey());
            row.createCell(2).setCellValue(totalDeduction);
            row.createCell(3).setCellValue(entry.getValue());
        }

        for (int i = 0; i < columns.length; i++) {
            sheet.autoSizeColumn(i);
        }

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        workbook.write(out);
        workbook.close();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", "DeductionSummary_" + month + "-" + year + ".xlsx");
        return ResponseEntity.ok().headers(headers).body(out.toByteArray());
    }

    // 4. Ứng Lương Theo Nhân Viên
    @GetMapping("/report/advance-salary")
    public ResponseEntity<byte[]> generateAdvanceSalaryReport(
            @RequestParam("month") int month,
            @RequestParam("year") int year) throws Exception {
        LocalDate startDate = LocalDate.of(year, month, 1);
        LocalDate endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());

        List<UngLuong> ungLuongList = ungLuongRepository.findByDateRange(startDate, endDate);

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Advance Salary");

        // Thêm cột STT vào header
        Row headerRow = sheet.createRow(0);
        String[] columns = {"STT", "Mã NV", "Ngày Ứng Lương", "Số Tiền Ứng", "Trạng Thái"};
        for (int i = 0; i < columns.length; i++) {
            headerRow.createCell(i).setCellValue(columns[i]);
        }

        int rowNum = 1;
        int stt = 1; // Biến đếm STT
        for (UngLuong ul : ungLuongList) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(stt++); // Thêm STT
            row.createCell(1).setCellValue(ul.getNV_ID());
            row.createCell(2).setCellValue(ul.getUL_NGAYUL().toString());
            row.createCell(3).setCellValue(ul.getUL_TIEN());
            row.createCell(4).setCellValue(ul.getUL_TRANGTHAI());
        }

        for (int i = 0; i < columns.length; i++) {
            sheet.autoSizeColumn(i);
        }

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        workbook.write(out);
        workbook.close();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", "AdvanceSalary_" + month + "-" + year + ".xlsx");
        return ResponseEntity.ok().headers(headers).body(out.toByteArray());
    }
}