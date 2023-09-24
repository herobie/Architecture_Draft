package com.example.home.ui;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.home.BR;
import com.example.home.R;
import com.example.home.base.BaseFragment;
import com.example.home.databinding.FragmentHomeBinding;
import com.example.home.model.api.EasyHttp;
import com.example.home.model.bean.HomeResult;
import com.example.home.ui.adapter.HomeAdapter;
import com.example.home.viewModel.HomeViewModel;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends BaseFragment<FragmentHomeBinding, HomeViewModel> {
    @Override
    protected void initParams() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            //rv初始化
//            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
            GridLayoutManager layoutManager = new GridLayoutManager(requireContext(), 2);
            binding.rv.setLayoutManager(layoutManager);
            HomeAdapter homeAdapter = new HomeAdapter(getContext(), viewModel);
            binding.rv.setAdapter(homeAdapter);

            //获取网络数据后将数据传给adapter
            viewModel.getDisplayData().observe(getLifeCycleOwner(), datas -> {
                Log.d("MainActivity", "onChanged: ");
                homeAdapter.setDatas(datas);
            });

            //如果创建fragment时没有数据，则加载第一页的内容
            if (viewModel.getDisplayData().getValue() == null){
                EasyHttp.getInstance()
                        .get("https://www.wanandroid.com/project/list/")
                        .getProjectResult(1, 294)
                        .enqueue(new Callback<HomeResult>() {
                            @Override
                            public void onResponse(@NonNull Call<HomeResult> call, @NonNull Response<HomeResult> response) {
                                HomeResult homeResult = Objects.requireNonNull(response.body());//可能需要get一下errorCode
                                if (EasyHttp.isLoadingSuccess(homeResult.getErrorCode())){
                                    List<HomeResult.Data.Datas> datas
                                            = homeResult.getProjectData().getDatas();
                                    viewModel.getDisplayData().setValue(datas);
                                    viewModel.overrideCacheData(datas.toArray(new HomeResult.Data.Datas[0]));
                                }else {//请求异常会调用缓存数据
                                    Toast.makeText(getContext(), homeResult.getErrorMsg(), Toast.LENGTH_SHORT).show();
                                    List<HomeResult.Data.Datas> datas = viewModel.getCacheData();
                                    viewModel.getDisplayData().setValue(datas);
                                }
                            }

                            @Override
                            public void onFailure(@NonNull Call<HomeResult> call, @NonNull Throwable t) {
                                List<HomeResult.Data.Datas> datas = viewModel.getCacheData();
                                viewModel.getDisplayData().setValue(datas);
                            }
                        });
            }
        }
    }

    @Override
    protected LifecycleOwner getLifeCycleOwner() {
        return this;
    }

    @Override
    protected int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    protected HomeViewModel createViewModel() {
        return new ViewModelProvider(this).get(HomeViewModel.class);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_home;
    }
}
