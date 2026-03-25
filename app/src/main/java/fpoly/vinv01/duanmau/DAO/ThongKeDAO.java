package fpoly.vinv01.duanmau.DAO;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import fpoly.vinv01.duanmau.DbHelper;
import fpoly.vinv01.duanmau.Model.CustomerTop;
import fpoly.vinv01.duanmau.Model.ProductTop;

public class ThongKeDAO {
    private DbHelper dbHelper;

    public ThongKeDAO(Context context) {
        dbHelper = new DbHelper(context);
    }

    public List<ProductTop> getTopProduct(String tuNgay, String denNgay, int limit) {//top sản phẩm
        List<ProductTop> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Thêm điều kiện JOIN với bảng HoaDon để lấy ngày lập (ngayLap)
        String sql = "SELECT sp.maSP, sp.tenSP, SUM(ct.soLuong) as soLuongBan " +
                "FROM HoaDonChiTiet ct " +
                "INNER JOIN SanPham sp ON ct.maSP = sp.maSP " +
                "INNER JOIN HoaDon hd ON ct.maHD = hd.maHD " +
                "WHERE (substr(hd.ngayLap,7,4) || substr(hd.ngayLap,4,2) || substr(hd.ngayLap,1,2)) BETWEEN ? AND ? " +
                "GROUP BY sp.maSP, sp.tenSP " +
                "ORDER BY soLuongBan DESC " +
                "LIMIT ?";

        // Khớp tên biến từ fromDate -> fromDateClean
        String tuNgayClean = tuNgay.substring(6,10) + tuNgay.substring(3,5) + tuNgay.substring(0,2);
        String denNgayClean = denNgay.substring(6,10) + denNgay.substring(3,5) + denNgay.substring(0,2);

        Cursor cursor = db.rawQuery(sql, new String[]{
                tuNgayClean,
                denNgayClean,
                String.valueOf(limit)
        });

        int rank = 1;
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                list.add(new fpoly.vinv01.duanmau.Model.ProductTop(
                        rank++,
                        cursor.getString(0), // Lấy trực tiếp mã SP từ DB
                        cursor.getString(1),
                        cursor.getInt(2),
                        fpoly.vinv01.duanmau.R.drawable.anhsanpham
                ));
                cursor.moveToNext();
            }
        }
        cursor.close();
        return list;
    }


    public List<CustomerTop> getTopCustomersByDate(String fromDate, String toDate, int limit) {
        List<CustomerTop> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();


        String sql = "SELECT kh.maKH, kh.hoTen, SUM(hd.tongTien) as totalSpent, COUNT(hd.maHD) as orderCount " +
                "FROM KhachHang kh " +
                "INNER JOIN HoaDon hd ON kh.maKH = hd.maKH " +
                "WHERE (substr(hd.ngayLap,7,4) || substr(hd.ngayLap,4,2) || substr(hd.ngayLap,1,2)) " +
                "BETWEEN ? AND ? " +
                "GROUP BY kh.maKH, kh.hoTen " +
                "ORDER BY totalSpent DESC " +
                "LIMIT ?";

        //  Format tham số truyền vào cũng về yyyyMMdd
        String fromDateClean = fromDate.substring(6,10) + fromDate.substring(3,5) + fromDate.substring(0,2);
        String toDateClean = toDate.substring(6,10) + toDate.substring(3,5) + toDate.substring(0,2);

        Cursor cursor = db.rawQuery(sql, new String[]{fromDateClean, toDateClean, String.valueOf(limit)});

        int rank = 1;
        if (cursor != null && cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                // Lấy mã khách hàng (Ví dụ: KH001)
                String maKH = cursor.getString(0);
                // Tách số an toàn: KH001 -> 1
                int idInt = 0;
                try {
                    idInt = Integer.parseInt(maKH.substring(2));
                } catch (Exception e) { e.printStackTrace(); }

                list.add(new CustomerTop(
                        rank++,
                        idInt,
                        cursor.getString(1),
                        cursor.getDouble(2),
                        cursor.getInt(3),
                        fpoly.vinv01.duanmau.R.drawable.anhkhachhang
                ));
                cursor.moveToNext();
            }
            cursor.close();
        }
        return list;
    }

    public int getDoanhThu(String tuNgay, String denNgay) {

        String sql = "SELECT SUM(tongTien) FROM HoaDon WHERE " +
                "(substr(ngayLap,7,4) || substr(ngayLap,4,2) || substr(ngayLap,1,2)) " +
                "BETWEEN ? AND ?";

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        //  Format tham số truyền vào cũng về yyyyMMdd
        String fromDate = tuNgay.substring(6,10) + tuNgay.substring(3,5) + tuNgay.substring(0,2);
        String toDate = denNgay.substring(6,10) + denNgay.substring(3,5) + denNgay.substring(0,2);

        Cursor cursor = db.rawQuery(sql, new String[]{fromDate, toDate});

        int result = 0;
        if (cursor.moveToFirst()) {
            result = cursor.getInt(0);
        }
        cursor.close();

        return result;
    }
}
