package fpoly.vinv01.duanmau.Model;

import java.io.Serializable;

public class HoaDonChiTiet implements Serializable {
    private int maHDCT;
    private String maHD;
    private int maSP;
    private int soLuong;
    private int giaLucBan;
    private String tenSP; // Thêm để hiển thị chi tiết

    public HoaDonChiTiet() {
    }

    public HoaDonChiTiet(int maHDCT, String maHD, int maSP, int soLuong, int giaLucBan) {
        this.maHDCT = maHDCT;
        this.maHD = maHD;
        this.maSP = maSP;
        this.soLuong = soLuong;
        this.giaLucBan = giaLucBan;
    }

    public int getMaHDCT() { return maHDCT; }
    public void setMaHDCT(int maHDCT) { this.maHDCT = maHDCT; }
    public String getMaHD() { return maHD; }
    public void setMaHD(String maHD) { this.maHD = maHD; }
    public int getMaSP() { return maSP; }
    public void setMaSP(int maSP) { this.maSP = maSP; }
    public int getSoLuong() { return soLuong; }
    public void setSoLuong(int soLuong) { this.soLuong = soLuong; }
    public int getGiaLucBan() { return giaLucBan; }
    public void setGiaLucBan(int giaLucBan) { this.giaLucBan = giaLucBan; }
    public String getTenSP() { return tenSP; }
    public void setTenSP(String tenSP) { this.tenSP = tenSP; }
}
