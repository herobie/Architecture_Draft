package com.example.home.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.home.base.BaseViewModel;
import com.example.home.ui.adapter.FragmentsAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainViewModel extends BaseViewModel {
    private final List<androidx.fragment.app.Fragment> fragments = new ArrayList<>();
    private FragmentsAdapter fragmentsAdapter;
    public MainViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    protected void initParams() {

    }

    public List<Fragment> getFragments() {
        return fragments;
    }

    public FragmentsAdapter getFragmentsAdapter() {
        return null == fragmentsAdapter? null : fragmentsAdapter;
    }

    public void setFragmentsAdapter(FragmentsAdapter fragmentsAdapter) {
        this.fragmentsAdapter = fragmentsAdapter;
    }
}
