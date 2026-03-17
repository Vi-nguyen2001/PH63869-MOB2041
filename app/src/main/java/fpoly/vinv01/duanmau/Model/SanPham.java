package fpoly.vinv01.duanmau.Model;

import java.io.Serializable;

public class SanPham implements Serializable {
    private int maSP;
    private String tenSP;
    private int giaBan;
    private String donViTinh;
    private int soLuong;
    private String ngayNhap;
    private int maDanhMuc;
    private String tenDanhMuc; // Để hiển thị ra list

    public SanPham() {}

    public SanPham(int maSP, String tenSP, int giaBan, String donViTinh, int soLuong, String ngayNhap, int maDanhMuc) {
        this.maSP = maSP;
        this.tenSP = tenSP;
        this.giaBan = giaBan;
        this.donViTinh = donViTinh;
        this.soLuong = soLuong;
        this.ngayNhap = ngayNhap;
        this.maDanhMuc = maDanhMuc;
    }

    // Getters and Setters
    public int getMaSP() { return maSP; }
    public void setMaSP(int maSP) { this.maSP = maSP; }
    public String getTenSP() { return tenSP; }
    public void setTenSP(String tenSP) { this.tenSP = tenSP; }
    public int getGiaBan() { return giaBan; }
    public void setGiaBan(int giaBan) { this.giaBan = giaBan; }
    public String getDonViTinh() { return donViTinh; }
    public void setDonViTinh(String donViTinh) { this.donViTinh = donViTinh; }
    public int getSoLuong() { return soLuong; }
    public void setSoLuong(int soLuong) { this.soLuong = soLuong; }
    public String getNgayNhap() { return ngayNhap; }
    public void setNgayNhap(String ngayNhap) { this.ngayNhap = ngayNhap; }
    public int getMaDanhMuc() { return maDanhMuc; }
    public void setMaDanhMuc(int maDanhMuc) { this.maDanhMuc = maDanhMuc; }
    public String getTenDanhMuc() { return tenDanhMuc; }
    public void setTenDanhMuc(String tenDanhMuc) { this.tenDanhMuc = tenDanhMuc; }
}