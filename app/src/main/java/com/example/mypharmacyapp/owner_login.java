package com.example.mypharmacyapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class owner_login extends AppCompatActivity {

    TextView txtLoginTitle, txtusername, txtpassword, txtforgotyoupassword;
    EditText etUsername, etPassword;
    Button btnLoginSubmit, btnregisterinloginpage, btndeletepastdata;
    OwnerAuthDbHelper ownerDb;
    UserAuthDbHelper userDb;
    dbConnect productDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_login);

        txtLoginTitle = findViewById(R.id.txtLoginTitle);
        txtusername = findViewById(R.id.txtusername);
        txtpassword = findViewById(R.id.txtpassword);
        txtforgotyoupassword = findViewById(R.id.txtforgotyoupassword);
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLoginSubmit = findViewById(R.id.btnLoginSubmit);
        btnregisterinloginpage = findViewById(R.id.btnregisterinloginpage);
        btndeletepastdata = findViewById(R.id.btndeletepastdata);

        ownerDb = new OwnerAuthDbHelper(this);
        userDb = new UserAuthDbHelper(this);
        productDb = new dbConnect(this);

        btnLoginSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etUsername.getText().toString().trim();
                String password = etPassword.getText().toString().trim();

                if (username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(owner_login.this, "Please enter username and password", Toast.LENGTH_SHORT).show();
                    return;
                }

                boolean isValidLogin = ownerDb.loginOwner(username, password);

                if (isValidLogin) {
                    // Successful login
                    SharedPreferencesUtil.setLoggedIn(owner_login.this, true, "owner");
                    Intent intent = new Intent(owner_login.this, owner_scrn.class);
                    startActivity(intent);
                    finish(); // Close the login activity so user can't go back to it
                } else {
                    // Failed login
                    Toast.makeText(owner_login.this, "Invalid details or please register first", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnregisterinloginpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(owner_login.this, owner_register.class);
                startActivity(i);
            }
        });

        btndeletepastdata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDeleteConfirmationDialog();
            }
        });

        txtforgotyoupassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(owner_login.this, forgot_your_password.class);
                startActivity(intent);
            }
        });
    }

    private void showDeleteConfirmationDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Delete All Data")
                .setMessage("Are you sure you want to delete all past data? This action cannot be undone.")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Delete all data
                        deleteAllData();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    private void deleteAllData() {
        // Delete all data from OwnerAuth database
        boolean ownersDeleted = ownerDb.deleteAllOwners();
        // Delete all data from UserAuth database
        boolean usersDeleted = userDb.deleteAllUsers();
        // Delete all data from Products database
        boolean productsDeleted = productDb.deleteAllProducts();

        if (ownersDeleted && usersDeleted && productsDeleted) {
            Toast.makeText(owner_login.this, "All past data has been deleted", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(owner_login.this, "Failed to delete some data", Toast.LENGTH_SHORT).show();
        }
    }
}
