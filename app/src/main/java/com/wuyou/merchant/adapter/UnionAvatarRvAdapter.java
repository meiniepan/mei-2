package com.wuyou.merchant.adapter;

import android.app.Activity;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.wuyou.merchant.R;
import com.wuyou.merchant.bean.entity.ContractMerchantEntity;
import com.wuyou.merchant.util.CommonUtil;
import com.wuyou.merchant.view.widget.recyclerHelper.BaseHolder;
import com.wuyou.merchant.view.widget.recyclerHelper.BaseQuickAdapter;

import java.util.List;

/**
 * Created by solang on 2018/2/5.
 */

public class UnionAvatarRvAdapter extends BaseQuickAdapter<ContractMerchantEntity, BaseHolder> {
    private Activity activity;

    public UnionAvatarRvAdapter(Activity activity, int layoutResId, @Nullable List<ContractMerchantEntity> data) {
        super(layoutResId, data);
        this.activity = activity;
    }

    @Override
    protected void convert(BaseHolder helper, ContractMerchantEntity item) {
        ImageView imageView = helper.getView(R.id.avatar);
        CommonUtil.GlideCircleLoad(activity,item.logo,imageView);
    }
}
