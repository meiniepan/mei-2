package com.wuyou.merchant.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.wuyou.merchant.R;
import com.wuyou.merchant.bean.entity.ContractMerchantEntity;
import com.wuyou.merchant.util.glide.GlideUtils;
import com.wuyou.merchant.view.widget.recyclerHelper.BaseHolder;
import com.wuyou.merchant.view.widget.recyclerHelper.BaseQuickAdapter;

import java.util.List;

/**
 * Created by DELL on 2018/3/30.
 */

public class CircleHeadAdapter extends BaseQuickAdapter<ContractMerchantEntity, BaseHolder> {
    public CircleHeadAdapter(int res, List<ContractMerchantEntity> list) {
        super(res, list);
    }

    @Override
    protected void convert(BaseHolder helper, ContractMerchantEntity item) {
        ImageView head = helper.getView(R.id.merchant_head);
        GlideUtils.loadImage(mContext, item.logo, head, true);
    }
}
