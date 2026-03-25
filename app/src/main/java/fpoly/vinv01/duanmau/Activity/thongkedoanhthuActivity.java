package fpoly.vinv01.duanmau.Activity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import fpoly.vinv01.duanmau.R;

public class thongkedoanhthuActivity extends AppCompatActivity {

    private TextInputEditText etFromDate, etToDate;
    private MaterialButton btnQuery;
    private Calendar calendar;
    private SimpleDateFormat dateFormat;
    private TextView tvTotalRevenue;
    private fpoly.vinv01.duanmau.DAO.ThongKeDAO thongKeDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.thongthedoangthu);

        // Initialize Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        // Handle Back button
        toolbar.setNavigationOnClickListener(v -> {
            finish();
        });

        // Initialize Views
        etFromDate = findViewById(R.id.etFromDate);
        etToDate = findViewById(R.id.etToDate);
        btnQuery = findViewById(R.id.btnQuery);

        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

        // Set Click Listeners for Date Selection
        etFromDate.setOnClickListener(v -> showDatePicker(etFromDate));
        etToDate.setOnClickListener(v -> showDatePicker(etToDate));
        tvTotalRevenue = findViewById(R.id.tvTotalRevenue);
        thongKeDAO = new fpoly.vinv01.duanmau.DAO.ThongKeDAO(this);

        // Query Button Listener
        btnQuery.setOnClickListener(v -> {
            String fromDate = etFromDate.getText().toString();
            String toDate = etToDate.getText().toString();

            if (fromDate.isEmpty() || toDate.isEmpty()) {
                Toast.makeText(this, "Vui lòng chọn đầy đủ khoảng ngày", Toast.LENGTH_SHORT).show();
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

            // Logic truy vấn từ Database sẽ được thêm ở đây
            int doanhThu = thongKeDAO.getDoanhThu(fromDate, toDate);
            //định dạng tiền
            DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
            tvTotalRevenue.setText(decimalFormat.format(doanhThu) + " VNĐ");
            Toast.makeText(this, "Đang truy vấn dữ liệu từ " + fromDate + " đến " + toDate, Toast.LENGTH_SHORT).show();
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