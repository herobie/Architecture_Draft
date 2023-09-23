package com.example.home.base;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.DialogFragment;

public abstract class BaseDialogFragment<T extends ViewDataBinding> extends DialogFragment {
    protected T binding;
    protected Handler handler = new Handler(Looper.getMainLooper());//内存泄漏啥的修一下
    protected DialogInterface.OnDismissListener onDismissListener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, getLayoutRes(), container, false);
        return binding == null?
                super.onCreateView(inflater, container, savedInstanceState) : binding.getRoot();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        if (setCustomDialogStyle() == null){
            Dialog dialog = super.onCreateDialog(savedInstanceState);
            LayoutInflater inflater = requireActivity().getLayoutInflater();
            if (binding != null){
                dialog.setContentView(binding.getRoot());
            }else {
                dialog.setContentView(inflater.inflate(getLayoutRes(), null));
            }
            return dialog;
        }else {
            return setCustomDialogStyle();
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initParams();
        setDismissCountdown();
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        if (onDismissListener != null){
            onDismissListener.onDismiss(dialog);//退出事件
        }
        handler = null;
    }

    abstract protected void setDismissCountdown();//设置自动消失读秒时间

    abstract protected Dialog setCustomDialogStyle();//自定义dialog的样式，如设置背景透明度之类的

    abstract protected void initParams();//加载dialog的一些参数，例如文本，图片等

    abstract protected int getLayoutRes();

    public interface onDismissListener{
        void onDismiss();
    }

    public void setOnDismissListener(DialogInterface.OnDismissListener onDismissListener) {
        this.onDismissListener = onDismissListener;
    }
}
