package fpoly.vinv01.duanmau.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.card.MaterialCardView;

import fpoly.vinv01.duanmau.R;

public class trangchunhanvien extends AppCompatActivity {

    private MaterialCardView cardCategory, cardProduct, cardCustomer, cardInvoice, cardPassword, cardLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trangchunhanvien);

        // Initialize Views
        cardCategory = findViewById(R.id.cardCategory);
        cardProduct = findViewById(R.id.cardProduct);
        cardCustomer = findViewById(R.id.cardCustomer);
        cardInvoice = findViewById(R.id.cardInvoice);
        cardPassword = findViewById(R.id.cardPassword);
        cardLogout = findViewById(R.id.cardLogout);

        // Set Click Listeners
        cardCategory.setOnClickListener(v -> {
            Intent intent = new Intent(trangchunhanvien.this, QLDanhMucActivity.class);
            startActivity(intent);
        });
        cardProduct.setOnClickListener(v -> {
            Intent intent = new Intent(trangchunhanvien.this, QLSanPhamActivity.class);
            startActivity(intent);
        });
        cardCustomer.setOnClickListener(v -> {
            Intent intent = new Intent(trangchunhanvien.this, QLKhachHangActivity.class);
            startActivity(intent);
        });
        cardInvoice.setOnClickListener(v -> {
            Intent intent = new Intent(trangchunhanvien.this, QLHoaDonActivity.class);
            startActivity(intent);
        });
        cardPassword.setOnClickListener(v -> {
            Intent intent = new Intent(trangchunhanvien.this, DoiMatKhauActivity.class);
            startActivity(intent);
        });
        
        cardLogout.setOnClickListener(v -> {
            //xóa trạng thái tự động đăng nhập
            SharedPreferences sharedPreferences = getSharedPreferences("LOGIN", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("remember", false);//chặn tự động login
            editor.apply();
            Toast.makeText(trangchunhanvien.this, "Đã đăng xuất", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(trangchunhanvien.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });

    }

}