package fpoly.vinv01.duanmau.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import fpoly.vinv01.duanmau.DbHelper;
import fpoly.vinv01.duanmau.Model.SanPham;

public class SanPhamDAO {
    private SQLiteDatabase db;

    public SanPhamDAO(Context context) {
        DbHelper dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public List<SanPham> getAll() {
        List<SanPham> list = new ArrayList<>();
        // INNER JOIN để lấy tên danh mục
        String sql = "SELECT sp.*, dm.tenDanhMuc FROM SanPham sp " +
                     "INNER JOIN DanhMuc dm ON sp.maDanhMuc = dm.maDanhMuc";
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                SanPham sp = new SanPham();
                sp.setMaSP(cursor.getInt(0));
                sp.setTenSP(cursor.getString(1));
                sp.setGiaBan(cursor.getInt(2));
                sp.setDonViTinh(cursor.getString(3));
                sp.setSoLuong(cursor.getInt(4));
                sp.setNgayNhap(cursor.getString(5));
                sp.setMaDanhMuc(cursor.getInt(6));
                sp.setTenDanhMuc(cursor.getString(7));
                list.add(sp);
                cursor.moveToNext();
            }
        }
        cursor.close();
        return list;
    }

    public long insert(SanPham sp) {
        ContentValues values = new ContentValues();
        values.put("tenSP", sp.getTenSP());
        values.put("giaBan", sp.getGiaBan());
        values.put("donViTinh", sp.getDonViTinh());
        values.put("soLuong", sp.getSoLuong());
        values.put("ngayNhap", sp.getNgayNhap());
        values.put("maDanhMuc", sp.getMaDanhMuc());
        return db.insert("SanPham", null, values);
    }

    public int update(SanPham sp) {
        ContentValues values = new ContentValues();
        values.put("tenSP", sp.getTenSP());
        values.put("giaBan", sp.getGiaBan());
        values.put("donViTinh", sp.getDonViTinh());
        values.put("soLuong", sp.getSoLuong());
        values.put("ngayNhap", sp.getNgayNhap());
        values.put("maDanhMuc", sp.getMaDanhMuc());
        return db.update("SanPham", values, "maSP = ?", new String[]{String.valueOf(sp.getMaSP())});
    }

    public int delete(int maSP) {
        return db.delete("SanPham", "maSP = ?", new String[]{String.valueOf(maSP)});
    }
}