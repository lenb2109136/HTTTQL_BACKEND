package com.example.hethongquanly.controller;

import com.example.hethongquanly.model.PhieuLuong;
import com.example.hethongquanly.model.UngLuong;
import com.example.hethongquanly.repository.PhieuLuongRepository;
import com.example.hethongquanly.repository.UngLuongRepository;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
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
import java.text.DecimalFormat;
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

    private CellStyle createNumberStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        DataFormat format = workbook.createDataFormat();
        style.setDataFormat(format.getFormat("#,##0.00"));
        return style;
    }

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
        CellStyle numberStyle = createNumberStyle(workbook);

        // Add title
        Row titleRow = sheet.createRow(0);
        titleRow.createCell(0).setCellValue("BÁO CÁO TỔNG LƯƠNG THEO PHÒNG BAN - " + month + "/" + year);
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 7));

        // Header
        Row headerRow = sheet.createRow(1);
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

        int rowNum = 2;
        int stt = 1;
        for (Map.Entry<String, double[]> entry : totalsByDepartment.entrySet()) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(stt++);
            row.createCell(1).setCellValue(entry.getKey());
            for (int i = 0; i < 6; i++) {
                Cell cell = row.createCell(i + 2);
                cell.setCellValue(entry.getValue()[i]);
                cell.setCellStyle(numberStyle);
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
        CellStyle numberStyle = createNumberStyle(workbook);

        // Add title
        Row titleRow = sheet.createRow(0);
        titleRow.createCell(0).setCellValue("BÁO CÁO CHI TIẾT PHIẾU LƯƠNG - " + month + "/" + year);
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 11));

        // Header
        Row headerRow = sheet.createRow(1);
        String[] columns = {"STT", "Mã NV", "Họ Tên", "Phòng Ban", "Ngày Tính Lương", "Lương Cơ Bản", 
                           "Lương Tăng Ca", "Tổng Thu Nhập", "Tổng Khấu Trừ", "Ứng Lương", 
                           "Lương Thực Nhận", "Trạng Thái"};
        for (int i = 0; i < columns.length; i++) {
            headerRow.createCell(i).setCellValue(columns[i]);
        }

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        int rowNum = 2;
        int stt = 1;
        for (PhieuLuong pl : phieuLuongList) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(stt++);
            row.createCell(1).setCellValue(pl.getNvId().getNV_ID());
            row.createCell(2).setCellValue(pl.getNvId().getNV_HOTEN());
            row.createCell(3).setCellValue(pl.getNvId().getPB_ID().getPB_TEN());
            row.createCell(4).setCellValue(sdf.format(pl.getNgayPhat()));
            Cell cell5 = row.createCell(5); cell5.setCellValue(pl.getLuongCoBan()); cell5.setCellStyle(numberStyle);
            Cell cell6 = row.createCell(6); cell6.setCellValue(pl.getLuongTangCa()); cell6.setCellStyle(numberStyle);
            Cell cell7 = row.createCell(7); cell7.setCellValue(pl.getTongThuNhap()); cell7.setCellStyle(numberStyle);
            Cell cell8 = row.createCell(8); cell8.setCellValue(pl.getTongKhauTru()); cell8.setCellStyle(numberStyle);
            Cell cell9 = row.createCell(9); cell9.setCellValue(pl.getUngLuong()); cell9.setCellStyle(numberStyle);
            Cell cell10 = row.createCell(10); cell10.setCellValue(pl.getLuongNhan()); cell10.setCellStyle(numberStyle);
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
        CellStyle numberStyle = createNumberStyle(workbook);

        // Add title
        Row titleRow = sheet.createRow(0);
        titleRow.createCell(0).setCellValue("BÁO CÁO TỔNG HỢP KHẤU TRỪ - " + month + "/" + year);
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 3));

        // Header
        Row headerRow = sheet.createRow(1);
        String[] columns = {"STT", "Loại Khấu Trừ", "Tổng Số Tiền Khấu Trừ", "Số Nhân Viên Áp Dụng"};
        for (int i = 0; i < columns.length; i++) {
            headerRow.createCell(i).setCellValue(columns[i]);
        }

        Map<Double, Integer> deductionCount = new HashMap<>();
        for (PhieuLuong pl : phieuLuongList) {
            deductionCount.put(pl.getTongKhauTru(), 
                deductionCount.getOrDefault(pl.getTongKhauTru(), 0) + 1);
        }

        int rowNum = 2;
        int stt = 1;
        for (Map.Entry<Double, Integer> entry : deductionCount.entrySet()) {
            Row row = sheet.createRow(rowNum++);
            double totalDeduction = entry.getKey() * entry.getValue();
            row.createCell(0).setCellValue(stt++);
            row.createCell(1).setCellValue("Khấu trừ " + new DecimalFormat("#,##0.00").format(entry.getKey()));
            Cell cell2 = row.createCell(2); cell2.setCellValue(totalDeduction); cell2.setCellStyle(numberStyle);
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
        CellStyle numberStyle = createNumberStyle(workbook);

        // Add title
        Row titleRow = sheet.createRow(0);
        titleRow.createCell(0).setCellValue("BÁO CÁO ỨNG LƯƠNG THEO NHÂN VIÊN - " + month + "/" + year);
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 4));

        // Header
        Row headerRow = sheet.createRow(1);
        String[] columns = {"STT", "Mã NV", "Ngày Ứng Lương", "Số Tiền Ứng", "Trạng Thái"};
        for (int i = 0; i < columns.length; i++) {
            headerRow.createCell(i).setCellValue(columns[i]);
        }

        int rowNum = 2;
        int stt = 1;
        for (UngLuong ul : ungLuongList) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(stt++);
            row.createCell(1).setCellValue(ul.getNV_ID());
            row.createCell(2).setCellValue(ul.getUL_NGAYUL().toString());
            Cell cell3 = row.createCell(3); cell3.setCellValue(ul.getUL_TIEN()); cell3.setCellStyle(numberStyle);
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