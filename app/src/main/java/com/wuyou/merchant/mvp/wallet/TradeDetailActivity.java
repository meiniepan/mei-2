package com.wuyou.merchant.mvp.wallet;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.wuyou.merchant.Constant;
import com.wuyou.merchant.R;
import com.wuyou.merchant.adapter.TradeDetailAdapter;
import com.wuyou.merchant.bean.entity.TradeItemEntity;
import com.wuyou.merchant.util.CommonUtil;
import com.wuyou.merchant.view.activity.BaseActivity;

import butterknife.BindView;

/**
 * Created by solang on 2018/2/6.
 */

public class TradeDetailActivity extends BaseActivity {

    @BindView(R.id.trade_detail_serve_name)
    TextView tradeDetailServeName;
    @BindView(R.id.trade_detail_amount)
    TextView tradeDetailAmount;
    @BindView(R.id.trade_detail_order_number)
    TextView tradeDetailOrderNumber;
    private String id;

    @BindView(R.id.trade_detail_list)
    RecyclerView recyclerView;
    private TradeDetailAdapter adapter;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_trade_detail;
    }

    @Override
    protected void bindView(Bundle savedInstanceState) {
        TradeItemEntity entity = getIntent().getParcelableExtra(Constant.TRANSACTION_ENTITY);
        setData(entity);
        adapter = new TradeDetailAdapter(R.layout.item_trade_detail);
        recyclerView.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setAutoMeasureEnabled(true);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    public void setData(TradeItemEntity data) {
        tradeDetailServeName.setText(data.service_name);
        tradeDetailOrderNumber.setText(data.order_number);
        tradeDetailAmount.setText(CommonUtil.formatPrice(data.amount));
        adapter.addData(data.transactions);
    }
}
