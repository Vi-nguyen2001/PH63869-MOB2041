package fpoly.vinv01.duanmau.Fragment;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

import fpoly.vinv01.duanmau.Adapter.SanPhamAdapter;
import fpoly.vinv01.duanmau.CartManager;
import fpoly.vinv01.duanmau.DAO.SanPhamDAO;
import fpoly.vinv01.duanmau.Model.GioHang;
import fpoly.vinv01.duanmau.Model.SanPham;
import fpoly.vinv01.duanmau.R;
import fpoly.vinv01.duanmau.Activity.GioHangActivity;
import fpoly.vinv01.duanmau.Activity.SuaSanPhamActivity;
import fpoly.vinv01.duanmau.Activity.ThemSanPhamActivity;

public class QLSanPhamFragment extends Fragment implements SanPhamAdapter.SanPhamItemListener {

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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_san_pham, container, false);

        Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(v -> {
            com.google.android.material.bottomnavigation.BottomNavigationView bottomNav = requireActivity().findViewById(R.id.bottom_nav);
            if (bottomNav != null) {
                bottomNav.setSelectedItemId(R.id.nav_home);
            }
        });

        lvSanPham = view.findViewById(R.id.lvSanPham);
        fabAdd = view.findViewById(R.id.fabAddProduct);
        dao = new SanPhamDAO(requireContext());

        // Map views cho Search và Cart
        etSearch = view.findViewById(R.id.etSearchSP);
        tvCartBadge = view.findViewById(R.id.tvCartBadge);
        rlCartIconLayout = view.findViewById(R.id.rlCartIconLayout);

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
            Intent intent = new Intent(requireContext(), GioHangActivity.class);
            startActivity(intent);
        });

        fabAdd.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), ThemSanPhamActivity.class);
            startActivityForResult(intent, REQUEST_CODE_ADD);
        });

        return view;
    }

    private void loadData() {
        list = dao.getAll();
        adapter = new SanPhamAdapter(requireContext(), list, this);
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
        adapter = new SanPhamAdapter(requireContext(), filteredList, this);
        lvSanPham.setAdapter(adapter);
    }

    private void updateCartBadge() {
        if(tvCartBadge == null) return;
        int count = CartManager.listGioHang.size();
        if (count > 0) {
            tvCartBadge.setVisibility(View.VISIBLE);
            tvCartBadge.setText(String.valueOf(count));
        } else {
            tvCartBadge.setVisibility(View.GONE);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        updateCartBadge();
        loadData(); // Tải lại vì có thể vừa Sửa/Thêm quay về ko qua activity result
    }

    @Override
    public void onAddToCart(SanPham sp) {
        GioHang gh = new GioHang(sp.getMaSP(), sp.getTenSP(), sp.getGiaBan(), 1);
        CartManager.addToCart(gh);
        updateCartBadge();
        Toast.makeText(requireContext(), "Đã thêm " + sp.getTenSP() + " vào giỏ hàng", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onEdit(SanPham sp) {
        Intent intent = new Intent(requireContext(), SuaSanPhamActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("PRODUCT", sp);
        intent.putExtras(bundle);
        startActivityForResult(intent, REQUEST_CODE_EDIT);
    }

    @Override
    public void onDelete(SanPham sp) {
        new AlertDialog.Builder(requireContext())
                .setTitle("Xác nhận xóa")
                .setMessage("Bạn có chắc chắn muốn xóa sản phẩm '" + sp.getTenSP() + "'?")
                .setPositiveButton("Xóa", (dialog, which) -> {
                    if (dao.delete(sp.getMaSP()) > 0) {
                        Toast.makeText(requireContext(), "Đã xóa", Toast.LENGTH_SHORT).show();
                        loadData();
                    }
                })
                .setNegativeButton("Hủy", null)
                .show();
    }
}
