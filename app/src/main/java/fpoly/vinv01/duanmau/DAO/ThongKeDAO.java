package fpoly.vinv01.duanmau.DAO;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import fpoly.vinv01.duanmau.DbHelper;
import fpoly.vinv01.duanmau.Model.ProductTop;

public class ThongKeDAO {
    private DbHelper dbHelper;

    public ThongKeDAO(Context context) {
        dbHelper = new DbHelper(context);
    }

    public List<ProductTop> getTopProduct(int limit) {
        List<ProductTop> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String sql = "SELECT sp.maSP, sp.tenSP, SUM(ct.soLuong) as soLuongBan " +
                "FROM HoaDonChiTiet ct " +
                "INNER JOIN SanPham sp ON ct.maSP = sp.maSP " +
                "GROUP BY sp.maSP, sp.tenSP " +
                "ORDER BY soLuongBan DESC " +
                "LIMIT " + limit;
        
        Cursor cursor = db.rawQuery(sql, null);
        int rank = 1;
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                list.add(new fpoly.vinv01.duanmau.Model.ProductTop(
                        rank++,
                        "SP" + String.format("%04d", cursor.getInt(0)),
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
}
