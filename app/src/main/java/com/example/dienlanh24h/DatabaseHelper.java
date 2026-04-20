package com.example.dienlanh24h;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "DienLanhDB";
    private static final int DATABASE_VERSION = 3;

    private static final String TABLE_SERVICES = "services";
    private static final String COL_ID = "id";
    private static final String COL_NAME = "name";
    private static final String COL_PRICE = "price";
    private static final String COL_PHONE = "phone";
    private static final String COL_DESCRIPTION = "description";
    private static final String COL_IMAGE = "image";
    private static final String COL_CATEGORY = "category";

    private static final String TABLE_PRODUCTS = "products";
    private static final String COL_STOCK = "stock";

    private static final String TABLE_TRANSACTIONS = "transactions";
    private static final String COL_TRANSACTION_ID = "transaction_id";
    private static final String COL_USER_ID = "user_id";
    private static final String COL_ITEM_NAME = "item_name";
    private static final String COL_ITEM_TYPE = "item_type";
    private static final String COL_QUANTITY = "quantity";
    private static final String COL_TOTAL_AMOUNT = "total_amount";
    private static final String COL_STATUS = "status";
    private static final String COL_PAYMENT_METHOD = "payment_method";
    private static final String COL_DATE = "date";
    private static final String COL_ADDRESS = "address";
    private static final String COL_CUSTOMER_NAME = "customer_name";
    private static final String COL_SERVICE_DATE = "service_date";
    private static final String COL_SERVICE_TIME = "service_time";
    private static final String COL_NOTES = "notes";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createServicesTable = "CREATE TABLE " + TABLE_SERVICES + "("
                + COL_ID + " TEXT PRIMARY KEY,"
                + COL_NAME + " TEXT,"
                + COL_PRICE + " TEXT,"
                + COL_PHONE + " TEXT,"
                + COL_DESCRIPTION + " TEXT,"
                + COL_IMAGE + " INTEGER,"
                + COL_CATEGORY + " TEXT" + ")";
        db.execSQL(createServicesTable);

        String createProductsTable = "CREATE TABLE " + TABLE_PRODUCTS + "("
                + COL_ID + " TEXT PRIMARY KEY,"
                + COL_NAME + " TEXT,"
                + COL_PRICE + " TEXT,"
                + COL_DESCRIPTION + " TEXT,"
                + COL_IMAGE + " INTEGER,"
                + COL_CATEGORY + " TEXT,"
                + COL_STOCK + " INTEGER" + ")";
        db.execSQL(createProductsTable);

        String createTransactionsTable = "CREATE TABLE " + TABLE_TRANSACTIONS + "("
                + COL_TRANSACTION_ID + " TEXT PRIMARY KEY,"
                + COL_USER_ID + " TEXT,"
                + COL_ITEM_NAME + " TEXT,"
                + COL_ITEM_TYPE + " TEXT,"
                + COL_PRICE + " TEXT,"
                + COL_QUANTITY + " INTEGER,"
                + COL_TOTAL_AMOUNT + " TEXT,"
                + COL_STATUS + " TEXT,"
                + COL_PAYMENT_METHOD + " TEXT,"
                + COL_DATE + " TEXT,"
                + COL_ADDRESS + " TEXT,"
                + COL_PHONE + " TEXT,"
                + COL_CUSTOMER_NAME + " TEXT,"
                + COL_SERVICE_DATE + " TEXT,"
                + COL_SERVICE_TIME + " TEXT,"
                + COL_NOTES + " TEXT" + ")";
        db.execSQL(createTransactionsTable);

        insertDefaultServices(db);
        insertDefaultProducts(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TRANSACTIONS);
        onCreate(db);
    }

    private void insertDefaultServices(SQLiteDatabase db) {
        insertService(db, new ServiceItem("1", "Vệ sinh máy lạnh", "150.000đ", "0909123456",
                "Vệ sinh sạch sẽ, bơm gas, kiểm tra dàn lạnh.",
                R.drawable.ic_default_service, "Máy lạnh"));
        insertService(db, new ServiceItem("2", "Sửa tủ lạnh", "200.000đ", "0909654321",
                "Sửa block, thay gas, xử lý không làm lạnh.",
                R.drawable.ic_default_service, "Tủ lạnh"));
        insertService(db, new ServiceItem("3", "Sửa máy giặt", "180.000đ", "0909111222",
                "Sửa board, thay dây curoa, vệ sinh lồng giặt.",
                R.drawable.ic_default_service, "Máy giặt"));
    }

    private void insertDefaultProducts(SQLiteDatabase db) {
        insertProduct(db, new ProductItem("1", "Gas R410A", "350.000đ",
                "Gas lạnh R410A chính hãng",
                R.drawable.ic_default_product, "Phụ tùng", 50));
        insertProduct(db, new ProductItem("2", "Gas R32", "380.000đ",
                "Gas lạnh R32 hiệu Suwa",
                R.drawable.ic_default_product, "Phụ tùng", 30));
        insertProduct(db, new ProductItem("3", "Dây đồng 1/4", "1.200.000đ",
                "Dây đồng quấn 1/4 inch",
                R.drawable.ic_default_product, "Vật tư tiêu hao", 20));
    }

    private void insertService(SQLiteDatabase db, ServiceItem service) {
        ContentValues values = new ContentValues();
        values.put(COL_ID, service.getId());
        values.put(COL_NAME, service.getName());
        values.put(COL_PRICE, service.getPrice());
        values.put(COL_PHONE, service.getPhone());
        values.put(COL_DESCRIPTION, service.getDescription());
        values.put(COL_IMAGE, service.getImageResource());
        values.put(COL_CATEGORY, service.getCategory());
        db.insert(TABLE_SERVICES, null, values);
    }

    private void insertProduct(SQLiteDatabase db, ProductItem product) {
        ContentValues values = new ContentValues();
        values.put(COL_ID, product.getId());
        values.put(COL_NAME, product.getName());
        values.put(COL_PRICE, product.getPrice());
        values.put(COL_DESCRIPTION, product.getDescription());
        values.put(COL_IMAGE, product.getImageResource());
        values.put(COL_CATEGORY, product.getCategory());
        values.put(COL_STOCK, product.getStock());
        db.insert(TABLE_PRODUCTS, null, values);
    }

    public List<ServiceItem> getAllServices() {
        List<ServiceItem> services = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_SERVICES, null);
        if (cursor.moveToFirst()) {
            do {
                services.add(new ServiceItem(
                        cursor.getString(0), cursor.getString(1), cursor.getString(2),
                        cursor.getString(3), cursor.getString(4), cursor.getInt(5),
                        cursor.getString(6)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return services;
    }

    public List<ProductItem> getAllProducts() {
        List<ProductItem> products = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_PRODUCTS, null);
        if (cursor.moveToFirst()) {
            do {
                products.add(new ProductItem(
                        cursor.getString(0), cursor.getString(1), cursor.getString(2),
                        cursor.getString(3), cursor.getInt(4), cursor.getString(5),
                        cursor.getInt(6)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return products;
    }

    public long insertTransaction(Transaction transaction) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_TRANSACTION_ID, transaction.getId());
        values.put(COL_USER_ID, transaction.getUserId());
        values.put(COL_ITEM_NAME, transaction.getItemName());
        values.put(COL_ITEM_TYPE, transaction.getItemType());
        values.put(COL_PRICE, transaction.getPrice());
        values.put(COL_QUANTITY, transaction.getQuantity());
        values.put(COL_TOTAL_AMOUNT, transaction.getTotalAmount());
        values.put(COL_STATUS, transaction.getStatus());
        values.put(COL_PAYMENT_METHOD, transaction.getPaymentMethod());
        values.put(COL_DATE, transaction.getDate());
        values.put(COL_ADDRESS, transaction.getAddress());
        values.put(COL_PHONE, transaction.getPhone());
        values.put(COL_CUSTOMER_NAME, transaction.getCustomerName());
        values.put(COL_SERVICE_DATE, transaction.getServiceDate());
        values.put(COL_SERVICE_TIME, transaction.getServiceTime());
        values.put(COL_NOTES, transaction.getNotes());
        return db.insert(TABLE_TRANSACTIONS, null, values);
    }

    public List<Transaction> getAllTransactions() {
        List<Transaction> transactions = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_TRANSACTIONS + " ORDER BY date DESC", null);
        if (cursor.moveToFirst()) {
            do {
                transactions.add(new Transaction(
                        cursor.getString(0),   // id
                        cursor.getString(1),   // userId
                        cursor.getString(2),   // itemName
                        cursor.getString(3),   // itemType
                        cursor.getString(4),   // price
                        cursor.getInt(5),      // quantity
                        cursor.getString(6),   // totalAmount
                        cursor.getString(7),   // status
                        cursor.getString(8),   // paymentMethod
                        cursor.getString(9),   // date
                        cursor.getString(10),  // address
                        cursor.getString(11),  // phone
                        cursor.getString(12),  // customerName
                        cursor.getString(13),  // serviceDate
                        cursor.getString(14),  // serviceTime
                        cursor.getString(15)   // notes
                ));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return transactions;
    }

    public List<Transaction> getTransactionsByType(String itemType) {
        List<Transaction> transactions = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_TRANSACTIONS +
                " WHERE item_type = ? ORDER BY date DESC", new String[]{itemType});
        if (cursor.moveToFirst()) {
            do {
                transactions.add(new Transaction(
                        cursor.getString(0),   // id
                        cursor.getString(1),   // userId
                        cursor.getString(2),   // itemName
                        cursor.getString(3),   // itemType
                        cursor.getString(4),   // price
                        cursor.getInt(5),      // quantity
                        cursor.getString(6),   // totalAmount
                        cursor.getString(7),   // status
                        cursor.getString(8),   // paymentMethod
                        cursor.getString(9),   // date
                        cursor.getString(10),  // address
                        cursor.getString(11),  // phone
                        cursor.getString(12),  // customerName
                        cursor.getString(13),  // serviceDate
                        cursor.getString(14),  // serviceTime
                        cursor.getString(15)   // notes
                ));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return transactions;
    }

    public int updateTransactionStatus(String id, String status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_STATUS, status);
        return db.update(TABLE_TRANSACTIONS, values, COL_TRANSACTION_ID + " = ?", new String[]{id});
    }
}