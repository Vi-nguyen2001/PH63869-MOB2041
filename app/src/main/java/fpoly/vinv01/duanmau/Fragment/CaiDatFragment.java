package fpoly.vinv01.duanmau.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.card.MaterialCardView;

import fpoly.vinv01.duanmau.Activity.DoiMatKhauActivity;
import fpoly.vinv01.duanmau.Activity.LoginActivity;
import fpoly.vinv01.duanmau.R;

public class CaiDatFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cai_dat, container, false);

        MaterialCardView cardDoiMatKhau = view.findViewById(R.id.cardDoiMatKhau);
        MaterialCardView cardDangXuat = view.findViewById(R.id.cardDangXuat);

        cardDoiMatKhau.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), DoiMatKhauActivity.class);
            startActivity(intent);
        });

        cardDangXuat.setOnClickListener(v -> {
            SharedPreferences sharedPreferences = requireContext().getSharedPreferences("LOGIN", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("remember", false);
            editor.apply();

            Toast.makeText(requireContext(), "Đã đăng xuất", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(requireContext(), LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            requireActivity().finish();
        });

        return view;
    }
}
