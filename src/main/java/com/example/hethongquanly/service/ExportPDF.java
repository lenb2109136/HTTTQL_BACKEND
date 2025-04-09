package com.example.hethongquanly.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.example.hethongquanly.model.NhanVien;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

@Service
public class ExportPDF {

    private static Font titleFont;
    private static Font headerFont;
    private static Font normalFont;
    private static final DecimalFormat CURRENCY_FORMAT = new DecimalFormat("#,###.00 VND");
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");

    static {
        try {
            String fontPath = new ClassPathResource("Fonts/Roboto-ExtraLight.ttf").getFile().getAbsolutePath();
            FontFactory.register(fontPath, "customFont");
            titleFont = FontFactory.getFont("customFont", BaseFont.IDENTITY_H, BaseFont.EMBEDDED, 16, Font.BOLD);
            headerFont = FontFactory.getFont("customFont", BaseFont.IDENTITY_H, BaseFont.EMBEDDED, 12, Font.BOLD);
            normalFont = FontFactory.getFont("customFont", BaseFont.IDENTITY_H, BaseFont.EMBEDDED, 11);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static byte[] createPdf(Map<Object, Object> data) throws Exception {
        Document document = new Document(PageSize.A4);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, outputStream);
            document.open();

            // Thêm thông tin công ty
            Paragraph companyInfo = new Paragraph("CÔNG TY SAMSUNG", headerFont);
            companyInfo.setAlignment(Element.ALIGN_LEFT);
            document.add(companyInfo);

            Paragraph companyAddress = new Paragraph("Địa chỉ: Đại học Cần Thơ", normalFont);
            companyAddress.setAlignment(Element.ALIGN_LEFT);
            document.add(companyAddress);

            // Thêm ngày in
            Paragraph date = new Paragraph("Ngày in: " + DATE_FORMAT.format(new Date()), normalFont);
            date.setAlignment(Element.ALIGN_RIGHT);
            document.add(date);

            document.add(new Paragraph(" ", normalFont));

            // Tiêu đề
            Paragraph title = new Paragraph("PHIẾU LƯƠNG", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            title.setSpacingAfter(20f);
            document.add(title);

            // Thông tin nhân viên
            Map<String, Object> nvMap = (Map<String, Object>) data.get("thongtinnhanvien");
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            NhanVien nv = mapper.convertValue(nvMap, NhanVien.class);

            PdfPTable table = new PdfPTable(2);
            table.setWidthPercentage(100);
            table.setWidths(new float[]{1, 2});
            PdfPCell headerCell = createCell("Thông tin nhân viên", headerFont, 2, Element.ALIGN_CENTER);
            headerCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
            table.addCell(headerCell);
            table.addCell(createCell("Tên nhân viên", normalFont));
            table.addCell(createCell(nv.getNV_HOTEN() != null ? nv.getNV_HOTEN() : "", normalFont));
            table.addCell(createCell("Số điện thoại", normalFont));
            table.addCell(createCell(nv.getNV_SDT() != null ? nv.getNV_SDT() : "", normalFont));
            table.addCell(createCell("Giới tính", normalFont));
            table.addCell(createCell(nv.getNV_GIOITINH() == 1 ? "Nam" : "Nữ", normalFont));
            table.addCell(createCell("Email", normalFont));
            table.addCell(createCell(nv.getNV_EMAIL() != null ? nv.getNV_EMAIL() : "", normalFont));

            document.add(table);
            document.add(new Paragraph(" ", normalFont));

            // Thông tin lương
            Double luongCoBan = (data.get("luongcoban") != null) ? Double.valueOf(data.get("luongcoban").toString()) : 0.0;
            Double luongTangCa = (data.get("luongtangca") != null) ? Double.valueOf(data.get("luongtangca").toString()) : 0.0;
            Double tongKhauTru = (data.get("tongkhautru") != null) ? Double.valueOf(data.get("tongkhautru").toString()) : 0.0;
            Double luongNhan = (data.get("luongnhan") != null) ? Double.valueOf(data.get("luongnhan").toString()) : 0.0;

            PdfPTable salaryTable = new PdfPTable(2);
            salaryTable.setWidthPercentage(100);
            salaryTable.setWidths(new float[]{2, 1});
            headerCell = createCell("Thông tin lương", headerFont, 2, Element.ALIGN_CENTER);
            headerCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
            salaryTable.addCell(headerCell);
            salaryTable.addCell(createCell("Lương cơ bản", normalFont));
            salaryTable.addCell(createCell(formatCurrency(luongCoBan), normalFont, 1, Element.ALIGN_RIGHT));
            salaryTable.addCell(createCell("Lương tăng ca", normalFont));
            salaryTable.addCell(createCell(formatCurrency(luongTangCa), normalFont, 1, Element.ALIGN_RIGHT));
            salaryTable.addCell(createCell("Tổng khấu trừ", normalFont));
            salaryTable.addCell(createCell(formatCurrency(tongKhauTru), normalFont, 1, Element.ALIGN_RIGHT));
            salaryTable.addCell(createCell("Lương thực nhận", headerFont));
            salaryTable.addCell(createCell(formatCurrency(luongNhan), headerFont, 1, Element.ALIGN_RIGHT));

            document.add(salaryTable);
            document.add(new Paragraph(" ", normalFont));

            // Khoản khấu trừ
            List<Map<String, Object>> danhSachKhauTru = (List<Map<String, Object>>) data.get("danhsachkhautru");
            if (danhSachKhauTru != null && !danhSachKhauTru.isEmpty()) {
                Paragraph khoanKhauTruTitle = new Paragraph("KHOẢN KHẤU TRỪ", headerFont);
                khoanKhauTruTitle.setAlignment(Element.ALIGN_CENTER);
                khoanKhauTruTitle.setSpacingAfter(5f);
                document.add(khoanKhauTruTitle);

                PdfPTable deductionTable = new PdfPTable(3);
                deductionTable.setWidthPercentage(100);
                deductionTable.setWidths(new float[]{2, 1, 1});
                headerCell = createCell("Tên khoản khấu trừ", headerFont);
                headerCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                deductionTable.addCell(headerCell);
                headerCell = createCell("Thời điểm lập", headerFont);
                headerCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                deductionTable.addCell(headerCell);
                headerCell = createCell("Số tiền khấu trừ", headerFont, 1, Element.ALIGN_RIGHT);
                headerCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                deductionTable.addCell(headerCell);

                for (Map<String, Object> khauTru : danhSachKhauTru) {
                    Map<String, Object> khauTruInfo = (Map<String, Object>) khauTru.get("khautru");
                    String description = khauTruInfo != null ? (String) khauTruInfo.get("kt_DIENGIAI") : "";
                    String date1 = khauTru != null ? (String) khauTru.get("tienung") : "";
                    String amount = (khauTruInfo != null && khauTruInfo.get("kt_SOTIEN") != null) 
                        ? formatCurrency(khauTruInfo.get("kt_SOTIEN")) 
                        : formatCurrency(0.0);

                    deductionTable.addCell(createCell(description, normalFont));
                    deductionTable.addCell(createCell(date1, normalFont));
                    deductionTable.addCell(createCell(amount, normalFont, 1, Element.ALIGN_RIGHT));
                }

                document.add(deductionTable);
                document.add(new Paragraph(" ", normalFont));
            }

            // Khoản ứng lương
            List<Map<String, Object>> danhSachUngLuong = (List<Map<String, Object>>) data.get("danhsachungluong");
            if (danhSachUngLuong != null && !danhSachUngLuong.isEmpty()) {
                Paragraph khoanUngLuongTitle = new Paragraph("KHOẢN ỨNG LƯƠNG", headerFont);
                khoanUngLuongTitle.setAlignment(Element.ALIGN_CENTER);
                khoanUngLuongTitle.setSpacingAfter(5f);
                document.add(khoanUngLuongTitle);

                PdfPTable advanceSalaryTable = new PdfPTable(2);
                advanceSalaryTable.setWidthPercentage(100);
                advanceSalaryTable.setWidths(new float[]{1, 1});
                headerCell = createCell("Ngày ứng lương", headerFont);
                headerCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                advanceSalaryTable.addCell(headerCell);
                headerCell = createCell("Tổng tiền ứng", headerFont, 1, Element.ALIGN_RIGHT);
                headerCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                advanceSalaryTable.addCell(headerCell);

                for (Map<String, Object> ungLuong : danhSachUngLuong) {
                    String date1 = ungLuong != null ? (String) ungLuong.get("ul_NGAYUL") : "";
                    String amount = (ungLuong != null && ungLuong.get("ul_TIEN") != null) 
                        ? formatCurrency(ungLuong.get("ul_TIEN")) 
                        : formatCurrency(0.0);

                    advanceSalaryTable.addCell(createCell(date1, normalFont));
                    advanceSalaryTable.addCell(createCell(amount, normalFont, 1, Element.ALIGN_RIGHT));
                }

                document.add(advanceSalaryTable);
            }

            // Thêm phần chữ ký
            document.add(new Paragraph(" ", normalFont));
            document.add(new Paragraph(" ", normalFont));

            PdfPTable signatureTable = new PdfPTable(2);
            signatureTable.setWidthPercentage(100);
            signatureTable.setWidths(new float[]{1, 1});
            signatureTable.addCell(createCell("Người lập phiếu\nNguyễn Thiện Ngôn\n\n\n\n(Ký, ghi rõ họ tên)", normalFont, 1, Element.ALIGN_CENTER));
            signatureTable.addCell(createCell("Nhân viên\n\n\n\n(Ký, ghi rõ họ tên)", normalFont, 1, Element.ALIGN_CENTER));

            document.add(signatureTable);

        } catch (DocumentException e) {
            e.printStackTrace();
        } finally {
            document.close();
        }

        return outputStream.toByteArray();
    }

    private static PdfPCell createCell(String text, Font font) {
        return createCell(text, font, 1, Element.ALIGN_LEFT);
    }

    private static PdfPCell createCell(String text, Font font, int colspan, int alignment) {
        PdfPCell cell = new PdfPCell(new Phrase(text != null ? text : "", font));
        cell.setColspan(colspan);
        cell.setHorizontalAlignment(alignment);
        cell.setBorder(PdfPCell.BOX);
        cell.setPadding(5f);
        cell.setMinimumHeight(20f); // Đảm bảo chiều cao tối thiểu cho ô
        return cell;
    }

    private static String formatCurrency(Object value) {
        double amount = (value != null) ? Double.parseDouble(value.toString()) : 0.0;
        return CURRENCY_FORMAT.format(amount);
    }
}