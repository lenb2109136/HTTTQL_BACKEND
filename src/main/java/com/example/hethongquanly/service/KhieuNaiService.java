package com.example.hethongquanly.service;
import com.example.hethongquanly.model.KhieuNai;
import com.example.hethongquanly.model.NhanVien;
import com.example.hethongquanly.repository.KhieuNaiRepository;
import com.example.hethongquanly.repository.NhanVienRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

@Service
public class KhieuNaiService {

    @Autowired
    private KhieuNaiRepository khieuNaiRepository;

    @Autowired
    private NhanVienRepository nhanVienRepository;

    public List<KhieuNai> getAllKhieuNai() {
        return khieuNaiRepository.findAll();
    }

    public Optional<KhieuNai> getKhieuNaiById(int id) {
        return khieuNaiRepository.findById(id);
    }

    public KhieuNai saveKhieuNai(KhieuNai khieuNai) {
        if (khieuNai.getKn_TRANGTHAI() == null) {
            khieuNai.setKn_TRANGTHAI("Chờ xử lý");
        }
        return khieuNaiRepository.save(khieuNai);
    }

    public void deleteKhieuNai(int id) {
        khieuNaiRepository.deleteById(id);
    }

    public void respondToComplaint(int id, String responseContent, String newStatus) {
        Optional<KhieuNai> optionalKhieuNai = khieuNaiRepository.findById(id);
        if (!optionalKhieuNai.isPresent()) {
            throw new RuntimeException("Không tìm thấy khiếu nại với ID: " + id);
        }

        KhieuNai khieuNai = optionalKhieuNai.get();
        khieuNai.setKn_TRANGTHAI(newStatus);
        khieuNaiRepository.save(khieuNai);

        NhanVien nhanVien = nhanVienRepository.findById(khieuNai.getNv_ID().getNV_ID())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy nhân viên với ID: " + khieuNai.getNv_ID().getNV_ID()));

        String recipientEmail = nhanVien.getNV_EMAIL();
        if (recipientEmail == null || recipientEmail.trim().isEmpty()) {
            throw new RuntimeException("Email của nhân viên không hợp lệ hoặc trống: " + nhanVien.getNV_ID());
        }

        final String username = "ntngon1230@gmail.com";
        final String password = "hnrz boov jqmu wqcj".trim();

        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        // Định dạng ngày khiếu nại với LocalDateTime
        String formattedDate;
        if (khieuNai.getKn_NGAYKN() != null) {
            formattedDate = khieuNai.getKn_NGAYKN().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
        } else {
            formattedDate = "N/A"; // Giá trị mặc định nếu ngày null
        }

        // Tạo nội dung HTML với lời chào cá nhân
        String htmlBody = "<html>"
                + "<body style='font-family: Arial, sans-serif; margin: 0; padding: 20px;'>"
                + "<div style='text-align: center; margin-bottom: 20px;'>"
                + "<img src='https://upload.wikimedia.org/wikipedia/commons/thumb/2/24/Samsung_Logo.svg/1280px-Samsung_Logo.svg.png' alt='Samsung Logo' style='width: 150px; height: auto;' />"
                + "<h2 style='color: #000; margin: 10px 0;'>Samsung Việt Nam</h2>"
                + "</div>"
                + "<p style='font-size: 16px; color: #333;'>Kính gửi Anh/Chị " + nhanVien.getNV_HOTEN() + ",</p>"
                + "<p style='font-size: 16px; color: #333;'>Cảm ơn Anh/Chị đã gửi khiếu nại đến Công ty chúng tôi. Dưới đây là thông tin chi tiết về khiếu nại của Anh/Chị:</p>"
                + "<ul style='font-size: 16px; color: #333;'>"
                + "<li><strong>Ngày khiếu nại:</strong> " + formattedDate + "</li>"
                + "<li><strong>Nội dung khiếu nại:</strong> " + khieuNai.getKn_NOIDUNG() + "</li>"
                + "</ul>"
                + "<p style='font-size: 16px; color: #333;'><strong>Phản hồi từ Công ty:</strong><br>" + responseContent + "</p>"
                + "<p style='font-size: 16px; color: #333;'>Chúng tôi rất mong nhận được ý kiến đóng góp thêm từ Anh/Chị để cải thiện chất lượng dịch vụ. Mọi thắc mắc vui lòng liên hệ qua email này hoặc hotline: 1800-588-889.</p>"
                + "<p style='font-size: 16px; color: #333; text-align: center; margin-top: 20px;'>Trân trọng,<br>Phòng Chăm Sóc Khách Hàng<br>Công ty Samsung Việt Nam<br>Email: support@samsung.com | Website: www.samsung.com/vn</p>"
                + "</body>"
                + "</html>";

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username)); // Sửa lỗi từ InternetException thành InternetAddress
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
            message.setSubject("Phản hồi thông tin khiếu nại của nhân viên - Samsung Việt Nam");

            // Thiết lập nội dung HTML
            MimeBodyPart mimeBodyPart = new MimeBodyPart();
            mimeBodyPart.setContent(htmlBody, "text/html; charset=utf-8");

            MimeMultipart multipart = new MimeMultipart();
            multipart.addBodyPart(mimeBodyPart);

            message.setContent(multipart);
            Transport.send(message);
            System.out.println("Email sent successfully to: " + recipientEmail);
        } catch (MessagingException e) {
            System.err.println("Error sending email: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Lỗi khi gửi email: " + e.getMessage());
        }
    }
}