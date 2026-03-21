package fpoly.vinv01.duanmau.Activity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.text.DecimalFormat;
import java.util.List;

import fpoly.vinv01.duanmau.Adapter.ChiTietHoaDonAdapter;
import fpoly.vinv01.duanmau.DAO.HoaDonDAO;
import fpoly.vinv01.duanmau.Model.HoaDonChiTiet;
import fpoly.vinv01.duanmau.R;

public class ChiTietHoaDonActivity extends AppCompatActivity {

    private TextView tvMaHD, tvNgayLap, tvTenKH, tvTongTien;
    private ListView lvChiTiet;
    private HoaDonDAO dao;
    private DecimalFormat formatter = new DecimalFormat("###,###,### đ");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_hoa_don);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        tvMaHD = findViewById(R.id.tvMaHDDetail);
        tvNgayLap = findViewById(R.id.tvNgayLapDetail);
        tvTenKH = findViewById(R.id.tvTenKHDetail);
        tvTongTien = findViewById(R.id.tvTongThanhToan);
        lvChiTiet = findViewById(R.id.lvChiTietHD);
        dao = new HoaDonDAO(this);

        // Nhận dữ liệu từ Intent
        String maHD = getIntent().getStringExtra("MA_HD");
        String ngayLap = getIntent().getStringExtra("NGAY_LAP");
        String tenKH = getIntent().getStringExtra("TEN_KH");

        tvMaHD.setText("MÃ HÓA ĐƠN: " + (maHD != null ? maHD : "N/A"));
        tvNgayLap.setText("NGÀY LẬP: " + (ngayLap != null ? ngayLap : "N/A"));
        tvTenKH.setText(tenKH != null ? tenKH : "Khách vãng lai");

        // Load danh sách chi tiết
        List<HoaDonChiTiet> list = dao.getChiTietByMaHD(maHD);
        ChiTietHoaDonAdapter adapter = new ChiTietHoaDonAdapter(this, list);
        lvChiTiet.setAdapter(adapter);

        // Tính tổng tiền
        int tong = 0;
        for (HoaDonChiTiet ct : list) {
            tong += (ct.getSoLuong() * ct.getGiaLucBan());
        }
        tvTongTien.setText(formatter.format(tong));
    }
}
