package fpoly.vinv01.duanmau.Model;

import java.io.Serializable;

public class GioHang implements Serializable {
    private int maSP;
    private String tenSP;
    private int giaBan;
    private int soLuongMua;

    public GioHang() {
    }

    public GioHang(int maSP, String tenSP, int giaBan, int soLuongMua) {
        this.maSP = maSP;
        this.tenSP = tenSP;
        this.giaBan = giaBan;
        this.soLuongMua = soLuongMua;
    }

    public int getMaSP() {
        return maSP;
    }

    public void setMaSP(int maSP) {
        this.maSP = maSP;
    }

    public String getTenSP() {
        return tenSP;
    }

    public void setTenSP(String tenSP) {
        this.tenSP = tenSP;
    }

    public int getGiaBan() {
        return giaBan;
    }

    public void setGiaBan(int giaBan) {
        this.giaBan = giaBan;
    }

    public int getSoLuongMua() {
        return soLuongMua;
    }

    public void setSoLuongMua(int soLuongMua) {
        this.soLuongMua = soLuongMua;
    }
}