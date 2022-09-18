package com.example.app.other;

import android.app.Application;
import android.util.Log;

import com.orhanobut.hawk.Hawk;
import com.orhanobut.hawk.LogInterceptor;

public class AppController extends Application {

    private Thread.UncaughtExceptionHandler defaultUEH;

    @Override
    public void onCreate() {
        super.onCreate();
        init();

    }

    public void init() {
        long startTime = System.currentTimeMillis();

        Hawk.init(this).setLogInterceptor(message -> Log.d("HAWK", message)).build();

        long endTime = System.currentTimeMillis();
    }


}

