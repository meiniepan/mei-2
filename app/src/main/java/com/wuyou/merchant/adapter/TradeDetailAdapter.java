package com.wuyou.merchant.adapter;

import com.gs.buluo.common.utils.TribeDateUtils;
import com.wuyou.merchant.R;
import com.wuyou.merchant.bean.entity.TradeEntity;
import com.wuyou.merchant.util.CommonUtil;
import com.wuyou.merchant.view.widget.recyclerHelper.BaseHolder;
import com.wuyou.merchant.view.widget.recyclerHelper.BaseQuickAdapter;

import java.util.Date;

/**
 * Created by DELL on 2018/4/4.
 */

public class TradeDetailAdapter extends BaseQuickAdapter<TradeEntity, BaseHolder> {
    public TradeDetailAdapter(int res) {
        super(res);
    }

    @Override
    protected void convert(BaseHolder helper, TradeEntity item) {
        helper.setText(R.id.tv_trade_hash, item.transaction_id)
                .setText(R.id.tv_status, item.confirmations)
                .setText(R.id.tv_trade_time, TribeDateUtils.dateFormat(new Date(item.time)))
                .setText(R.id.tv_confirm_time, TribeDateUtils.dateFormat(new Date(item.timereceived)))
                .setText(R.id.tv_trade_sum, CommonUtil.formatPrice(item.amount))
                .setText(R.id.tv_trade_fee, CommonUtil.formatPrice(item.fee));
    }
}
