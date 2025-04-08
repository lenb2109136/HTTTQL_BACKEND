package com.example.hethongquanly.service;

import jakarta.mail.*;
import jakarta.mail.internet.*;
import jakarta.mail.util.ByteArrayDataSource;
import jakarta.activation.DataHandler;
import jakarta.activation.DataSource;

import org.springframework.stereotype.Service;

import java.util.Properties;

@Service
public class SendEmail {

    public boolean taomail(String email, String tieude, String noidung, byte[] pdfData) {
        final String username = "ntngon1230@gmail.com";
        final String password = "bpzfoypzqyzcbkor";

        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        Session session = Session.getInstance(properties, new GmailAuthenticator(username, password));

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username, "Hệ thống gửi lương", "UTF-8"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));

            message.setSubject(MimeUtility.encodeText(tieude, "UTF-8", "B"));

            MimeMultipart multipart = new MimeMultipart();

            MimeBodyPart textPart = new MimeBodyPart();
            String htmlContent = "<html><body><strong>" + tieude + "</strong><br>" + noidung + "</body></html>";
            textPart.setContent(htmlContent, "text/html; charset=UTF-8");
            multipart.addBodyPart(textPart);

            MimeBodyPart pdfPart = new MimeBodyPart();
            DataSource source = new ByteArrayDataSource(pdfData, "application/pdf");
            pdfPart.setDataHandler(new DataHandler(source));
            pdfPart.setFileName("phieuluong.pdf");
            multipart.addBodyPart(pdfPart);

            message.setContent(multipart);

            Transport.send(message);
            System.out.println("✅ Email đã gửi thành công!");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void main(String[] args) {
        SendEmail sendEmail = new SendEmail();
        String email = "receiver@example.com";
        String tieude = "Tiêu đề mail";
        String noidung = "Đây là nội dung của email.";
        byte[] pdfData = {};

        sendEmail.taomail(email, tieude, noidung, pdfData);
    }
}

class GmailAuthenticator extends Authenticator {
    private String username;
    private String password;

    public GmailAuthenticator(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(username, password);
    }
}