package com.example.home.base;

import android.os.Handler;
import android.os.Looper;

import androidx.lifecycle.ViewModel;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public abstract class BaseModel {
    protected Handler handler = new Handler(Looper.getMainLooper());
    protected ExecutorService executorService = Executors.newFixedThreadPool(1);

    //请求异步操作
    protected void requestAsyncTask(RequestAsyncTaskListener requestAsyncTaskListener){
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                requestAsyncTaskListener.doInBackground();
                switchToUiThread(requestAsyncTaskListener);
            }
        });
    }

    //异步操作执行完毕后切换到主线程操作
    protected void switchToUiThread(RequestAsyncTaskListener requestAsyncTaskListener){
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                requestAsyncTaskListener.doInUiThread();
            }
        }, 0);
    }

    protected void shutdown(){
        if (!executorService.isShutdown()){
            executorService.shutdown();
        }
    }
}
