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
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.google.android.material.textfield.TextInputEditText;

import fpoly.vinv01.duanmau.CartManager;
import fpoly.vinv01.duanmau.Model.GioHang;
import java.util.ArrayList;

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
    private TextInputEditText etSearch;
    private TextView tvCartBadge;
    private RelativeLayout rlCartIconLayout;
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
        toolbar.setNavigationOnClickListener(v -> finish());

        lvSanPham = findViewById(R.id.lvSanPham);
        fabAdd = findViewById(R.id.fabAddProduct);
        dao = new SanPhamDAO(this);

        // Map views cho Search và Cart
        etSearch = findViewById(R.id.etSearchSP);
        tvCartBadge = findViewById(R.id.tvCartBadge);
        rlCartIconLayout = findViewById(R.id.rlCartIconLayout);

        loadData();
        updateCartBadge();

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

        rlCartIconLayout.setOnClickListener(v -> {
            Intent intent = new Intent(QLSanPhamActivity.this, GioHangActivity.class);
            startActivity(intent);
        });

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

    private void filter(String text) {
        List<SanPham> filteredList = new ArrayList<>();
        for (SanPham sp : list) {
            String name = sp.getTenSP() != null ? sp.getTenSP().toLowerCase() : "";
            if (name.contains(text.toLowerCase())) {
                filteredList.add(sp);
            }
        }
        adapter = new SanPhamAdapter(this, filteredList, this);
        lvSanPham.setAdapter(adapter);
    }

    private void updateCartBadge() {
        // Cập nhật: đếm số lượng mặt hàng (loại sản phẩm)
        int count = CartManager.listGioHang.size();
        if (count > 0) {
            tvCartBadge.setVisibility(View.VISIBLE);
            tvCartBadge.setText(String.valueOf(count));
        } else {
            tvCartBadge.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateCartBadge();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            loadData();
        }
    }

    @Override
    public void onAddToCart(SanPham sp) {
        GioHang gh = new GioHang(sp.getMaSP(), sp.getTenSP(), sp.getGiaBan(), 1);
        CartManager.addToCart(gh);
        updateCartBadge();
        Toast.makeText(this, "Đã thêm " + sp.getTenSP() + " vào giỏ hàng", Toast.LENGTH_SHORT).show();
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