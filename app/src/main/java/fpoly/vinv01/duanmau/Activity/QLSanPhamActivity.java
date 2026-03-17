package fpoly.vinv01.duanmau.Activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import fpoly.vinv01.duanmau.R;
import fpoly.vinv01.duanmau.Model.SanPham;
import fpoly.vinv01.duanmau.Adapter.SanPhamAdapter;
import fpoly.vinv01.duanmau.DAO.SanPhamDAO;

public class QLSanPhamActivity extends AppCompatActivity implements SanPhamAdapter.SanPhamItemListener {

    private ListView lvSanPham;
    private FloatingActionButton fabAdd;
    private SanPhamDAO dao;
    private List<SanPham> list;
    private SanPhamAdapter adapter;
    private static final int REQUEST_CODE_ADD = 1;
    private static final int REQUEST_CODE_EDIT = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_san_pham);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        lvSanPham = findViewById(R.id.lvSanPham);
        fabAdd = findViewById(R.id.fabAddProduct);
        dao = new SanPhamDAO(this);

        loadData();

        fabAdd.setOnClickListener(v -> {
            Intent intent = new Intent(QLSanPhamActivity.this, ThemSanPhamActivity.class);
            startActivityForResult(intent, REQUEST_CODE_ADD);
        });
    }

    private void loadData() {
        list = dao.getAll();
        adapter = new SanPhamAdapter(this, list, this);
        lvSanPham.setAdapter(adapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            loadData();
        }
    }

    @Override
    public void onEdit(SanPham sp) {
        Intent intent = new Intent(this, SuaSanPhamActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("PRODUCT", sp);
        intent.putExtras(bundle);
        startActivityForResult(intent, REQUEST_CODE_EDIT);
    }

    @Override
    public void onDelete(SanPham sp) {
        new AlertDialog.Builder(this)
                .setTitle("Xác nhận xóa")
                .setMessage("Bạn có chắc chắn muốn xóa sản phẩm '" + sp.getTenSP() + "'?")
                .setPositiveButton("Xóa", (dialog, which) -> {
                    if (dao.delete(sp.getMaSP()) > 0) {
                        Toast.makeText(this, "Đã xóa", Toast.LENGTH_SHORT).show();
                        loadData();
                    }
                })
                .setNegativeButton("Hủy", null)
                .show();
    }
}