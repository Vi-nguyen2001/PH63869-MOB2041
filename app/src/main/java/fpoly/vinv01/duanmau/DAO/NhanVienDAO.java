package fpoly.vinv01.duanmau.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import fpoly.vinv01.duanmau.DbHelper;
import fpoly.vinv01.duanmau.Model.NhanVien;

public class NhanVienDAO {
    private SQLiteDatabase db;
    private DbHelper dbHelper;

    public NhanVienDAO(Context context) {
        dbHelper = new DbHelper(context);
    }

    public List<NhanVien> getAll() {
        List<NhanVien> list = new ArrayList<>();
        db = dbHelper.getReadableDatabase();
        Cursor cursor = null;
        try {
            db.beginTransaction();
            String sql = "SELECT * FROM NhanVien";
            cursor = db.rawQuery(sql, null);
            if (cursor != null && cursor.moveToFirst()) {
                while (!cursor.isAfterLast()){
                    NhanVien nv = new NhanVien();
                    nv.setMaNV(cursor.getString(0));
                    nv.setHoTen(cursor.getString(1));
                    nv.setDiaChi(cursor.getString(2));
                    nv.setChucVu(cursor.getString(3));
                    nv.setLuong(cursor.getInt(4));
                    nv.setMatKhau(cursor.getString(5));
                    list.add(nv);
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

    public long insert(NhanVien nv) {
        db = dbHelper.getWritableDatabase();
        long kq = -1;
        try {
            db.beginTransaction();
            ContentValues values = new ContentValues();
            values.put("maNV", nv.getMaNV());
            values.put("hoTen", nv.getHoTen());
            values.put("diaChi", nv.getDiaChi());
            values.put("chucVu", nv.getChucVu());
            values.put("luong", nv.getLuong());
            values.put("matKhau", nv.getMatKhau());
            kq = db.insert("NhanVien", null, values);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }
        return kq;
    }

    public int update(NhanVien nv) {
        db = dbHelper.getWritableDatabase();
        int kq = 0;
        try {
            db.beginTransaction();
            ContentValues values = new ContentValues();
            values.put("hoTen", nv.getHoTen());
            values.put("diaChi", nv.getDiaChi());
            values.put("chucVu", nv.getChucVu());
            values.put("luong", nv.getLuong());
            values.put("matKhau", nv.getMatKhau());
            kq = db.update("NhanVien", values, "maNV = ?", new String[]{nv.getMaNV()});
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }
        return kq;
    }

    public int delete(String maNV) {
        db = dbHelper.getWritableDatabase();
        int kq = 0;
        try {
            db.beginTransaction();
            kq = db.delete("NhanVien", "maNV = ?", new String[]{maNV});
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }
        return kq;
    }
    public String getNewMaNV() {//hàm mã nhân viên tự động tăng lên
        db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT maNV FROM NhanVien ORDER BY maNV DESC LIMIT 1", null);
        if (cursor != null && cursor.moveToFirst()) {
            String lastMa = cursor.getString(0);
            cursor.close();
            int lastNum = Integer.parseInt(lastMa.substring(2));
            return String.format("NV%03d", lastNum + 1);
        }
        return "NV001";
    }

    public int updatePassword(String maNV, String newPass) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("matKhau", newPass); // Cột 'matKhau' phải khớp với tên cột trong DbHelper
        // Cập nhật mật khẩu cho nhân viên có mã tương ứng
        return db.update("NhanVien", values, "maNV = ?", new String[]{maNV});
    }
    public boolean checkOldPassword(String maNV, String oldPass) {
        db = dbHelper.getReadableDatabase();
        String sql = "SELECT * FROM NhanVien WHERE maNV = ? AND matKhau = ?";
        Cursor cursor = db.rawQuery(sql, new String[]{maNV, oldPass});
        boolean result = cursor.getCount() > 0;
        cursor.close();
        return result;
    }

    public boolean checkLogin(String username, String password) {
        db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM NhanVien WHERE maNV = ? AND matKhau = ?", new String[]{username, password});
        int count = cursor.getCount();
        cursor.close();
        return count >0;

    }

}
