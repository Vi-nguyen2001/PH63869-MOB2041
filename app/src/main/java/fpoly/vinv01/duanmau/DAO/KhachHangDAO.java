package fpoly.vinv01.duanmau.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import fpoly.vinv01.duanmau.DbHelper;
import fpoly.vinv01.duanmau.Model.KhachHang;

public class KhachHangDAO {
    private SQLiteDatabase db;
    private DbHelper dbHelper;

    public KhachHangDAO(Context context) {
        dbHelper = new DbHelper(context);
    }

    public List<KhachHang> getAll() {
        List<KhachHang> list = new ArrayList<>();
        db = dbHelper.getReadableDatabase();
        Cursor cursor = null;
        try {
            db.beginTransaction();
            String sql = "SELECT * FROM KhachHang";
            cursor = db.rawQuery(sql, null);
            if (cursor != null && cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {
                    KhachHang kh = new KhachHang();
                    kh.setMaKH(cursor.getString(0));
                    kh.setHoTen(cursor.getString(1));
                    kh.setDienThoai(cursor.getString(2));
                    kh.setEmail(cursor.getString(3));
                    kh.setDiaChi(cursor.getString(4));
                    list.add(kh);
                    cursor.moveToNext();
                }
            }
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) cursor.close();
            db.endTransaction();
        }
        return list;
    }

    public long insert(KhachHang kh) {
        db = dbHelper.getWritableDatabase();
        long kq = -1;
        try {
            db.beginTransaction();
            ContentValues values = new ContentValues();
            values.put("maKH", kh.getMaKH());
            values.put("hoTen", kh.getHoTen());
            values.put("dienThoai", kh.getDienThoai());
            values.put("email", kh.getEmail());
            values.put("diaChi", kh.getDiaChi());
            kq = db.insert("KhachHang", null, values);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }
        return kq;
    }

    public int update(KhachHang kh) {
        db = dbHelper.getWritableDatabase();
        int kq = 0;
        try {
            db.beginTransaction();
            ContentValues values = new ContentValues();
            values.put("hoTen", kh.getHoTen());
            values.put("dienThoai", kh.getDienThoai());
            values.put("email", kh.getEmail());
            values.put("diaChi", kh.getDiaChi());
            kq = db.update("KhachHang", values, "maKH = ?", new String[]{kh.getMaKH()});
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }
        return kq;
    }

    public int delete(String maKH) {
        db = dbHelper.getWritableDatabase();
        int kq = 0;
        try {
            db.beginTransaction();
            kq = db.delete("KhachHang", "maKH = ?", new String[]{maKH});
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }
        return kq;
    }

    public String getNewMaKH() {
        db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT maKH FROM KhachHang ORDER BY maKH DESC LIMIT 1", null);
        if (cursor != null && cursor.moveToFirst()) {
            String lastMa = cursor.getString(0);
            cursor.close();
            try {
                int lastNum = Integer.parseInt(lastMa.substring(2));
                return String.format("KH%03d", lastNum + 1);
            } catch (Exception e) {
                return "KH001";
            }
        }
        return "KH001";
    }
}
