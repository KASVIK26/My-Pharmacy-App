package com.example.mypharmacyapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.mlkit.vision.barcode.common.Barcode;
import com.google.mlkit.vision.codescanner.GmsBarcodeScanner;
import com.google.mlkit.vision.codescanner.GmsBarcodeScannerOptions;
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning;

public class owner_update extends AppCompatActivity {

    private TextView p_name, p_udps, p_expiry, p_udpb, txt_update;
    private EditText e_expiry, e_udps, e_udpb, e_capturebarcode;
    private Button update;
    private GmsBarcodeScanner scanner;
    private dbConnect database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_update);

        p_name = findViewById(R.id.p_name);
        p_udps = findViewById(R.id.p_udps);
        p_expiry = findViewById(R.id.p_expiry);
        p_udpb = findViewById(R.id.p_udpb);
        txt_update = findViewById(R.id.txt_update);
        e_expiry = findViewById(R.id.e_expiry);
        e_udps = findViewById(R.id.e_udps);
        e_udpb = findViewById(R.id.e_udpb);
        e_capturebarcode = findViewById(R.id.e_capturebarcode);
        update = findViewById(R.id.update);

        // Initialize the database
        database = new dbConnect(this);

        initializeGoogleScanner();

        e_capturebarcode.setOnClickListener(v -> startScanning());

        update.setOnClickListener(v -> {
            String barcode = e_capturebarcode.getText().toString();
            String expiry = e_expiry.getText().toString();
            String dps = e_udps.getText().toString();
            String dpb = e_udpb.getText().toString();

            if (barcode.isEmpty() || expiry.isEmpty() || dps.isEmpty() || dpb.isEmpty()) {
                Toast.makeText(owner_update.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            boolean isUpdated = database.updateProduct(barcode, expiry, dps, dpb);
            if (isUpdated) {
                Toast.makeText(owner_update.this, "Product updated successfully", Toast.LENGTH_SHORT).show();
                // Clear all fields
                e_capturebarcode.setText("");
                e_expiry.setText("");
                e_udps.setText("");
                e_udpb.setText("");
            } else {
                Toast.makeText(owner_update.this, "Failed to update product", Toast.LENGTH_SHORT).show();
            }
        });
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
                    String result = barcode.getRawValue();
                    if (result != null) {
                        e_capturebarcode.setText(result);
                        fetchProductDetails(result);
                    }
                })
                .addOnCanceledListener(() -> Toast.makeText(owner_update.this, "Cancelled", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e -> Toast.makeText(owner_update.this, e.getMessage(), Toast.LENGTH_SHORT).show());
    }

    private void fetchProductDetails(String barcode) {
        Product product = database.getProductByBarcode(barcode);
        if (product != null) {
            e_expiry.setText(product.getExpiry());
            e_udps.setText(product.getDps());
            e_udpb.setText(product.getDpb());
        } else {
            Toast.makeText(owner_update.this, "Product not found", Toast.LENGTH_SHORT).show();
        }
    }
}
