package fpoly.vinv01.duanmau.Activity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import fpoly.vinv01.duanmau.DAO.NhanVienDAO;
import fpoly.vinv01.duanmau.Model.NhanVien;
import fpoly.vinv01.duanmau.R;

public class ThemNhanVienActivity extends AppCompatActivity {
    protected TextInputEditText etMaNV, etHoTen, etDiaChi, etLuong;
    protected Spinner spChucVu;
    protected MaterialButton btnSave, btnCancel;
    protected NhanVienDAO dao;
    protected String[] arrChucVu = {"Chọn chức vụ", "Quản lý", "Nhân viên bán hàng", "Nhân viên thu ngân"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_nhan_vien);

        initViews(); // Ánh xạ view và khởi tạo DAO
        setupToolbar();
        setupSpinner();

        // Kiểm tra xem là THÊM MỚI hay đang SỬA
        // Nếu không phải là SuaNhanVienActivity thì mới tự động lấy mã mới
        if (!(this instanceof SuaNhanVienActivity)) {
            try {
                String maMoi = dao.getNewMaNV();
                etMaNV.setText(maMoi);
                etMaNV.setEnabled(false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        btnSave.setOnClickListener(v -> saveStaff());
        btnCancel.setOnClickListener(v -> finish());
    }

    protected void initViews() {
        etMaNV = findViewById(R.id.etMaNV);
        etHoTen = findViewById(R.id.etHoTen);
        etDiaChi = findViewById(R.id.etDiaChi);
        etLuong = findViewById(R.id.etLuong);
        spChucVu = findViewById(R.id.spChucVu);
        btnSave = findViewById(R.id.btnSave);
        btnCancel = findViewById(R.id.btnCancel);
        dao = new NhanVienDAO(this);
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
    }

    private void setupSpinner() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, arrChucVu);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spChucVu.setAdapter(adapter);
    }

    protected void saveStaff() {
        String ma = etMaNV.getText().toString().trim();
        String ten = etHoTen.getText().toString().trim();
        String dc = etDiaChi.getText().toString().trim();
        String luongStr = etLuong.getText().toString().trim();
        int posCV = spChucVu.getSelectedItemPosition();

        if (ma.isEmpty() || ten.isEmpty() || luongStr.isEmpty() || posCV == 0) {
            Toast.makeText(this, "Vui lòng nhập đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            int luong = Integer.parseInt(luongStr);
            if (luong <= 0) {
                Toast.makeText(this, "Bắt buộc nhập lương > 0", Toast.LENGTH_SHORT).show();
                return;
            }
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Lương không hợp lệ", Toast.LENGTH_SHORT).show();
            return;
        }

        NhanVien nv = new NhanVien(ma, ten, dc, arrChucVu[posCV], Integer.parseInt(luongStr), "123456");
        if (dao.insert(nv) != -1) {
            Toast.makeText(this, "Thêm thành công", Toast.LENGTH_SHORT).show();
            setResult(RESULT_OK);
            finish();
        } else {
            Toast.makeText(this, "Lỗi: Mã nhân viên đã tồn tại!", Toast.LENGTH_SHORT).show();
        }
    }
}