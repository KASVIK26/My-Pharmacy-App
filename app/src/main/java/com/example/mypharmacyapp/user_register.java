package com.example.mypharmacyapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class user_register extends AppCompatActivity {

    TextView txtRegisterTitle, txtname, txtpassword, txtemail;
    EditText etregistername, etregisterpassword, etregisteremail;
    Button btnRegisterSubmit;
    UserAuthDbHelper userAuthDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register);

        // Initialize UI elements
        txtRegisterTitle = findViewById(R.id.txtRegisterTitle);
        txtname = findViewById(R.id.txtname);
        txtpassword = findViewById(R.id.txtpassword);
        txtemail = findViewById(R.id.txtemail);
        etregistername = findViewById(R.id.etname);
        etregisterpassword = findViewById(R.id.etregisterpassword);
        etregisteremail = findViewById(R.id.etemail);
        btnRegisterSubmit = findViewById(R.id.btnRegisterSubmit);

        // Initialize database helper
        userAuthDbHelper = new UserAuthDbHelper(this);

        // Set onClick listener for the register button
        btnRegisterSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get user input
                String username = etregistername.getText().toString().trim();
                String password = etregisterpassword.getText().toString().trim();
                String email = etregisteremail.getText().toString().trim();

                // Validate input
                if (username.isEmpty() || password.isEmpty() || email.isEmpty()) {
                    Toast.makeText(user_register.this, "All fields are required", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Register the user
                boolean success = userAuthDbHelper.registerUser(username, password, email);

                if (success) {
                    Toast.makeText(user_register.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(user_register.this, user_login.class);
                    startActivity(i); // Go to login screen
                    finish(); // Close registration screen
                } else {
                    Log.e("UserRegister", "Registration failed for username: " + username);
                    Toast.makeText(user_register.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
