package com.example.getinn.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.getinn.R;
import com.example.getinn.adapters.FoodAdapter;

public class FoodActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    String s1[], s2[], s3[];
    int images[] = {R.drawable.popcorn, R.drawable.truffle_fries, R.drawable.classic_poutine, R.drawable.classic_burger,
            R.drawable.beyond_meat_burger, R.drawable.pepsi, R.drawable.canada_dry, R.drawable.ice_cream_float,
            R.drawable.ice_cream_sundae, R.drawable.coco_mocha};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);

        recyclerView = findViewById(R.id.foodRecyclerView);

        s1 = getResources().getStringArray(R.array.food_menu);
        s2 = getResources().getStringArray(R.array.description);
        s3 = getResources().getStringArray(R.array.price);

        FoodAdapter foodAdapter = new FoodAdapter(this, s1, s2, s3, images);
        recyclerView.setAdapter(foodAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}