package com.vanhieu.doan.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

public class Lichtrinh {

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
    @SerializedName("data")
    List<Lichtrinh> list;

    public Lichtrinh(int xe_id, String tuyen_id, String xuatben, Date ngaydi) {
        this.xe_id = xe_id;
        this.tuyen_id = tuyen_id;
        this.xuatben = xuatben;
        this.ngaydi = ngaydi;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Lichtrinh> getList() {
        return list;
    }

    public void setList(List<Lichtrinh> list) {
        this.list = list;
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
}
