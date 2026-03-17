package fpoly.vinv01.duanmau.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import fpoly.vinv01.duanmau.DbHelper;
import fpoly.vinv01.duanmau.Model.DanhMuc;

public class DanhMucDAO {
    private SQLiteDatabase db;

    public DanhMucDAO(Context context) {
        DbHelper dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public List<DanhMuc> getAll() {
        List<DanhMuc> list = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM DanhMuc", null);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                DanhMuc dm = new DanhMuc(
                        cursor.getInt(0),
                        cursor.getString(1)
                );
                list.add(dm);
                cursor.moveToNext();
            }
        }
        cursor.close();
        return list;
    }

    public long insert(DanhMuc dm) {
        ContentValues values = new ContentValues();
        values.put("tenDanhMuc", dm.getTenDanhMuc());
        return db.insert("DanhMuc", null, values);
    }

    public int update(DanhMuc dm) {
        ContentValues values = new ContentValues();
        values.put("tenDanhMuc", dm.getTenDanhMuc());
        return db.update("DanhMuc", values, "maDanhMuc = ?", new String[]{String.valueOf(dm.getMaDanhMuc())});
    }

    public int delete(int maDanhMuc) {
        return db.delete("DanhMuc", "maDanhMuc = ?", new String[]{String.valueOf(maDanhMuc)});
    }
}