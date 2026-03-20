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
    private DbHelper dbHelper;


    public SanPhamDAO(Context context) {
        dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public List<SanPham> getAll() {
        List<SanPham> list = new ArrayList<>();
        db = dbHelper.getReadableDatabase();
        Cursor cursor = null;
        try{
            db.beginTransaction();
            String sql = "SELECT sp.*, dm.tenDanhMuc " +
                    "FROM SanPham sp " +
                    "LEFT JOIN DanhMuc dm ON sp.maDanhMuc = dm.maDanhMuc";
            cursor = db.rawQuery(sql, null);
            if (cursor != null && cursor.moveToFirst()){
                while (!cursor.isAfterLast()){
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
            db.setTransactionSuccessful();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (cursor != null)
                cursor.close();
            db.endTransaction();
        }
        return list;
    }

    public long insert(SanPham sp) {
        db = dbHelper.getWritableDatabase();
        long kq=-1;
        try{
            db.beginTransaction();
            ContentValues values = new ContentValues();
            values.put("tenSP", sp.getTenSP());
            values.put("giaBan", sp.getGiaBan());
            values.put("donViTinh", sp.getDonViTinh());
            values.put("soLuong", sp.getSoLuong());
            values.put("ngayNhap", sp.getNgayNhap());
            values.put("maDanhMuc", sp.getMaDanhMuc());
            kq = db.insert("SanPham", null, values);
            db.setTransactionSuccessful();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            db.endTransaction();
        }

        return kq;
    }

    public int update(SanPham sp) {
        db = dbHelper.getWritableDatabase();
        int kq = 0;
        try {
            db.beginTransaction();
            ContentValues values = new ContentValues();
            values.put("tenSP", sp.getTenSP());
            values.put("giaBan", sp.getGiaBan());
            values.put("donViTinh", sp.getDonViTinh());
            values.put("soLuong", sp.getSoLuong());
            values.put("ngayNhap", sp.getNgayNhap());
            values.put("maDanhMuc", sp.getMaDanhMuc());
            kq = db.update("SanPham", values, "maSP = ?", new String[]{String.valueOf(sp.getMaSP())});
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
            return kq;
        }

    }

    public int delete(int maSP) {
        db = dbHelper.getWritableDatabase();
        int kq = 0;
        try {
            db.beginTransaction();
            kq = db.delete("SanPham", "maSP = ?", new String[]{String.valueOf(maSP)});
            db.setTransactionSuccessful();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            db.endTransaction();
        }
        return kq;

    }
}