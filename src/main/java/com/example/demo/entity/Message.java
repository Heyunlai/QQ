package com.example.demo.entity;

public class Message {
    //谁发送的消息
    private String userName;
    //消息的具体内容
    private String message;
    //发送消息的类型（-1:上线用户列表,0：系统消息，1：用户消息，2：抖动）
    private int type;
    private String cid;

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public Message(String userName, String message, int type) {
        this.userName = userName;
        this.message = message;
        this.type = type;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Message{" +
                "userName='" + userName + '\'' +
                ", message='" + message + '\'' +
                ", type=" + type +
                '}';
    }
}
