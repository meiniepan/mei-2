package com.wuyou.merchant.adapter;

import com.wuyou.merchant.R;
import com.wuyou.merchant.view.widget.recyclerHelper.BaseHolder;
import com.wuyou.merchant.view.widget.recyclerHelper.BaseQuickAdapter;

import java.util.List;

/**
 * Created by DELL on 2018/3/30.
 */

public class FundTagAdapter extends BaseQuickAdapter<String, BaseHolder> {
    public FundTagAdapter(int res, List<String> list) {
        super(res, list);
    }

    @Override
    protected void convert(BaseHolder helper, String item) {
        helper.setText(R.id.tv_fund_tag,item);
    }
}
