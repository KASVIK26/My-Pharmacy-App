package com.example.mypharmacyapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class owner_scrn extends AppCompatActivity {

    TextView hellotext;
    Button add, view, update, delete,btnuserregister;
ImageView btnlogout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_scrn);

        add = findViewById(R.id.add);
        view = findViewById(R.id.view);
        update = findViewById(R.id.update);
        delete = findViewById(R.id.delete);
        hellotext = findViewById(R.id.hellotext);
        btnuserregister = findViewById(R.id.btnuserregister);
        btnlogout = findViewById(R.id.btnlogout);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(owner_scrn.this, owner_add.class);
                startActivity(i);
            }

    });

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(owner_scrn.this, owner_view.class);
                                        startActivity(i);
                                    }
                                });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(owner_scrn.this, owner_update.class);
                        startActivity(i);
                    }
                });


        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(owner_scrn.this, owner_delete.class);
                        startActivity(i);
                    }
                });

        btnuserregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(owner_scrn.this, user_register.class);
                startActivity(i);
            }
        });

        btnlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showLogoutConfirmationDialog();
            }
        });
    }
    private void showLogoutConfirmationDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Logout")
                .setMessage("Are you sure you want to logout?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Clear the login state
                        SharedPreferencesUtil.logout(owner_scrn.this);

                        // Redirect to MainActivity
                        Intent intent = new Intent(owner_scrn.this, MainActivity.class);
                        startActivity(intent);
                        finish(); // Close the owner_scrn activity so user can't go back to it
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }


}
