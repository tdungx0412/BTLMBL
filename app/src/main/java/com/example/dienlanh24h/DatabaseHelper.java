package com.example.dienlanh24h;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "DienLanh24h.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_SERVICES = "services";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_PRICE = "price";
    private static final String COLUMN_PHONE = "phone";
    private static final String COLUMN_DESCRIPTION = "description";
    private static final String COLUMN_IMAGE = "image";
    private static final String COLUMN_CATEGORY = "category";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_SERVICES_TABLE = "CREATE TABLE " + TABLE_SERVICES + "("
                + COLUMN_ID + " TEXT PRIMARY KEY,"
                + COLUMN_NAME + " TEXT,"
                + COLUMN_PRICE + " TEXT,"
                + COLUMN_PHONE + " TEXT,"
                + COLUMN_DESCRIPTION + " TEXT,"
                + COLUMN_IMAGE + " INTEGER,"
                + COLUMN_CATEGORY + " TEXT" + ")";
        db.execSQL(CREATE_SERVICES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SERVICES);
        onCreate(db);
    }

    public void addService(ServiceItem service) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, service.getId());
        values.put(COLUMN_NAME, service.getName());
        values.put(COLUMN_PRICE, service.getPrice());
        values.put(COLUMN_PHONE, service.getPhone());
        values.put(COLUMN_DESCRIPTION, service.getDescription());
        values.put(COLUMN_IMAGE, service.getImageResource());
        values.put(COLUMN_CATEGORY, service.getCategory());

        db.insert(TABLE_SERVICES, null, values);
        db.close();
    }

    public List<ServiceItem> getAllServices() {
        List<ServiceItem> serviceList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_SERVICES;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                ServiceItem service = new ServiceItem();
                service.setId(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ID)));
                service.setName(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME)));
                service.setPrice(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PRICE)));
                service.setPhone(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PHONE)));
                service.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPTION)));
                service.setImageResource(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_IMAGE)));
                service.setCategory(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CATEGORY)));
                serviceList.add(service);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return serviceList;
    }

    public int updateService(ServiceItem service) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, service.getName());
        values.put(COLUMN_PRICE, service.getPrice());
        values.put(COLUMN_PHONE, service.getPhone());
        values.put(COLUMN_DESCRIPTION, service.getDescription());
        values.put(COLUMN_IMAGE, service.getImageResource());
        values.put(COLUMN_CATEGORY, service.getCategory());

        return db.update(TABLE_SERVICES, values, COLUMN_ID + " = ?",
                new String[]{String.valueOf(service.getId())});
    }

    public void deleteService(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_SERVICES, COLUMN_ID + " = ?", new String[]{id});
        db.close();
    }
}
