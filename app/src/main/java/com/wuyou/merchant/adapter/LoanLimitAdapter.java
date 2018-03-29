package com.wuyou.merchant.adapter;

import com.wuyou.merchant.R;
import com.wuyou.merchant.bean.entity.PrepareSignEntity;
import com.wuyou.merchant.view.widget.recyclerHelper.BaseHolder;
import com.wuyou.merchant.view.widget.recyclerHelper.BaseQuickAdapter;

import java.util.List;

/**
 * Created by DELL on 2018/3/29.
 */

public class LoanLimitAdapter extends BaseQuickAdapter<PrepareSignEntity.RatesBean, BaseHolder> {
    public LoanLimitAdapter(int res, List<PrepareSignEntity.RatesBean> loanEntities) {
        super(res, loanEntities);
    }

    @Override
    protected void convert(BaseHolder helper, PrepareSignEntity.RatesBean item) {
        helper.setText(R.id.item_loan_time, item.stage)
                .setText(R.id.item_loan_rate, item.rate);
    }
}
