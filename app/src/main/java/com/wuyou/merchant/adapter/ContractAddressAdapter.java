package com.wuyou.merchant.adapter;

import android.app.Activity;
import android.support.annotation.Nullable;

import com.wuyou.merchant.R;
import com.gs.buluo.common.widget.recyclerHelper.BaseHolder;
import com.gs.buluo.common.widget.recyclerHelper.BaseQuickAdapter;

import java.util.List;

/**
 * Created by solang on 2018/2/5.
 */

public class ContractAddressAdapter extends BaseQuickAdapter<String, BaseHolder> {
    private Activity activity;

    public ContractAddressAdapter(Activity activity, int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
        this.activity = activity;
    }

    @Override
    protected void convert(BaseHolder helper, String item) {

        helper.setText(R.id.tv_address, item);
    }
}
