package com.example.dienlanh24h;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity implements ServiceAdapter.OnServiceClickListener {

    RecyclerView recyclerView;
    ServiceAdapter adapter;
    List<ServiceItem> serviceList;
    List<ServiceItem> filteredList;
    DataManager dataManager;

    EditText etSearch;
    Spinner spinnerCategory;
    TextView tvNoResults;
    FloatingActionButton fabAdd;

    String selectedCategory = "Tất cả";
    String[] categories = {"Tất cả", "Máy lạnh", "Tủ lạnh", "Máy giặt", "Máy nước nóng", "Khác"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        etSearch = findViewById(R.id.etSearch);
        spinnerCategory = findViewById(R.id.spinnerCategory);
        tvNoResults = findViewById(R.id.tvNoResults);
        fabAdd = findViewById(R.id.fabAdd);

        dataManager = new DataManager(this);
        loadServices();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ServiceAdapter(this, filteredList, dataManager, this);
        recyclerView.setAdapter(adapter);

        setupSpinner();
        setupSearch();

        fabAdd.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddServiceActivity.class);
            startActivity(intent);
        });
    }

    private void loadServices() {
        serviceList = dataManager.getServices();
        filteredList = new ArrayList<>(serviceList);
    }

    private void setupSpinner() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, categories);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(adapter);

        spinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedCategory = categories[position];
                filterData();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    private void setupSearch() {
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterData();
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private void filterData() {
        String searchText = etSearch.getText().toString().trim().toLowerCase();

        filteredList = serviceList.stream()
                .filter(service -> {
                    boolean matchesSearch = service.getName().toLowerCase().contains(searchText) ||
                            service.getDescription().toLowerCase().contains(searchText);
                    boolean matchesCategory = selectedCategory.equals("Tất cả") ||
                            service.getCategory().equals(selectedCategory);
                    return matchesSearch && matchesCategory;
                })
                .collect(Collectors.toList());

        if (filteredList.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            tvNoResults.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            tvNoResults.setVisibility(View.GONE);
            adapter.updateList(filteredList);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadServices();
        filterData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_reload) {
            loadServices();
            filterData();
            Toast.makeText(this, "Đã làm mới dữ liệu", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onEdit(ServiceItem service) {
        Intent intent = new Intent(this, AddServiceActivity.class);
        intent.putExtra("editService", service);
        startActivity(intent);
    }
}