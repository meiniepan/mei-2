package com.wuyou.merchant.mvp.store;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import com.gs.buluo.common.widget.recyclerHelper.RefreshRecyclerView;
import com.wuyou.merchant.R;
import com.wuyou.merchant.adapter.OrderStatusRvAdapter;
import com.wuyou.merchant.bean.entity.OrderInfoEntity;
import com.wuyou.merchant.view.activity.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Solang on 2018/12/26.
 */

public class MessageActivity extends BaseActivity {
    @BindView(R.id.rv_message)
    RefreshRecyclerView recyclerView;
    OrderStatusRvAdapter adapter;
    List<OrderInfoEntity> data = new ArrayList();
    @Override
    protected int getContentLayout() {
        return R.layout.activity_message;
    }

    @Override
    protected void bindView(Bundle savedInstanceState) {
        setTitleText("消息中心");
        recyclerView.getRecyclerView().setErrorAction(v -> {
            recyclerView.showProgressView();
            fetchDatas();
        });
        adapter = new OrderStatusRvAdapter(this, R.layout.item_order_status, data);
        recyclerView.setAdapter(adapter);
        recyclerView.getRecyclerView().setLayoutManager(new LinearLayoutManager(this));
    }

    private void fetchDatas() {
    }
}
