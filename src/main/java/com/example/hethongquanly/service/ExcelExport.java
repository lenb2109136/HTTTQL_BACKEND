package com.example.hethongquanly.service;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import com.example.hethongquanly.model.NhanVien;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class ExcelExport {
	
    public static byte[] exportToExcel(List<Map<Object, Object>> dataList) {
        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Danh sách nhân viên");
            Row headerRow = sheet.createRow(0);
            String[] headers = {"STT", "Họ Tên", "Lương cơ bản", "Lương tăng ca", "Lương khấu trừ", "Tổng lương nhân viên", "Lương ứng trước", "Lương thực lãnh"};
            
            // Style cho header
            CellStyle headerStyle = workbook.createCellStyle();
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerStyle.setFont(headerFont);
            
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
                cell.setCellStyle(headerStyle);
            }
            
            int rowNum = 1;
            for (Map<Object, Object> item : dataList) {
                Row row = sheet.createRow(rowNum);
                
                // Lấy thông tin nhân viên an toàn
                NhanVien employee = (NhanVien) item.get("thongtinnhanvien");
                String hoTen = (employee != null && employee.getNV_HOTEN() != null) ? employee.getNV_HOTEN() : "Không rõ";
                
                // Ghi dữ liệu vào Excel, kiểm tra null tránh lỗi
                row.createCell(0).setCellValue(rowNum); 
                row.createCell(1).setCellValue(hoTen);
                row.createCell(2).setCellValue(getDoubleValue(item.get("luongcoban")));
                row.createCell(3).setCellValue(getDoubleValue(item.get("luongtangca"))); 
                row.createCell(4).setCellValue(getDoubleValue(item.get("tongkhautru"))); 
                row.createCell(5).setCellValue(getDoubleValue(item.get("tongthunhap"))); 
                row.createCell(6).setCellValue(getDoubleValue(item.get("tongungluong"))); 
                row.createCell(7).setCellValue(getDoubleValue(item.get("luongnhan")));



                
                // Debug log
                System.out.println("Ghi dữ liệu nhân viên: " + hoTen);
                rowNum++;
            }
            
            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }
            
            workbook.write(outputStream);
            workbook.close(); // Đảm bảo workbook được đóng an toàn
            return outputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public static double getDoubleValue(Object obj) {
        if (obj == null) return 0;
        try {
            String str = obj.toString()
                            .replace("₫", "")
                            .replace(" ", "")       // khoảng trắng đặc biệt
                            .replace(".", "")       // bỏ dấu chấm ngăn cách hàng nghìn
                            .replace(",", ".")      // chuyển dấu phẩy (nếu có) sang dấu chấm thập phân
                            .trim();
            return Double.parseDouble(str);
        } catch (Exception e) {
            return 0;
        }
    }



}
