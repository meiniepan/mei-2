package com.wuyou.merchant.adapter;

import android.support.annotation.Nullable;

import com.gs.buluo.common.utils.TribeDateUtils;
import com.wuyou.merchant.R;
import com.wuyou.merchant.bean.entity.TradeEntity;
import com.wuyou.merchant.view.widget.recyclerHelper.BaseHolder;
import com.wuyou.merchant.view.widget.recyclerHelper.BaseQuickAdapter;

import java.util.Date;
import java.util.List;

/**
 * Created by solang on 2018/2/5.
 */

public class TradeListRvAdapter extends BaseQuickAdapter<TradeEntity, BaseHolder> {

    public TradeListRvAdapter(int layoutResId, @Nullable List<TradeEntity> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseHolder helper, TradeEntity item) {
        String time = TribeDateUtils.SDF7.format(new Date(Long.parseLong(item.time) * 1000));
        if (item.category.equals("send")) {
            helper.setText(R.id.tv_action, "收款方")
                    .setText(R.id.tv_action_person, item.to);
        } else if (item.category.equals("receive")) {
            helper.setText(R.id.tv_action, "付款方")
                    .setText(R.id.tv_action_person, item.from);
        }else  {
            helper.setText(R.id.tv_action, "奖励");
        }
        if (Integer.parseInt(item.confirmations)>0) {
            helper.setText(R.id.tv_confirm, "已确认");
        }else {
            helper.setText(R.id.tv_confirm, "未确认");
        }
        helper.setText(R.id.tv_num, item.amount)
                .setText(R.id.tv_time, time);
    }
}
