package com.example.getinn.utilities;
import android.app.Application;

import com.appizona.yehiahd.fastsave.FastSave;

public class EFastSaveInit extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FastSave.init(getApplicationContext());
    }
}
