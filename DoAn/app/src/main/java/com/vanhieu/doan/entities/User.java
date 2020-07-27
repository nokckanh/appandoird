package com.vanhieu.doan.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.squareup.moshi.Json;

public class User {
    @SerializedName("id" )
    int id;
//    @Json(name = "name" )
    @SerializedName("name" )
    String name;
//    @Json(name = "phone" )
    @SerializedName("phone" )
    String phone;
//    @Json(name = "usertype" )
    @SerializedName("usertype" )
    String usertype;
//    @Json(name = "email" )
    @SerializedName("email" )
    String email;
    @SerializedName("xe_id" )
    String xe_id;

//    public int getXe_id() {
//        return xe_id;
//    }
//
//    public void setXe_id(int xe_id) {
//        this.xe_id = xe_id;
//    }


    public String getXe_id() {
        return xe_id;
    }

    public void setXe_id(String xe_id) {
        this.xe_id = xe_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public String getUsertype() {
        return usertype;
    }

    public void setUsertype(String usertype) {
        this.usertype = usertype;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
