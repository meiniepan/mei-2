package com.wuyou.merchant.adapter;

import android.app.Activity;
import android.support.annotation.Nullable;

import com.gs.buluo.common.utils.TribeDateUtils;
import com.wuyou.merchant.R;
import com.wuyou.merchant.bean.entity.OrderInfoEntity;
import com.wuyou.merchant.view.widget.recyclerHelper.BaseHolder;
import com.wuyou.merchant.view.widget.recyclerHelper.BaseQuickAdapter;

import java.util.Date;
import java.util.List;

/**
 * Created by solang on 2018/2/5.
 */

public class OrderIngRvAdapter extends BaseQuickAdapter<OrderInfoEntity, BaseHolder> {
    private Activity activity;

    public OrderIngRvAdapter(Activity activity, int layoutResId, @Nullable List<OrderInfoEntity> data) {
        super(layoutResId, data);
        this.activity = activity;
    }

    @Override
    protected void convert(BaseHolder helper, OrderInfoEntity item) {
        String[] s = {"待分单", "未开始", "进行中", "待评价", "已完成"};
        int i = Integer.parseInt(item.status) - 1;
        String create_time = TribeDateUtils.dateFormat(new Date(item.created_at * 1000));
        helper.setText(R.id.tv_create_time, create_time)
//                .setText(R.id.tv_category, item.category)
                .setText(R.id.tv_address, item.address.city + item.address.district + item.address.area + item.address.address)
                .setText(R.id.tv_status, s[i])
                .setText(R.id.tv_sum, item.price);
    }
}
