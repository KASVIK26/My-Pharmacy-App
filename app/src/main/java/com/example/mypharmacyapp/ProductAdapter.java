package com.example.mypharmacyapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class ProductAdapter extends BaseAdapter {

    private Context context;
    private List<Product> products;

    public ProductAdapter(Context context, List<Product> products) {
        this.context = context;
        this.products = products;
    }

    @Override
    public int getCount() {
        return products.size();
    }

    @Override
    public Object getItem(int position) {
        return products.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.product_list_item, parent, false);
        }

        Product product = products.get(position);

        TextView title = convertView.findViewById(R.id.title);
        TextView productName = convertView.findViewById(R.id.productName);
        TextView productBarcode = convertView.findViewById(R.id.productBarcode);
        TextView productExpiry = convertView.findViewById(R.id.productExpiry);
        TextView productDps = convertView.findViewById(R.id.productDps);
        TextView productDpb = convertView.findViewById(R.id.productDpb);

        // Setting text with numbered format
        title.setText("Product " + (position + 1) + " Details");

        productName.setText("1. Medicine Name: " + product.getName());
        productBarcode.setText("2. Barcode No.: " + product.getBarcode());
        productExpiry.setText("3. Expiry: " + product.getExpiry());
        productDps.setText("4. Price (Small Quantity): " + product.getDps());
        productDpb.setText("5. Price (Bulk Quantity): " + product.getDpb());

        return convertView;
    }
}
