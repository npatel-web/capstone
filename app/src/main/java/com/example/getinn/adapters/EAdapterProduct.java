package com.example.getinn.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.example.getinn.R;
import com.example.getinn.models.Product;
import com.example.getinn.utilities.ECONSTANT;

public class EAdapterProduct extends ArrayAdapter<Product> {
    private Context context;

    public EAdapterProduct(Context context, Product[] objects) {
        super(context, 0, objects);
        this.context = context;
    }

    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View userView = convertView;
        if (convertView == null) {
            userView = LayoutInflater.from(context).inflate(R.layout.single_row_product,
                    parent, false);
        }
        Product product = getItem(position);

        ImageView ivProduct = userView.findViewById(R.id.ivcatagory);

       // ((TextView) userView.findViewById(R.id.tvProductDescription)).setText(product.getP_description());
        ((TextView) userView.findViewById(R.id.sbcattitle)).setText(product.getP_title());
        ((TextView) userView.findViewById(R.id.tvProductPrice)).setText(String.valueOf("$"+product.getP_price()));
        Glide.with(context).load(ECONSTANT.URL_IMG_SUBCATAGORY + product.getP_image()).into(ivProduct);


        return userView;
    }
}
