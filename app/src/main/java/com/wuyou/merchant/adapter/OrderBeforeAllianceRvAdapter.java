package com.wuyou.merchant.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.gs.buluo.common.utils.ToastUtils;
import com.gs.buluo.common.utils.TribeDateUtils;
import com.wuyou.merchant.Constant;
import com.wuyou.merchant.R;
import com.wuyou.merchant.bean.entity.OrderInfoEntity;
import com.wuyou.merchant.mvp.order.ChoseServerActivity;
import com.wuyou.merchant.mvp.order.ChoseServerAllianceActivity;
import com.wuyou.merchant.view.widget.recyclerHelper.BaseHolder;
import com.wuyou.merchant.view.widget.recyclerHelper.BaseQuickAdapter;

import java.util.Date;
import java.util.List;

/**
 * Created by solang on 2018/2/5.
 */

public class OrderBeforeAllianceRvAdapter extends BaseQuickAdapter<OrderInfoEntity, BaseHolder> {
    private Activity activity;

    public OrderBeforeAllianceRvAdapter(Activity activity, int layoutResId, @Nullable List<OrderInfoEntity> data) {
        super(layoutResId, data);
        this.activity = activity;
    }

    @Override
    protected void convert(BaseHolder helper, OrderInfoEntity item) {
        String create_time = TribeDateUtils.dateFormat(new Date(item.created_at*1000));
        helper.setText(R.id.tv_create_time, create_time)
//                .setText(R.id.tv_category, item.category)
//                .setText(R.id.tv_address, item.address)
                .setText(R.id.tv_sum, item.price);
        View ll_receiver = helper.getView(R.id.ll_receiver);
        Button dispatch = helper.getView(R.id.btn_divide_bill);

        if (item.status.equals("0")) {
            ll_receiver.setVisibility(View.GONE);
            dispatch.setText("分单");
            dispatch.setOnClickListener(view -> {
                Intent intent = new Intent(activity, ChoseServerAllianceActivity.class);
                intent.putExtra(Constant.ORDER_ID,item.order_id);
                activity.startActivity(intent);
            });
        } else {
            ll_receiver.setVisibility(View.VISIBLE);
//            helper.setText(R.id.tv_receiver,item.receiver);
            dispatch.setText("发信息");
            dispatch.setOnClickListener(view -> {
                ToastUtils.ToastMessage(activity,"此功能暂未开通！");
            });
        }
    }
}
