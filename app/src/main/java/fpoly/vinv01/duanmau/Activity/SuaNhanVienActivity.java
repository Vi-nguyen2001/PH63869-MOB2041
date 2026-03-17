package fpoly.vinv01.duanmau.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import fpoly.vinv01.duanmau.Model.NhanVien;
import fpoly.vinv01.duanmau.R;

public class SuaNhanVienActivity extends ThemNhanVienActivity {

    private NhanVien currentNV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Tái sử dụng layout của ThemNhanVienActivity đã set trong cha

        TextView tvTitle = findViewById(R.id.tvToolbarTitle);
        tvTitle.setText("Sửa nhân viên");
        btnSave.setText("Lưu thay đổi");

        // Hiện ô mật khẩu ở màn hình sửa
        findViewById(R.id.llMatKhau).setVisibility(View.VISIBLE);

        // Khóa ô mã nhân viên
        etMaNV.setEnabled(false);
        etMaNV.setFocusable(false);

        // Lấy dữ liệu
        currentNV = (NhanVien) getIntent().getSerializableExtra("NHAN_VIEN");
        if (currentNV != null) {
            fillData();
        }
    }

    private void fillData() {
        etMaNV.setText(currentNV.getMaNV());
        etHoTen.setText(currentNV.getHoTen());
        etDiaChi.setText(currentNV.getDiaChi());
        etLuong.setText(String.valueOf(currentNV.getLuong()));
        
        findViewById(R.id.etMatKhau).setVisibility(View.VISIBLE);
        ((android.widget.EditText)findViewById(R.id.etMatKhau)).setText(currentNV.getMatKhau());

        // Set Spinner
        for (int i = 0; i < arrChucVu.length; i++) {
            if (arrChucVu[i].equals(currentNV.getChucVu())) {
                spChucVu.setSelection(i);
                break;
            }
        }
        android.widget.EditText etMatKhau = findViewById(R.id.etMatKhau);
        etMatKhau.setText("");
        etMatKhau.setHint("Nhập mật khẩu mới nếu muốn thay đổi");
    }

    @Override
    protected void saveStaff() {
        String ten = etHoTen.getText().toString().trim();
        String dc = etDiaChi.getText().toString().trim();
        String luongStr = etLuong.getText().toString().trim();

        android.widget.EditText etMatKhau = findViewById(R.id.etMatKhau);
        String mkMoi = etMatKhau.getText().toString().trim();

        int posCV = spChucVu.getSelectedItemPosition();
        if (ten.isEmpty() || luongStr.isEmpty() || posCV == 0) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ họ tên, lương và chức vụ", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!mkMoi.isEmpty() && !mkMoi.equals("••••••••")) {
            currentNV.setMatKhau(mkMoi);
        }
        currentNV.setHoTen(ten);
        currentNV.setDiaChi(dc);
        currentNV.setLuong(Integer.parseInt(luongStr));
        currentNV.setChucVu(arrChucVu[posCV]);

        if (dao.update(currentNV) > 0) {
            Toast.makeText(this, "Cập nhật nhân viên thành công", Toast.LENGTH_SHORT).show();
            setResult(RESULT_OK);
            finish();
        } else {
            Toast.makeText(this, "Cập nhật thất bại, vui lòng thử lại", Toast.LENGTH_SHORT).show();
        }
    }
}
