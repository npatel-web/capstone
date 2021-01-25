package com.example.getinn.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.getinn.R;
import com.example.getinn.utilities.Config;
import com.example.getinn.utilities.ECONSTANT;
import com.example.getinn.utilities.EHelpingFunctions;
import com.example.getinn.utilities.MyNetwork;
import com.manojbhadane.PaymentCardView;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class ActivityBookingTicket extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    int price;
    public static String name, email, address, phone;
    private TextView etODDetailName, tvOPrice, etODDetailEmail, etODDetailContact, etODDetailAddress;
    private static final String TAG = "TAG";
    private RadioButton rbMC;
    private String timex;
    private DatePicker datePicker;
    /*private static final int PAYPAL_REQUEST_CODE = 123;

    private static PayPalConfiguration config = new PayPalConfiguration()
            .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
            .clientId(Config.PAYPAL_CLIENT_ID);*/
    Button btnPayNow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_ticket);
        setTitle("Booking Tickets");

        Intent intent = getIntent();
        final int count = intent.getIntExtra("count",0);
        final int p_id = intent.getIntExtra("p_id",0);
        final int price =  intent.getIntExtra("total_price",0);

        Spinner spinner = (Spinner) findViewById(R.id.spTime);
        datePicker = findViewById(R.id.date_picker);
        spinner.setOnItemSelectedListener(this);
        List<String> categories = new ArrayList<String>();
        categories.add("06:00 PM");
        categories.add("08:00 PM");
        categories.add("10:00 PM");
        categories.add("12:00 PM");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);

        PaymentCardView paymentCard =  findViewById(R.id.creditCard);

        etODDetailName = findViewById(R.id.etODDetailName);
        etODDetailEmail = findViewById(R.id.etODDetailEmail);
        etODDetailContact = findViewById(R.id.etODDetailContact);
       // etODDetailAddress = findViewById(R.id.etODDetailAddress);
        tvOPrice = findViewById(R.id.tvOPrice);
        tvOPrice.setText(String.valueOf(price));
        etODDetailName.setText(ECONSTANT.logedUser.getName());
        etODDetailEmail.setText(ECONSTANT.logedUser.getEmail());
        etODDetailContact.setText(ECONSTANT.logedUser.getPhone_no());
