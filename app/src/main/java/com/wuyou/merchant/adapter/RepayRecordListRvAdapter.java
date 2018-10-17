package com.wuyou.merchant.adapter;

import android.support.annotation.Nullable;

import com.gs.buluo.common.utils.TribeDateUtils;
import com.wuyou.merchant.R;
import com.wuyou.merchant.bean.entity.RepayRecordEntity;
import com.gs.buluo.common.widget.recyclerHelper.BaseHolder;
import com.gs.buluo.common.widget.recyclerHelper.BaseQuickAdapter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by solang on 2018/2/5.
 */

public class RepayRecordListRvAdapter extends BaseQuickAdapter<RepayRecordEntity, BaseHolder> {

    public RepayRecordListRvAdapter(int layoutResId, @Nullable List<RepayRecordEntity> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseHolder helper, RepayRecordEntity item) {
        String stage = new SimpleDateFormat("yyyy年M月").format(new Date(Long.parseLong(item.stage)*1000));
        String s = TribeDateUtils.dateFormat(new Date(Long.parseLong(item.real_repayment_at)*1000));
        helper.setText(R.id.tv_stage, stage)
                .setText(R.id.tv_repay_num, item.amount+"元")
                .setText(R.id.tv_time, s);
    }
}
