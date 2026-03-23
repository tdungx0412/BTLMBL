package com.example.dienlanh24h;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class DetailActivity extends AppCompatActivity {

    ImageView imgDetail;
    TextView tvName, tvPrice, tvDesc, tvPhone, tvCategory;
    Button btnCall, btnEdit;
    ServiceItem service;
    DataManager dataManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        imgDetail = findViewById(R.id.detailImg);
        tvName = findViewById(R.id.detailName);
        tvPrice = findViewById(R.id.detailPrice);
        tvDesc = findViewById(R.id.detailDesc);
        tvPhone = findViewById(R.id.detailPhone);
        tvCategory = findViewById(R.id.detailCategory);
        btnCall = findViewById(R.id.btnCallNow);
        btnEdit = findViewById(R.id.btnEdit);

        dataManager = new DataManager(this);
        service = (ServiceItem) getIntent().getSerializableExtra("service");

        if (service != null) {
            displayService();
        }

        btnCall.setOnClickListener(v -> {
            Intent callIntent = new Intent(Intent.ACTION_DIAL);
            callIntent.setData(Uri.parse("tel:" + service.getPhone()));
            startActivity(callIntent);
        });

        btnEdit.setOnClickListener(v -> {
            Intent intent = new Intent(DetailActivity.this, AddServiceActivity.class);
            intent.putExtra("editService", service);
            startActivity(intent);
        });
    }

    private void displayService() {
        tvName.setText(service.getName());
        tvPrice.setText(service.getPrice());
        tvDesc.setText(service.getDescription());
        tvPhone.setText(service.getPhone());
        tvCategory.setText(service.getCategory());
        imgDetail.setImageResource(service.getImageResource());
    }

    @Override
    protected void onResume() {
        super.onResume();
        service = (ServiceItem) getIntent().getSerializableExtra("service");
        if (service != null) {
            displayService();
        }
    }
}