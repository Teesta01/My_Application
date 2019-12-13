package com.example.myapplication;

public class UserInfo {
    String email;
    String userName;
    String mobileNo;
    String createdOn;
    String Type;
    public UserInfo(){}

    public UserInfo(String email, String userName, String mobileNo, String createdOn,String type) {
        this.email = email;
        this.userName = userName;
        this.mobileNo = mobileNo;
        this.createdOn = createdOn;
        this.Type = type;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }
}
