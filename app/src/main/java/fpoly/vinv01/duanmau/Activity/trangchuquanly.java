package fpoly.vinv01.duanmau.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.card.MaterialCardView;

import fpoly.vinv01.duanmau.R;

public class trangchuquanly extends AppCompatActivity {

    // Khai báo các CardView cho phần Thống kê
    private MaterialCardView cardRevenue, cardTopProduct, cardTopCustomer;

    // Khai báo các CardView cho phần Quản lý
    private MaterialCardView cardCategory, cardProduct, cardCustomer,
            cardEmployee, cardInvoice, cardPassword, cardLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trangchuquanly);

        // 1. Ánh xạ ID cho các CardView phần Thống kê
        cardRevenue = findViewById(R.id.cardRevenue);
        cardTopProduct = findViewById(R.id.cardTopProduct);
        cardTopCustomer = findViewById(R.id.cardTopCustomer);

        // 2. Ánh xạ ID cho các CardView phần Quản lý
        cardCategory = findViewById(R.id.cardCategory);
        cardProduct = findViewById(R.id.cardProduct);
        cardCustomer = findViewById(R.id.cardCustomer);
        cardEmployee = findViewById(R.id.cardEmployee);
        cardInvoice = findViewById(R.id.cardInvoice);
        cardPassword = findViewById(R.id.cardPassword);
        cardLogout = findViewById(R.id.cardLogout);

        // 3. Thiết lập sự kiện click cho các CardView
        setupClickListeners();
    }

    private void setupClickListeners() {
        // Sự kiện cho phần Thống kê
        cardRevenue.setOnClickListener(v -> {
            Intent intent = new Intent(trangchuquanly.this, thongkedoanhthuActivity.class);
            startActivity(intent);
            // Thêm logic chuyển màn hình hoặc xử lý tại đây
        });

        cardTopProduct.setOnClickListener(v -> {
            Intent intent = new Intent(trangchuquanly.this, TopProductsActivity.class);
            startActivity(intent);
            // Thêm logic chuyển màn hình hoặc xử lý tại đây
        });

        cardTopCustomer.setOnClickListener(v -> {
            Intent intent = new Intent(trangchuquanly.this, TopCustomerActivity.class);
            startActivity(intent);
        });

        // Sự kiện cho phần Quản lý
        cardCategory.setOnClickListener(v -> {
            Intent intent = new Intent(trangchuquanly.this, QLDanhMucActivity.class);
            startActivity(intent);

        });

        cardProduct.setOnClickListener(v -> {
            Intent intent = new Intent(trangchuquanly.this, QLSanPhamActivity.class);
            startActivity(intent);
        });

        cardCustomer.setOnClickListener(v -> {
            Intent intent = new Intent(trangchuquanly.this, QLKhachHangActivity.class);
            startActivity(intent);
        });

        cardEmployee.setOnClickListener(v -> {
            Intent intent = new Intent(trangchuquanly.this, QLNhanVienActivity.class);
            startActivity(intent);

        });

        cardInvoice.setOnClickListener(v -> {
            Intent intent = new Intent(trangchuquanly.this, QLHoaDonActivity.class);
            startActivity(intent);
        });

        cardPassword.setOnClickListener(v -> {
            Intent intent = new Intent(trangchuquanly.this, DoiMatKhauActivity.class);
            startActivity(intent);
        });

        cardLogout.setOnClickListener(v -> {
            // 1. Mở SharedPreferences với đúng tên "LOGIN" mà Vĩ đã đặt bên LoginActivity
            SharedPreferences sharedPreferences = getSharedPreferences("LOGIN", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();

            // 2. Chỉnh trạng thái remember thành false để nó không tự động login nữa
            editor.putBoolean("remember", false);
            // Nếu muốn xóa sạch cả tên đăng nhập và mật khẩu thì dùng: editor.clear();
            editor.apply();

            Toast.makeText(trangchuquanly.this, "Đã đăng xuất", Toast.LENGTH_SHORT).show();

            // 3. Chuyển về Login và xóa Stack
            Intent intent = new Intent(trangchuquanly.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });
    }
}
