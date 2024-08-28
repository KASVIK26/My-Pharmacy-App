package com.example.mypharmacyapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class owner_delete extends AppCompatActivity {

    TextView p_name, txt_deleteproduct;
    EditText e_name;
    Button btndelete;
    private dbConnect database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_delete);

        p_name = findViewById(R.id.p_name);
        txt_deleteproduct = findViewById(R.id.txt_deleteproduct);
        e_name = findViewById(R.id.e_name);
        btndelete = findViewById(R.id.btndelete);

        // Initialize the database
        database = new dbConnect(this);

        btndelete.setOnClickListener(v -> {
            String productName = e_name.getText().toString().trim();
            if (productName.isEmpty()) {
                Toast.makeText(owner_delete.this, "Please enter a product name", Toast.LENGTH_SHORT).show();
                return;
            }

            boolean isDeleted = deleteProductByName(productName);
            if (isDeleted) {
                Toast.makeText(owner_delete.this, "Product deleted successfully", Toast.LENGTH_SHORT).show();
                e_name.setText(""); // Clear the EditText field
            } else {
                Toast.makeText(owner_delete.this, "Product not found", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean deleteProductByName(String name) {
        // Convert name to lowercase for case-insensitive comparison
        List<Product> products = database.searchProductByName(name);
        if (products.isEmpty()) {
            return false; // No product found with the given name
        }

        // Loop through the products found and delete each one
        boolean allDeleted = true;
        for (Product product : products) {
            String barcode = product.getBarcode();
            if (!database.deleteProductByBarcode(barcode)) {
                allDeleted = false; // At least one product could not be deleted
            }
        }
        return allDeleted;
    }
}
