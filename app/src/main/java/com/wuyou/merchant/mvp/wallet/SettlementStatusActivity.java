package com.wuyou.merchant.mvp.wallet;

import android.os.Bundle;

import com.gs.buluo.common.widget.recyclerHelper.RefreshRecyclerView;
import com.wuyou.merchant.Constant;
import com.wuyou.merchant.R;
import com.wuyou.merchant.adapter.SettlementStatusAdapter;
import com.wuyou.merchant.bean.entity.OrderInfoEntity;
import com.wuyou.merchant.view.activity.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Solang on 2018/12/27.
 */

public class SettlementStatusActivity extends BaseActivity {
    boolean isUnFinish;
    @BindView(R.id.rv_settlement_status)
    RefreshRecyclerView recyclerView;
    SettlementStatusAdapter adapter;
    List<OrderInfoEntity> data = new ArrayList();

    @Override
    protected int getContentLayout() {
        return R.layout.activity_settlement_status;
    }

    @Override
    protected void bindView(Bundle savedInstanceState) {
        isUnFinish = getIntent().getBooleanExtra(Constant.UN_FINISH, false);
        if (isUnFinish) {
            setTitleText("待完结");
        } else {
            setTitleText("待结算");
        }
        adapter = new SettlementStatusAdapter(R.layout.item_settlement_status, data);
        recyclerView.setAdapter(adapter);
        fetchDatas();
    }

    private void fetchDatas() {
        data.add(new OrderInfoEntity());
        data.add(new OrderInfoEntity());
        data.add(new OrderInfoEntity());
    }
}
