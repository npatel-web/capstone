package com.example.getinn.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.appizona.yehiahd.fastsave.FastSave;
import com.example.getinn.R;
import com.example.getinn.models.User;
import com.example.getinn.utilities.ECONSTANT;
import com.example.getinn.utilities.EHelpingFunctions;
import com.example.getinn.utilities.MyNetwork;
import com.google.gson.Gson;

import org.json.JSONObject;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class ActivityLogin extends AppCompatActivity {
    private static final String TAG = ECONSTANT.TAG;
    private EditText etemail, etpassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        try {

            etemail = findViewById(R.id.etLoginEmail);
            etpassword = findViewById(R.id.etLoginPassword);
            ECONSTANT.logedUser = FastSave.getInstance().getObject(ECONSTANT.KEY_LOGED_USER, User.class);
            if (ECONSTANT.logedUser != null) {
                startActivity(new Intent(ActivityLogin.this, ActivityHome.class));
                finish();
            }
            findViewById(R.id.btnOpenSignUp).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(ActivityLogin.this, ActivitySignup.class));


                }
            });
            findViewById(R.id.btnLogin).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        String email = etemail.getText().toString().trim();
                        String password = etpassword.getText().toString().trim();
                        if (email.isEmpty()) {
                            etemail.setError("Must Fill Field ");
                            etemail.requestFocus();
                            return;
                        }
                        if (password.isEmpty()) {
                            etpassword.setError("Must Full Field");
                            etpassword.requestFocus();
                            return;
                        }
                        EHelpingFunctions.showLoading(ActivityLogin.this,"Please Wait...");
                        validation(email, password);
                    } catch (Exception ex) {
                        Log.e(TAG, "onClick: " + ex.toString());
                    }
                }
            });
        } catch (Exception ex) {
            Log.e(TAG, "onCreate: " + ex.toString());
        }
    }

    private void validation(String email, String password) {
        try {
            JSONObject loginCredential = new JSONObject();
            loginCredential.put("email", email);
            loginCredential.put("password", password);
            final JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.POST,
                    ECONSTANT.URL_LOGIN,
                    loginCredential,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                if (response.getBoolean("status")) {
                                    Gson gson = new Gson();
                                    EHelpingFunctions.stopLoading();
                                    ECONSTANT.logedUser = gson.fromJson(response.getJSONObject("data").toString(),
                                            User.class);
                                    Log.e(TAG, "onResponse: " + response.toString());
                                    FastSave.getInstance().saveObject(ECONSTANT.KEY_LOGED_USER, ECONSTANT.logedUser);
                                    // EHelpingFunctions.stopLoading();
                                    startActivity(
                                            new Intent(ActivityLogin.this,
                                                    ActivityHome.class));
                                    ActivityLogin.this.finish();


                                } else {
                                    EHelpingFunctions.stopLoading();
                                    new SweetAlertDialog(ActivityLogin.this, SweetAlertDialog.ERROR_TYPE)
                                            .setTitleText("Oops...")
                                            .setContentText("Please Check your Email and password!")
                                            .show();
                                }
                            } catch (Exception ex) {
                                EHelpingFunctions.stopLoading();
                                Log.e(TAG, "onResponse: " + ex.toString());
                            }

                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            EHelpingFunctions.stopLoading();
                            Log.e(TAG, "onErrorResponse: ERROR: " + error.toString());
                        }
                    }
            );
            MyNetwork.getInstance(ActivityLogin.this).addToRequestQueue(objectRequest);
        } catch (Exception ex) {
            EHelpingFunctions.stopLoading();
            Log.e(TAG, "validation: " + ex.toString());
        }
    }
}
