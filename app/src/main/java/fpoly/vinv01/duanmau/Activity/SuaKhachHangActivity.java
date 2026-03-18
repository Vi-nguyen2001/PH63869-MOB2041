package fpoly.vinv01.duanmau.Activity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import fpoly.vinv01.duanmau.Model.KhachHang;
import fpoly.vinv01.duanmau.R;

public class SuaKhachHangActivity extends ThemKhachHangActivity {

    private KhachHang currentKH;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TextView tvTitle = findViewById(R.id.tvToolbarTitleKH);
        tvTitle.setText("Sửa khách hàng");
        btnSaveKH.setText("Lưu thay đổi");

        // Lấy dữ liệu từ Intent
        currentKH = (KhachHang) getIntent().getSerializableExtra("KHACH_HANG");
        if (currentKH != null) {
            fillData();
        }
    }

    private void fillData() {
        etMaKH.setText(currentKH.getMaKH());
        etHoTenKH.setText(currentKH.getHoTen());
        etDienThoaiKH.setText(currentKH.getDienThoai());
        etEmailKH.setText(currentKH.getEmail());
        etDiaChiKH.setText(currentKH.getDiaChi());
    }

    @Override
    protected void saveCustomer() {
        String ten = etHoTenKH.getText().toString().trim();
        String dt = etDienThoaiKH.getText().toString().trim();
        String email = etEmailKH.getText().toString().trim();
        String dc = etDiaChiKH.getText().toString().trim();

        if (ten.isEmpty() || dt.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập họ tên và số điện thoại", Toast.LENGTH_SHORT).show();
            return;
        }

        currentKH.setHoTen(ten);
        currentKH.setDienThoai(dt);
        currentKH.setEmail(email);
        currentKH.setDiaChi(dc);

        if (dao.update(currentKH) > 0) {
            Toast.makeText(this, "Cập nhật khách hàng thành công", Toast.LENGTH_SHORT).show();
            setResult(RESULT_OK);
            finish();
        } else {
            Toast.makeText(this, "Lỗi khi cập nhật khách hàng", Toast.LENGTH_SHORT).show();
        }
    }
}
