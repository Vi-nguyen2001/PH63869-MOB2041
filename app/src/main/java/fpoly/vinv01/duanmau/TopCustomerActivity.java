package fpoly.vinv01.duanmau;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
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

public class TopCustomerActivity extends AppCompatActivity {

    private TextInputEditText etFromDate, etToDate, etQuantity;
    private MaterialButton btnQuery;
    private ListView lvTopCustomers;
    private TextView tvResultCount;
    private Calendar calendar;
    private SimpleDateFormat dateFormat;
    private List<CustomerTop> customerList;
    private TopCustomerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_customer);

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
        lvTopCustomers = findViewById(R.id.lvTopCustomers);
        tvResultCount = findViewById(R.id.tvResultCount);

        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

        // Date selection
        etFromDate.setOnClickListener(v -> showDatePicker(etFromDate));
        etToDate.setOnClickListener(v -> showDatePicker(etToDate));

        // Setup ListView with Dummy Data
        customerList = new ArrayList<>();
        customerList.add(new CustomerTop(1, 234, "Nguyễn Văn A", 45200000, 12, R.drawable.anhkhachhang));
        customerList.add(new CustomerTop(2, 112, "Trần Thị B", 32850000, 8, R.drawable.anhkhachhang));
        customerList.add(new CustomerTop(3, 890, "Lê Hoàng C", 28400000, 15, R.drawable.anhkhachhang));
        customerList.add(new CustomerTop(4, 543, "Phạm Minh D", 15500000, 5, R.drawable.anhkhachhang));
        customerList.add(new CustomerTop(5, 321, "Hoàng Thanh E", 12100000, 4, R.drawable.anhkhachhang));

        adapter = new TopCustomerAdapter(this, customerList);
        lvTopCustomers.setAdapter(adapter);
        tvResultCount.setText("Đã tải " + customerList.size() + " kết quả");

        // Query Button
        btnQuery.setOnClickListener(v -> {
            String fromDate = etFromDate.getText().toString();
            String toDate = etToDate.getText().toString();
            String quantity = etQuantity.getText().toString();

            if (fromDate.isEmpty() || toDate.isEmpty() || quantity.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Đang truy vấn Top " + quantity + " khách hàng...", Toast.LENGTH_SHORT).show();
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