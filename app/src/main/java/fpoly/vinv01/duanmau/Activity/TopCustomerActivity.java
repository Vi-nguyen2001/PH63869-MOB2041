package fpoly.vinv01.duanmau.Activity;

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
import fpoly.vinv01.duanmau.DAO.ThongKeDAO;
import fpoly.vinv01.duanmau.Adapter.TopCustomerAdapter;
import fpoly.vinv01.duanmau.Model.CustomerTop;
import fpoly.vinv01.duanmau.R;

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


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        toolbar.setNavigationOnClickListener(v -> {
            finish();
        });


        etFromDate = findViewById(R.id.etFromDate);
        etToDate = findViewById(R.id.etToDate);
        etQuantity = findViewById(R.id.etQuantity);
        btnQuery = findViewById(R.id.btnQuery);
        lvTopCustomers = findViewById(R.id.lvTopCustomers);
        tvResultCount = findViewById(R.id.tvResultCount);

        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());


        etFromDate.setOnClickListener(v -> showDatePicker(etFromDate));
        etToDate.setOnClickListener(v -> showDatePicker(etToDate));
        customerList = new ArrayList<>();



        adapter = new TopCustomerAdapter(this, customerList);
        lvTopCustomers.setAdapter(adapter);
        tvResultCount.setText("Đã tải " + customerList.size() + " kết quả");


        btnQuery.setOnClickListener(v -> {
            String fromDate = etFromDate.getText().toString();
            String toDate = etToDate.getText().toString();
            String quantity = etQuantity.getText().toString();

            if (fromDate.isEmpty() || toDate.isEmpty() || quantity.isEmpty()) {
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

            int limit = Integer.parseInt(quantity);
            ThongKeDAO thongKeDAO = new ThongKeDAO(this);
            List<CustomerTop> topCustomers = thongKeDAO.getTopCustomersByDate(fromDate, toDate, limit);
            if (topCustomers.isEmpty()){
                Toast.makeText(this, "Không có dữ liệu", Toast.LENGTH_SHORT).show();
            }
            customerList.clear();
            customerList.addAll(topCustomers);
            adapter.notifyDataSetChanged();
            tvResultCount.setText("Đã tải " + customerList.size() + " kết quả");
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