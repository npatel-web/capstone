package com.example.getinn.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.example.getinn.R;
import com.example.getinn.models.Category;
import com.example.getinn.utilities.ECONSTANT;
import com.makeramen.roundedimageview.RoundedImageView;


public class EAdapterCategory extends ArrayAdapter<Category> {
    Context context;


    public EAdapterCategory(@NonNull Context context, Category[] user) {
        super(context, 0, user);
        this.context = context;
    }

    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View userView = convertView;
        if (convertView == null) {
            userView = LayoutInflater.from(context).inflate(R.layout.single_row_product_category,
                    parent, false);
        }
        Category user = getItem(position);

        RoundedImageView ivcat = userView.findViewById(R.id.ivcatagory);
        TextView tvtitle = userView.findViewById(R.id.sbcattitle);
        TextView tvDescription = userView.findViewById(R.id.tvDescription);
        Glide.with(context).load(ECONSTANT
                .URL_IMG_CATAGORY + user.getCat_image()).into(ivcat);
        tvtitle.setText(user.getCat_title());
        tvDescription.setText(user.getCat_description());
        return userView;
    }

}