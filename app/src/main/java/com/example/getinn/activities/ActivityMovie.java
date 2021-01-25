package com.example.getinn.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.appizona.yehiahd.fastsave.FastSave;
import com.example.getinn.R;
import com.example.getinn.adapters.EAdapterProduct;
import com.example.getinn.models.Product;
import com.example.getinn.utilities.ECONSTANT;
import com.example.getinn.utilities.MyNetwork;
import com.google.gson.Gson;

import org.json.JSONObject;

public class ActivityMovie extends AppCompatActivity {
    private GridView gvSubCategory;
    private EAdapterProduct adapterProduct;
    private String TAG = "TAG";
    private LottieAnimationView pgcatagory;
    private int product_id = 0;
    private int catId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        setTitle("Movies");

        try {
            Intent intent = getIntent();
            catId =  intent.getIntExtra(ECONSTANT.KEY_CAT_ID,1);
            gvSubCategory = findViewById(R.id.gvSubCategory);
            pgcatagory = findViewById(R.id.pgcatagory);
            getdatafromProducts(catId);
            FastSave.getInstance().saveInt(ECONSTANT.CAT_ID_KEY, product_id);

            gvSubCategory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Product model = adapterProduct.getItem(position);
                    product_id = model.getP_id();
                    Log.e(TAG, "onItemClick:  " + model.getP_id());
                    Intent intent = new Intent(ActivityMovie.this,
                            ActivityMovieDetail.class);
                    intent.putExtra(ECONSTANT.KEY_PRODUCT_ID, product_id);
                    startActivity(intent);
                }
            });
        } catch (Exception e) {

            Log.e(TAG, "onCreate: " + e.toString());
        }
    }

    private void getdatafromProducts(int id) {
        try {
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    Request.Method.GET
                    , ECONSTANT.URL_EPRODUCTSBYSUBCATID + id, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.e(TAG, "onResponse: " + response.toString());
                            final Gson gson = new Gson();
                            pgcatagory.setVisibility(View.INVISIBLE);
                            try {
                                if (response.getBoolean("status")) {
                                    Product[] models =
                                            gson.fromJson(String.valueOf(
                                                    response.getJSONArray("data")),
                                                    Product[].class);
                                    adapterProduct = new
                                            EAdapterProduct(ActivityMovie.this,
                                            models);
                                    gvSubCategory.setAdapter(adapterProduct);
                                } else {
                                    Product[] models =
                                            gson.fromJson(String.valueOf(
                                                    response.getJSONArray("data")),
                                                    Product[].class);
                                    adapterProduct = new
                                            EAdapterProduct(ActivityMovie.this,
                                            models);
                                    gvSubCategory.setAdapter(adapterProduct);
                                    pgcatagory.setVisibility(View.GONE);
                                    (findViewById(R.id.animProductEmpty)).setVisibility(View.VISIBLE);

                                    Toast.makeText(ActivityMovie.this, "No Data", Toast.LENGTH_SHORT).show();
                                }
                            } catch (Exception e) {
                                Log.e(TAG, "onResponse: " + e.toString());
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    pgcatagory.setVisibility(View.INVISIBLE);
                    Log.e(TAG, "onErrorResponse: " + error.toString());
                }
            });
            MyNetwork.getInstance(this).addToRequestQueue(jsonObjectRequest);
        } catch (Exception e) {

        }

    }
}