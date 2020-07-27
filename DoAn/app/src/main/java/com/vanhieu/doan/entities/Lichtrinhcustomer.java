package com.vanhieu.doan.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Lichtrinhcustomer {

    @SerializedName("id")
    @Expose
    int id;
    @SerializedName("xe_id")
    @Expose
    int xe_id;
    @SerializedName("tuyen_id")
    @Expose
    String tuyen_id;
    @SerializedName("xuatben")
    @Expose
    String xuatben;
    @SerializedName("ngaydi")
    @Expose
    Date ngaydi;
    @SerializedName("noidi")
    @Expose
    String noidi;
    @SerializedName("noiden")
    @Expose
    String noiden;
    @SerializedName("BSX")
    @Expose
    String BSX;
    @SerializedName("name")
    @Expose
    String name;
    @SerializedName("dongia")
    @Expose
    String dongia;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getXe_id() {
        return xe_id;
    }

    public void setXe_id(int xe_id) {
        this.xe_id = xe_id;
    }

    public String getTuyen_id() {
        return tuyen_id;
    }

    public void setTuyen_id(String tuyen_id) {
        this.tuyen_id = tuyen_id;
    }

    public String getXuatben() {
        return xuatben;
    }

    public void setXuatben(String xuatben) {
        this.xuatben = xuatben;
    }

    public Date getNgaydi() {
        return ngaydi;
    }

    public void setNgaydi(Date ngaydi) {
        this.ngaydi = ngaydi;
    }

    public String getNoidi() {
        return noidi;
    }

    public void setNoidi(String noidi) {
        this.noidi = noidi;
    }

    public String getNoiden() {
        return noiden;
    }

    public void setNoiden(String noiden) {
        this.noiden = noiden;
    }

    public String getBSX() {
        return BSX;
    }

    public void setBSX(String BSX) {
        this.BSX = BSX;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDongia() {
        return dongia;
    }

    public void setDongia(String dongia) {
        this.dongia = dongia;
    }
}
