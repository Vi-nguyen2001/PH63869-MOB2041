package fpoly.vinv01.duanmau.Activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

import fpoly.vinv01.duanmau.Adapter.HoaDonAdapter;
import fpoly.vinv01.duanmau.DAO.HoaDonDAO;
import fpoly.vinv01.duanmau.Model.HoaDon;
import fpoly.vinv01.duanmau.R;

public class QLHoaDonActivity extends AppCompatActivity implements HoaDonAdapter.HoaDonItemListener {

    private ListView lvHoaDon;
    private TextInputEditText etSearch;
    private HoaDonDAO dao;
    private List<HoaDon> list;
    private HoaDonAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hoa_don);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        lvHoaDon = findViewById(R.id.lvHoaDon);
        etSearch = findViewById(R.id.etSearchHD);
        dao = new HoaDonDAO(this);

        loadData();

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private void loadData() {
        list = dao.getAll();
        adapter = new HoaDonAdapter(this, list, this);
        lvHoaDon.setAdapter(adapter);
    }

    private void filter(String text) {
        List<HoaDon> filteredList = new ArrayList<>();
        for (HoaDon hd : list) {
            String maHoaDon = hd.getMaHD() != null ? hd.getMaHD() : "";
            String tenKhach = hd.getTenKhachHang() != null ? hd.getTenKhachHang() : "";

            if (maHoaDon.toLowerCase().contains(text.toLowerCase()) ||
                tenKhach.toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(hd);
            }
        }
        adapter = new HoaDonAdapter(this, filteredList, this);
        lvHoaDon.setAdapter(adapter);
    }

    @Override
    public void onDelete(HoaDon hd) {
        new AlertDialog.Builder(this)
                .setTitle("Xác nhận xóa")
                .setMessage("Xóa hóa đơn " + hd.getMaHD() + " sẽ xóa toàn bộ chi tiết liên quan. Bạn chắc chắn chứ?")
                .setPositiveButton("Xóa", (dialog, which) -> {
                    if (dao.delete(hd.getMaHD()) > 0) {
                        Toast.makeText(this, "Đã xóa hóa đơn", Toast.LENGTH_SHORT).show();
                        loadData();
                    }
                })
                .setNegativeButton("Hủy", null)
                .show();
    }

    @Override
    public void onClick(HoaDon hd) {
        Intent intent = new Intent(this, ChiTietHoaDonActivity.class);
        intent.putExtra("MA_HD", hd.getMaHD());
        intent.putExtra("NGAY_LAP", hd.getNgayLap());
        intent.putExtra("TEN_KH", hd.getTenKhachHang());
        startActivity(intent);
    }
}
