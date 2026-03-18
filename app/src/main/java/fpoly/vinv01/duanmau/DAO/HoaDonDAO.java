package fpoly.vinv01.duanmau.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import fpoly.vinv01.duanmau.DbHelper;
import fpoly.vinv01.duanmau.Model.HoaDon;
import fpoly.vinv01.duanmau.Model.HoaDonChiTiet;

public class HoaDonDAO {
    private SQLiteDatabase db;
    private DbHelper dbHelper;

    public HoaDonDAO(Context context) {
        dbHelper = new DbHelper(context);
    }

    public List<HoaDon> getAll() {
        List<HoaDon> list = new ArrayList<>();
        db = dbHelper.getReadableDatabase();
        Cursor cursor = null;
        try {
            db.beginTransaction();
            String sql = "SELECT hd.*, nv.hoTen FROM HoaDon hd " +
                         "INNER JOIN NhanVien nv ON hd.maNV = nv.maNV";
            cursor = db.rawQuery(sql, null);
            if (cursor != null && cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {
                    HoaDon hd = new HoaDon();
                    hd.setMaHD(cursor.getString(0));
                    hd.setNgayLap(cursor.getString(1));
                    hd.setMaNV(cursor.getString(2));
                    hd.setTenKhachHang(cursor.getString(3));
                    hd.setTongTien(cursor.getInt(4));
                    hd.setTenNV(cursor.getString(5));
                    list.add(hd);
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

    public long insert(HoaDon hd) {
        db = dbHelper.getWritableDatabase();
        long kq = -1;
        try {
            db.beginTransaction();
            ContentValues values = new ContentValues();
            values.put("maHD", hd.getMaHD());
            values.put("ngayLap", hd.getNgayLap());
            values.put("maNV", hd.getMaNV());
            values.put("tenKhachHang", hd.getTenKhachHang());
            values.put("tongTien", hd.getTongTien());
            kq = db.insert("HoaDon", null, values);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }
        return kq;
    }

    public List<HoaDonChiTiet> getChiTietByMaHD(String maHD) {
        List<HoaDonChiTiet> list = new ArrayList<>();
        db = dbHelper.getReadableDatabase();
        Cursor cursor = null;
        try{
            db.beginTransaction();
            String sql = "SELECT ct.*, sp.tenSP FROM HoaDonChiTiet ct " +
                    "INNER JOIN SanPham sp ON ct.maSP = sp.maSP " +
                    "WHERE ct.maHD = ?";
            cursor = db.rawQuery(sql, new String[]{maHD});
            if (cursor != null && cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {
                    HoaDonChiTiet ct = new HoaDonChiTiet();
                    ct.setMaHDCT(cursor.getInt(0));
                    ct.setMaHD(cursor.getString(1));
                    ct.setMaSP(cursor.getInt(2));
                    ct.setSoLuong(cursor.getInt(3));
                    ct.setGiaLucBan(cursor.getInt(4));
                    ct.setTenSP(cursor.getString(5));
                    list.add(ct);
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

    public int delete(String maHD) {
        db = dbHelper.getWritableDatabase();
        int kq = 0;
        try {
            db.beginTransaction();
            db.delete("HoaDonChiTiet", "maHD = ?", new String[]{maHD});
            kq = db.delete("HoaDon", "maHD = ?", new String[]{maHD});
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }
        return kq;
    }

    public String getNewMaHD() {
        db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT maHD FROM HoaDon ORDER BY maHD DESC LIMIT 1", null);
        if (cursor != null && cursor.moveToFirst()) {
            String lastMa = cursor.getString(0);
            cursor.close();
            try {
                int lastNum = Integer.parseInt(lastMa.substring(2));
                return String.format("HD%03d", lastNum + 1);
            } catch (Exception e) {
                return "HD001";
            }
        }
        return "HD001";
    }
}
