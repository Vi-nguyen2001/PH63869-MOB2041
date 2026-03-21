package fpoly.vinv01.duanmau.Activity;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import fpoly.vinv01.duanmau.DAO.KhachHangDAO;
import fpoly.vinv01.duanmau.Model.KhachHang;
import fpoly.vinv01.duanmau.R;

public class ThemKhachHangActivity extends AppCompatActivity {

    protected TextInputEditText etMaKH, etHoTenKH, etDienThoaiKH, etEmailKH, etDiaChiKH;
    protected MaterialButton btnSaveKH, btnCancelKH;
    protected KhachHangDAO dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_khach_hang);

        initViews();
        setupToolbar();

        // Tự động sinh mã KH mới
        etMaKH.setText(dao.getNewMaKH());

        btnSaveKH.setOnClickListener(v -> saveCustomer());
        btnCancelKH.setOnClickListener(v -> finish());
    }

    protected void initViews() {
        etMaKH = findViewById(R.id.etMaKH);
        etHoTenKH = findViewById(R.id.etHoTenKH);
        etDienThoaiKH = findViewById(R.id.etDienThoaiKH);
        etEmailKH = findViewById(R.id.etEmailKH);
        etDiaChiKH = findViewById(R.id.etDiaChiKH);
        btnSaveKH = findViewById(R.id.btnSaveKH);
        btnCancelKH = findViewById(R.id.btnCancelKH);
        dao = new KhachHangDAO(this);
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
    }

    protected void saveCustomer() {
        String ma = etMaKH.getText().toString().trim();
        String ten = etHoTenKH.getText().toString().trim();
        String dt = etDienThoaiKH.getText().toString().trim();
        String email = etEmailKH.getText().toString().trim();
        String dc = etDiaChiKH.getText().toString().trim();

        if (ten.isEmpty() || dt.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập họ tên và số điện thoại", Toast.LENGTH_SHORT).show();
            return;
        }
        if (dao.checkPhoneExists(dt)) {
            Toast.makeText(this, "Số điện thoại đã tồn tại", Toast.LENGTH_SHORT).show();
            return;
        }

        KhachHang kh = new KhachHang(ma, ten, dt, email, dc);
        if (dao.insert(kh) != -1) {
            Toast.makeText(this, "Thêm khách hàng thành công", Toast.LENGTH_SHORT).show();
            setResult(RESULT_OK);
            finish();
        } else {
            Toast.makeText(this, "Lỗi khi thêm khách hàng", Toast.LENGTH_SHORT).show();
        }
    }
}
