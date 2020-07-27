package com.vanhieu.doan.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Chithu {

    @SerializedName("lichtrinh_id")
    @Expose
    int lichtrinh_id;
    @SerializedName("name")
    @Expose
    String name;
    @SerializedName("chithu")
    @Expose
    String chithu;
    @SerializedName("ghichu")
    @Expose
    String ghichu;

    public int getLichtrinh_id() {
        return lichtrinh_id;
    }

    public void setLichtrinh_id(int lichtrinh_id) {
        this.lichtrinh_id = lichtrinh_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getChithu() {
        return chithu;
    }

    public void setChithu(String chithu) {
        this.chithu = chithu;
    }

    public String getGhichu() {
        return ghichu;
    }

    public void setGhichu(String ghichu) {
        this.ghichu = ghichu;
    }
}
