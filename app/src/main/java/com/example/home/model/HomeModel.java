package com.example.home.model;

import android.content.Context;

import com.example.home.base.BaseModel;
import com.example.home.base.RequestAsyncTaskListener;
import com.example.home.model.bean.HomeResult;
import com.example.home.model.database.HomeDao;
import com.example.home.model.database.UserDatabase;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class HomeModel extends BaseModel {
    private Context context;
    private HomeDao homeDao;

    public HomeModel(Context context) {
        this.context = context;
        this.homeDao = UserDatabase.getInstance(context.getApplicationContext()).getHomeDao();
    }

    //写入缓存
    public void overrideCacheData(HomeResult.Data.Datas...datas){
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                homeDao.clearCache();
                homeDao.add(datas);
            }
        });
    }

    //获取缓存数据
    public List<HomeResult.Data.Datas> getCacheData(){
        Future<List<HomeResult.Data.Datas>> future = executorService.submit(new Callable<List<HomeResult.Data.Datas>>() {

            @Override
            public List<HomeResult.Data.Datas> call() throws Exception {
                return homeDao.getCacheData();
            }
        });

        try {
            return future.get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }
}
