package fpoly.vinv01.duanmau.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.card.MaterialCardView;

import fpoly.vinv01.duanmau.Activity.QLDanhMucActivity;
import fpoly.vinv01.duanmau.Activity.QLHoaDonActivity;
import fpoly.vinv01.duanmau.Activity.QLKhachHangActivity;
import fpoly.vinv01.duanmau.Activity.QLNhanVienActivity;
import fpoly.vinv01.duanmau.Activity.QLSanPhamActivity;
import fpoly.vinv01.duanmau.Activity.TopCustomerActivity;
import fpoly.vinv01.duanmau.Activity.TopProductsActivity;
import fpoly.vinv01.duanmau.Activity.thongkedoanhthuActivity;
import fpoly.vinv01.duanmau.R;

public class HomeQuanLyFragment extends Fragment {

    private MaterialCardView cardRevenue, cardTopProduct, cardTopCustomer;
    private MaterialCardView cardCategory, cardProduct, cardCustomer, cardEmployee, cardInvoice;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_quanly, container, false);

        // 1. Ánh xạ ID cho các CardView phần Thống kê
        cardRevenue = view.findViewById(R.id.cardRevenue);
        cardTopProduct = view.findViewById(R.id.cardTopProduct);
        cardTopCustomer = view.findViewById(R.id.cardTopCustomer);

        // 2. Ánh xạ ID cho các CardView phần Quản lý
        cardCategory = view.findViewById(R.id.cardCategory);
        cardProduct = view.findViewById(R.id.cardProduct);
        cardCustomer = view.findViewById(R.id.cardCustomer);
        cardEmployee = view.findViewById(R.id.cardEmployee);
        cardInvoice = view.findViewById(R.id.cardInvoice);

        // Nút Password và Logout đã được chuyển sang CaiDatFragment
        MaterialCardView cardPassword = view.findViewById(R.id.cardPassword);
        MaterialCardView cardLogout = view.findViewById(R.id.cardLogout);
        
        if (cardPassword != null) cardPassword.setVisibility(View.GONE);
        if (cardLogout != null) cardLogout.setVisibility(View.GONE);

        // 3. Thiết lập sự kiện click cho các CardView
        setupClickListeners();

        return view;
    }

    private void setupClickListeners() {
        cardRevenue.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), thongkedoanhthuActivity.class);
            startActivity(intent);
        });

        cardTopProduct.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), TopProductsActivity.class);
            startActivity(intent);
        });

        cardTopCustomer.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), TopCustomerActivity.class);
            startActivity(intent);
        });

        cardCategory.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), QLDanhMucActivity.class);
            startActivity(intent);
        });

        cardProduct.setOnClickListener(v -> {
            com.google.android.material.bottomnavigation.BottomNavigationView bottomNav = requireActivity().findViewById(R.id.bottom_nav);
            if (bottomNav != null) {
                bottomNav.setSelectedItemId(R.id.nav_product);
            }
        });

        cardCustomer.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), QLKhachHangActivity.class);
            startActivity(intent);
        });

        cardEmployee.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), QLNhanVienActivity.class);
            startActivity(intent);
        });

        cardInvoice.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), QLHoaDonActivity.class);
            startActivity(intent);
        });
    }
}
