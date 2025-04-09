package com.example.hethongquanly.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.example.hethongquanly.model.NhanVien;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.example.hethongquanly.model.NhanVien;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

@Service
public class ExportPDF {

    private static Font customFont;
    private static Font customBoldFont;
    public static Double parseDoubleSafe(Object value) {
        if (value == null) return 0.0;
        try {
            String s = value.toString().replace(",", "").replaceAll("[^0-9.\\-]", "");
            int countDots = s.length() - s.replace(".", "").length();
            if (countDots > 1) {
                // Trường hợp có nhiều dấu chấm, giữ dấu chấm cuối cùng làm dấu thập phân
                int lastDot = s.lastIndexOf(".");
                s = s.substring(0, lastDot).replace(".", "") + s.substring(lastDot);
            }
            return Double.parseDouble(s);
        } catch (NumberFormatException e) {
            return 0.0; // hoặc log lỗi nếu cần
        }
    }

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

    public static String formatCurrencyVND(double amount) {
        NumberFormat formatter = NumberFormat.getInstance(new Locale("vi", "VN"));
        return formatter.format(amount) + " VNĐ"; // hoặc dùng "₫" nếu thích
    }
    public static String formatCurrencyVND(Object amountObj) {
        if (amountObj == null) return "0 VNĐ";
        try {
            double amount = Double.parseDouble(amountObj.toString());
            NumberFormat formatter = NumberFormat.getInstance(new Locale("vi", "VN"));
            formatter.setMaximumFractionDigits(0); // nếu bạn muốn bỏ phần thập phân
            return formatter.format(amount) + " VNĐ";
        } catch (NumberFormatException e) {
            return "0 VNĐ";
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

            Map<String, Object> nvMap = (Map<String, Object>) data.get("thongtinnhanvien");
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            NhanVien nv = mapper.convertValue(nvMap, NhanVien.class);
            Double luongCoBan = parseDoubleSafe(data.get("luongcoban"));
            Double luongTangCa = parseDoubleSafe(data.get("luongtangca"));
            Double tongKhauTru = parseDoubleSafe(data.get("tongkhautru"));
            Double luongNhan = parseDoubleSafe(data.get("luongnhan"));


            List<Map<String, Object>> danhSachKhauTru = (List<Map<String, Object>>) data.get("danhsachkhautru");
            List<Map<String, Object>> danhSachUngLuong = (List<Map<String, Object>>) data.get("danhsachungluong");

            PdfPTable table = new PdfPTable(2);
            table.setWidthPercentage(100);
            table.addCell(createCell("Thông tin nhân viên", customBoldFont, 2, Element.ALIGN_CENTER));
            table.addCell(createCell("Tên nhân viên", customFont));
            table.addCell(createCell((String) nv.getNV_HOTEN(), customFont));
            table.addCell(createCell("Số điện thoại", customFont));
            table.addCell(createCell((String) nv.getNV_SDT(), customFont));
            table.addCell(createCell("Giới tính", customFont));
//            table.addCell(createCell((nv.getNV_GIOITINH() ? "Nam" : "Nữ", customFont));
//            table.addCell(createCell((nv.getNV_GIOITINH()==1 ? "Nam" : "Nữ", customFont)));
            table.addCell(createCell("Email", customFont));
            table.addCell(createCell((String) nv.getNV_EMAIL(), customFont));

            document.add(table);
            document.add(new Paragraph(" "));

            PdfPTable salaryTable = new PdfPTable(2);
            salaryTable.setWidthPercentage(100);
            salaryTable.addCell(createCell("Thông tin lương", customBoldFont, 2, Element.ALIGN_CENTER));
            salaryTable.addCell(createCell("Lương cơ bản", customFont));
            salaryTable.addCell(createCell("Lương cơ bản", customFont));
            salaryTable.addCell(createCell("Lương cơ bản", customFont));
            salaryTable.addCell(createCell(formatCurrencyVND(luongCoBan), customFont));

            salaryTable.addCell(createCell("Lương tăng ca", customFont));
            salaryTable.addCell(createCell(formatCurrencyVND(luongTangCa), customFont));

            salaryTable.addCell(createCell("Tổng khấu trừ", customFont));
            salaryTable.addCell(createCell(formatCurrencyVND(tongKhauTru), customFont));

            salaryTable.addCell(createCell("Lương thực nhận", customBoldFont));
            salaryTable.addCell(createCell(formatCurrencyVND(luongNhan), customBoldFont));




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
                    deductionTable.addCell(createCell(String.valueOf(khauTru.get("tienung")), customFont));

                    deductionTable.addCell(createCell(formatCurrencyVND(khauTruInfo.get("kt_SOTIEN")), customFont));
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

