package fpoly.vinv01.duanmau.Activity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import fpoly.vinv01.duanmau.Adapter.TopProductAdapter;
import fpoly.vinv01.duanmau.DAO.ThongKeDAO;
import fpoly.vinv01.duanmau.Model.ProductTop;
import fpoly.vinv01.duanmau.R;

public class TopProductsActivity extends AppCompatActivity {

    private TextInputEditText etFromDate, etToDate, etQuantity;
    private MaterialButton btnQuery;
    private ListView lvTopProducts;
    private Calendar calendar;
    private SimpleDateFormat dateFormat;
    private List<ProductTop> productList;
    private TopProductAdapter adapter;
    private ThongKeDAO thongKeDAO;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_products);

        // Initialize Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        toolbar.setNavigationOnClickListener(v -> {
            finish();
        });
        thongKeDAO = new ThongKeDAO(this);


        // Initialize Views
        etFromDate = findViewById(R.id.etFromDate);
        etToDate = findViewById(R.id.etToDate);
        etQuantity = findViewById(R.id.etQuantity);
        btnQuery = findViewById(R.id.btnQuery);
        lvTopProducts = findViewById(R.id.lvTopProducts);

        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

        // Date selection
        etFromDate.setOnClickListener(v -> showDatePicker(etFromDate));
        etToDate.setOnClickListener(v -> showDatePicker(etToDate));

        adapter = new TopProductAdapter(this, productList);
        lvTopProducts.setAdapter(adapter);

        // Query Button
        btnQuery.setOnClickListener(v -> {
            String fromDate = etFromDate.getText().toString().trim();
            String toDate = etToDate.getText().toString().trim();
            String quantityStr = etQuantity.getText().toString().trim();

            // 1. Kiểm tra nhập liệu
            if (fromDate.isEmpty() || toDate.isEmpty() || quantityStr.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                java.util.Date startDate = dateFormat.parse(fromDate);
                java.util.Date endDate = dateFormat.parse(toDate);
                if (startDate.after(endDate)) {
                    Toast.makeText(this, "Ngày Bắt Đầu Phải Nhỏ Hơn Ngày Kết Thúc", Toast.LENGTH_SHORT).show();
                    return;
                }
            } catch (java.text.ParseException e) {
                e.printStackTrace();
                Toast.makeText(this, "Định dạng ngày không hợp lệ", Toast.LENGTH_SHORT).show();
                return;
            }

            try {

                int limit = Integer.parseInt(quantityStr);

                // 2. Gọi hàm lấy dữ liệu thật từ DAO
                // Lưu ý: Nếu hàm getTopProduct nằm trong file khác, Vĩ gọi đúng file đó
                List<ProductTop> resultList = thongKeDAO.getTopProduct(fromDate, toDate, limit);

                // 3. Kiểm tra kết quả và cập nhật giao diện
                if (resultList != null && !resultList.isEmpty()) {
                    productList.clear(); // Xóa dữ liệu mẫu cũ
                    productList.addAll(resultList); // Thêm dữ liệu thật vào list
                    adapter.notifyDataSetChanged(); // Báo cho ListView vẽ lại giao diện

                    Toast.makeText(this, "Đã cập nhật danh sách Top sản phẩm", Toast.LENGTH_SHORT).show();
                } else {
                    productList.clear();
                    adapter.notifyDataSetChanged();
                    Toast.makeText(this, "Không tìm thấy dữ liệu trong khoảng này", Toast.LENGTH_SHORT).show();
                }
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Số lượng nhập vào không hợp lệ", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showDatePicker(final TextInputEditText editText) {
        DatePickerDialog.OnDateSetListener dateSetListener = (view, year, month, dayOfMonth) -> {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            editText.setText(dateFormat.format(calendar.getTime()));
        };

        new DatePickerDialog(this, dateSetListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    private String convertDate(String date) {
        String[] s = date.split("/");
        // Chuyển từ dd/MM/yyyy sang yyyy-MM-dd
        return s[2] + "-" + s[1] + "-" + s[0];
    }
}