//        etODDetailAddress.setText(ECONSTANT.logedUser.getAddress());
        rbMC = findViewById(R.id.rbMC);
        ((findViewById(R.id.llMC))).setVisibility(View.VISIBLE);
        (( findViewById(R.id.btnConfirmOrder))).setVisibility(View.GONE);

        rbMC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((findViewById(R.id.llMC))).setVisibility(View.VISIBLE);
                (( findViewById(R.id.btnConfirmOrder))).setVisibility(View.GONE);
            }
        });

        btnPayNow = findViewById(R.id.btnPayNow);
        btnPayNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                processPayment();
            }
        });

        paymentCard.setOnPaymentCardEventListener(new PaymentCardView.OnPaymentCardEventListener() {
            @Override
            public void onCardDetailsSubmit(String month, String year, String cardNumber, String cvv) {
                EHelpingFunctions.showLoading(ActivityBookingTicket.this,"Please Wait...");
                JSONObject testModel = new JSONObject();
                try {
                    testModel.put("status", "CONFIRMED");
                    testModel.put("price", price);
                    testModel.put("user_id", ECONSTANT.logedUser.getId());
                    testModel.put("p_time", timex);
                    testModel.put("p_date", datePicker.getDayOfMonth() + ":" + datePicker.getMonth() + ":"+ datePicker.getYear());
                    // 0----> COD   1---->MC
                    testModel.put("method", 1);
                    testModel.put("m_id", p_id);
                    testModel.put("tickets", count);

                    addProductToOrders(testModel);

                } catch (JSONException e) {
                    Log.e(TAG, "onClick: " + e.toString());
                }

                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("month",month);
                    jsonObject.put("year",year);
                    jsonObject.put("cardNumber",cardNumber);
                    jsonObject.put("cvv",cvv);
                    jsonObject.put("u_id",ECONSTANT.logedUser.getId());
                    addCard(jsonObject);

                } catch (JSONException e) {
                    Log.e(TAG, "onClick: " + e.toString());
                }
            }

            @Override
            public void onError(String error) {
                Toast.makeText(ActivityBookingTicket.this, error, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelClick() {
                ((findViewById(R.id.llMC))).setVisibility(View.GONE);
                (( findViewById(R.id.btnConfirmOrder))).setVisibility(View.GONE);
                (( findViewById(R.id.btnConfirmOrder))).setVisibility(View.VISIBLE);
            }
        });
        findViewById(R.id.btnConfirmOrder).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EHelpingFunctions.showLoading(ActivityBookingTicket.this,"Please Wait...");
                try {
                    JSONObject testModel = new JSONObject();
                    try {
                        testModel.put("status", "CONFIRMED");
                        testModel.put("price", price);
                        testModel.put("user_id", ECONSTANT.logedUser.getId());
                        testModel.put("p_time", timex);
                        testModel.put("p_date", datePicker.getDayOfMonth() + ":" + datePicker.getMonth() + ":"+ datePicker.getYear());
                        // 0----> COD   1---->MC
                        testModel.put("method", 0);
                        testModel.put("m_id", p_id);
                        testModel.put("tickets", count);
                        addProductToOrders(testModel);

                    } catch (JSONException e) {
                        Log.e(TAG, "onClick: " + e.toString());
                    }


                } catch (Exception ex) {
                    Log.e(TAG, "onClick: " + ex.toString());
                }
            }
        });
    }

    private void processPayment() {
        Intent intent1 = new Intent(this,PaypalPayment.class);
        intent1.putExtra("amount",price);
        startActivity(intent1);
    }

    private void addCard(JSONObject jsonObject) {
        try{
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                    ECONSTANT.URL_ADD_CARD,
                    jsonObject,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.e(TAG, "onResponse: " + response.toString());

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e(TAG, "onErrorResponse: " + error.toString());
                }
            });
            MyNetwork.getInstance(this).addToRequestQueue(jsonObjectRequest);
        }
        catch (Exception e){
            Log.e(TAG, "addCard: EXP: "+e.toString());
        }
    }

    private void addProductToOrders(JSONObject testModel) {
        try {
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                    ECONSTANT.URL_ADD_TO_ORDERS,
                    testModel,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.e(TAG, "onResponse: " + response.toString());
                            try {
                                EHelpingFunctions.stopLoading();
                                if (!response.getBoolean("data")) {
                                    new SweetAlertDialog(ActivityBookingTicket.this, SweetAlertDialog.ERROR_TYPE)
                                            .setTitleText("Oops...")
                                            .setContentText("Could not book ticket!")
                                            .show();
                                } else {

                                    new SweetAlertDialog(ActivityBookingTicket.this, SweetAlertDialog.SUCCESS_TYPE)
                                            .setTitleText("Success!")
                                            .setContentText("Ticket Booked Successfully.")
                                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                                @Override
                                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                                    startActivity(new Intent(ActivityBookingTicket.this,
                                                            ActivityBookings.class));
                                                    price = 0;
                                                    finish();
                                                }
                                            })
                                            .show();
                                }
                            } catch (JSONException e) {
                                EHelpingFunctions.stopLoading();
                                Log.e(TAG, "onResponse: EXP: " + e.toString());
                            }

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    EHelpingFunctions.stopLoading();
                    Log.e(TAG, "onErrorResponse: " + error.toString());
                }
            });
            MyNetwork.getInstance(this).addToRequestQueue(jsonObjectRequest);
        } catch (Exception e) {
            EHelpingFunctions.stopLoading();
            Log.e(TAG, "addProductToOrders: " + e.toString());
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        timex = parent.getItemAtPosition(position).toString();
    }
    public void onNothingSelected(AdapterView<?> arg0) {
        timex = "06:00 PM";
    }
}