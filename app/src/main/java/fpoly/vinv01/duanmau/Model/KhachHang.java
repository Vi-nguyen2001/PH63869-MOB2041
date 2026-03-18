package fpoly.vinv01.duanmau.Model;

import java.io.Serializable;

public class KhachHang implements Serializable {
    private String maKH;
    private String hoTen;
    private String dienThoai;
    private String email;
    private String diaChi;

    public KhachHang() {
    }

    public KhachHang(String maKH, String hoTen, String dienThoai, String email, String diaChi) {
        this.maKH = maKH;
        this.hoTen = hoTen;
        this.dienThoai = dienThoai;
        this.email = email;
        this.diaChi = diaChi;
    }

    public String getMaKH() { return maKH; }
    public void setMaKH(String maKH) { this.maKH = maKH; }
    public String getHoTen() { return hoTen; }
    public void setHoTen(String hoTen) { this.hoTen = hoTen; }
    public String getDienThoai() { return dienThoai; }
    public void setDienThoai(String dienThoai) { this.dienThoai = dienThoai; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getDiaChi() { return diaChi; }
    public void setDiaChi(String diaChi) { this.diaChi = diaChi; }
}
