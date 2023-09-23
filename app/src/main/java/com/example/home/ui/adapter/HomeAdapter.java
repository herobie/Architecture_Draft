package com.example.home.ui.adapter;

import android.content.Context;
import android.os.Build;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.home.R;
import com.example.home.base.BaseLoadMoreAdapter;
import com.example.home.databinding.FragmentHomeBinding;
import com.example.home.databinding.ItemGoodsBinding;
import com.example.home.model.api.EasyHttp;
import com.example.home.model.bean.HomeResult;
import com.example.home.viewModel.HomeViewModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@RequiresApi(api = Build.VERSION_CODES.M)
public class HomeAdapter extends BaseLoadMoreAdapter<ItemGoodsBinding> {
    private HomeViewModel viewModel;
    private Context context;
    private List<HomeResult.Data.Datas> datas;

    public HomeAdapter(Context context, HomeViewModel viewModel) {
        this.viewModel = viewModel;
        this.context = context;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder.getItemViewType() == TYPE_ITEM){
            binding.itemGoodsTitle.setText(datas.get(position).getTitle());
        }
    }

    public void setDatas(List<HomeResult.Data.Datas> datas) {
        if (this.datas != null){
            int insertPosition = this.datas.size();
            this.datas.addAll(this.datas.size(), datas);
            notifyItemInserted(insertPosition);
        }else {
            this.datas = datas;
            notifyItemInserted(datas.size());
        }
    }

    @Override
    protected void loadMoreAction() {
        int page = viewModel.getCurrentPage() + 1;
        EasyHttp.getInstance()
                .get("https://www.wanandroid.com/project/list/")
                .getProjectResult(page, 294)
                .enqueue(new Callback<HomeResult>() {
                    @Override
                    public void onResponse(Call<HomeResult> call, Response<HomeResult> response) {
                        List<HomeResult.Data.Datas> datas = response.body().getProjectData().getDatas();
                        viewModel.setCurrentPage(page);
                        setDatas(datas);
                    }

                    @Override
                    public void onFailure(Call<HomeResult> call, Throwable t) {

                    }
                });
    }

    @Override
    protected int getItemLayoutRes() {
        return R.layout.item_goods;
    }

    @Override
    public int getItemCount() {
        return datas == null? 0:datas.size() + 1;
    }
}
