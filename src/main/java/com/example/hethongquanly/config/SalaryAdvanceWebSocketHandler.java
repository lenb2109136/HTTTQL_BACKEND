package com.example.hethongquanly.config;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class SalaryAdvanceWebSocketHandler extends TextWebSocketHandler {
    private final CopyOnWriteArrayList<WebSocketSession> sessions = new CopyOnWriteArrayList<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.add(session);
        String message = session.getAttributes().get("subscribe") != null ? session.getAttributes().get("subscribe").toString() : "";
        if (!message.isEmpty()) {
            session.sendMessage(new TextMessage("Đã subscribe thành công cho " + message));
        }
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        if (payload.contains("subscribe")) {
            session.getAttributes().put("subscribe", payload.split(":")[1]);
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessions.remove(session);
    }

    public void notifyStatusUpdate(int nvId, String status) {
        String updateMessage = String.format(
            "{\"type\":\"statusUpdate\",\"nvId\":%d,\"UL_TRANGTHAI\":\"%s\",\"message\":\"Yêu cầu ứng lương đã được %s\",\"time\":\"Vừa xong\"}",
            nvId, status, status.equals("Đã duyệt") ? "phê duyệt" : "từ chối"
        );
        System.out.println("Sending WebSocket message: " + updateMessage); // Thêm log để debug
        sessions.stream().filter(WebSocketSession::isOpen).forEach(session -> {
            try {
                if (session.getAttributes().get("subscribe") != null && session.getAttributes().get("subscribe").toString().contains(String.valueOf(nvId))) {
                    session.sendMessage(new TextMessage(updateMessage));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}