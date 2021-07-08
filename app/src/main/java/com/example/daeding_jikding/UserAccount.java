package com.example.daeding_jikding;

import java.util.ArrayList;
import java.util.List;

/**
 * 사용자 계정 정보 모델 클래스
 */
public class UserAccount {
    private String idToken;//firebase 고유아이디
    private String emailId;
    private String password;
    private List<String> circle_list = new ArrayList<>();
    private boolean SorG;


    public UserAccount(){

    }



    public boolean isSorG() {
        return SorG;
    }

    public void setSorG(boolean sorG) {
        SorG = sorG;
    }

    public List<String> getCircle_list() {
        return circle_list;
    }

    public void setCircle_list(List<String> circle_list) {
        this.circle_list = circle_list;
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
