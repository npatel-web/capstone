package com.example.getinn.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.getinn.R;
import com.example.getinn.models.MyOrders;
import com.example.getinn.utilities.ECONSTANT;
import com.makeramen.roundedimageview.RoundedImageView;

public class ActivityBookingDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_details);
        setTitle("Booking Details");
        Intent intent = getIntent();
        MyOrders myOrders = (MyOrders) intent.getSerializableExtra("STATUS");
        RoundedImageView ivMovie = findViewById(R.id.ivMovie);
        if (myOrders != null) {
            Glide.with(this).load(ECONSTANT.URL_IMG_PRODUCTS + myOrders.getImage()).into(ivMovie);
            ((TextView)findViewById(R.id.tvTitle)).setText(myOrders.getTitle());
            ((TextView)findViewById(R.id.tvDescription)).setText(myOrders.getDescription());
            ((TextView)findViewById(R.id.tvOrderStatus)).setText(myOrders.getStatus());
            ((TextView)findViewById(R.id.tvTickets)).setText(String.valueOf(myOrders.getTickets()));
            ((TextView)findViewById(R.id.tvOrderTime)).setText(myOrders.getTime());
            ((TextView)findViewById(R.id.tvOrderDate)).setText(myOrders.getDate());
        }
    }
}