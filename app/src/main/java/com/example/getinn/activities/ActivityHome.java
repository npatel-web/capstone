package com.example.getinn.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.appizona.yehiahd.fastsave.FastSave;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.getinn.R;
import com.example.getinn.models.Banner;
import com.example.getinn.models.User;
import com.example.getinn.utilities.ECONSTANT;
import com.example.getinn.utilities.MyNetwork;
import com.glide.slider.library.SliderLayout;
import com.glide.slider.library.animations.DescriptionAnimation;
import com.glide.slider.library.slidertypes.BaseSliderView;
import com.glide.slider.library.slidertypes.TextSliderView;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.karan.churi.PermissionManager.PermissionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import de.hdodenhof.circleimageview.CircleImageView;

public class ActivityHome extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = ECONSTANT.TAG;
    private CircleImageView userImage;
    private SliderLayout sliderLayout;
    private List<String> imageList;
    private Banner[] banners;
    private int p_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        PermissionManager permission = new PermissionManager() {
        };
        permission.checkAndRequestPermissions(this);
        ECONSTANT.logedUser = FastSave.getInstance().getObject(ECONSTANT.KEY_LOGED_USER, User.class);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        sliderLayout = findViewById(R.id.slider);
        getBanner();
        NavigationView navigationView = findViewById(R.id.nav_view);
        if (ECONSTANT.logedUser != null) {
            navigationView.inflateMenu(R.menu.activity_home_drawer_login);
        } else {
            navigationView.inflateMenu(R.menu.activity_home_drawer_without_login);
        }
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        View headerLayout = navigationView.getHeaderView(0);
        userImage = headerLayout.findViewById(R.id.ivNavUser);
        if (ECONSTANT.logedUser != null) {
            Glide.with(this).load(
                    ECONSTANT.URL_IMG_USER + ECONSTANT.logedUser.getImage())
                    .into(userImage);
            ((TextView) headerLayout.findViewById(R.id.tvNavName)).setText(ECONSTANT.logedUser.getName());
            ((TextView) headerLayout.findViewById(R.id.tvNavEmail)).setText(ECONSTANT.logedUser.getEmail());
        } else {
            ((TextView) headerLayout.findViewById(R.id.tvNavName)).setText("Login");
            ((TextView) headerLayout.findViewById(R.id.tvNavEmail)).setText("");

        }
        sliderLayout.setDuration(5000);
        sliderLayout.setPresetTransformer(SliderLayout.Transformer.RotateUp);
        sliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        sliderLayout.setCustomAnimation(new DescriptionAnimation());
        imageList = new ArrayList<>();

        findViewById(R.id.btnMoviesCat).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ActivityHome.this, ActivityCategories.class));
            }
        });

        findViewById(R.id.btnMoviesAll).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivityHome.this, ActivityMovie.class);
                intent.putExtra(ECONSTANT.KEY_CAT_ID, 0);
                startActivity(intent);
            }
        });

        findViewById(R.id.btncurrentmovie).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivityHome.this, ActivityMovie.class);
                intent.putExtra(ECONSTANT.KEY_CAT_ID, -1);
                startActivity(intent);
            }
        });

        findViewById(R.id.btnfood).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Intent intent = new Intent(ActivityHome.this, ActivityMovie.class);
                intent.putExtra(ECONSTANT.KEY_CAT_ID, -2);
                startActivity(intent);*/
                startActivity(new Intent(ActivityHome.this, FoodActivity.class));
            }
        });

        findViewById(R.id.btnMyProfile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ECONSTANT.logedUser != null) {
                    startActivity(new Intent(ActivityHome.this, ActivityProfile.class));
                } else {
                    startActivity(new Intent(ActivityHome.this, ActivityLogin.class));
                }
            }
        });

        findViewById(R.id.btnMyBookings).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ECONSTANT.logedUser != null) {
                    startActivity(new Intent(ActivityHome.this, ActivityBookings.class));
                } else {
                    startActivity(new Intent(ActivityHome.this, ActivityLogin.class));
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        switch (id) {
            case R.id.nav_Orders:
                startActivity(new Intent(ActivityHome.this, ActivityBookings.class));
                break;
            case R.id.nav_Cat:
                startActivity(new Intent(ActivityHome.this, ActivityCategories.class));
                break;
            case R.id.nav_home:
                break;
            case R.id.nav_MyProfile:
                startActivity(new Intent(ActivityHome.this, ActivityProfile.class));
                break;
            case R.id.nav_Current:
                Intent intentC = new Intent(ActivityHome.this, ActivityMovie.class);
                intentC.putExtra(ECONSTANT.KEY_CAT_ID, -1);
                startActivity(intentC);
                break;
            /*case R.id.nav_upComing:
                Intent intentCx = new Intent(ActivityHome.this, ActivityMovie.class);
                intentCx.putExtra(ECONSTANT.KEY_CAT_ID, -2);
                startActivity(intentCx);
                break;*/
            case R.id.nav_MyCart:
                Intent intent = new Intent(ActivityHome.this, ActivityMovie.class);
                intent.putExtra(ECONSTANT.KEY_CAT_ID, 0);
                startActivity(intent);
                break;
            case R.id.nav_food:
                startActivity(new Intent(ActivityHome.this, FoodActivity.class));
                break;
            case R.id.nav_api:
                startActivity(new Intent(ActivityHome.this, MainActivity.class));
                break;
            case R.id.nav_Contact_Us:
                startActivity(new Intent(ActivityHome.this, ActivityAboutUs.class));
                break;
            case R.id.nav_Register:
                startActivity(new Intent(ActivityHome.this, ActivitySignup.class));
                break;
            case R.id.nav_Login:
                startActivity(new Intent(ActivityHome.this, ActivityLogin.class));
                break;
            case R.id.nav_logout: {
                new SweetAlertDialog(ActivityHome.this, SweetAlertDialog.SUCCESS_TYPE)
                        .setTitleText("Are you sure...!")
                        .setContentText("You want to Log Out")
                        .setConfirmButton("Confirm", new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismissWithAnimation();
                                FastSave.getInstance().clearSession();
                                ECONSTANT.logedUser = null;
                                startActivity(new Intent(ActivityHome.this, ActivityHome.class));
                                finish();
                            }
                        })
                        .setCancelButton("Cancel", new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismissWithAnimation();
                            }
                        }).show();
            }
            break;
        }
        return true;
    }

    private void getBanner() {
        try {
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                    ECONSTANT.URL_GET_BANNER,
                    null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.e(TAG, "onResponse: " + response);
                            final Gson gson = new Gson();
                            try {
                                banners = gson.fromJson(String.valueOf(response.getJSONArray("data")), Banner[].class);
                                RequestOptions requestOptions = new RequestOptions();
                                requestOptions.autoClone();
                                for (final Banner banner : banners) {
                                    TextSliderView textSliderView = new TextSliderView(ActivityHome.this);
                                    textSliderView
                                            .description("")
                                            .setRequestOption(requestOptions)
                                            .image(ECONSTANT.URL_IMG_BANNERS + banner.getImage())
                                            .setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                                                @Override
                                                public void onSliderClick(BaseSliderView slider) {
                                                    Intent intent = new Intent(ActivityHome.this,
                                                            ActivityMovieDetail.class);
                                                    p_id = banner.getP_id();
                                                    intent.putExtra(ECONSTANT.KEY_PRODUCT_ID, p_id);
                                                    startActivity(intent);

                                                }
                                            });
                                    sliderLayout.addSlider(textSliderView);
                                }


                            } catch (JSONException e) {
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
            Log.e(TAG, "getBanner: " + e.toString());
        }
    }

}