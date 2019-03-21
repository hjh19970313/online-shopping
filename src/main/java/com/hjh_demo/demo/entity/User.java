package com.hjh_demo.demo.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import io.swagger.annotations.ApiModel;

import java.io.Serializable;
import java.util.Date;
@TableName("user")
@ApiModel
public class User implements Serializable{
    private String UserCode;
    private String Password;
    private String Gender;
    private String Status;
    private java.sql.Date Birthday;
    private String info; // 洗衣机--1  电视机--2 冰箱--3 空调--4

    @Override
    public String toString() {
        return "User{" +
                "UserCode='" + UserCode + '\'' +
                ", Password='" + Password + '\'' +
                ", Gender='" + Gender + '\'' +
                ", Status='" + Status + '\'' +
                ", Birthday=" + Birthday +
                ", info='" + info + '\'' +
                '}';
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public java.sql.Date getBirthday() {
        return Birthday;
    }

    public void setBirthday(java.sql.Date birthday) {
        Birthday = birthday;
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
