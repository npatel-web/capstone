package com.example.getinn.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.getinn.R;
import com.example.getinn.utilities.ECONSTANT;

public class ActivityProfile extends AppCompatActivity {
    private static final String TAG = "TAG";
    private ImageView ivProfile;
    private TextView tvName, tvEmail, tvPhoneNo, tvAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        setTitle("My Profile");

        try {
            ivProfile = findViewById(R.id.ivProfile);
            tvName = findViewById(R.id.tvName);
            tvEmail = findViewById(R.id.tvEmail);
            tvPhoneNo = findViewById(R.id.tvPhoneNo);
            tvAddress = findViewById(R.id.tvAddress);

            tvName.setText(ECONSTANT.logedUser.getName());
            tvEmail.setText(ECONSTANT.logedUser.getEmail());
            tvPhoneNo.setText(ECONSTANT.logedUser.getPhone_no());
            tvAddress.setText(ECONSTANT.logedUser.getAddress());
            Glide.with(this).load(
                    ECONSTANT.URL_IMG_USER + ECONSTANT.logedUser.getImage())
                    .into(ivProfile);


            findViewById(R.id.tvMyCrt).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(ActivityProfile.this, ActivityBookings.class));
                }
            });

        } catch (Exception e) {
            Log.e(TAG, "onCreate: " + e.toString());
        }
    }
}