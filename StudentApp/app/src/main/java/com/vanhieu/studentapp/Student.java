package com.vanhieu.studentapp;

import java.io.Serializable;

public class Student implements Serializable {

//    private int id;
    private String name;
    private Integer age;
    private String phone;
    private String address;
    private String description;
    private String ava;
    private String sex;

    public Student(String name, Integer age, String phone, String address, String sex) {
        this.name = name;
        this.age = age;
        this.phone = phone;
        this.address = address;
        this.sex = sex;
    }

    public Student(String name, Integer age, String phone, String address, String ava, String sex) {
        this.name = name;
        this.age = age;
        this.phone = phone;
        this.address = address;
        this.ava = ava;
        this.sex = sex;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }


    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAva() {
        return ava;
    }

    public void setAva(String ava) {
        this.ava = ava;
    }

    public Student() {

    }

//    public int getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }



}
