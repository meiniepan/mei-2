package com.wuyou.merchant.adapter;

import android.support.annotation.Nullable;

import com.wuyou.merchant.view.widget.recyclerHelper.BaseHolder;
import com.wuyou.merchant.view.widget.recyclerHelper.BaseQuickAdapter;

import java.util.List;

/**
 * Created by solang on 2018/2/5.
 */

public class ChoseArtisanRvAdapter extends BaseQuickAdapter<Integer,BaseHolder>{
    public ChoseArtisanRvAdapter(int layoutResId, @Nullable List<Integer> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseHolder helper, Integer item) {

    }
}
