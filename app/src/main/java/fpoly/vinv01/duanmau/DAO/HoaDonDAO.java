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
                    hd.setMaKH(cursor.getString(3));
                    hd.setTenKhachHang(cursor.getString(4));
                    hd.setTongTien(cursor.getInt(5));
                    hd.setTenNV(cursor.getString(6));
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
        // Cập nhật: Sắp xếp theo giá trị số để tránh lỗi khi mã hóa đơn vượt mốc 999
        Cursor cursor = db.rawQuery("SELECT maHD FROM HoaDon ORDER BY CAST(SUBSTR(maHD, 3) AS INTEGER) DESC LIMIT 1", null);
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
    public boolean insertFull(HoaDon hd, List<HoaDonChiTiet> listCT) {
        db = dbHelper.getWritableDatabase();
        try {
            db.beginTransaction();

            // 1. Insert bảng HoaDon
            ContentValues vHD = new ContentValues();
            vHD.put("maHD", hd.getMaHD());
            vHD.put("ngayLap", hd.getNgayLap());
            vHD.put("maNV", hd.getMaNV());
            vHD.put("maKH", hd.getMaKH()); // Đã có maKH từ DbHelper version 5
            vHD.put("tenKhachHang", hd.getTenKhachHang());
            vHD.put("tongTien", hd.getTongTien());

            long checkHD = db.insert("HoaDon", null, vHD);
            if (checkHD == -1) return false; // Thất bại thì thoát luôn

            // 2. Insert danh sách bảng HoaDonChiTiet
            for (HoaDonChiTiet ct : listCT) {
                // Cập nhật: Kiểm tra số lượng tồn kho trước khi thanh toán
                Cursor cSP = db.rawQuery("SELECT soLuong FROM SanPham WHERE maSP = ?", new String[]{String.valueOf(ct.getMaSP())});
                int tonKho = 0;
                if (cSP != null && cSP.moveToFirst()) {
                    tonKho = cSP.getInt(0);
                    cSP.close();
                }
                if (tonKho < ct.getSoLuong()) {
                    return false; // Trả về false nếu không đủ hàng trong kho
                }

                ContentValues vCT = new ContentValues();
                vCT.put("maHD", hd.getMaHD());
                vCT.put("maSP", ct.getMaSP());
                vCT.put("soLuong", ct.getSoLuong());
                vCT.put("giaLucBan", ct.getGiaLucBan());

                long checkCT = db.insert("HoaDonChiTiet", null, vCT);
                if (checkCT == -1) return false; // Một dòng lỗi là hủy cả đơn hàng

                // 3. (Tùy chọn) Cập nhật trừ số lượng tồn kho ở đây
                db.execSQL("UPDATE SanPham SET soLuong = soLuong - ? WHERE maSP = ?",
                        new Object[]{ct.getSoLuong(), ct.getMaSP()});
            }

            db.setTransactionSuccessful();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            db.endTransaction();
        }
    }
}

