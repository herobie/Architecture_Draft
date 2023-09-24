package com.example.home.base;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public abstract class BaseModel {
    protected Handler handler = new Handler(Looper.getMainLooper());
    protected ExecutorService executorService = Executors.newFixedThreadPool(1);

    protected void shutdown(){
        if (!executorService.isShutdown()){
            executorService.shutdown();
        }
    }
}
