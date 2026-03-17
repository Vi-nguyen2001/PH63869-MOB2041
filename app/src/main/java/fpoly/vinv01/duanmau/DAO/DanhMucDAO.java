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
    private DbHelper dbHelper;
    private SQLiteDatabase db;

    public DanhMucDAO(Context context) {
        dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public List<DanhMuc> getAll() {
        List<DanhMuc> list = new ArrayList<>();
        db = dbHelper.getReadableDatabase();
        Cursor cursor = null;
        try{
            db.beginTransaction();
            cursor = db.rawQuery("SELECT * FROM DanhMuc", null);
            if (cursor != null && cursor.moveToFirst()){
                while (!cursor.isAfterLast()){
                    DanhMuc dm = new DanhMuc();
                    dm.setMaDanhMuc(cursor.getInt(0));
                    dm.setTenDanhMuc(cursor.getString(1));
                    list.add(dm);
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

    public long insert(DanhMuc dm) {
       db= dbHelper.getWritableDatabase();
       long kq=-1;
       try{
           db.beginTransaction();
           ContentValues values = new ContentValues();
           values.put("tenDanhMuc", dm.getTenDanhMuc());
           kq = db.insert("DanhMuc", null, values);
           db.setTransactionSuccessful();
       }catch (Exception e){
           e.printStackTrace();
       }finally {
           db.endTransaction();
       }

        return kq;

    }

    public int update(DanhMuc dm) {
       db = dbHelper.getWritableDatabase();
       int kq = 0;
       try{
           db.beginTransaction();
           ContentValues values = new ContentValues();
           values.put("tenDanhMuc", dm.getTenDanhMuc());
           kq = db.update("DanhMuc", values, "maDanhMuc = ?", new String[]{String.valueOf(dm.getMaDanhMuc())});
           db.setTransactionSuccessful();
       }catch (Exception e){
           e.printStackTrace();
       }finally {
           db.endTransaction();
       }
       return kq;
    }

    public int delete(int maDanhMuc) {
        db = dbHelper.getWritableDatabase();
        int kq = 0;
        try{
            db.beginTransaction();
            kq = db.delete("DanhMuc", "maDanhMuc = ?", new String[]{String.valueOf(maDanhMuc)});
            db.setTransactionSuccessful();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            db.endTransaction();
        }
        return kq;
    }
}