package fpoly.vinv01.duanmau;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "JPMart.db";
    public static final int DB_VERSION = 2; // Tăng version để cập nhật bảng mới

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

        // Chèn dữ liệu mẫu Danh mục
        db.execSQL("INSERT INTO DanhMuc (tenDanhMuc) VALUES ('Rau củ quả'), ('Bánh kẹo'), ('Đồ uống')");
        
        // Chèn dữ liệu mẫu Sản phẩm
        db.execSQL("INSERT INTO SanPham (tenSP, giaBan, donViTinh, soLuong, ngayNhap, maDanhMuc) VALUES " +
                "('Táo Envy', 120000, 'Kg', 50, '01/10/2023', 1), " +
                "('Bánh KitKat', 15000, 'Gói', 100, '02/10/2023', 2)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS SanPham");
        db.execSQL("DROP TABLE IF EXISTS DanhMuc");
        onCreate(db);
    }
}