package com.babulgamit.rokomariapp;


public class UserRegistration {

    private String First_Name,Email_Number,MobileNo,Password,RePassword;

    public UserRegistration(String first_Name, String email_Number, String mobileNo, String password, String rePassword) {
        this.First_Name = first_Name;
        this.Email_Number = email_Number;
        this.MobileNo = mobileNo;
        this.Password = password;
        this.RePassword = rePassword;
    }

    public String getFirst_Name() {
        return First_Name;
    }

    public void setFirst_Name(String first_Name) {
        First_Name = first_Name;
    }

    public String getEmail_Number() {
        return Email_Number;
    }

    public void setEmail_Number(String email_Number) {
        Email_Number = email_Number;
    }

    public String getMobileNo() {
        return MobileNo;
    }

    public void setMobileNo(String mobileNo) {
        MobileNo = mobileNo;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getRePassword() {
        return RePassword;
    }

    public void setRePassword(String rePassword) {
        RePassword = rePassword;
    }
}
