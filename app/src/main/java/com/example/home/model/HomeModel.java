package com.example.home.model;

import android.content.Context;

import com.example.home.base.BaseModel;
import com.example.home.model.bean.HomeResult;
import com.example.home.model.database.HomeDao;
import com.example.home.model.database.UserDatabase;

public class HomeModel extends BaseModel {
    private Context context;
    private HomeDao homeDao;

    public HomeModel(Context context) {
        this.context = context;
        this.homeDao = UserDatabase.getInstance(context.getApplicationContext()).getHomeDao();
    }

    public void overrideCacheData(HomeResult.Data.Datas...datas){
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                homeDao.add(datas);
            }
        });
    }
}
