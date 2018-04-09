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

public class ContractTradeDetailActivity extends BaseActivity {

    @BindView(R.id.contract_trade_detail_number)
    TextView contractTradeDetailNumber;
    @BindView(R.id.contract_trade_detail_name)
    TextView contractTradeDetailName;
    @BindView(R.id.contract_trade_detail_id)
    TextView contractTradeDetailId;
    @BindView(R.id.contract_trade_detail_project)
    TextView contractTradeDetailProject;
    @BindView(R.id.contract_trade_detail_price)
    TextView contractTradeDetailPrice;
    @BindView(R.id.contract_trade_detail_count)
    TextView contractTradeDetailCount;
    @BindView(R.id.contract_trade_detail_amount)
    TextView contractTradeDetailAmount;
    private String id;

    @BindView(R.id.contract_trade_detail_list)
    RecyclerView recyclerView;
    private TradeDetailAdapter adapter;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_contract_trade_detail;
    }

    @Override
    protected void bindView(Bundle savedInstanceState) {
        TradeItemEntity entity = getIntent().getParcelableExtra(Constant.TRANSACTION_ENTITY);
        adapter = new TradeDetailAdapter(2,R.layout.item_trade_detail);
        setData(entity);
        recyclerView.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setAutoMeasureEnabled(true);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    private void setData(TradeItemEntity data) {
        contractTradeDetailName.setText(data.contract_name);
        contractTradeDetailProject.setText(data.service_name);
        contractTradeDetailCount.setText(data.number + "");
        contractTradeDetailNumber.setText(data.order_number);
        contractTradeDetailId.setText(data.contract_number);
        contractTradeDetailPrice.setText(CommonUtil.formatPrice(data.price));
        contractTradeDetailAmount.setText(CommonUtil.formatPrice(data.total_amount));
        adapter.setNewData(data.transactions);
    }
}
