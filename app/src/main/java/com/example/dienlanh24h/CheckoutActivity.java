package com.example.dienlanh24h;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

public class CheckoutActivity extends AppCompatActivity {

    EditText etName, etPhone, etAddress, etNotes;
    RadioGroup rgPayment;
    TextView tvTotal, tvServiceDate, tvServiceTime;
    Button btnConfirm, btnCancel, btnPickDate, btnPickTime;
    DataManager dataManager;

    String itemName, itemType, price, totalAmount;
    int quantity;
    String serviceDate = "", serviceTime = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        itemType = getIntent().getStringExtra("itemType");
        if (itemType.equals("service")) {
            setContentView(R.layout.activity_checkout_service);
        } else {
            setContentView(R.layout.activity_checkout_product);
        }

        etName = findViewById(R.id.etName);
        etPhone = findViewById(R.id.etPhone);
        etAddress = findViewById(R.id.etAddress);
        etNotes = findViewById(R.id.etNotes);
        rgPayment = findViewById(R.id.rgPayment);
        tvTotal = findViewById(R.id.tvTotal);
        btnConfirm = findViewById(R.id.btnConfirm);
        btnCancel = findViewById(R.id.btnCancel);

        if (itemType.equals("service")) {
            tvServiceDate = findViewById(R.id.tvServiceDate);
            tvServiceTime = findViewById(R.id.tvServiceTime);
            btnPickDate = findViewById(R.id.btnPickDate);
            btnPickTime = findViewById(R.id.btnPickTime);

            btnPickDate.setOnClickListener(v -> pickDate());
            btnPickTime.setOnClickListener(v -> pickTime());
        }

        dataManager = new DataManager(this);

        itemName = getIntent().getStringExtra("itemName");
        itemType = getIntent().getStringExtra("itemType");
        price = getIntent().getStringExtra("price");
        quantity = getIntent().getIntExtra("quantity", 1);
        totalAmount = getIntent().getStringExtra("totalAmount");

        tvTotal.setText(totalAmount);

        btnConfirm.setOnClickListener(v -> {
            if (validateInput()) {
                if (itemType.equals("service") && serviceDate.isEmpty()) {
                    Toast.makeText(this, "Vui lòng chọn ngày hẹn!", Toast.LENGTH_SHORT).show();
                    return;
                }
                createTransaction();
            }
        });

        btnCancel.setOnClickListener(v -> finish());
    }

    private void pickDate() {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, year, month, dayOfMonth) -> {
                    serviceDate = dayOfMonth + "/" + (month + 1) + "/" + year;
                    tvServiceDate.setText(serviceDate);
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    private void pickTime() {
        Calendar calendar = Calendar.getInstance();
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                (view, hourOfDay, minute) -> {
                    serviceTime = String.format("%02d:%02d", hourOfDay, minute);
                    tvServiceTime.setText(serviceTime);
                },
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                true);
        timePickerDialog.show();
    }

    private boolean validateInput() {
        if (etName.getText().toString().trim().isEmpty()) {
            etName.setError("Nhập họ tên");
            return false;
        }
        if (etPhone.getText().toString().trim().isEmpty()) {
            etPhone.setError("Nhập SĐT");
            return false;
        }
        if (etAddress.getText().toString().trim().isEmpty()) {
            etAddress.setError("Nhập địa chỉ");
            return false;
        }
        return true;
    }

    private void createTransaction() {
        String customerId = UUID.randomUUID().toString();
        String customerName = etName.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();
        String address = etAddress.getText().toString().trim();
        String notes = etNotes.getText().toString().trim();

        String paymentMethod = "COD";
        int selectedId = rgPayment.getCheckedRadioButtonId();
        if (selectedId == R.id.rbBank) paymentMethod = "Bank Transfer";
        else if (selectedId == R.id.rbMomo) paymentMethod = "MoMo";

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
        String date = sdf.format(new Date());

        Transaction transaction = new Transaction(
                UUID.randomUUID().toString(), customerId, itemName, itemType,
                price, quantity, totalAmount, "pending", paymentMethod,
                date, address, phone, customerName, serviceDate, serviceTime, notes
        );

        dataManager.addTransaction(transaction);
        Toast.makeText(this, itemType.equals("service") ? "✅ Đặt lịch thành công!" : "✅ Đặt hàng thành công!", Toast.LENGTH_LONG).show();
        finish();
    }
}