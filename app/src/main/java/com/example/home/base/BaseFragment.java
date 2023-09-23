package com.example.home.base;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModel;

public abstract class BaseFragment<T extends ViewDataBinding, VM extends ViewModel> extends Fragment {
    protected T binding;
    protected VM viewModel;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, getLayoutRes(), container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = createViewModel();
        Log.w("MainActivity", "onViewCreated: ");
        if (getBindingVariable() > 0) {
            binding.setVariable(getBindingVariable(), viewModel);
            binding.executePendingBindings();
        }
        binding.setLifecycleOwner(getLifeCycleOwner());
        initParams();
    }

    protected abstract void initParams();

    protected abstract LifecycleOwner getLifeCycleOwner();

    protected abstract int getBindingVariable();

    protected abstract VM createViewModel();
    @LayoutRes
    public abstract int getLayoutRes();
}
