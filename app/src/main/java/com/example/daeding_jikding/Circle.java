package com.example.daeding_jikding;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.HashMap;

public class Circle implements Parcelable {
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


    ////

    protected Circle(Parcel in) {
        circleToken = in.readString();
        circle_name = in.readString();
        if (in.readByte() == 0x01) {
            members = new ArrayList<String>();
            in.readList(members, String.class.getClassLoader());
        } else {
            members = null;
        }
        type = in.readString();
        circle_description = in.readString();
        byte officialVal = in.readByte();
        official = officialVal == 0x02 ? null : officialVal != 0x00;
        byte certifyVal = in.readByte();
        certify = certifyVal == 0x02 ? null : certifyVal != 0x00;
        if (in.readByte() == 0x01) {
            messages = new ArrayList<ChatMessage>();
            in.readList(messages, ChatMessage.class.getClassLoader());
        } else {
            messages = null;
        }
        rank = in.readString();
        profile = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(circleToken);
        dest.writeString(circle_name);
        if (members == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(members);
        }
        dest.writeString(type);
        dest.writeString(circle_description);
        if (official == null) {
            dest.writeByte((byte) (0x02));
        } else {
            dest.writeByte((byte) (official ? 0x01 : 0x00));
        }
        if (certify == null) {
            dest.writeByte((byte) (0x02));
        } else {
            dest.writeByte((byte) (certify ? 0x01 : 0x00));
        }
        if (messages == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(messages);
        }
        dest.writeString(rank);
        dest.writeString(profile);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Circle> CREATOR = new Parcelable.Creator<Circle>() {
        @Override
        public Circle createFromParcel(Parcel in) {
            return new Circle(in);
        }

        @Override
        public Circle[] newArray(int size) {
            return new Circle[size];
        }
    };
}
