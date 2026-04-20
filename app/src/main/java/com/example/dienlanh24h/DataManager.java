package com.example.dienlanh24h;

import android.content.Context;
import java.util.List;

public class DataManager {
    private DatabaseHelper dbHelper;

    public DataManager(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public List<ServiceItem> getServices() { return dbHelper.getAllServices(); }
    public List<ProductItem> getProducts() { return dbHelper.getAllProducts(); }

    public void addTransaction(Transaction transaction) {
        dbHelper.insertTransaction(transaction);
    }

    public List<Transaction> getAllTransactions() {
        return dbHelper.getAllTransactions();
    }

    public List<Transaction> getTransactionsByType(String type) {
        return dbHelper.getTransactionsByType(type);
    }

    public void updateTransactionStatus(String id, String status) {
        dbHelper.updateTransactionStatus(id, status);
    }
}