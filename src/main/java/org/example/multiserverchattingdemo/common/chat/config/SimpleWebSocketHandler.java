//package org.example.multiserverchattingdemo.common.chat.config;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Component;
//import org.springframework.web.socket.handler.TextWebSocketHandler;
//
//import org.springframework.stereotype.Component;
//import org.springframework.web.socket.*;
//import org.springframework.web.socket.handler.TextWebSocketHandler;
//
//import java.util.Set;
//import java.util.concurrent.ConcurrentHashMap;
//
////connect(http 요청은 아님)로 웹소 연결 요청이 들어왔을 때 이를 처리할 클래스다.
////클라이언트의 정보를 메모리(set 자료구조)에 저장하여 관리한다.
////filter에서 /connect는 http 요청이 아니기에 인증 객체를 만들 수 없어 토큰 필터에서 제외한다.
////http가 아니기에 cors 설정을 별도로 해줘야 한다.
//@Component
//@Slf4j
//public class SimpleWebSocketHandler extends TextWebSocketHandler {
//
//    // 그냥 HashSet은 스레드 세이프하지 않음.
//    private final Set<WebSocketSession> sessions = ConcurrentHashMap.newKeySet();
//
//    // 연결이 됬을 때
//    @Override
//    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
//        sessions.add(session); // 서버에서 사용자에게 메시지를 보낼 때 필요한 정보들
//        log.debug("Connected : " + session.getId());
//    }
//
//    // 메시지가 들어왔을 때
//    @Override
//    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
//        String payload = message.getPayload();
//        log.debug("received message : " + payload);
//
//        // 메시지를 받을 수 있는 모든 세션에 메시지를 전달한다.
//        for(WebSocketSession s : sessions){
//            if(s.isOpen()){
//                s.sendMessage(new TextMessage(payload));
//            }
//        }
//    }
//
//
//    //세션을 닫을 때
//    @Override
//    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
//        sessions.remove(session);
//        System.out.println("disconnected!!");
//    }
//
//
//}
