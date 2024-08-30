package com.example.mypharmacyapp;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class forgot_your_password extends AppCompatActivity {

    TextView txtname, txtemail, txtforgotyourpassword;
    EditText etname, etemail;
    Button btngetpassword;
    OwnerAuthDbHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_your_password);

        // Initialize UI elements
        txtname = findViewById(R.id.txtname);
        txtemail = findViewById(R.id.txtemail);
        txtforgotyourpassword = findViewById(R.id.txtforgotyourpassword);
        etname = findViewById(R.id.etname);
        etemail = findViewById(R.id.etemail);
        btngetpassword = findViewById(R.id.btngetpassword);

        // Initialize database helper
        db = new OwnerAuthDbHelper(this);

        // Set onClick listener for the button
        btngetpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etname.getText().toString().trim();
                String email = etemail.getText().toString().trim();

                if (username.isEmpty() || email.isEmpty()) {
                    Toast.makeText(forgot_your_password.this, "Please enter both username and email", Toast.LENGTH_SHORT).show();
                    return;
                }

                retrievePassword(username, email);
            }
        });
    }

    private void retrievePassword(String username, String email) {
        SQLiteDatabase dbReadable = db.getReadableDatabase();
        String query = "SELECT " + OwnerAuthDbHelper.COLUMN_PASSWORD +
                " FROM " + OwnerAuthDbHelper.TABLE_NAME +
                " WHERE " + OwnerAuthDbHelper.COLUMN_USERNAME + " = ?" +
                " AND " + OwnerAuthDbHelper.COLUMN_EMAIL + " = ?";
        Cursor cursor = dbReadable.rawQuery(query, new String[]{username, email});

        if (cursor != null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                int passwordIndex = cursor.getColumnIndex(OwnerAuthDbHelper.COLUMN_PASSWORD);
                if (passwordIndex != -1) {
                    String password = cursor.getString(passwordIndex);
                    cursor.close();

                    // Show password in a dialog
                    new AlertDialog.Builder(this)
                            .setTitle("Password Retrieved")
                            .setMessage("Your password is: " + password)
                            .setPositiveButton("OK", null)
                            .show();
                } else {
                    cursor.close();
                    Toast.makeText(this, "Column index not found for password", Toast.LENGTH_SHORT).show();
                }
            }
        } else {
            if (cursor != null) {
                cursor.close();
            }
            Toast.makeText(this, "Username and/or email not found", Toast.LENGTH_SHORT).show();
        }
        dbReadable.close();
    }
}
