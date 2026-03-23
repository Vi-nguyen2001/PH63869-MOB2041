package fpoly.vinv01.duanmau.Activity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
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

    private void tinhTongTien() {
        int tong = 0;
        for (GioHang gh : CartManager.listGioHang) {
            tong += (gh.getGiaBan() * gh.getSoLuongMua());
        }
        tvTongTien.setText(formatter.format(tong));
    }

    @Override
    public void onQuantityChanged() { tinhTongTien(); }

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
