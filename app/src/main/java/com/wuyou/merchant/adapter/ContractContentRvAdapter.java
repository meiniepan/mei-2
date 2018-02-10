package com.wuyou.merchant.adapter;

import android.app.Activity;
import android.support.annotation.Nullable;

import com.gs.buluo.common.utils.TribeDateUtils;
import com.wuyou.merchant.R;
import com.wuyou.merchant.bean.entity.ContractContentEntity;
import com.wuyou.merchant.bean.entity.OrderInfoEntity;
import com.wuyou.merchant.view.widget.recyclerHelper.BaseHolder;
import com.wuyou.merchant.view.widget.recyclerHelper.BaseQuickAdapter;

import java.util.Date;
import java.util.List;

/**
 * Created by solang on 2018/2/5.
 */

public class ContractContentRvAdapter extends BaseQuickAdapter<ContractContentEntity, BaseHolder> {

    public ContractContentRvAdapter(int layoutResId, @Nullable List<ContractContentEntity> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseHolder helper, ContractContentEntity item) {
        helper.setText(R.id.title, item.title)
                .setText(R.id.content, item.content);
    }
}
