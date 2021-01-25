package com.example.getinn.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.util.Patterns;
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
import com.mikelau.croperino.Croperino;
import com.mikelau.croperino.CroperinoConfig;
import com.mikelau.croperino.CroperinoFileUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;

import cn.pedant.SweetAlert.SweetAlertDialog;
import de.hdodenhof.circleimageview.CircleImageView;

public class ActivitySignup extends AppCompatActivity {
    public CircleImageView ivUser;
    private static final String TAG = "TAG";
    public EditText etName, etEmail, etpassword, etphone, etaddress;
    private String email, password,name,address,phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        try {
            etName = findViewById(R.id.etSFullName);
            etEmail = findViewById(R.id.etSEmail);
            etpassword = findViewById(R.id.etSPassword);
            etaddress = findViewById(R.id.etSAddress);
            etphone = findViewById(R.id.etSPhoneNo_);
            ivUser = findViewById(R.id.ivUser);
            new CroperinoConfig("IMG_" + System.currentTimeMillis() + ".jpg", "/EPharmacy/Pictures", "/sdcard/EPharmacy/Pictures");
            CroperinoFileUtil.verifyStoragePermissions(ActivitySignup.this);
            CroperinoFileUtil.setupDirectory(ActivitySignup.this);

            findViewById(R.id.ibUserImagePicker).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        new SweetAlertDialog(ActivitySignup.this, SweetAlertDialog.WARNING_TYPE)
                                .setTitleText("Select Image or Capture")
                                .setConfirmButton("Gallery", new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                                        sweetAlertDialog.dismissWithAnimation();
                                        Croperino.prepareGallery(ActivitySignup.this);
                                    }
                                })
                                .setCancelButton("Camera", new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                                        sweetAlertDialog.dismissWithAnimation();

                                        Croperino.prepareCamera(ActivitySignup.this);
                                    }
                                })
                                .show();
                    } catch (Exception e) {
                        Log.e(TAG, "onClick: EXP: " + e.toString());
                    }
                }
            });
            findViewById(R.id.btnSignUp).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        name = etName.getText().toString().trim();
                        email = etEmail.getText().toString().trim();
                        password = etpassword.getText().toString().trim();
                        address = etaddress.getText().toString().trim();
                        phone = etphone.getText().toString().trim();
                        if (name.isEmpty()) {
                            etName.setError("Must Fill Field");
                            etName.requestFocus();
                            return;
                        }
                        if (email.isEmpty()) {
                            etEmail.setError("Must Fill Field");
                            etEmail.requestFocus();
                            return;
                        }
                        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                            etEmail.setError("Must enter valid Email");
                            etEmail.requestFocus();
                            return;
                        }
                        if (password.isEmpty()) {
                            etpassword.setError("Must Fill Field");
                            etpassword.requestFocus();
                            return;
                        }
                        if (password.length() < 6) {
                            etpassword.setError("Minimum length of password should be 6");
                            etpassword.requestFocus();
                            return;
                        }
                        if (address.isEmpty()) {
                            etaddress.setError("Must Fill Field");
                            etaddress.requestFocus();
                            return;
                        }
                        if (phone.isEmpty()) {
                            etphone.setError("Must Fill Field");
                            etphone.requestFocus();
                            return;
                        }
                        EHelpingFunctions.showLoading(ActivitySignup.this,"Please Wait...");
                        new MyTask().execute();
                    } catch (Exception ex) {
                        Log.e(TAG, "onClick: " + ex.toString());
                    }
                }
            });
        } catch (Exception ex) {
            Log.e(TAG, "onCreate: " + ex.toString());
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case CroperinoConfig.REQUEST_TAKE_PHOTO:
                if (resultCode == Activity.RESULT_OK) {
                    /* Parameters of runCropImage = File, Activity Context, Image is Scalable or Not, Aspect Ratio X, Aspect Ratio Y, Button Bar Color, Background Color */
                    Croperino.runCropImage(CroperinoFileUtil.getTempFile(), ActivitySignup.this, true, 1, 1, R.color.gray, R.color.gray_variant);
                }
                break;
            case CroperinoConfig.REQUEST_PICK_FILE:
                if (resultCode == Activity.RESULT_OK) {
                    CroperinoFileUtil.newGalleryFile(data, ActivitySignup.this);
                    Croperino.runCropImage(CroperinoFileUtil.getTempFile(), ActivitySignup.this, true, 0, 0, R.color.gray, R.color.gray_variant);
                }
                break;
            case CroperinoConfig.REQUEST_CROP_PHOTO:
                if (resultCode == Activity.RESULT_OK) {
                    Uri i = Uri.fromFile(CroperinoFileUtil.getTempFile());
                    ivUser.setImageURI(i);
                }
                break;
            default:
                break;
        }
    }

    private class MyTask extends AsyncTask<Void, Void, String> {
        SweetAlertDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.e(TAG, "onPreExecute: ");
        }

        @Override
        protected String doInBackground(Void... uri) {
            try {
                Bitmap selectedImage = ((BitmapDrawable) ivUser.getDrawable()).getBitmap();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                selectedImage.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] b = baos.toByteArray();
                String base64EncodingString = Base64.encodeToString(b, Base64.DEFAULT);
                return base64EncodingString;
            } catch (Exception ex) {
                Log.e(TAG, "doInBackground: " + ex.toString());
                return null;
            }

        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (result != null) {
                Log.e(TAG, "onPostExecute: " + result);
                User user = new User(-1, etName.getText().toString(),
                        etEmail.getText().toString(),
                        etpassword.getText().toString(),
                        etaddress.getText().toString(),
                        etphone.getText().toString(), result);
                insertUserRecord(user);
            }
        }
    }

    private void insertUserRecord(User user) {
        try {
            JsonObjectRequest insertRequest = new JsonObjectRequest(Request.Method.POST,
                    ECONSTANT.URL_SIGNUP,
                    new JSONObject(new Gson().toJson(user)),
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            EHelpingFunctions.stopLoading();
                            Log.e(TAG, "onResponse: " + response.toString());
                            try {
                                if (response.getBoolean("data") == true) {

                                    new SweetAlertDialog(ActivitySignup.this, SweetAlertDialog.SUCCESS_TYPE)
                                            .setTitleText("Sign Up Successfully!")
                                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                                @Override
                                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                                    validation(email, password);
                                                }
                                            })
                                            .show();


                                } else {
                                    new SweetAlertDialog(ActivitySignup.this, SweetAlertDialog.ERROR_TYPE)
                                            .setTitleText("Oops...")
                                            .setContentText("Email already exist Or not available!")
                                            .show();
                                }
                            } catch (JSONException e) {
                                new SweetAlertDialog(ActivitySignup.this, SweetAlertDialog.ERROR_TYPE)
                                        .setTitleText("Oops...")
                                        .setContentText("" + e.toString())
                                        .show();
                                Log.e(TAG, "onResponse: EXP: " + e.toString());
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e(TAG, "onErrorResponse: " + error.toString());
                            EHelpingFunctions.stopLoading();
                            new SweetAlertDialog(ActivitySignup.this, SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText("Oops...")
                                    .setContentText("" + error.toString())
                                    .show();


                        }
                    });
            MyNetwork.getInstance(ActivitySignup.this).addToRequestQueue(insertRequest);

        } catch (Exception ex) {
            Log.e(TAG, "insertUserRecord: " + ex.toString());
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
                                    ECONSTANT.logedUser = gson.fromJson(response.getJSONObject("data").toString(), User.class);
                                    Log.e(TAG, "onResponse: " + response.toString());
                                    FastSave.getInstance().saveObject(ECONSTANT.KEY_LOGED_USER, ECONSTANT.logedUser);
                                    startActivity(
                                            new Intent(ActivitySignup.this,
                                                    ActivityHome.class));
                                    ActivitySignup.this.finish();

                                } else {
                                    EHelpingFunctions.stopLoading();

                                    Log.e(TAG, "onResponse: " );
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
            MyNetwork.getInstance(ActivitySignup.this).addToRequestQueue(objectRequest);
        } catch (Exception ex) {
            EHelpingFunctions.stopLoading();
            Log.e(TAG, "validation: " + ex.toString());
        }
    }

}