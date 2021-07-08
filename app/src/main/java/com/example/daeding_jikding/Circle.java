package com.example.daeding_jikding;

import java.util.ArrayList;

public class Circle {
    private String circleToken;
    private String circle_name;
    private ArrayList<String> members;
    private String type;
    private String circle_description;
    private Boolean official;
    private Boolean certify;
    private ArrayList<ChatMessage> messages;
    private String rank;
    private String profile;

    public Circle() {

    }

    public String getCircleToken() {
        return circleToken;
    }

    public void setCircleToken(String circleToken) {
        this.circleToken = circleToken;
    }

    public String getCircle_name() {
        return circle_name;
    }

    public void setCircle_name(String circle_name) {
        this.circle_name = circle_name;
    }

    public ArrayList<String> getMembers() {
        return members;
    }

    public void setMembers(ArrayList<String> members) {
        this.members = members;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCircle_description() {
        return circle_description;
    }

    public void setCircle_description(String circle_description) {
        this.circle_description = circle_description;
    }

    public Boolean getOfficial() {
        return official;
    }

    public void setOfficial(Boolean official) {
        this.official = official;
    }

    public Boolean getCertify() {
        return certify;
    }

    public void setCertify(Boolean certify) {
        this.certify = certify;
    }

    public ArrayList<ChatMessage> getMessages() {
        return messages;
    }

    public void setMessages(ArrayList<ChatMessage> messages) {
        this.messages = messages;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }
}
