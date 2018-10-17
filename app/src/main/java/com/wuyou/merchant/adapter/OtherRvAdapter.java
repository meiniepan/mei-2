package com.wuyou.merchant.adapter;

import android.app.Activity;
import android.support.annotation.Nullable;

import com.gs.buluo.common.widget.recyclerHelper.BaseHolder;
import com.gs.buluo.common.widget.recyclerHelper.BaseQuickAdapter;

import java.util.List;

/**
 * Created by solang on 2018/2/5.
 */

public class OtherRvAdapter extends BaseQuickAdapter<Integer, BaseHolder> {
    private Activity activity;
    public OtherRvAdapter(Activity activity, int layoutResId, @Nullable List<Integer> data) {
        super(layoutResId, data);
        this.activity = activity;
    }

    @Override
    protected void convert(BaseHolder helper, Integer item) {

    }
}
