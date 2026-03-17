package fpoly.vinv01.duanmau.Model;

import java.io.Serializable;

public class NhanVien implements Serializable {
    private String maNV;
    private String hoTen;
    private String diaChi;
    private String chucVu;
    private int luong;
    private String matKhau;

    public NhanVien() {
    }

    public NhanVien(String maNV, String hoTen, String diaChi, String chucVu, int luong, String matKhau) {
        this.maNV = maNV;
        this.hoTen = hoTen;
        this.diaChi = diaChi;
        this.chucVu = chucVu;
        this.luong = luong;
        this.matKhau = matKhau;
    }

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getChucVu() {
        return chucVu;
    }

    public void setChucVu(String chucVu) {
        this.chucVu = chucVu;
    }

    public int getLuong() {
        return luong;
    }

    public void setLuong(int luong) {
        this.luong = luong;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }
}
