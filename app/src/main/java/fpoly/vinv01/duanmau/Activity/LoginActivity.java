package fpoly.vinv01.duanmau.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import fpoly.vinv01.duanmau.R;

public class LoginActivity extends AppCompatActivity {

    private EditText etUsername, etPassword;
    private CheckBox cbRememberMe;
    private Button btnLogin;
    private TextView tvForgotPassword;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // ánh xạ view
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        cbRememberMe = findViewById(R.id.cbRememberMe);
        btnLogin = findViewById(R.id.btnLogin);


        // khởi tạo SharedPreferences
        sharedPreferences = getSharedPreferences("LOGIN", MODE_PRIVATE);

        // kiểm tra nếu đã lưu đăng nhập trước đó
        String savedUser = sharedPreferences.getString("username", "");
        String savedPass = sharedPreferences.getString("password", "");
        boolean remember = sharedPreferences.getBoolean("remember", false);

        if (remember) {

            etUsername.setText(savedUser);
            etPassword.setText(savedPass);
            cbRememberMe.setChecked(true);

            // tự động vào MainActivity
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }

        // sự kiện login
        btnLogin.setOnClickListener(v -> {

            String username = etUsername.getText().toString();
            String password = etPassword.getText().toString();

            if (username.isEmpty() || password.isEmpty()) {

                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();

            }

            else if (username.equals("admin") && password.equals("123")) {

                SharedPreferences.Editor editor = sharedPreferences.edit();

                if (cbRememberMe.isChecked()) {

                    editor.putString("username", username);
                    editor.putString("password", password);
                    editor.putBoolean("remember", true);

                } else {

                    editor.clear();

                }

                editor.apply();

                Toast.makeText(this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(LoginActivity.this, trangchuquanly.class);
                startActivity(intent);
                finish();

            }

            else {

                Toast.makeText(this, "Sai tài khoản hoặc mật khẩu", Toast.LENGTH_SHORT).show();

            }

        });
    }
}