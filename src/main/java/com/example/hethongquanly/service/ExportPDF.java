package com.example.hethongquanly.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

@Service
public class ExportPDF {

    private static Font customFont;
    private static Font customBoldFont;

    static {
        try {
            String fontPath = new ClassPathResource("Fonts/Roboto-ExtraLight.ttf").getFile().getAbsolutePath();
            FontFactory.register(fontPath, "customFont");
            customFont = FontFactory.getFont("customFont", BaseFont.IDENTITY_H, BaseFont.EMBEDDED, 12);
            customBoldFont = FontFactory.getFont("customFont", BaseFont.IDENTITY_H, BaseFont.EMBEDDED, 12, Font.BOLD);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static byte[] createPdf(Map<Object, Object> data) throws Exception {
        Document document = new Document();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, outputStream);
            document.open();
            Paragraph title = new Paragraph("Phiếu Lương", customBoldFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            document.add(new Paragraph(" "));

            Map<String, Object> thongTinNhanVien = (Map<String, Object>) data.get("thongtinnhanvien");
            Double luongCoBan = (data.get("luongcoban") != null) ? Double.valueOf(data.get("luongcoban").toString()) : 0.0;
            Double luongTangCa = (data.get("luongtangca") != null) ? Double.valueOf(data.get("luongtangca").toString()) : 0.0;
            Double tongKhauTru = (data.get("tongkhautru") != null) ? Double.valueOf(data.get("tongkhautru").toString()) : 0.0;
            Double luongNhan = (data.get("luongnhan") != null) ? Double.valueOf(data.get("luongnhan").toString()) : 0.0;

            List<Map<String, Object>> danhSachKhauTru = (List<Map<String, Object>>) data.get("danhsachkhautru");
            List<Map<String, Object>> danhSachUngLuong = (List<Map<String, Object>>) data.get("danhsachungluong");

            PdfPTable table = new PdfPTable(2);
            table.setWidthPercentage(100);
            table.addCell(createCell("Thông tin nhân viên", customBoldFont, 2, Element.ALIGN_CENTER));
            table.addCell(createCell("Tên nhân viên", customFont));
            table.addCell(createCell((String) thongTinNhanVien.get("nv_HOTEN"), customFont));
            table.addCell(createCell("Số điện thoại", customFont));
            table.addCell(createCell((String) thongTinNhanVien.get("nv_SDT"), customFont));
            table.addCell(createCell("Giới tính", customFont));
            table.addCell(createCell(((boolean) thongTinNhanVien.get("nv_GIOITINH")) ? "Nam" : "Nữ", customFont));
            table.addCell(createCell("Email", customFont));
            table.addCell(createCell((String) thongTinNhanVien.get("nv_EMAIL"), customFont));

            document.add(table);
            document.add(new Paragraph(" "));

            PdfPTable salaryTable = new PdfPTable(2);
            salaryTable.setWidthPercentage(100);
            salaryTable.addCell(createCell("Thông tin lương", customBoldFont, 2, Element.ALIGN_CENTER));
            salaryTable.addCell(createCell("Lương cơ bản", customFont));
            salaryTable.addCell(createCell(String.valueOf(luongCoBan), customFont));
            salaryTable.addCell(createCell("Lương tăng ca", customFont));
            salaryTable.addCell(createCell(String.valueOf(luongTangCa), customFont));
            salaryTable.addCell(createCell("Tổng khấu trừ", customFont));
            salaryTable.addCell(createCell(String.valueOf(tongKhauTru), customFont));
            salaryTable.addCell(createCell("Lương thực nhận", customBoldFont));
            salaryTable.addCell(createCell(String.valueOf(luongNhan), customBoldFont));

            document.add(salaryTable);
            document.add(new Paragraph(" "));

            if (danhSachKhauTru != null && !danhSachKhauTru.isEmpty()) {
                Paragraph khoanKhauTruTitle = new Paragraph("Khoản khấu trừ", customBoldFont);
                khoanKhauTruTitle.setAlignment(Element.ALIGN_CENTER);
                document.add(khoanKhauTruTitle);
                document.add(new Paragraph(" "));

                PdfPTable deductionTable = new PdfPTable(3);
                deductionTable.setWidthPercentage(100);
                deductionTable.addCell(createCell("Tên khoản khấu trừ", customBoldFont));
                deductionTable.addCell(createCell("Thời điểm lập", customBoldFont));
                deductionTable.addCell(createCell("Số tiền khấu trừ", customBoldFont));

                for (Map<String, Object> khauTru : danhSachKhauTru) {
                    Map<String, Object> khauTruInfo = (Map<String, Object>) khauTru.get("khautru");
                    deductionTable.addCell(createCell((String) khauTruInfo.get("kt_DIENGIAI"), customFont));
                    deductionTable.addCell(createCell((String) khauTru.get("tienung"), customFont));
                    deductionTable.addCell(createCell(String.valueOf(khauTruInfo.get("kt_SOTIEN")), customFont));
                }

                document.add(deductionTable);
                document.add(new Paragraph(" "));
            }

            if (danhSachUngLuong != null && !danhSachUngLuong.isEmpty()) {
                Paragraph khoanUngLuongTitle = new Paragraph("Khoản ứng lương", customBoldFont);
                khoanUngLuongTitle.setAlignment(Element.ALIGN_CENTER);
                document.add(khoanUngLuongTitle);
                document.add(new Paragraph(" "));

                PdfPTable advanceSalaryTable = new PdfPTable(2);
                advanceSalaryTable.setWidthPercentage(100);
                advanceSalaryTable.addCell(createCell("Ngày ứng lương", customBoldFont));
                advanceSalaryTable.addCell(createCell("Tổng tiền ứng", customBoldFont));

                for (Map<String, Object> ungLuong : danhSachUngLuong) {
                    advanceSalaryTable.addCell(createCell((String) ungLuong.get("ul_NGAYUL"), customFont));
                    advanceSalaryTable.addCell(createCell(String.valueOf(ungLuong.get("ul_TIEN")), customFont));
                }

                document.add(advanceSalaryTable);
            }

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
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setColspan(colspan);
        cell.setHorizontalAlignment(alignment);
        cell.setBorder(PdfPCell.BOX);
        return cell;
    }
}