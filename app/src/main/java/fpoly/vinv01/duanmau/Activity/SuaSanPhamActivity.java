package fpoly.vinv01.duanmau.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import fpoly.vinv01.duanmau.R;
import fpoly.vinv01.duanmau.Model.SanPham;

public class SuaSanPhamActivity extends ThemSanPhamActivity {

    private SanPham currentProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Lưu ý: setContentView đã được gọi ở lớp cha (ThemSanPhamActivity)

        // 1. Ánh xạ và đổi tiêu đề Toolbar cho đúng màn hình Sửa
        TextView tvTitle = findViewById(R.id.tvToolbarTitle);
        if (tvTitle != null) {
            tvTitle.setText("Sửa sản phẩm");
        }

        // 2. Hiện các trường Mã sản phẩm (vốn bị ẩn ở màn hình Thêm)
        View lblMaSP = findViewById(R.id.lblMaSP);
        View tilMaSP = findViewById(R.id.tilMaSP);
        if (lblMaSP != null) lblMaSP.setVisibility(View.VISIBLE);
        if (tilMaSP != null) tilMaSP.setVisibility(View.VISIBLE);

        // 3. Tùy chỉnh nút bấm
        btnSave.setText("Lưu thay đổi");
        btnSave.setIcon(null); // Nếu muốn bỏ icon hoặc thay icon khác

        // 4. Lấy dữ liệu sản phẩm từ Intent truyền sang
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            currentProduct = (SanPham) bundle.getSerializable("PRODUCT");
            fillData(); // Đổ dữ liệu vào các ô nhập
        }
    }

    private void fillData() {
        if (currentProduct != null) {
            // 1. Ánh xạ và xử lý Mã sản phẩm (Sửa thành %03d để ra SP001 giống ảnh)
            TextInputEditText etMaSP = findViewById(R.id.etMaSP);
            if (etMaSP != null) {
                etMaSP.setText("SP" + String.format("%03d", currentProduct.getMaSP()));
                etMaSP.setEnabled(false); // Khóa ô nhập (giống icon ổ khóa trong ảnh)
                etMaSP.setFocusable(false);
            }

            // 2. Đổ dữ liệu vào các ô nhập text
            etTenSP.setText(currentProduct.getTenSP());
            etGiaBan.setText(String.valueOf(currentProduct.getGiaBan()));
            etDonViTinh.setText(currentProduct.getDonViTinh());
            etSoLuong.setText(String.valueOf(currentProduct.getSoLuong()));
            etNgayNhap.setText(currentProduct.getNgayNhap());

            // 3. Xử lý Spinner chọn danh mục (Quan trọng: Sửa getMaDanhMuc chữ M viết hoa)
            if (danhMucList != null && !danhMucList.isEmpty()) {
                for (int i = 0; i < danhMucList.size(); i++) {
                    // Phải dùng getMaDanhMuc() mới đúng với Model DanhMuc của Vĩ
                    if (danhMucList.get(i).getMaDanhMuc() == currentProduct.getMaDanhMuc()) {
                        spDanhMuc.setSelection(i);
                        break;
                    }
                }
            }
        }
    }

    @Override
    protected void saveProduct() {
        if(danhMucList==null || danhMucList.isEmpty()){// Kiểm tra danh mục có sản phẩm hay không)
            Toast.makeText(this,"Bạn cần thêm danh mục trước khi Sửa sản phẩm",Toast.LENGTH_SHORT).show();
            return;
        }
        String ten = etTenSP.getText().toString().trim();
        String gia = etGiaBan.getText().toString().trim();
        String dvt = etDonViTinh.getText().toString().trim();
        String sl = etSoLuong.getText().toString().trim();
        String ngay = etNgayNhap.getText().toString().trim();

        if (ten.isEmpty() || gia.isEmpty() || sl.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }

        //Lấy vị trí và mã danh mục mới từ Spinner
        int pos = spDanhMuc.getSelectedItemPosition();
        int maDM = danhMucList.get(pos).getMaDanhMuc();

        //Cập nhật thông tin vào đối tượng hiện tại (currentProduct)
        currentProduct.setTenSP(ten);
        currentProduct.setGiaBan(Integer.parseInt(gia));
        currentProduct.setDonViTinh(dvt);
        currentProduct.setSoLuong(Integer.parseInt(sl));
        currentProduct.setNgayNhap(ngay);
        currentProduct.setMaDanhMuc(maDM);

        if (dao.update(currentProduct) > 0) {
            Toast.makeText(this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
            setResult(RESULT_OK);
            finish();
        }else{
            Toast.makeText(this, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
        }

    }

}