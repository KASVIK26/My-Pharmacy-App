package com.example.mypharmacyapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class owner_register extends AppCompatActivity {

    TextView txtRegisterTitle, txtname, txtpassword, txtownerkey, txtemail;
    EditText etregistername, etregisterpassword, etownerkey, etemail;
    Button btngetpassword;
    OwnerAuthDbHelper ownerAuthDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_register);

        txtRegisterTitle = findViewById(R.id.txtRegisterTitle);
        txtname = findViewById(R.id.txtname);
        txtpassword = findViewById(R.id.txtpassword);
        txtownerkey = findViewById(R.id.txtownerkey);
        txtemail = findViewById(R.id.txtemail);
        etemail = findViewById(R.id.etemail);
        etregistername = findViewById(R.id.etname);
        etregisterpassword = findViewById(R.id.etregisterpassword);
        etownerkey = findViewById(R.id.etownerkey);
        btngetpassword = findViewById(R.id.btngetpassword);

        ownerAuthDbHelper = new OwnerAuthDbHelper(this);

        btngetpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etregistername.getText().toString();
                String password = etregisterpassword.getText().toString();
                String ownerKey = etownerkey.getText().toString();
                String email = etemail.getText().toString();

                if (username.isEmpty() || password.isEmpty() || ownerKey.isEmpty() || email.isEmpty()) {
                    Toast.makeText(owner_register.this, "All fields are required", Toast.LENGTH_SHORT).show();
                    return;
                }

                boolean success = ownerAuthDbHelper.registerOwner(username, password, ownerKey, email);

                if (success) {
                    Toast.makeText(owner_register.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                    finish();
                    Intent i = new Intent(owner_register.this, owner_login.class);
                    startActivity(i); // Go back to login screen
                } else {
                    Toast.makeText(owner_register.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
