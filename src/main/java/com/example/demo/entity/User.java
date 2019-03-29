package com.example.demo.entity;

import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

public class User implements Serializable{
    private String userName;
    private String password;
    private byte[] face;

    public byte[] getFace() {
        return face;
    }
    public void setFace(byte[] face) {
        this.face = face;
    }
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}