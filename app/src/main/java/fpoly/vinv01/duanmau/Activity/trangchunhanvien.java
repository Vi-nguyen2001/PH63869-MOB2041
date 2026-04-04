package fpoly.vinv01.duanmau.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.card.MaterialCardView;

import fpoly.vinv01.duanmau.Fragment.CaiDatFragment;
import fpoly.vinv01.duanmau.Fragment.QLSanPhamFragment;
import fpoly.vinv01.duanmau.R;

public class trangchunhanvien extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private View oldContent, frameContainer;

    private MaterialCardView cardCategory, cardProduct, cardCustomer, cardInvoice, cardPassword, cardLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trangchunhanvien);

        bottomNavigationView = findViewById(R.id.bottom_nav);
        oldContent = findViewById(R.id.old_content);
        frameContainer = findViewById(R.id.frame_container);

        cardCategory = findViewById(R.id.cardCategory);
        cardProduct = findViewById(R.id.cardProduct);
        cardCustomer = findViewById(R.id.cardCustomer);
        cardInvoice = findViewById(R.id.cardInvoice);
        cardPassword = findViewById(R.id.cardPassword);
        cardLogout = findViewById(R.id.cardLogout);

        setupClickListeners();

        if (savedInstanceState == null) {
            bottomNavigationView.setSelectedItemId(R.id.nav_home);
        }

        bottomNavigationView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_home) {
                oldContent.setVisibility(View.VISIBLE);
                frameContainer.setVisibility(View.GONE);
                return true;
            } else if (id == R.id.nav_product) {
                oldContent.setVisibility(View.GONE);
                frameContainer.setVisibility(View.VISIBLE);
                replaceFragment(new QLSanPhamFragment());
                return true;
            } else if (id == R.id.nav_setting) {
                oldContent.setVisibility(View.GONE);
                frameContainer.setVisibility(View.VISIBLE);
                replaceFragment(new CaiDatFragment());
                return true;
            }
            return false;
        });
    }

    private void setupClickListeners() {
        cardCategory.setOnClickListener(v -> {
            Intent intent = new Intent(this, QLDanhMucActivity.class);
            startActivity(intent);
        });

        cardProduct.setOnClickListener(v -> {
            bottomNavigationView.setSelectedItemId(R.id.nav_product);
        });

        cardCustomer.setOnClickListener(v -> {
            Intent intent = new Intent(this, QLKhachHangActivity.class);
            startActivity(intent);
        });

        cardInvoice.setOnClickListener(v -> {
            Intent intent = new Intent(this, QLHoaDonActivity.class);
            startActivity(intent);
        });

        cardPassword.setOnClickListener(v -> {
            Intent intent = new Intent(this, DoiMatKhauActivity.class);
            startActivity(intent);
        });

        cardLogout.setOnClickListener(v -> {
            SharedPreferences sharedPreferences = getSharedPreferences("LOGIN", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("remember", false);
            editor.apply();

            Toast.makeText(this, "Đã đăng xuất", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });
    }

    public void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.commit();
    }
}