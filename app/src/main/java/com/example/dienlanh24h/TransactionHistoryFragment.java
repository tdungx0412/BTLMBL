package com.example.dienlanh24h;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.tabs.TabLayout;
import java.util.ArrayList;
import java.util.List;

public class TransactionHistoryFragment extends Fragment {

    RecyclerView recyclerView;
    TabLayout tabLayout;
    TransactionAdapter adapter;
    List<Transaction> transactionList;
    DataManager dataManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_transaction_history, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        tabLayout = view.findViewById(R.id.tabLayout);
        dataManager = new DataManager(getContext());

        loadTransactions("all");

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new TransactionAdapter(getContext(),
                transactionList != null ? transactionList : new ArrayList<>(), dataManager);
        recyclerView.setAdapter(adapter);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                    loadTransactions("all");
                } else if (tab.getPosition() == 1) {
                    loadTransactions("service");
                } else if (tab.getPosition() == 2) {
                    loadTransactions("product");
                }
            }
            @Override public void onTabUnselected(TabLayout.Tab tab) {}
            @Override public void onTabReselected(TabLayout.Tab tab) {}
        });

        return view;
    }

    private void loadTransactions(String type) {
        if (type.equals("all")) {
            transactionList = dataManager.getAllTransactions();
        } else {
            transactionList = dataManager.getTransactionsByType(type);
        }

        if (adapter != null) {
            adapter.updateList(transactionList != null ? transactionList : new ArrayList<>());
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        loadTransactions("all");
    }
}