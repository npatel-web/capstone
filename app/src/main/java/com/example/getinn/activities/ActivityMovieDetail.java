package com.example.getinn.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bumptech.glide.Glide;
import com.example.getinn.R;
import com.example.getinn.models.Product;
import com.example.getinn.models.ProductImages;
import com.example.getinn.utilities.ECONSTANT;
import com.example.getinn.utilities.MyNetwork;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import de.hdodenhof.circleimageview.CircleImageView;

public class ActivityMovieDetail extends AppCompatActivity {
    private TextView tvProductPrice, tvProductTitle, tvProductDescrition, tvTotalPrice;
    private String TAG = "TAG";
    private int counter = 1;
    private ImageView ivProduct;
    private Button btnAddToCArt;
    private CircleImageView iv1Product, iv2Product, iv3Product, iv4Product;
    private Product[] products;
    private int x;
    private int product_id, No_ofProducts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        setTitle("Movie Details");

        try {
            ImageButton btninc = findViewById(R.id.ibInc);
            ImageButton btndec = findViewById(R.id.ibDec);
            final TextView tvcount = findViewById(R.id.tvCount);

            tvProductDescrition = findViewById(R.id.tvMDescrition);
            tvProductTitle = findViewById(R.id.tvMTitle);
            tvTotalPrice = findViewById(R.id.tvTotalPrice);
            tvProductPrice = findViewById(R.id.tvMPrice);
            iv1Product = findViewById(R.id.iv1MProduct);
            iv2Product = findViewById(R.id.iv2MProduct);
            iv3Product = findViewById(R.id.iv3MProduct);
            iv4Product = findViewById(R.id.iv4MProduct);

            ivProduct = findViewById(R.id.ivMProduct);
            btnAddToCArt = findViewById(R.id.btnAddToCArt);
            Intent i = getIntent();
            product_id = i.getIntExtra(ECONSTANT.KEY_PRODUCT_ID, -1);
            getsingleproduct();

            tvcount.setText(String.valueOf(counter));
            btninc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (counter == No_ofProducts) {
                        Toast.makeText(ActivityMovieDetail.this, "No More Tickets", Toast.LENGTH_SHORT).show();
                    } else {
                        counter = counter + 1;
                        x = products[0].getP_price() * counter;
                        tvTotalPrice.setText("$" + String.valueOf(x));
                        tvcount.setText(String.valueOf(counter));
                    }

                }
            });
            btndec.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (counter == 1) {
                        Toast.makeText(ActivityMovieDetail.this, "Can't Decrease", Toast.LENGTH_SHORT).show();
                    } else {
                        counter = counter - 1;
                        x = products[0].getP_price() * counter;
                        tvTotalPrice.setText("$" + String.valueOf(x));
                        tvcount.setText(String.valueOf(counter));
                    }
                }
            });


            btnAddToCArt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (ECONSTANT.logedUser != null){
                        int count = Integer.parseInt(tvcount.getText().toString());
                        Intent intent = new Intent(ActivityMovieDetail.this, ActivityBookingTicket.class);
                        intent.putExtra("count",count);
                        intent.putExtra("p_id",product_id);
                        intent.putExtra("user_id",ECONSTANT.logedUser.getId());
                        intent.putExtra("total_price",x);
                        startActivity(intent);
                    }
                    else {
                        startActivity(new Intent(ActivityMovieDetail.this, ActivityLogin.class));
                    }
                }
            });
        } catch (Exception e) {
            Log.e(TAG, "onCreate: " + e.toString());
        }
    }

    private void getsingleproduct() {
        try {
            JsonObjectRequest objectRequest =
                    new JsonObjectRequest(Request.Method.GET,
                            ECONSTANT.URL_MASTERDTAIL_PRODUCTS_ECOM + product_id,
                            null, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            final Gson gson = new Gson();
                            try {
                                if (response.getJSONArray("data") != null) {
                                    products = gson.fromJson(
                                            String.valueOf(response.getJSONArray("data")),
                                            Product[].class);

                                    tvTotalPrice.setText(("$" + products[0].getP_price()));
                                    x= products[0].getP_price();
                                    No_ofProducts = products[0].getP_quantity();
                                    product_id = products[0].getP_id();
                                    tvProductDescrition.setText(products[0].getP_description());
                                    tvProductPrice.setText(String.valueOf(products[0].getP_price()));
                                    tvProductTitle.setText(products[0].getP_title());
                                    Glide.with(ActivityMovieDetail.this).
                                            load(ECONSTANT.URL_IMG_SUBCATAGORY +
                                                    products[0].getP_image()).into(ivProduct);
                                    /*((TextView)findViewById(R.id.tvDate)).setText(products[0].getPdate());
                                    ((TextView)findViewById(R.id.tvTime)).setText(products[0].getPtime());
                                    ((TextView)findViewById(R.id.tvTickets)).setText(String.valueOf(products[0].getP_quantity()));
                                */} else {
                                    Log.e(TAG, "onResponse: No Data About The Product detail");
                                }
                                final ProductImages[] imageModel;
                                try {
                                    imageModel = gson.fromJson(
                                            String.valueOf(response.getJSONArray("images")),
                                            ProductImages[].class);

                                    if (imageModel.length > 0) {
                                        Glide.with(ActivityMovieDetail.this).
                                                load(ECONSTANT.URL_IMG_PRODUCTS +
                                                        imageModel[0].getImage1()).into(iv1Product);
                                        Glide.with(ActivityMovieDetail.this).
                                                load(ECONSTANT.URL_IMG_PRODUCTS +
                                                        imageModel[0].getImage2()).into(iv2Product);
                                        Glide.with(ActivityMovieDetail.this).
                                                load(ECONSTANT.URL_IMG_PRODUCTS +
                                                        imageModel[0].getImage3()).into(iv3Product);
                                        Glide.with(ActivityMovieDetail.this).
                                                load(ECONSTANT.URL_IMG_PRODUCTS +
                                                        imageModel[0].getImage4()).into(iv4Product);

                                        iv1Product.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                Glide.with(ActivityMovieDetail.this).
                                                        load(ECONSTANT.URL_IMG_PRODUCTS +
                                                                imageModel[0].getImage1()).into(ivProduct);

                                            }
                                        });
                                        iv2Product.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                Glide.with(ActivityMovieDetail.this).
                                                        load(ECONSTANT.URL_IMG_PRODUCTS +
                                                                imageModel[0].getImage2()).into(ivProduct);

                                            }
                                        });
                                        iv3Product.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                Glide.with(ActivityMovieDetail.this).
                                                        load(ECONSTANT.URL_IMG_PRODUCTS +
                                                                imageModel[0].getImage3()).into(ivProduct);

                                            }
                                        });
                                        iv4Product.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                Glide.with(ActivityMovieDetail.this).
                                                        load(ECONSTANT.URL_IMG_PRODUCTS +
                                                                imageModel[0].getImage4()).into(ivProduct);

                                            }
                                        });
                                    } else {
                                        Log.e(TAG, "onResponse: No data in the images");
                                    }
                                } catch (JSONException exc) {
                                    Log.e(TAG, "onResponse: EXP: ---" + exc.toString());
                                }

                            } catch (Exception ex) {
                                Log.e(TAG, "onResponse:Exception =====" + ex.toString());
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e(TAG, "onErrorResponse: " + error.toString());
                        }
                    });
            MyNetwork.getInstance(this).addToRequestQueue(objectRequest);

        } catch (Exception e) {
            Log.e(TAG, "EXXXP :" + e.toString());
        }
    }
}