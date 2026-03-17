package fpoly.vinv01.duanmau;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "JPMart.db";
    public static final int DB_VERSION = 1;

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

        // Chèn dữ liệu mẫu
        db.execSQL("INSERT INTO DanhMuc (tenDanhMuc) VALUES ('Rau củ quả'), ('Bánh kẹo'), ('Đồ uống')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS DanhMuc");
        onCreate(db);
    }
}