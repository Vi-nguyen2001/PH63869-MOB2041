package fpoly.vinv01.duanmau;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.card.MaterialCardView;

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
        cardCategory.setOnClickListener(v -> showToast("Quản lý danh mục"));
        cardProduct.setOnClickListener(v -> showToast("Quản lý sản phẩm"));
        cardCustomer.setOnClickListener(v -> showToast("Quản lý khách hàng"));
        cardInvoice.setOnClickListener(v -> showToast("Quản lý hóa đơn"));
        cardPassword.setOnClickListener(v -> showToast("Đổi mật khẩu"));
        
        cardLogout.setOnClickListener(v -> {
            Intent intent = new Intent(trangchunhanvien.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}