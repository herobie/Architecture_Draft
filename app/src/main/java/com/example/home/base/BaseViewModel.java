package com.example.home.base;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

public abstract class BaseViewModel<M> extends AndroidViewModel{
    protected M model;
    public BaseViewModel(@NonNull Application application) {
        super(application);
        initParams();
    }

    protected abstract void initParams();

}
