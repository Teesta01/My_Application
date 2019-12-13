package com.example.myapplication;

public class SellerInfo {
    String email;
    String userName;
    String shopName;
    String shopAddress;
    String mobileNo;
    String Categories;
    String CreatedOn;
    String Type;
    public SellerInfo(){}

    public SellerInfo(String email, String userName, String shopName, String shopAddress, String mobileNo, String categories, String createdOn, String type) {
        this.email = email;
        this.userName = userName;
        this.shopName = shopName;
        this.shopAddress = shopAddress;
        this.mobileNo = mobileNo;
        Categories = categories;
        CreatedOn = createdOn;
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

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopAddress() {
        return shopAddress;
    }

    public void setShopAddress(String shopAddress) {
        this.shopAddress = shopAddress;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getCategories() {
        return Categories;
    }

    public void setCategories(String categories) {
        Categories = categories;
    }

    public String getCreatedOn() {
        return CreatedOn;
    }

    public void setCreatedOn(String createdOn) {
        CreatedOn = createdOn;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }
}
