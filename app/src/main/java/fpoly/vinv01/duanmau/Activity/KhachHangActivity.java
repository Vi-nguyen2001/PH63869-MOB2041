package fpoly.vinv01.duanmau.Activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

import fpoly.vinv01.duanmau.Adapter.KhachHangAdapter;
import fpoly.vinv01.duanmau.DAO.KhachHangDAO;
import fpoly.vinv01.duanmau.Model.KhachHang;
import fpoly.vinv01.duanmau.R;

public class KhachHangActivity extends AppCompatActivity implements KhachHangAdapter.KhachHangItemListener {

    private ListView lvKhachHang;
    private FloatingActionButton fabAdd;
    private TextInputEditText etSearch;
    private KhachHangDAO dao;
    private List<KhachHang> list;
    private KhachHangAdapter adapter;
    private static final int REQUEST_CODE_KH = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_khach_hang);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        lvKhachHang = findViewById(R.id.lvKhachHang);
        fabAdd = findViewById(R.id.fabAddKhachHang);
        etSearch = findViewById(R.id.etSearchKH);
        dao = new KhachHangDAO(this);

        loadData();

        fabAdd.setOnClickListener(v -> {
            Intent intent = new Intent(KhachHangActivity.this, ThemKhachHangActivity.class);
            startActivityForResult(intent, REQUEST_CODE_KH);
        });

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
        adapter = new KhachHangAdapter(this, list, this);
        lvKhachHang.setAdapter(adapter);
    }

    private void filter(String text) {
        List<KhachHang> filteredList = new ArrayList<>();
        for (KhachHang kh : list) {
            if (kh.getHoTen().toLowerCase().contains(text.toLowerCase()) ||
                kh.getDienThoai().contains(text)) {
                filteredList.add(kh);
            }
        }
        adapter = new KhachHangAdapter(this, filteredList, this);
        lvKhachHang.setAdapter(adapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            loadData();
        }
    }

    @Override
    public void onEdit(KhachHang kh) {
        Intent intent = new Intent(this, SuaKhachHangActivity.class);
        intent.putExtra("KHACH_HANG", kh);
        startActivityForResult(intent, REQUEST_CODE_KH);
    }

    @Override
    public void onDelete(KhachHang kh) {
        new AlertDialog.Builder(this)
                .setTitle("Xác nhận xóa")
                .setMessage("Bạn có chắc chắn muốn xóa khách hàng '" + kh.getHoTen() + "'?")
                .setPositiveButton("Xóa", (dialog, which) -> {
                    if (dao.delete(kh.getMaKH()) > 0) {
                        Toast.makeText(this, "Đã xóa khách hàng", Toast.LENGTH_SHORT).show();
                        loadData();
                    }
                })
                .setNegativeButton("Hủy", null)
                .show();
    }
}
