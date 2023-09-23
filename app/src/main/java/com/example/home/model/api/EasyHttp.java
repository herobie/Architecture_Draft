package com.example.home.model.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EasyHttp {
    private Retrofit.Builder retrofitBuilder;
    private volatile static EasyHttp easyHttp = null;

    public static EasyHttp getInstance() {
        if (easyHttp == null) {
            synchronized (EasyHttp.class) {
                if (easyHttp == null) {
                    easyHttp = new EasyHttp();
                }
            }
        }
        return easyHttp;
    }

    private EasyHttp() {
        init();
    }

    //加载retrofitBuilder
    private void init(){
        retrofitBuilder = new Retrofit.Builder();
        retrofitBuilder.addConverterFactory(GsonConverterFactory.create(initGson()));
    }

    //初始化gson对象
    private Gson initGson(){
        return new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
                .setLenient().create();
    }

    //get请求
    public ApiStore get(String url){
        if (null == retrofitBuilder){
            init();
        }
        return retrofitBuilder.baseUrl(url)
                .build()
                .create(ApiStore.class);
    }


}
