package com.example.mypharmacyapp;

import java.io.Serializable;

public class Product implements Serializable {
    private String name;
    private String barcode;
    private String expiry;
    private String dps;
    private String dpb;

    public Product(String name, String barcode, String expiry, String dps, String dpb) {
        this.name = name;
        this.barcode = barcode;
        this.expiry = expiry;
        this.dps = dps;
        this.dpb = dpb;
    }

    // Getters and setters (if needed)
    public String getName() { return name; }
    public String getBarcode() { return barcode; }
    public String getExpiry() { return expiry; }
    public String getDps() { return dps; }
    public String getDpb() { return dpb; }
}
