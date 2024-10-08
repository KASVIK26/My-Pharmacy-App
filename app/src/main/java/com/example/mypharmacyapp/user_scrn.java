package com.example.mypharmacyapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.mlkit.vision.barcode.common.Barcode;
import com.google.mlkit.vision.codescanner.GmsBarcodeScanner;
import com.google.mlkit.vision.codescanner.GmsBarcodeScannerOptions;
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning;

public class user_scrn extends AppCompatActivity {

    TextView txtcapturebarcode, txthello;
    EditText e_scanbarcode;
    Button btnsearch;
    private GmsBarcodeScanner scanner;
    private dbConnect database;
    ImageView btnuserlogout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_scrn);

        txtcapturebarcode = findViewById(R.id.txtcapturebarcode);
        txthello = findViewById(R.id.txthello);
        e_scanbarcode = findViewById(R.id.e_scanbarcode);
        btnsearch = findViewById(R.id.btnsearch);
        btnuserlogout = findViewById(R.id.btnuserlogout);
        // Initialize the database and barcode scanner
        database = new dbConnect(this);
        initializeGoogleScanner();

        // Set EditText to be clickable
        e_scanbarcode.setOnClickListener(v -> startScanning());

        // Optional: Clear the EditText when button is clicked
        btnsearch.setOnClickListener(v -> e_scanbarcode.setText(""));
        // Set up logout button with confirmation dialog
        btnuserlogout.setOnClickListener(v -> showLogoutConfirmationDialog());
    }

    private void initializeGoogleScanner() {
        GmsBarcodeScannerOptions options = new GmsBarcodeScannerOptions.Builder()
                .setBarcodeFormats(Barcode.FORMAT_UPC_A)
                .enableAutoZoom()
                .build();

        scanner = GmsBarcodeScanning.getClient(this, options);
    }

    private void startScanning() {
        scanner.startScan()
                .addOnSuccessListener(barcode -> {
                    String barcodeValue = barcode.getRawValue();
                    if (barcodeValue != null) {
                        e_scanbarcode.setText(barcodeValue);
                        displayProductDetails(barcodeValue);
                    }
                })
                .addOnCanceledListener(() -> Toast.makeText(user_scrn.this, "Scanning cancelled", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e -> Toast.makeText(user_scrn.this, e.getMessage(), Toast.LENGTH_SHORT).show());
    }

    private void displayProductDetails(String barcode) {
        Product product = database.getProductByBarcode(barcode);
        if (product != null) {
            String productInfo = "Name: " + product.getName() + "\n" +
                    "Expiry Date: " + product.getExpiry() + "\n" +
                    "Price (Small Qty): " + product.getDps() + "\n" +
                    "Price (Bulk Qty): " + product.getDpb();
            txtcapturebarcode.setText(productInfo);
        } else {
            txtcapturebarcode.setText("Product not found");
        }
    }
    private void showLogoutConfirmationDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Logout")
                .setMessage("Are you sure you want to logout?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    // Perform logout
                    SharedPreferencesUtil.logout(user_scrn.this);
                    Intent intent = new Intent(user_scrn.this, MainActivity.class);
                    startActivity(intent);
                    finish(); // Close user_scrn so the user can't go back to it
                })
                .setNegativeButton("No", null)
                .show();
    }
}