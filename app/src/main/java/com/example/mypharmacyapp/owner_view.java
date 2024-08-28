package com.example.mypharmacyapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.Serializable;
import java.util.List;

public class owner_view extends AppCompatActivity {
    EditText e_enterproductname;
    Button btnsearch, btnviewall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_view);

        e_enterproductname = findViewById(R.id.e_enterproductname);
        btnsearch = findViewById(R.id.btnsearch);
        btnviewall = findViewById(R.id.btnviewall);

        dbConnect db = new dbConnect(this);

        btnsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String productName = e_enterproductname.getText().toString().trim();
                if (!productName.isEmpty()) {
                    List<Product> products = db.searchProductByName(productName);
                    if (!products.isEmpty()) {
                        Intent intent = new Intent(owner_view.this, ProductListActivity.class);
                        intent.putExtra("products", (Serializable) products);
                        startActivity(intent);
                    } else {
                        Toast.makeText(owner_view.this, "No product found", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(owner_view.this, "Please enter a product name", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnviewall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Product> products = db.getAllProducts();
                Intent intent = new Intent(owner_view.this, ProductListActivity.class);
                intent.putExtra("products", (Serializable) products);
                startActivity(intent);
            }
        });
    }
}
