package com.wuyou.merchant.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gs.buluo.common.widget.recyclerHelper.BaseHolder;
import com.gs.buluo.common.widget.recyclerHelper.BaseQuickAdapter;
import com.wuyou.merchant.R;
import com.wuyou.merchant.bean.entity.ServiceEntity;
import com.wuyou.merchant.bean.entity.ServicesEntity;
import com.wuyou.merchant.util.CommonUtil;
import com.wuyou.merchant.util.glide.GlideUtils;

import java.util.List;


/**
 * Created by solang on 2018/2/5.
 */

public class OrderDetailServiceAdapter extends BaseQuickAdapter<ServicesEntity, BaseHolder> {

    public OrderDetailServiceAdapter(int layoutResId, @Nullable List<ServicesEntity> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseHolder helper, ServicesEntity item) {
        TextView tag = helper.getView(R.id.tv_tag1);
        if (item.stage.equals("1")) {
            tag.setVisibility(View.GONE);
        } else {
            tag.setVisibility(View.VISIBLE);
        }

        ImageView imageView = helper.getView(R.id.iv_service_confirm);
        GlideUtils.loadRoundCornerImage(mContext, item.image, imageView);
        helper.setText(R.id.tv_name2, item.service_name).setText(R.id.tv_service_confirm_num, "x " + item.number)
                .setText(R.id.tv_service_confirm_price, "¥"+CommonUtil.formatPrice(item.price));
        if (item.has_specification.equals("1")) {
            helper.setText(R.id.tv_service_confirm_sub_name, item.specification.name)
                    .setText(R.id.tv_service_confirm_price, "¥"+CommonUtil.formatPrice(item.specification.price));
        }
    }
}
