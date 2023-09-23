package com.example.home.base;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModel;

public abstract class BaseActivity<T extends ViewDataBinding, VM extends ViewModel> extends AppCompatActivity {
    protected T binding;
    protected VM viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.viewModel = viewModel  == null? createViewModel() : viewModel;
        binding = DataBindingUtil.setContentView(this, getLayoutRes());
        if (binding != null){
            if (getBindingVariable() > 0){
                binding.setVariable(getBindingVariable(), viewModel);
                binding.executePendingBindings();
            }
            binding.setLifecycleOwner(getLifeCycleOwner());
        }else {
            setContentView(getLayoutRes());
        }
        initParams();
    }

    protected abstract void initParams();//初始化数据

    protected abstract int getLayoutRes();

    protected abstract VM createViewModel();

    protected abstract LifecycleOwner getLifeCycleOwner();

    protected abstract int getBindingVariable();
}
