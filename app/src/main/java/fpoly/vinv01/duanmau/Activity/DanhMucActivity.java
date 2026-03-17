package fpoly.vinv01.duanmau.Activity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

import fpoly.vinv01.duanmau.Adapter.DanhMucAdapter;
import fpoly.vinv01.duanmau.DAO.DanhMucDAO;
import fpoly.vinv01.duanmau.Model.DanhMuc;
import fpoly.vinv01.duanmau.R;

public class DanhMucActivity extends AppCompatActivity implements DanhMucAdapter.DanhMucItemListener {

    private ListView lvDanhMuc;
    private FloatingActionButton fabAdd;
    private List<DanhMuc> danhMucList;
    private DanhMucAdapter adapter;
    private DanhMucDAO dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_muc);

        // Initialize Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        // Initialize DAO and Views
        dao = new DanhMucDAO(this);
        lvDanhMuc = findViewById(R.id.lvDanhMuc);
        fabAdd = findViewById(R.id.fabAdd);

        loadData();

        fabAdd.setOnClickListener(v -> showDialog(null));
    }

    private void loadData() {
        danhMucList = dao.getAll();
        adapter = new DanhMucAdapter(this, danhMucList, this);
        lvDanhMuc.setAdapter(adapter);
    }

    private void showDialog(DanhMuc danhMuc) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view;

        // 1. Kiểm tra: Nếu danhMuc == null là THÊM, ngược lại là SỬA
        if (danhMuc == null) {
            // Nạp layout Thêm (Không có ô Mã danh mục)
            view = LayoutInflater.from(this).inflate(R.layout.dialog_them_danh_muc, null);
        } else {
            // Nạp layout Sửa (Có ô Mã danh mục để hiện DM00x)
            view = LayoutInflater.from(this).inflate(R.layout.dialog_sua_danh_muc, null);

            // Ánh xạ và hiển thị Mã danh mục (Chỉ có ở layout Sua)
            TextInputEditText etMa = view.findViewById(R.id.etMaDanhMucDialog);
            if (etMa != null) {
                etMa.setText("DM" + String.format("%03d", danhMuc.getMaDanhMuc()));
            }
        }

        builder.setView(view);
        AlertDialog dialog = builder.create();
        dialog.show();

        // 2. Ánh xạ các View dùng chung cho cả 2 layout
        TextInputEditText etTen = view.findViewById(R.id.etTenDanhMucDialog);
        MaterialButton btnLuu = view.findViewById(R.id.btnLuuDialog);
        MaterialButton btnHuy = view.findViewById(R.id.btnHuyDialog);

        // Nếu là Sửa, điền tên hiện tại vào ô nhập
        if (danhMuc != null && etTen != null) {
            etTen.setText(danhMuc.getTenDanhMuc());
        }

        // 3. Xử lý sự kiện nút bấm
        btnLuu.setOnClickListener(v -> {
            String ten = etTen.getText().toString().trim();

            if (ten.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập tên danh mục", Toast.LENGTH_SHORT).show();
                return;
            }

            if (danhMuc == null) {
                // Logic THÊM MỚI
                DanhMuc newDM = new DanhMuc(0, ten);
                if (dao.insert(newDM) > 0) {
                    Toast.makeText(this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                    loadData(); // Load lại dữ liệu từ SQLite
                    dialog.dismiss();
                }
            } else {
                // Logic CẬP NHẬT
                danhMuc.setTenDanhMuc(ten);
                if (dao.update(danhMuc) > 0) {
                    Toast.makeText(this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                    loadData(); // Load lại dữ liệu từ SQLite
                    dialog.dismiss();
                }
            }
        });

        // Nút Hủy để đóng dialog
        btnHuy.setOnClickListener(v -> dialog.dismiss());
    }

    @Override
    public void onEdit(DanhMuc danhMuc) {
        showDialog(danhMuc);
    }

    @Override
    public void onDelete(DanhMuc danhMuc) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Xác nhận xóa");
        builder.setMessage("Bạn có chắc chắn muốn xóa danh mục '" + danhMuc.getTenDanhMuc() + "'?");
        builder.setPositiveButton("Xóa", (dialog, which) -> {
            if (dao.delete(danhMuc.getMaDanhMuc()) > 0) {
                Toast.makeText(this, "Đã xóa", Toast.LENGTH_SHORT).show();
                loadData();
            }
        });
        builder.setNegativeButton("Hủy", (dialog, which) -> dialog.dismiss());
        builder.show();
    }
}