package com.example.demo.controller;

import com.example.demo.entity.Message;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

public class ChatController extends AbstractWebSocketHandler{
    //保存会话
    private static CopyOnWriteArrayList<WebSocketSession> sessionList=new CopyOnWriteArrayList();
    //保存用户名
    private static CopyOnWriteArrayList<String> userList=new CopyOnWriteArrayList();
    //后台对象转json用
    private ObjectMapper objectMapper=new ObjectMapper();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String userName=session.getAttributes().get("userName").toString();
        //保存当前登录的session
        sessionList.add(session);
        //保存用户名
        userList.add(userName);
        //把所有人传到页面，页面更新在线列表
        Message message = new Message("系统消息",
                userName+"上线了！",0);
        String jsonStr=objectMapper.writeValueAsString(message);
        sendMessage(jsonStr);//给页面发送json数据
        sendOnlineUsers();//发送在线用户列表
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        String userName=session.getAttributes().get("userName").toString();
        String userMessage=message.getPayload().toString();
        int backUpIndex=userMessage.indexOf("\0");
        if(backUpIndex >= 0){
            Message message0 = new Message("系统消息",
                    userName+"撤回了一条消息！",0);
            sendMessage(objectMapper.writeValueAsString(message0));
        }
            Message message1=new Message(userName,userMessage,1);
            //添加一个用户信息的唯一标识
            message1.setCid(UUID.randomUUID().toString());
            sendMessage(objectMapper.writeValueAsString(message1));


    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {

        System.out.println("发生错误！"+exception);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessionList.remove(session);
        String userName=session.getAttributes().get("userName").toString();
        //删除用户
        userList.remove(userName);
        //发送用户列表
        sendOnlineUsers();
        Message message = new Message("系统消息",userName+"下线了！",0);
        String jsonStr=objectMapper.writeValueAsString(message);
        sendMessage(jsonStr);

        session.close();
    }
    private void sendMessage(String jsonStr) throws IOException {
        //发送消息
        for (WebSocketSession session:sessionList) {
            if(!session.isOpen()) {
                sessionList.remove(session);
                continue;
            }
            session.sendMessage(new TextMessage(jsonStr));
        }
    }
    private void sendOnlineUsers() throws IOException {
        //发送在线用户列表
        String users=objectMapper.writeValueAsString(userList);
        Message message1=new Message("系统消息",users,-1);
        sendMessage(objectMapper.writeValueAsString(message1));
    }

//    public static void main(String[] args){
//        System.out.println(UUID.randomUUID().toString());
//    }

}
