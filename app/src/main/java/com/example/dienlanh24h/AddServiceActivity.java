package com.example.dienlanh24h;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.UUID;

public class AddServiceActivity extends AppCompatActivity {

    EditText etName, etPrice, etPhone, etDesc;
    Spinner spinnerCategory;
    Button btnSave, btnCancel;
    DataManager dataManager;
    ServiceItem editService;
    boolean isEditMode = false;

    String[] categories = {"Máy lạnh", "Tủ lạnh", "Máy giặt", "Máy nước nóng", "Khác"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_service);

        etName = findViewById(R.id.etName);
        etPrice = findViewById(R.id.etPrice);
        etPhone = findViewById(R.id.etPhone);
        etDesc = findViewById(R.id.etDesc);
        spinnerCategory = findViewById(R.id.spinnerCategory);
        btnSave = findViewById(R.id.btnSave);
        btnCancel = findViewById(R.id.btnCancel);

        dataManager = new DataManager(this);

        editService = (ServiceItem) getIntent().getSerializableExtra("editService");
        if (editService != null) {
            isEditMode = true;
            populateEditData();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, categories);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(adapter);

        btnSave.setOnClickListener(v -> {
            if (validateInput()) {
                saveService();
            }
        });

        btnCancel.setOnClickListener(v -> finish());
    }

    private void populateEditData() {
        etName.setText(editService.getName());
        etPrice.setText(editService.getPrice().replace("đ", ""));
        etPhone.setText(editService.getPhone());
        etDesc.setText(editService.getDescription());

        for (int i = 0; i < categories.length; i++) {
            if (categories[i].equals(editService.getCategory())) {
                spinnerCategory.setSelection(i);
                break;
            }
        }
    }

    private boolean validateInput() {
        if (etName.getText().toString().trim().isEmpty()) {
            etName.setError("Vui lòng nhập tên dịch vụ");
            return false;
        }
        if (etPrice.getText().toString().trim().isEmpty()) {
            etPrice.setError("Vui lòng nhập giá");
            return false;
        }
        if (etPhone.getText().toString().trim().isEmpty()) {
            etPhone.setError("Vui lòng nhập số điện thoại");
            return false;
        }
        return true;
    }

    private void saveService() {
        String name = etName.getText().toString().trim();
        String price = etPrice.getText().toString().trim() + "đ";
        String phone = etPhone.getText().toString().trim();
        String desc = etDesc.getText().toString().trim();
        String category = spinnerCategory.getSelectedItem().toString();

        if (isEditMode && editService != null) {
            editService.setName(name);
            editService.setPrice(price);
            editService.setPhone(phone);
            editService.setDescription(desc);
            editService.setCategory(category);
            dataManager.updateService(editService);
            Toast.makeText(this, "Đã cập nhật dịch vụ!", Toast.LENGTH_SHORT).show();
        } else {
            String id = UUID.randomUUID().toString();
            ServiceItem newService = new ServiceItem(id, name, price, phone, desc,
                    android.R.drawable.ic_menu_gallery, category);
            dataManager.addService(newService);
            Toast.makeText(this, "Đã thêm dịch vụ thành công!", Toast.LENGTH_SHORT).show();
        }

        finish();
    }
}