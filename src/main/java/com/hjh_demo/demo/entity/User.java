package com.hjh_demo.demo.entity;

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable{
    private String UserCode;
    private String Password;
    private String Gender;
    private String Status;
    private java.sql.Date Birthday;

    public java.sql.Date getBirthday() {
        return Birthday;
    }

    public void setBirthday(java.sql.Date birthday) {
        Birthday = birthday;
    }

    @Override
    public String toString() {
        return "User{" +
                "UserCode='" + UserCode + '\'' +
                ", Password='" + Password + '\'' +
                ", Gender='" + Gender + '\'' +
                ", Status='" + Status + '\'' +
                ", Birthday=" + Birthday +

                '}';
    }



    public String getUserCode() {
        return UserCode;
    }

    public void setUserCode(String userCode) {
        UserCode = userCode;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }
}
