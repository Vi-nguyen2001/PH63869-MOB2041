package fpoly.vinv01.duanmau.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import fpoly.vinv01.duanmau.DAO.NhanVienDAO;
import fpoly.vinv01.duanmau.R;

public class DoiMatKhauActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextInputLayout tilCurrent, tilNew, tilConfirm;
    private TextInputEditText etCurrent, etNew, etConfirm;
    private MaterialButton btnSave, btnCancel;
    private NhanVienDAO dao;
    private String maNV;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doi_mat_khau);

        // Initialize views
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tilCurrent = findViewById(R.id.Doimatkhau);
        etCurrent = (TextInputEditText) tilCurrent.getEditText();

        tilNew = findViewById(R.id.MatKhauMoi);
        etNew = (TextInputEditText) tilNew.getEditText();

        tilConfirm = findViewById(R.id.XacNhanMatKhau);
        etConfirm = (TextInputEditText) tilConfirm.getEditText();

        btnSave = findViewById(R.id.btn_save);
        btnCancel = findViewById(R.id.btn_cancel);

        dao = new NhanVienDAO(this);
        SharedPreferences sharedPreferences = getSharedPreferences("LOGIN", MODE_PRIVATE);
        maNV = sharedPreferences.getString("username", "");


        // Set listeners
        btnSave.setOnClickListener(v -> {
            String oldPass = etCurrent.getText().toString().trim();
            String newPass = etNew.getText().toString().trim();
            String confirmPass = etConfirm.getText().toString().trim();

            if (oldPass.isEmpty() || newPass.isEmpty() || confirmPass.isEmpty()) {
                Toast.makeText(this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!dao.checkOldPassword(maNV, oldPass)) {
                Toast.makeText(this, "Mật khẩu cũ không đúng", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!newPass.equals(confirmPass)) {
                Toast.makeText(this, "Mật khẩu mới không trùng khớp", Toast.LENGTH_SHORT).show();
                return;
            }
            if (newPass.length() < 6) {
                Toast.makeText(this, "Mật khẩu mới phải có ít nhất 6 ký tự", Toast.LENGTH_SHORT).show();
                return;
            }

            int result = dao.updatePassword(maNV, newPass);
            if (result > 0) {
                Toast.makeText(this, "Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.apply();
                Intent intent = new Intent(DoiMatKhauActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(this, "Đổi mật khẩu thất bại", Toast.LENGTH_SHORT).show();
            }
        });

        btnCancel.setOnClickListener(v -> finish());
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed(); // Hoặc finish();
        return true;
    }
}
