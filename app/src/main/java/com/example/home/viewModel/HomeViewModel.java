package com.example.home.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.home.base.BaseViewModel;
import com.example.home.model.HomeModel;
import com.example.home.model.bean.HomeResult;

import java.util.ArrayList;
import java.util.List;

public class HomeViewModel extends BaseViewModel<HomeModel> {
    private int currentPage = 1;
    private MutableLiveData<List<HomeResult.Data.Datas>> displayData = new MutableLiveData<>();
    public HomeViewModel(@NonNull Application application) {
        super(application);
        model = new HomeModel(application);
    }

    @Override
    protected void initParams() {

    }

    public void overrideCacheData(HomeResult.Data.Datas...datas){
        if (model != null){
            model.overrideCacheData(datas);
        }
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
