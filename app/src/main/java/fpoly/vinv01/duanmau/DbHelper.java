package fpoly.vinv01.duanmau;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "JPMart.db";
    public static final int DB_VERSION = 4; // Tăng version để cập nhật bảng HoaDon

    public DbHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Tạo bảng Danh mục
        String createTableDanhMuc = "CREATE TABLE DanhMuc (" +
                "maDanhMuc INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "tenDanhMuc TEXT NOT NULL)";
        db.execSQL(createTableDanhMuc);

        // Tạo bảng Sản phẩm
        String createTableSanPham = "CREATE TABLE SanPham (" +
                "maSP INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "tenSP TEXT NOT NULL, " +
                "giaBan INTEGER NOT NULL, " +
                "donViTinh TEXT, " +
                "soLuong INTEGER DEFAULT 0, " +
                "ngayNhap TEXT, " +
                "maDanhMuc INTEGER, " +
                "FOREIGN KEY (maDanhMuc) REFERENCES DanhMuc(maDanhMuc))";
        db.execSQL(createTableSanPham);

        // Tạo bảng Nhân viên
        String createTableNhanVien = "CREATE TABLE NhanVien (" +
                "maNV TEXT PRIMARY KEY, " +
                "hoTen TEXT NOT NULL, " +
                "diaChi TEXT, " +
                "chucVu TEXT, " +
                "luong INTEGER, " +
                "matKhau TEXT NOT NULL)";
        db.execSQL(createTableNhanVien);

        // Tạo bảng Hóa đơn
        String createTableHoaDon = "CREATE TABLE HoaDon (" +
                "maHD TEXT PRIMARY KEY, " +
                "ngayLap TEXT NOT NULL, " +
                "maNV TEXT NOT NULL, " +
                "tenKhachHang TEXT, " +
                "tongTien INTEGER, " +
                "FOREIGN KEY (maNV) REFERENCES NhanVien(maNV))";
        db.execSQL(createTableHoaDon);

        // Tạo bảng Hóa đơn chi tiết
        String createTableHoaDonChiTiet = "CREATE TABLE HoaDonChiTiet (" +
                "maHDCT INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "maHD TEXT NOT NULL, " +
                "maSP INTEGER NOT NULL, " +
                "soLuong INTEGER NOT NULL, " +
                "giaLucBan INTEGER NOT NULL, " +
                "FOREIGN KEY (maHD) REFERENCES HoaDon(maHD), " +
                "FOREIGN KEY (maSP) REFERENCES SanPham(maSP))";
        db.execSQL(createTableHoaDonChiTiet);

        // Chèn dữ liệu mẫu
        db.execSQL("INSERT INTO DanhMuc (tenDanhMuc) VALUES ('Rau củ quả'), ('Bánh kẹo'), ('Đồ uống')");
        
        db.execSQL("INSERT INTO SanPham (tenSP, giaBan, donViTinh, soLuong, ngayNhap, maDanhMuc) VALUES " +
                "('Táo Envy', 120000, 'Kg', 50, '01/10/2023', 1), " +
                "('Bánh KitKat', 15000, 'Gói', 100, '02/10/2023', 2), " +
                "('Sữa tươi tiệt trùng', 28000, 'Hộp', 200, '20/12/2023', 3)");

        db.execSQL("INSERT INTO NhanVien (maNV, hoTen, diaChi, chucVu, luong, matKhau) VALUES " +
                "('NV001', 'Nguyễn Văn An', 'Hà Nội', 'Quản lý kho', 15000000, '123456'), " +
                "('NV002', 'Trần Thị Bình', 'TP.HCM', 'Nhân viên bán hàng', 7500000, '123456')");

        // Dữ liệu mẫu Hóa đơn
        db.execSQL("INSERT INTO HoaDon (maHD, ngayLap, maNV, tenKhachHang, tongTien) VALUES " +
                "('HD001', '20/10/2023 | 14:30', 'NV001', 'Trần Thị B', 500000), " +
                "('HD002', '20/10/2023 | 15:45', 'NV002', 'Phạm Văn D', 1200000)");

        // Dữ liệu mẫu HDCT
        db.execSQL("INSERT INTO HoaDonChiTiet (maHD, maSP, soLuong, giaLucBan) VALUES " +
                "('HD001', 3, 2, 28000)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS HoaDonChiTiet");
        db.execSQL("DROP TABLE IF EXISTS HoaDon");
        db.execSQL("DROP TABLE IF EXISTS SanPham");
        db.execSQL("DROP TABLE IF EXISTS DanhMuc");
        db.execSQL("DROP TABLE IF EXISTS NhanVien");
        onCreate(db);
    }
}