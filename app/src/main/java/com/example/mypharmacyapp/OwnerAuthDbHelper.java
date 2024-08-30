package com.example.mypharmacyapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class OwnerAuthDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "OwnerAuth.db";
    private static final int DATABASE_VERSION = 2; // Incremented version to handle schema changes
    public static final String TABLE_NAME = "owners";
    public static final String COLUMN_USERNAME = "username";
    static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_KEY = "owner_key";
    public static final String COLUMN_EMAIL = "email"; // New column for email

    public OwnerAuthDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_USERNAME + " TEXT PRIMARY KEY, " +
                COLUMN_PASSWORD + " TEXT, " +
                COLUMN_KEY + " TEXT, " +
                COLUMN_EMAIL + " TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 2) {
            // Add email column in version 2 upgrade
            String alterTable = "ALTER TABLE " + TABLE_NAME + " ADD COLUMN " + COLUMN_EMAIL + " TEXT";
            db.execSQL(alterTable);
        }
    }

    public boolean registerOwner(String username, String password, String ownerKey, String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_USERNAME, username);
        contentValues.put(COLUMN_PASSWORD, password);
        contentValues.put(COLUMN_KEY, ownerKey);
        contentValues.put(COLUMN_EMAIL, email);

        long result = db.insert(TABLE_NAME, null, contentValues);
        Log.d("OwnerAuthDbHelper", "Registering Owner: Username: " + username + ", OwnerKey: " + ownerKey + ", Email: " + email + ", Result: " + result);
        return result != -1;
    }

    public boolean loginOwner(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_USERNAME + " = ? AND " + COLUMN_PASSWORD + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{username, password});
        boolean isValid = cursor.getCount() > 0;
        cursor.close();
        return isValid;
    }

    public boolean validateOwnerKey(String username, String ownerKey) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_USERNAME + " = ? AND " + COLUMN_KEY + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{username, ownerKey});

        // Logging for debugging
        Log.d("OwnerAuthDbHelper", "Query: " + query + ", Username: " + username + ", OwnerKey: " + ownerKey);
        boolean isValid = cursor.getCount() > 0;
        Log.d("OwnerAuthDbHelper", "IsValid: " + isValid);

        cursor.close();
        return isValid;
    }

    public boolean deleteAllOwners() {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_NAME, null, null);
        return result > 0;
    }
}
