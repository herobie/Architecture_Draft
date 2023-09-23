package com.example.home.base;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.home.R;

@RequiresApi(api = Build.VERSION_CODES.M)
public abstract class BaseLoadMoreAdapter<T extends ViewDataBinding>
        extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    protected T binding;
    protected final int TYPE_ITEM = 1001;
    protected final int TYPE_FOOTER = 1002;

    protected final int STATE_LOADING = 0;
    protected final int STATE_FINISHED = 1;
    protected final int STATE_FAILED = 2;

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM){
            binding = DataBindingUtil
                    .inflate(LayoutInflater.from(parent.getContext()) , getItemLayoutRes() , parent , false);
            return new BaseViewHolder(binding.getRoot());
        }else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading, parent, false);
            return new FootViewHolder(view);
        }
    }

    @Override
    public int getItemViewType(int position) {
        //给最后一个item设置foot_view
        if (position + 1 == getItemCount()) {
            return TYPE_FOOTER;
        } else {
            return TYPE_ITEM;
        }
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            private boolean isSlidingUpward = false;
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
                if (newState == RecyclerView.SCROLL_STATE_IDLE){
                    int lastItemPosition = manager.findLastCompletelyVisibleItemPosition();
                    int itemCount = manager.getItemCount();
                    if(lastItemPosition == (itemCount - 1) && isSlidingUpward){
                        loadMoreAction();
                    }
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                isSlidingUpward = dy > 0;
            }
        });
    }

    abstract protected void loadMoreAction();

    abstract protected int getItemLayoutRes();

    class BaseViewHolder extends RecyclerView.ViewHolder{

        public BaseViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    class FootViewHolder extends RecyclerView.ViewHolder{

        public FootViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
