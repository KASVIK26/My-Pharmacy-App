package com.example.mypharmacyapp;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.mlkit.vision.barcode.common.Barcode;
import com.google.mlkit.vision.codescanner.GmsBarcodeScanner;
import com.google.mlkit.vision.codescanner.GmsBarcodeScannerOptions;
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning;

public class owner_add extends AppCompatActivity {
    TextView p_name, p_capturebarcode, p_expiry, p_dps, p_dpb, headline;
    EditText e_scanbarcode, e_expiry, e_dps, e_dpb, e_name;
    Button add;
    dbConnect db;
    private GmsBarcodeScanner scanner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_add);
        p_name = findViewById(R.id.p_name);
        p_capturebarcode = findViewById(R.id.p_capturebarcode);
        p_expiry = findViewById(R.id.p_expiry);
        p_dps = findViewById(R.id.p_dps);
        p_dpb = findViewById(R.id.p_dpb);
        headline = findViewById(R.id.headline);
        e_name = findViewById(R.id.e_name);
        e_scanbarcode = findViewById(R.id.e_scanbarcode);
        e_expiry = findViewById(R.id.e_expiry);
        e_dps = findViewById(R.id.e_dps);
        e_dpb = findViewById(R.id.e_dpb);
        add = findViewById(R.id.add);

        db = new dbConnect(this);

        initializeGoogleScanner();

        e_scanbarcode.setOnClickListener(v -> startScanning());

        add.setOnClickListener(v -> {
            String name = e_name.getText().toString();
            String barcode = e_scanbarcode.getText().toString();
            String expiry = e_expiry.getText().toString();
            String dps = e_dps.getText().toString();
            String dpb = e_dpb.getText().toString();

            if (validateInputs(name, barcode, expiry, dps, dpb)) {
                if (isProductExists(barcode, name)) {
                    Toast.makeText(owner_add.this, "Product with this barcode or name already exists", Toast.LENGTH_SHORT).show();
                } else {
                    boolean isInserted = db.addProduct(name, barcode, expiry, dps, dpb);
                    if (isInserted) {
                        Toast.makeText(owner_add.this, "Product Added Successfully", Toast.LENGTH_SHORT).show();
                        clearFields();
                    } else {
                        Toast.makeText(owner_add.this, "Failed to Add Product", Toast.LENGTH_SHORT).show();
                    }
                }
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
                        e_scanbarcode.setText(result);
                    }
                })
                .addOnCanceledListener(() -> Toast.makeText(owner_add.this, "Cancelled", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e -> Toast.makeText(owner_add.this, e.getMessage(), Toast.LENGTH_SHORT).show());
    }

    private boolean validateInputs(String name, String barcode, String expiry, String dps, String dpb) {
        // Check if any field is empty
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(barcode) || TextUtils.isEmpty(expiry) || TextUtils.isEmpty(dps) || TextUtils.isEmpty(dpb)) {
            Toast.makeText(owner_add.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Validate barcode
        if (barcode.length() != 12 || !barcode.matches("\\d+")) {
            Toast.makeText(owner_add.this, "Barcode must be a 12-digit integer", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Validate expiry format
        if (!expiry.matches("\\d{2}/\\d{2}/\\d{2}")) {
            Toast.makeText(owner_add.this, "Expiry date must be in the format dd/mm/yy", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Validate price values
        try {
            Float.parseFloat(dps);
        } catch (NumberFormatException e) {
            Toast.makeText(owner_add.this, "Price for small quantity must be a valid number", Toast.LENGTH_SHORT).show();
            return false;
        }

        try {
            Float.parseFloat(dpb);
        } catch (NumberFormatException e) {
            Toast.makeText(owner_add.this, "Price for bulk quantity must be a valid number", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private boolean isProductExists(String barcode, String name) {
        // Check if the barcode or name already exists
        if (db.getProductByBarcode(barcode) != null || !db.searchProductByName(name).isEmpty()) {
            return true;
        }
        return false;
    }

    private void clearFields() {
        e_name.setText("");
        e_scanbarcode.setText("");
        e_expiry.setText("");
        e_dps.setText("");
        e_dpb.setText("");
    }
}
