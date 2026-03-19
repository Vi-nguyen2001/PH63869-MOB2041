package fpoly.vinv01.duanmau.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import fpoly.vinv01.duanmau.Model.NhanVien;
import fpoly.vinv01.duanmau.R;

public class SuaNhanVienActivity extends ThemNhanVienActivity {
    private NhanVien currentNV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Lưu ý: Không gọi lại initViews() vì onCreate cha đã gọi rồi

        TextView tvTitle = findViewById(R.id.tvToolbarTitle);
        if (tvTitle != null) tvTitle.setText("Sửa nhân viên");
        if (btnSave != null) btnSave.setText("Lưu thay đổi");

        // Lấy dữ liệu từ Intent
        currentNV = (NhanVien) getIntent().getSerializableExtra("NHAN_VIEN");

        if (currentNV != null) {
            etMaNV.setText(currentNV.getMaNV());
            etMaNV.setEnabled(false);
            etHoTen.setText(currentNV.getHoTen());
            etDiaChi.setText(currentNV.getDiaChi());
            etLuong.setText(String.valueOf(currentNV.getLuong()));

            // Hiển thị ô mật khẩu
            View llMatKhau = findViewById(R.id.llMatKhau);
            if (llMatKhau != null) llMatKhau.setVisibility(View.VISIBLE);

            // Chọn chức vụ
            for (int i = 0; i < arrChucVu.length; i++) {
                if (arrChucVu[i].equals(currentNV.getChucVu())) {
                    spChucVu.setSelection(i);
                    break;
                }
            }
        }
    }

    @Override
    protected void saveStaff() {
        String ten = etHoTen.getText().toString().trim();
        String dc = etDiaChi.getText().toString().trim();
        String luongStr = etLuong.getText().toString().trim();
        int posCV = spChucVu.getSelectedItemPosition();
        EditText MatKhau = findViewById(R.id.etMatKhau);
        String mkMoi = MatKhau.getText().toString().trim();

        if (ten.isEmpty() || luongStr.isEmpty() || posCV == 0) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }

        currentNV.setHoTen(ten);
        currentNV.setDiaChi(dc);
        currentNV.setLuong(Integer.parseInt(luongStr));
        currentNV.setChucVu(arrChucVu[posCV]);

        if (!mkMoi.isEmpty()) {
            currentNV.setMatKhau(mkMoi);
        }

        if (dao.update(currentNV) > 0) {
            Toast.makeText(this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
            setResult(RESULT_OK);
            finish();
        } else {
            Toast.makeText(this, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
        }
    }
}