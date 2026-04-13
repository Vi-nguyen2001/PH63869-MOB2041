package fpoly.vinv01.duanmau.Activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;
import android.app.Dialog;
import android.widget.ArrayAdapter;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import fpoly.vinv01.duanmau.Adapter.GioHangAdapter;
import fpoly.vinv01.duanmau.CartManager;
import fpoly.vinv01.duanmau.DAO.HoaDonDAO;
import fpoly.vinv01.duanmau.Model.HoaDon;
import fpoly.vinv01.duanmau.Model.HoaDonChiTiet;
import fpoly.vinv01.duanmau.Model.GioHang;
import fpoly.vinv01.duanmau.R;

import androidx.appcompat.widget.Toolbar;

import com.google.android.material.textfield.TextInputEditText;

import fpoly.vinv01.duanmau.DAO.KhachHangDAO;
import fpoly.vinv01.duanmau.Model.KhachHang;

public class GioHangActivity extends AppCompatActivity implements GioHangAdapter.GioHangListener {
    private TextInputEditText etCustomerPhone;
    private ImageView ivAddCustomer;
    private TextView tvCustomerNameResult;
    private Toolbar toolbar;
    private ListView lvGioHang;
    private TextView tvTongTien;
    private MaterialButton btnThanhToan;
    private GioHangAdapter adapter;
    private HoaDonDAO hoaDonDAO;
    private DecimalFormat formatter = new DecimalFormat("###,###,### đ");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giohang);


        lvGioHang = findViewById(R.id.lvGioHang);
        tvTongTien = findViewById(R.id.tvTongTienCart);
        btnThanhToan = findViewById(R.id.btnThanhToan);
        hoaDonDAO = new HoaDonDAO(this);

        etCustomerPhone = findViewById(R.id.etCustomerPhone);
        ivAddCustomer = findViewById(R.id.ivAddCustomer);
        tvCustomerNameResult = findViewById(R.id.tvCustomerNameResult);

        ivAddCustomer.setOnClickListener(v -> {
            showSelectCustomerDialog();
        });

        etCustomerPhone.addTextChangedListener(new android.text.TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(android.text.Editable s) {
                checkCustomerPhone(s.toString().trim());
            }
        });

        // Thiết lập Toolbar để nhấn nút Back
        toolbar = findViewById(R.id.toolbarCart);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(v -> finish()); // Nhấn mũi tên để thoát/quay lại

        adapter = new GioHangAdapter(this, CartManager.listGioHang, this);
        lvGioHang.setAdapter(adapter);

        tinhTongTien();

        btnThanhToan.setOnClickListener(v -> clickThanhToan());
    }

    private void showSelectCustomerDialog() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_select_customer);
        dialog.getWindow().setLayout(android.view.ViewGroup.LayoutParams.MATCH_PARENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT);

        ListView lvDialogCustomers = dialog.findViewById(R.id.lvDialogCustomers);
        com.google.android.material.button.MaterialButton btnDialogAddCustomer = dialog.findViewById(R.id.btnDialogAddCustomer);

        KhachHangDAO khDAO = new KhachHangDAO(this);
        List<KhachHang> listKhachHang = khDAO.getAll();
        List<String> listDisplay = new ArrayList<>();
        for (KhachHang kh : listKhachHang) {
            listDisplay.add(kh.getHoTen() + " - " + kh.getDienThoai());
        }

        ArrayAdapter<String> dialogAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listDisplay);
        lvDialogCustomers.setAdapter(dialogAdapter);

        lvDialogCustomers.setOnItemClickListener((parent, view, position, id) -> {
            KhachHang selectedKhachHang = listKhachHang.get(position);
            etCustomerPhone.setText(selectedKhachHang.getDienThoai());
            dialog.dismiss();
        });

        btnDialogAddCustomer.setOnClickListener(v -> {
            Intent intent = new Intent(GioHangActivity.this, ThemKhachHangActivity.class);
            startActivity(intent);
            dialog.dismiss();
        });

        dialog.show();
    }

    private void checkCustomerPhone(String phone) {
        if (phone.isEmpty()) {
            tvCustomerNameResult.setVisibility(android.view.View.GONE);
            return;
        }
        KhachHangDAO khDAO = new KhachHangDAO(this);
        KhachHang kh = khDAO.getKhachHangByPhone(phone);
        tvCustomerNameResult.setVisibility(android.view.View.VISIBLE);
        if (kh != null) {
            tvCustomerNameResult.setText("Khách hàng: " + kh.getHoTen());
            tvCustomerNameResult.setTextColor(android.graphics.Color.parseColor("#27AE60"));
        } else {
            tvCustomerNameResult.setText("Chưa có trong danh sách");
            tvCustomerNameResult.setTextColor(android.graphics.Color.parseColor("#E74C3C"));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (etCustomerPhone != null) {
            String currentPhone = etCustomerPhone.getText().toString().trim();
            if (!currentPhone.isEmpty()) {
                checkCustomerPhone(currentPhone);
            }
        }
    }

    private void tinhTongTien() {
        int tong = 0;
        for (GioHang gh : CartManager.listGioHang) {
            tong += (gh.getGiaBan() * gh.getSoLuongMua());
        }
        tvTongTien.setText(formatter.format(tong));
    }

    @Override
    public void onQuantityChanged() {
        tinhTongTien();
    }

    private void clickThanhToan() {
        if (CartManager.listGioHang.isEmpty()) {
            Toast.makeText(this, "Giỏ hàng trống!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Cập nhật: Kiểm tra số điện thoại khách hàng
        String phone = etCustomerPhone.getText().toString().trim();
        if (phone.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập SĐT khách hàng!", Toast.LENGTH_SHORT).show();
            return;
        }

        KhachHangDAO khDAO = new KhachHangDAO(this);
        KhachHang kh = khDAO.getKhachHangByPhone(phone);
        if (kh == null) {
            Toast.makeText(this, "SĐT không tồn tại, vui lòng thêm KH trước!", Toast.LENGTH_LONG).show();
            return;
        }

        // 1. Tạo đối tượng HoaDon
        String maHD = hoaDonDAO.getNewMaHD();
        String ngay = new SimpleDateFormat("dd/MM/yyyy | HH:mm", Locale.getDefault()).format(new Date());
        int tongTien = 0;

        List<HoaDonChiTiet> listCT = new ArrayList<>();
        for (GioHang gh : CartManager.listGioHang) {
            tongTien += (gh.getGiaBan() * gh.getSoLuongMua());
            // Tạo chi tiết hóa đơn từ giỏ hàng
            HoaDonChiTiet ct = new HoaDonChiTiet();
            ct.setMaHD(maHD);
            ct.setMaSP(gh.getMaSP());
            ct.setSoLuong(gh.getSoLuongMua());
            ct.setGiaLucBan(gh.getGiaBan());
            listCT.add(ct);
        }

        // Cập nhật: Lấy maNV từ SharedPreferences (người đang đăng nhập)
        android.content.SharedPreferences pref = getSharedPreferences("LOGIN", MODE_PRIVATE);
        String maNV = pref.getString("username", "NV001");

        HoaDon hd = new HoaDon(maHD, ngay, maNV, kh.getMaKH(), kh.getHoTen(), tongTien);


        if (hoaDonDAO.insertFull(hd, listCT)) {
            Toast.makeText(this, "Thanh toán thành công!", Toast.LENGTH_LONG).show();
            CartManager.listGioHang.clear(); // Xóa giỏ hàng
            finish(); // Quay về
        } else {
            // Cập nhật: Báo lỗi cụ thể hơn (có thể do lỗi tồn kho hoặc DB)
            Toast.makeText(this, "Lỗi thanh toán! Vui lòng kiểm tra lại số lượng tồn kho.", Toast.LENGTH_SHORT).show();
        }
    }
}
