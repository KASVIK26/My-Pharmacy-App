package com.example.mypharmacyapp;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class ProductListActivity extends AppCompatActivity {

    private ListView listView;
    private ProductAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        listView = findViewById(R.id.listView);
        dbConnect db = new dbConnect(this);

        List<Product> products = (List<Product>) getIntent().getSerializableExtra("products");
        adapter = new ProductAdapter(this, products);
        listView.setAdapter(adapter);
    }
}
