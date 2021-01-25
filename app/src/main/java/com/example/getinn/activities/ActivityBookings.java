package com.example.getinn.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.getinn.R;
import com.example.getinn.adapters.EAdapterOrders;
import com.example.getinn.models.MyOrders;
import com.example.getinn.utilities.ECONSTANT;
import com.example.getinn.utilities.MyNetwork;
import com.google.gson.Gson;

import org.json.JSONObject;

public class ActivityBookings extends AppCompatActivity {
    private static final String TAG = "TAG";
    ListView lvOrders;
    LottieAnimationView animOrderEmpty;
    private EAdapterOrders adapterOrders;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookings);

        try {
            setTitle("My Booking");
            lvOrders = findViewById(R.id.lvOrders);
            animOrderEmpty = findViewById(R.id.animOrder);
            getProductOrders();
            lvOrders.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    MyOrders orders = adapterOrders.getItem(position);
                    Intent intent = new Intent(ActivityBookings.this,
                            ActivityBookingDetails.class);
                    intent.putExtra("STATUS", orders);
                    startActivity(intent);
                }
            });

        } catch (Exception e) {
            Log.e(TAG, "onCreate: " + e.toString());
        }
    }

    private void getProductOrders() {
        try {
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                    ECONSTANT.URL_GET_PRODUCT_ORDER + ECONSTANT.logedUser.getId(),
                    null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {

                            Log.e(TAG, "onResponse: " + response.toString());
                            final Gson gson = new Gson();
                            try {

                                if (response.getBoolean("status")) {
                                    (findViewById(R.id.rlOrder)).setVisibility(View.VISIBLE);
                                    (findViewById(R.id.rlOrderEmpty)).setVisibility(View.GONE);
                                    animOrderEmpty.setVisibility(View.GONE);

                                    MyOrders[] models = gson.fromJson
                                            (String.valueOf(response.getJSONArray("data")), MyOrders[].class);
                                    adapterOrders = new EAdapterOrders(ActivityBookings.this,
                                            models);
                                    lvOrders.setAdapter(adapterOrders);

                                } else {
                                    (findViewById(R.id.rlOrder)).setVisibility(View.GONE);
                                    (findViewById(R.id.rlOrderEmpty)).setVisibility(View.VISIBLE);


                                }

                            } catch (Exception e) {
                                Log.e(TAG, "onResponse: " + e.toString());
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e(TAG, "onErrorResponse: " + error.toString());
                }
            });
            MyNetwork.getInstance(this).addToRequestQueue(jsonObjectRequest);
        } catch (Exception e) {
            Log.e(TAG, "getProductOrders: " + e.toString());
        }
    }
}