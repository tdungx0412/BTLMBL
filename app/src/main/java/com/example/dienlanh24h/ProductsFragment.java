package com.example.dienlanh24h;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class ProductsFragment extends Fragment {

    RecyclerView recyclerView;
    ProductAdapter adapter;
    List<ProductItem> productList, filteredList;
    DataManager dataManager;
    EditText etSearch;
    Spinner spinnerCategory;
    TextView tvNoResults;
    String selectedCategory = "Tất cả";
    String[] categories = {"Tất cả", "Phụ tùng", "Dụng cụ", "Vật tư tiêu hao", "Khác"};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_products, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        etSearch = view.findViewById(R.id.etSearch);
        spinnerCategory = view.findViewById(R.id.spinnerCategory);
        tvNoResults = view.findViewById(R.id.tvNoResults);

        dataManager = new DataManager(getContext());
        loadProducts();

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new ProductAdapter(getContext(), filteredList, dataManager);
        recyclerView.setAdapter(adapter);

        setupSpinner();
        setupSearch();
        return view;
    }

    private void loadProducts() {
        productList = dataManager.getProducts();
        filteredList = new ArrayList<>(productList);
    }

    private void setupSpinner() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_item, categories);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(adapter);
        spinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedCategory = categories[position];
                filterData();
            }
            @Override public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    private void setupSearch() {
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) { filterData(); }
            @Override public void afterTextChanged(Editable s) {}
        });
    }

    private void filterData() {
        String searchText = etSearch.getText().toString().trim().toLowerCase();
        filteredList = new ArrayList<>();
        for (ProductItem product : productList) {
            boolean matchesSearch = product.getName().toLowerCase().contains(searchText) ||
                    product.getDescription().toLowerCase().contains(searchText);
            boolean matchesCategory = selectedCategory.equals("Tất cả") ||
                    product.getCategory().equals(selectedCategory);
            if (matchesSearch && matchesCategory) filteredList.add(product);
        }

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
    public void onResume() {
        super.onResume();
        loadProducts();
        filterData();
    }
}