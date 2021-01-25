package com.example.getinn.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.getinn.R;
import com.example.getinn.adapters.EAdapterCategory;
import com.example.getinn.models.Category;
import com.example.getinn.utilities.ECONSTANT;
import com.example.getinn.utilities.MyNetwork;
import com.google.gson.Gson;

import org.json.JSONObject;

public class ActivityCategories extends AppCompatActivity {

    private static final String TAG = ECONSTANT.TAG;
    private EAdapterCategory eAdapterCategory;
    private ListView lvCategories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
        setTitle("Movies Categories");

        lvCategories = findViewById(R.id.lvCategories);
        getdatafromcatagory();
    }

    private void getdatafromcatagory() {
        try {
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    Request.Method.GET
                    , ECONSTANT.URL_CATAGORIES, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.e(TAG, "onResponse: " + response.toString());
                            final Gson gson = new Gson();
                            try {
                                Category[] models =
                                        gson.fromJson(String.valueOf(
                                                response.getJSONArray("data")),
                                                Category[].class);
                                eAdapterCategory = new
                                        EAdapterCategory(ActivityCategories.this,
                                        models);
                                lvCategories.setAdapter(eAdapterCategory);
                                lvCategories.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                        final Category model = eAdapterCategory.getItem(position);
                                        Intent intent = new Intent(ActivityCategories.this,ActivityMovie.class);
                                        intent.putExtra(ECONSTANT.KEY_CAT_ID,model.getCategory_id());
                                        startActivity(intent);
                                    }
                                });
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

        }
    }
}