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
        // Lấy mã khách hàng lớn nhất hiện có
        Cursor cursor = db.rawQuery("SELECT maKH FROM KhachHang ORDER BY maKH DESC LIMIT 1", null);

        if (cursor != null && cursor.moveToFirst()) {
            String lastMa = cursor.getString(0);
            cursor.close();

            try {
                // Kiểm tra nếu chuỗi bắt đầu bằng "KH" và có độ dài đủ để cắt
                if (lastMa != null && lastMa.startsWith("KH") && lastMa.length() > 2) {
                    // Tách phần số sau chữ "KH"
                    String sNumber = lastMa.substring(2);
                    int lastNum = Integer.parseInt(sNumber);
                    // Tăng số lên 1 và định dạng lại thành KHxxx (ví dụ: KH002)
                    return String.format("KH%03d", lastNum + 1);
                }
            } catch (Exception e) {
                e.printStackTrace();
                // Nếu có lỗi định dạng (ví dụ mã là "KHabc"), trả về mã dự phòng an toàn
                return "KH001";
            }
        }

        // Nếu bảng trống hoặc cursor null, trả về mã mặc định đầu tiên
        return "KH001";
    }

    public boolean checkPhoneExists(String phone) {//kiểm tra có trùng số điện thoại
        db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM KhachHang WHERE dienThoai = ?", new String[]{phone});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    public KhachHang getKhachHangByPhone(String phone) {
        db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM KhachHang WHERE dienThoai = ?", new String[]{phone});
        KhachHang kh = null;
        if (cursor != null && cursor.moveToFirst()) {
            kh = new KhachHang();
            kh.setMaKH(cursor.getString(0));
            kh.setHoTen(cursor.getString(1));
            kh.setDienThoai(cursor.getString(2));
            kh.setEmail(cursor.getString(3));
            kh.setDiaChi(cursor.getString(4));
            cursor.close();
        }
        return kh;
    }

    public boolean CheckKHExists(String maKH) {//kiểm tra khách hàng óc trong hóa đơn chưa
        db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM HoaDon WHERE maKH = ?", new String[]{maKH});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }
}
