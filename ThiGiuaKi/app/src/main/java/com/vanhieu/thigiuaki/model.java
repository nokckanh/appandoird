package com.vanhieu.thigiuaki;

public class model {

    private int anInt;
    private String tencasi;
    private String tenbaihat;
    public model(){

    }

    public model(int anInt, String tencasi, String tenbaihat) {
        this.anInt = anInt;
        this.tencasi = tencasi;
        this.tenbaihat = tenbaihat;
    }

    public int getAnInt() {
        return anInt;
    }

    public void setAnInt(int anInt) {
        this.anInt = anInt;
    }

    public String getTencasi() {
        return tencasi;
    }

    public void setTencasi(String tencasi) {
        this.tencasi = tencasi;
    }

    public String getTenbaihat() {
        return tenbaihat;
    }

    public void setTenbaihat(String tenbaihat) {
        this.tenbaihat = tenbaihat;
    }
}
