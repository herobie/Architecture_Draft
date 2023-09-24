package com.example.home.ui.adapter;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.home.R;
import com.example.home.base.BaseLoadMoreAdapter;
import com.example.home.databinding.ItemGoodsBinding;
import com.example.home.model.api.EasyHttp;
import com.example.home.model.bean.HomeResult;
import com.example.home.viewModel.HomeViewModel;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@RequiresApi(api = Build.VERSION_CODES.M)
public class HomeAdapter extends BaseLoadMoreAdapter<ItemGoodsBinding> {
    private final HomeViewModel viewModel;
    private final Context context;
    private List<HomeResult.Data.Datas> datas;

    public HomeAdapter(Context context, HomeViewModel viewModel) {
        this.viewModel = viewModel;
        this.context = context;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        if (holder.getItemViewType() == TYPE_ITEM){
            binding.itemGoodsTitle.setText(datas.get(position).getTitle());
            binding.itemGoodsPrice.setText(datas.get(position).getAuthor());
            Glide.with(context)
                    .load(datas.get(position).getEnvelopePic())
                    .into(binding.itemGoodsPreview);
        }
    }

    public void setDatas(List<HomeResult.Data.Datas> datas) {
        if (this.datas != null){//如果已经有数据了，则把数据添加到集合的末尾
            int insertPosition = this.datas.size();
            this.datas.addAll(this.datas.size(), datas);
            notifyItemInserted(insertPosition);
        }else {//没有数据就直接传入数据集合
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
                    public void onResponse(@NonNull Call<HomeResult> call, @NonNull Response<HomeResult> response) {
                        Log.d("MainActivity", "currentPage:" + page);
                        HomeResult homeResult = Objects.requireNonNull(response.body());
                        if (EasyHttp.isLoadingSuccess(homeResult.getErrorCode())){//检查请求数据是否正常
                            List<HomeResult.Data.Datas> datas
                                    = homeResult.getProjectData().getDatas();
                            viewModel.setCurrentPage(page);//加载成功就把加载成功的页码保存为当前页码，否则不保存
                            setDatas(datas);
                        }else {
                            Toast.makeText(context, homeResult.getErrorMsg(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<HomeResult> call, @NonNull Throwable t) {
                        Toast.makeText(context, "网络异常", Toast.LENGTH_SHORT).show();
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
