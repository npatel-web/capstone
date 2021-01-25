package com.example.getinn.utilities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.provider.Settings;
import android.text.format.Formatter;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.irozon.alertview.AlertActionStyle;
import com.irozon.alertview.AlertStyle;
import com.irozon.alertview.AlertView;
import com.irozon.alertview.interfaces.AlertActionListener;
import com.irozon.alertview.objects.AlertAction;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import cn.pedant.SweetAlert.SweetAlertDialog;
import dmax.dialog.SpotsDialog;

import static android.content.Context.WIFI_SERVICE;

public class EHelpingFunctions {
    public static AlertDialog builder;
    public static String TAG = ECONSTANT.TAG;

    public static boolean isNetworkConnected(Context context) {
        try {
            if (context != null) {
                ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
            }
        } catch (Exception ex) {
            Log.e(TAG, "isNetworkConnected: EXP : " + ex.toString());
        }
        return false;
    }

    public static void showNetworkError(final Context context) {
        try {
            if (context != null) {
                AlertView alertView = new AlertView("Error..!\nInternet Connection not available.",
                        "Connect your smart phone to Wifi or Mobile Data to use this application.",
                        AlertStyle.DIALOG);
                alertView.addAction(new AlertAction("Connect to Wifi", AlertActionStyle.POSITIVE, new AlertActionListener() {
                    @Override
                    public void onActionClick(AlertAction alertAction) {
                        context.startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                    }
                }));
                alertView.addAction(new AlertAction("Turn On Mobile Data", AlertActionStyle.POSITIVE, new AlertActionListener() {
                    @Override
                    public void onActionClick(AlertAction alertAction) {
                        context.startActivity(new Intent(Settings.ACTION_AIRPLANE_MODE_SETTINGS));
                    }
                }));
                alertView.addAction(new AlertAction("CANCEL", AlertActionStyle.NEGATIVE, new AlertActionListener() {
                    @Override
                    public void onActionClick(AlertAction alertAction) {
                        Toast.makeText(context, "Internet connection is required.", Toast.LENGTH_LONG).show();
                    }
                }));
                alertView.show((AppCompatActivity) context);
            }
        } catch (Exception ex) {
            Log.e(TAG, "showNetworkError: EXP :" + ex.toString());
        }
    }

    public static void showAlertWithTitleAndMessage(final Context context, final String title, final String message) {
        try {
            if (context != null) {
                AlertView alertView = new AlertView(title,
                        message,
                        AlertStyle.DIALOG);
                alertView.addAction(new AlertAction("OK", AlertActionStyle.DEFAULT, new AlertActionListener() {
                    @Override
                    public void onActionClick(AlertAction alertAction) {
                        //Toast.makeText(context, "OK Clicked", Toast.LENGTH_LONG).show();
                    }
                }));
                alertView.show((AppCompatActivity) context);
            }
        } catch (Exception ex) {
            Log.e(TAG, "showAlertWithTitleAndMessage: EXP:" + ex.toString());
        }
    }

    public static void showLoading(Context context, String message) {
        SpotsDialog.Builder dialog = new SpotsDialog.Builder();
        dialog.setContext(context);
        dialog.setMessage(message);
        builder = dialog.build();
        builder.show();

    }

    public static void stopLoading() {
        builder.dismiss();
    }

    public static void loadImage(final Context context, String imageURL, ImageView imageView, final View progressView) {
        try {
            Glide.with(context)
                    .load(imageURL)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            progressView.setVisibility(View.GONE);
                            return false;
                        }
                    })
                    .fitCenter()
                    .into(imageView);
        } catch (Exception ex) {
            Log.e(TAG, "loadImage: EXP : " + context.getClass().getName() + "-->" + ex.toString());
        }
    }

    public static void showError(Context context, String title, String message) {
        try {
            new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText(title)
                    .setContentText(message)
                    .show();


        } catch (Exception ex) {
            Log.e(TAG, "showError: " + ex.toString());
        }
    }

    public static String convertTime(long millis) {

        SimpleDateFormat formatter = new SimpleDateFormat("hh:mm a dd-MMM");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millis);
        return formatter.format(calendar.getTime());
    }

    public static String getSysIp(Context context){
        try{
            WifiManager wifiMgr = (WifiManager) context.getApplicationContext().getSystemService(WIFI_SERVICE);
            WifiInfo wifiInfo = wifiMgr.getConnectionInfo();
            int ip = wifiInfo.getIpAddress();
            return Formatter.formatIpAddress(ip);
        }
        catch (Exception e){
            Log.e(TAG, "getSysIp: EXP: "+e.toString());
            return null;
        }

    }
}
