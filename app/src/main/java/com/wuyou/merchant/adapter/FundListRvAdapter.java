package com.wuyou.merchant.adapter;

import android.support.annotation.Nullable;

import com.wuyou.merchant.R;
import com.wuyou.merchant.bean.entity.FundEntity;
import com.wuyou.merchant.view.widget.recyclerHelper.BaseHolder;
import com.wuyou.merchant.view.widget.recyclerHelper.BaseQuickAdapter;

import java.util.List;

/**
 * Created by solang on 2018/2/5.
 */

public class FundListRvAdapter extends BaseQuickAdapter<FundEntity, BaseHolder> {

    public FundListRvAdapter(int layoutResId, @Nullable List<FundEntity> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseHolder helper, FundEntity item) {
        helper.setText(R.id.tv_name, item.fund_name)
                .setText(R.id.tv_content, item.description);
    }
}
