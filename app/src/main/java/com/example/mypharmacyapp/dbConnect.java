package com.example.mypharmacyapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class dbConnect extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Products.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "products";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_BARCODE = "barcode";
    private static final String COLUMN_EXPIRY = "expiry";
    private static final String COLUMN_DPS = "dosage_per_serving";
    private static final String COLUMN_DPB = "dosage_per_bottle";

    public dbConnect(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT UNIQUE, " +
                COLUMN_BARCODE + " TEXT UNIQUE, " +
                COLUMN_EXPIRY + " TEXT, " +
                COLUMN_DPS + " TEXT, " +
                COLUMN_DPB + " TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean addProduct(String name, String barcode, String expiry, String dps, String dpb) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME, name);
        contentValues.put(COLUMN_BARCODE, barcode);
        contentValues.put(COLUMN_EXPIRY, expiry);
        contentValues.put(COLUMN_DPS, dps);
        contentValues.put(COLUMN_DPB, dpb);

        long result = db.insert(TABLE_NAME, null, contentValues);
        return result != -1; // Returns true if the data is inserted successfully
    }

    public List<Product> searchProductByName(String name) {
        List<Product> products = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE LOWER(" + COLUMN_NAME + ") = ?";
        Cursor cursor = db.rawQuery(query, new String[]{name.toLowerCase()});

        if (cursor.moveToFirst()) {
            do {
                String productName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME));
                String barcode = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_BARCODE));
                String expiry = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EXPIRY));
                String dps = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DPS));
                String dpb = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DPB));

                products.add(new Product(productName, barcode, expiry, dps, dpb));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return products;
    }

    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                String productName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME));
                String barcode = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_BARCODE));
                String expiry = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EXPIRY));
                String dps = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DPS));
                String dpb = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DPB));

                products.add(new Product(productName, barcode, expiry, dps, dpb));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return products;
    }

    public Product getProductByBarcode(String barcode) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_BARCODE + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{barcode});

        if (cursor.moveToFirst()) {
            String name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME));
            String expiry = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EXPIRY));
            String dps = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DPS));
            String dpb = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DPB));
            cursor.close();
            return new Product(name, barcode, expiry, dps, dpb);
        }
        cursor.close();
        return null;
    }

    public boolean updateProduct(String barcode, String expiry, String dps, String dpb) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_EXPIRY, expiry);
        contentValues.put(COLUMN_DPS, dps);
        contentValues.put(COLUMN_DPB, dpb);

        int result = db.update(TABLE_NAME, contentValues, COLUMN_BARCODE + " = ?", new String[]{barcode});
        return result > 0; // Returns true if the product was updated successfully
    }

    public boolean deleteProductByBarcode(String barcode) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_NAME, COLUMN_BARCODE + " = ?", new String[]{barcode});
        return result > 0; // Returns true if the product was deleted successfully
    }

}
