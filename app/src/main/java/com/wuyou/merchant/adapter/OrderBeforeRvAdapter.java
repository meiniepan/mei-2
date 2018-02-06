package com.wuyou.merchant.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.wuyou.merchant.R;
import com.wuyou.merchant.mvp.order.ChoseServerActivity;
import com.wuyou.merchant.view.widget.recyclerHelper.BaseHolder;
import com.wuyou.merchant.view.widget.recyclerHelper.BaseQuickAdapter;

import java.util.List;

/**
 * Created by solang on 2018/2/5.
 */

public class OrderBeforeRvAdapter extends BaseQuickAdapter<Integer, BaseHolder> {
    private Activity activity;
    public OrderBeforeRvAdapter(Activity activity,int layoutResId, @Nullable List<Integer> data) {
        super(layoutResId, data);
        this.activity = activity;
    }

    @Override
    protected void convert(BaseHolder helper, Integer item) {
        helper.getView(R.id.btn_divide_bill).setOnClickListener(view -> {
            Intent intent = new Intent(activity, ChoseServerActivity.class);
            activity.startActivity(intent);
        });
    }
}
