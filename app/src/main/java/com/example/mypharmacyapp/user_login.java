package com.example.mypharmacyapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class user_login extends AppCompatActivity {

    TextView txtLoginTitle, txtusername, txtpassword;
    EditText etUsername, etPassword;
    Button btnLoginSubmit;
    UserAuthDbHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

        txtLoginTitle = findViewById(R.id.txtLoginTitle);
        txtusername = findViewById(R.id.txtusername);
        txtpassword = findViewById(R.id.txtpassword);
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLoginSubmit = findViewById(R.id.btnLoginSubmit);

        db = new UserAuthDbHelper(this);

        btnLoginSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();

                if (db.loginUser(username, password)) {
                    Toast.makeText(user_login.this, "Login Successful", Toast.LENGTH_SHORT).show();
                    // Redirect to user dashboard or main activity
                    Intent intent = new Intent(user_login.this, user_scrn.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(user_login.this, "Login Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
