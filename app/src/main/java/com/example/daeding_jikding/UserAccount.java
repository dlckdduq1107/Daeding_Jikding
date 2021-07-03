package com.example.daeding_jikding;

/**
 * 사용자 계정 정보 모델 클래스
 */
public class UserAccount {
    private String idToken;//firebase 고유아이디
    private String emailId;
    private String password;

    public UserAccount(){

    }

    public String getIdToken() {
        return idToken;
    }

    public void setIdToken(String idToken) {
        this.idToken = idToken;
    }

    public String getEmailId() {
        return emailId;
    }

    public String getPassword() {
        return password;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
