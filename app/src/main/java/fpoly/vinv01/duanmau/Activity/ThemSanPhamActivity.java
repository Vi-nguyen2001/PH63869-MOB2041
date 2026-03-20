package fpoly.vinv01.duanmau.Activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import fpoly.vinv01.duanmau.Model.DanhMuc;
import fpoly.vinv01.duanmau.DAO.DanhMucDAO;
import fpoly.vinv01.duanmau.R;
import fpoly.vinv01.duanmau.Model.SanPham;
import fpoly.vinv01.duanmau.DAO.SanPhamDAO;

public class ThemSanPhamActivity extends AppCompatActivity {

    protected TextInputEditText etTenSP, etGiaBan, etDonViTinh, etSoLuong, etNgayNhap;
    protected Spinner spDanhMuc;
    protected MaterialButton btnSave, btnCancel;
    protected SanPhamDAO dao;
    protected DanhMucDAO danhMucDAO;
    protected List<DanhMuc> danhMucList;
    protected Calendar calendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_san_pham);

        initViews();
        setupToolbar();
        setupSpinner();

        etNgayNhap.setOnClickListener(v -> showDatePicker());

        btnSave.setOnClickListener(v -> saveProduct());
        btnCancel.setOnClickListener(v -> finish());
    }

    protected void initViews() {
        etTenSP = findViewById(R.id.etTenSP);
        etGiaBan = findViewById(R.id.etGiaBan);
        etDonViTinh = findViewById(R.id.etDonViTinh);
        etSoLuong = findViewById(R.id.etSoLuong);
        etNgayNhap = findViewById(R.id.etNgayNhap);
        spDanhMuc = findViewById(R.id.spDanhMuc);
        btnSave = findViewById(R.id.btnSave);
        btnCancel = findViewById(R.id.btnCancel);
        dao = new SanPhamDAO(this);
        danhMucDAO = new DanhMucDAO(this);
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
        danhMucList = danhMucDAO.getAll();
        List<String> tenDMList = new ArrayList<>();
        for (DanhMuc dm : danhMucList) {
            tenDMList.add(dm.getTenDanhMuc());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, tenDMList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spDanhMuc.setAdapter(adapter);
    }

    private void showDatePicker() {
        new DatePickerDialog(this, (view, year, month, dayOfMonth) -> {
            calendar.set(year, month, dayOfMonth);
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            etNgayNhap.setText(sdf.format(calendar.getTime()));
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    protected void saveProduct() {
        if (danhMucList==null){// Kiểm tra danh mục có sản phẩm hay không
            Toast.makeText(this,"Bạn cần thêm danh mục trước khi thêm sản phẩm",Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this,QLDanhMucActivity.class));
            return;
        }
        String ten = etTenSP.getText().toString().trim();
        String gia = etGiaBan.getText().toString().trim();
        String dvt = etDonViTinh.getText().toString().trim();
        String sl = etSoLuong.getText().toString().trim();
        String ngay = etNgayNhap.getText().toString().trim();

        if (ten.isEmpty() || gia.isEmpty() || sl.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }
        int pos = spDanhMuc.getSelectedItemPosition();
        int maDM = danhMucList.get(pos).getMaDanhMuc();




        SanPham sp = new SanPham(0, ten, Integer.parseInt(gia), dvt, Integer.parseInt(sl), ngay, maDM);
        if (dao.insert(sp) > 0) {
            Toast.makeText(this, "Thêm thành công", Toast.LENGTH_SHORT).show();
            setResult(RESULT_OK);
            finish();
        }
    }
}