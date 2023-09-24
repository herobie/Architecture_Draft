package com.example.home.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.home.base.BaseViewModel;
import com.example.home.model.HomeModel;
import com.example.home.model.bean.HomeResult;

import java.util.List;

public class HomeViewModel extends BaseViewModel<HomeModel> {
    private int currentPage = 1;
    private final MutableLiveData<List<HomeResult.Data.Datas>> displayData = new MutableLiveData<>();
    public HomeViewModel(@NonNull Application application) {
        super(application);
        model = new HomeModel(application);
    }

    @Override
    protected void initParams() {

    }

    //重写缓存数据
    public void overrideCacheData(HomeResult.Data.Datas...datas){
        if (model != null){
            model.overrideCacheData(datas);
        }
    }

    //获取缓存数据
    public List<HomeResult.Data.Datas> getCacheData(){
        return model.getCacheData();
    }

    public MutableLiveData<List<HomeResult.Data.Datas>> getDisplayData() {
        return displayData;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
}
