package com.wuyou.merchant.adapter;

import android.app.Activity;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.wuyou.merchant.R;
import com.wuyou.merchant.bean.entity.ContractEntity;
import com.wuyou.merchant.bean.entity.WorkerEntity;
import com.wuyou.merchant.util.CommonUtil;
import com.wuyou.merchant.view.widget.recyclerHelper.BaseHolder;
import com.wuyou.merchant.view.widget.recyclerHelper.BaseQuickAdapter;

import java.util.List;

/**
 * Created by solang on 2018/2/5.
 */

public class CreatedContractListRvAdapter extends BaseQuickAdapter<ContractEntity, BaseHolder> {

    public CreatedContractListRvAdapter(int layoutResId, @Nullable List<ContractEntity> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseHolder helper, ContractEntity item) {
//        helper.setText(R.id.tv_name, item.name)
//                .setText(R.id.tv_code, item.name)
//                .setText(R.id.tv_start_time, item.name)
//                .setText(R.id.tv_end_time, item.name)
//                .setText(R.id.tv_merchant_num, item.name)
//                .setText(R.id.tv_status, item.name);
    }
}
