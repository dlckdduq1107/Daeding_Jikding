package com.example.daeding_jikding;

public class CircleAccount {//동아리 정보 클래스
    private String circle_name;
    private String profile;
    private String circle_description;
    private String idToken;

    public CircleAccount() {
    }

    public String getCircle_name() {
        return circle_name;
    }

    public String getIdToken() {
        return idToken;
    }

    public void setIdToken(String idToken) {
        this.idToken = idToken;
    }

    public void setCircle_name(String circle_name) {
        this.circle_name = circle_name;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getCircle_description() {
        return circle_description;
    }

    public void setCircle_description(String circle_description) {
        this.circle_description = circle_description;
    }
}
