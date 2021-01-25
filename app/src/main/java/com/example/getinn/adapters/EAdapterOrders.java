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
import com.example.getinn.models.MyOrders;
import com.example.getinn.utilities.ECONSTANT;


public class EAdapterOrders extends ArrayAdapter<MyOrders> {
    Context context;

    public EAdapterOrders(@NonNull Context context, MyOrders[] user) {
        super(context, 0, user);
        this.context = context;
    }

    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View userView = convertView;
        if (convertView == null) {
            userView = LayoutInflater.from(context).inflate(R.layout.single_row_orders,
                    parent, false);
        }
        MyOrders product = getItem(position);

        ImageView ivProduct = userView.findViewById(R.id.ivOrderProducts);
        ((TextView) userView.findViewById(R.id.tvOrderProductStatus)).setText(product.getStatus());
        ((TextView) userView.findViewById(R.id.tvOrderProductPrice)).setText(String.valueOf("$" + product.getPrice()));
        ((TextView) userView.findViewById(R.id.tvOrderProductTitle)).setText(product.getTitle());
        if (product.getImage() != null) {
            Glide.with(context).load(ECONSTANT.URL_IMG_PRODUCTS + product.getImage()).into(ivProduct);
        }
        return userView;
    }

}
