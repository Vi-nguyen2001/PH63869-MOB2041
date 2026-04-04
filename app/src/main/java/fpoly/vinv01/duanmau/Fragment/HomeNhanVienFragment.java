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
import fpoly.vinv01.duanmau.Activity.QLSanPhamActivity;
import fpoly.vinv01.duanmau.R;

public class HomeNhanVienFragment extends Fragment {

    private MaterialCardView cardCategory, cardProduct, cardCustomer, cardInvoice;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_nhanvien, container, false);

        cardCategory = view.findViewById(R.id.cardCategory);
        cardProduct = view.findViewById(R.id.cardProduct);
        cardCustomer = view.findViewById(R.id.cardCustomer);
        cardInvoice = view.findViewById(R.id.cardInvoice);

        // Ẩn 2 card này vì đã sang màn cài đặt
        MaterialCardView cardPassword = view.findViewById(R.id.cardPassword);
        MaterialCardView cardLogout = view.findViewById(R.id.cardLogout);
        if(cardPassword != null) cardPassword.setVisibility(View.GONE);
        if(cardLogout != null) cardLogout.setVisibility(View.GONE);

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
        cardInvoice.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), QLHoaDonActivity.class);
            startActivity(intent);
        });

        return view;
    }
}
