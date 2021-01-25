package com.example.getinn.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.getinn.R;
import com.example.getinn.utilities.ECONSTANT;

import in.codeshuffle.typewriterview.TypeWriterView;

public class ActivitySplash extends AppCompatActivity {
    private static final String TAG = ECONSTANT.TAG;
    private Animation animation;
    private TextView tvTitle;
    private TypeWriterView typeWriterView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        tvTitle = findViewById(R.id.tvTitle);
        try
        {
            Thread thread= new Thread(){
                public void run()
                {
                    animation = AnimationUtils.loadAnimation(ActivitySplash.this,R.anim.bounce);
                    tvTitle.startAnimation(animation);
                    try
                    { sleep(3000);}
                    catch (InterruptedException e) {
                        Log.e(TAG, "run: " + e.toString());
                    }
                }};
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    typeWriterView = findViewById(R.id.typeWriterView);
                    typeWriterView.setDelay(100);
                    typeWriterView.setWithMusic(false);
                    typeWriterView.animateText("Where Movie Lovers Belong.");
                }
            }, 1000);

            Thread thread1 = new Thread()
            {
                public void run()
                {
                    try {
                        sleep(5500);
                    } catch (InterruptedException e) {
                        Log.e(TAG, "run: "+e.toString());
                    }
                    startActivity(new Intent(ActivitySplash.this,ActivityHome.class));
                    finish();
                }

            };
            thread.start();
            thread1.start();
        }
        catch (Exception ex)
        {
            Log.e(TAG, "onCreate: "+ex.toString() );
        }
    }
}