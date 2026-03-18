package fpoly.vinv01.duanmau.Model;

import java.io.Serializable;

public class HoaDon implements Serializable {
    private String maHD;
    private String ngayLap;
    private String maNV;
    private String tenKhachHang;
    private int tongTien;
    private String tenNV; // Thêm trường này để hiển thị tên NV lập hóa đơn

    public HoaDon() {
    }

    public HoaDon(String maHD, String ngayLap, String maNV, String tenKhachHang, int tongTien) {
        this.maHD = maHD;
        this.ngayLap = ngayLap;
        this.maNV = maNV;
        this.tenKhachHang = tenKhachHang;
        this.tongTien = tongTien;
    }

    public String getMaHD() { return maHD; }
    public void setMaHD(String maHD) { this.maHD = maHD; }
    public String getNgayLap() { return ngayLap; }
    public void setNgayLap(String ngayLap) { this.ngayLap = ngayLap; }
    public String getMaNV() { return maNV; }
    public void setMaNV(String maNV) { this.maNV = maNV; }
    public String getTenKhachHang() { return tenKhachHang; }
    public void setTenKhachHang(String tenKhachHang) { this.tenKhachHang = tenKhachHang; }
    public int getTongTien() { return tongTien; }
    public void setTongTien(int tongTien) { this.tongTien = tongTien; }
    public String getTenNV() { return tenNV; }
    public void setTenNV(String tenNV) { this.tenNV = tenNV; }
}
