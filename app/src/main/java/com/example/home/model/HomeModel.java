package com.example.home.model;

import android.content.Context;

import com.example.home.base.BaseModel;
import com.example.home.model.bean.HomeResult;
import com.example.home.model.database.HomeDao;
import com.example.home.model.database.UserDatabase;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class HomeModel extends BaseModel {
    private final HomeDao homeDao;

    public HomeModel(Context context) {
        this.homeDao = UserDatabase.getInstance(context.getApplicationContext()).getHomeDao();
    }

    //写入，覆盖缓存
    public void overrideCacheData(HomeResult.Data.Datas...datas){
        executorService.submit(() -> {
            homeDao.clearCache();
            homeDao.add(datas);
        });
    }

    //获取缓存数据
    public List<HomeResult.Data.Datas> getCacheData(){
        Future<List<HomeResult.Data.Datas>> future = executorService.submit(homeDao::getCacheData);

        try {
            return future.get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }
}
