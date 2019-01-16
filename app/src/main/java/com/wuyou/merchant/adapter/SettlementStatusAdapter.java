package com.wuyou.merchant.adapter;

import android.support.annotation.Nullable;

import com.gs.buluo.common.widget.recyclerHelper.BaseHolder;
import com.gs.buluo.common.widget.recyclerHelper.BaseQuickAdapter;
import com.wuyou.merchant.bean.entity.OrderInfoEntity;

import java.util.List;

/**
 * Created by solang on 2018/2/5.
 */

public class SettlementStatusAdapter extends BaseQuickAdapter<OrderInfoEntity, BaseHolder> {

    public SettlementStatusAdapter(int layoutResId, @Nullable List<OrderInfoEntity> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseHolder helper, OrderInfoEntity item) {

    }


}
