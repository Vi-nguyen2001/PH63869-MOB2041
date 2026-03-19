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

import fpoly.vinv01.duanmau.Adapter.NhanVienAdapter;
import fpoly.vinv01.duanmau.DAO.NhanVienDAO;
import fpoly.vinv01.duanmau.Model.NhanVien;
import fpoly.vinv01.duanmau.R;

public class QLNhanVienActivity extends AppCompatActivity implements NhanVienAdapter.NhanVienItemListener {

    private ListView lvNhanVien;
    private FloatingActionButton fabAdd;
    private TextInputEditText etSearch;
    private NhanVienDAO dao;
    private List<NhanVien> list;
    private NhanVienAdapter adapter;
    private static final int REQUEST_CODE_STAFF = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nhan_vien);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        lvNhanVien = findViewById(R.id.lvNhanVien);
        fabAdd = findViewById(R.id.fabAddNhanVien);
        etSearch = findViewById(R.id.etSearch);
        dao = new NhanVienDAO(this);

        loadData();

        fabAdd.setOnClickListener(v -> {
            Intent intent = new Intent(QLNhanVienActivity.this, ThemNhanVienActivity.class);
            startActivityForResult(intent, REQUEST_CODE_STAFF);
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
        toolbar.setNavigationOnClickListener(v -> {
            Intent intent = new Intent(QLNhanVienActivity.this, trangchuquanly.class);
            startActivity(intent);
            finish(); // Kết thúc màn hình hiện tại
        });


    }

    private void loadData() {
        list = dao.getAll();
        adapter = new NhanVienAdapter(this, list, this);
        lvNhanVien.setAdapter(adapter);
    }

    private void filter(String text) {
        List<NhanVien> filteredList = new ArrayList<>();
        for (NhanVien nv : list) {
            if (nv.getHoTen().toLowerCase().contains(text.toLowerCase()) || 
                nv.getMaNV().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(nv);
            }
        }
        adapter = new NhanVienAdapter(this, filteredList, this);
        lvNhanVien.setAdapter(adapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            loadData();
        }
    }

    @Override
    public void onEdit(NhanVien nv) {
        Intent intent = new Intent(this, SuaNhanVienActivity.class);
        intent.putExtra("NHAN_VIEN", nv);
        startActivityForResult(intent, REQUEST_CODE_STAFF);
    }

    @Override
    public void onDelete(NhanVien nv) {
        new AlertDialog.Builder(this)
                .setTitle("Xác nhận xóa")
                .setMessage("Bạn có chắc chắn muốn xóa nhân viên '" + nv.getHoTen() + "'?")
                .setPositiveButton("Xóa", (dialog, which) -> {
                    if (dao.delete(nv.getMaNV()) > 0) {
                        Toast.makeText(this, "Đã xóa nhân viên", Toast.LENGTH_SHORT).show();
                        loadData();
                    }
                })
                .setNegativeButton("Hủy", null)
                .show();
    }


}
