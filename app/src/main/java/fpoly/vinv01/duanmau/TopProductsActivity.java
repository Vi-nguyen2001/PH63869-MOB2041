package fpoly.vinv01.duanmau;

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

public class TopProductsActivity extends AppCompatActivity {

    private TextInputEditText etFromDate, etToDate, etQuantity;
    private MaterialButton btnQuery;
    private ListView lvTopProducts;
    private Calendar calendar;
    private SimpleDateFormat dateFormat;
    private List<ProductTop> productList;
    private TopProductAdapter adapter;

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
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

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

        // Setup ListView with Dummy Data
        productList = new ArrayList<>();
        // Add sample data to test UI
        productList.add(new ProductTop(1, "SP00124", "Trà Xanh Thái Nguyên Đặc Biệt", 1240, R.drawable.anhsanpham));
        productList.add(new ProductTop(2, "SP00189", "Cà Phê Arabica Nguyên Chất", 956, R.drawable.anhsanpham));
        productList.add(new ProductTop(3, "SP00231", "Mật Ong Hoa Nhãn 500ml", 812, R.drawable.anhsanpham));
        productList.add(new ProductTop(4, "SP00542", "Hạt Điều Rang Muối Loại A", 645, R.drawable.anhsanpham));
        productList.add(new ProductTop(5, "SP00901", "Gạo Lứt Huyết Rồng 2kg", 432, R.drawable.anhsanpham));

        // Use the updated BaseAdapter constructor
        adapter = new TopProductAdapter(this, productList);
        lvTopProducts.setAdapter(adapter);

        // Query Button
        btnQuery.setOnClickListener(v -> {
            String fromDate = etFromDate.getText().toString();
            String toDate = etToDate.getText().toString();
            String quantity = etQuantity.getText().toString();

            if (fromDate.isEmpty() || toDate.isEmpty() || quantity.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Đang truy vấn Top " + quantity + " sản phẩm từ " + fromDate + " đến " + toDate, Toast.LENGTH_SHORT).show();
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
}