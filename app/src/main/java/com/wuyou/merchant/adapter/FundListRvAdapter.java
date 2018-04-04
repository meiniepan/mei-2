package com.wuyou.merchant.adapter;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

import com.wuyou.merchant.R;
import com.wuyou.merchant.bean.entity.FundEntity;
import com.wuyou.merchant.util.AutoLineFeedLayoutManager;
import com.wuyou.merchant.view.widget.recyclerHelper.BaseHolder;
import com.wuyou.merchant.view.widget.recyclerHelper.BaseQuickAdapter;

import java.util.List;

/**
 * Created by solang on 2018/2/5.
 */

public class FundListRvAdapter extends BaseQuickAdapter<FundEntity, BaseHolder> {

    public FundListRvAdapter(int layoutResId, @Nullable List<FundEntity> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseHolder helper, FundEntity item) {
        RecyclerView recyclerView = helper.getView(R.id.rv_tags_fund);
        recyclerView.setLayoutManager(new AutoLineFeedLayoutManager(true));
        FundTagAdapter adapter = new FundTagAdapter(R.layout.item_fund_tag, item.tags);
        recyclerView.setAdapter(adapter);
        String[] status = {"可申请","审核中","审核通过","审核失败"};
        int[] colors = {R.drawable.bac_text_blue_shape,R.drawable.bac_text_green_shape,R.drawable.bac_text_white_shape,R.drawable.bac_text_red_shape};
        int[] textColors = {R.color.white,R.color.white,R.color.common_gray,R.color.white};
        helper.setText(R.id.tv_name, item.fund_name)
                .setText(R.id.tv_rate_fund, item.rate)
                .setText(R.id.tv_status_fund, status[item.status])
                .setBackgroundRes(R.id.tv_status_fund,colors[item.status])
                .setTextColor(R.id.tv_status_fund,mContext.getResources().getColor(textColors[item.status]))
                .setText(R.id.tv_content, item.desc);
    }
}
