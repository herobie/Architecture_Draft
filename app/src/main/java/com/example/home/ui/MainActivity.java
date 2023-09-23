package com.example.home.ui;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;

import com.example.home.R;
import com.example.home.base.BaseActivity;
import com.example.home.databinding.ActivityMainBinding;
import com.example.home.ui.adapter.FragmentsAdapter;
import com.example.home.viewModel.MainViewModel;

public class MainActivity extends BaseActivity<ActivityMainBinding, MainViewModel> {

    @Override
    protected void initParams() {
        //初始化viewPager里的fragment
        if (viewModel.getFragments().isEmpty()){
            viewModel.getFragments().add(new HomeFragment());
        }
        //初始化viewPager的adapter
        if (null == viewModel.getFragmentsAdapter()){
            FragmentsAdapter fragmentsAdapter
                    = new FragmentsAdapter(getSupportFragmentManager(), getLifecycle(), viewModel.getFragments());
            viewModel.setFragmentsAdapter(fragmentsAdapter);
        }
        binding.mainContainer.setAdapter(viewModel.getFragmentsAdapter());
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_main;
    }

    @Override
    protected MainViewModel createViewModel() {
        return new ViewModelProvider(this).get(MainViewModel.class);
    }

    @Override
    protected LifecycleOwner getLifeCycleOwner() {
        return this;
    }

    @Override
    protected int getBindingVariable() {
        return 0;
    }
}