package com.wuyou.merchant.adapter;

import android.support.annotation.Nullable;

import com.wuyou.merchant.R;
import com.wuyou.merchant.bean.entity.FundRateEntity;
import com.wuyou.merchant.view.widget.recyclerHelper.BaseHolder;
import com.wuyou.merchant.view.widget.recyclerHelper.BaseQuickAdapter;

import java.util.List;

/**
 * Created by solang on 2018/3/23.
 */

public class FundRateListRvAdapter extends BaseQuickAdapter<FundRateEntity, BaseHolder> {

    public FundRateListRvAdapter(int layoutResId, @Nullable List<FundRateEntity> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseHolder helper, FundRateEntity item) {
        helper.setText(R.id.tv_id, item.stage_number+"期")
                .setText(R.id.tv_name, item.rate+"%/期");
    }
}
