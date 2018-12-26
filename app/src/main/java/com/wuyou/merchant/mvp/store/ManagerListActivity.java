package com.wuyou.merchant.mvp.store;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.gs.buluo.common.widget.StatusLayout;
import com.wuyou.merchant.Constant;
import com.wuyou.merchant.R;
import com.wuyou.merchant.adapter.WorkersRvAdapter;
import com.wuyou.merchant.bean.entity.WorkerEntity;
import com.wuyou.merchant.view.activity.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by DELL on 2018/12/26.
 */

public class ManagerListActivity extends BaseActivity {
    @BindView(R.id.sl_list_layout)
    StatusLayout statusLayout;
    @BindView(R.id.rv_orders)
    RecyclerView recyclerView;
    List<WorkerEntity> data = new ArrayList();
    WorkersRvAdapter adapter;
    String orderId;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_worker_list;
    }

    @Override
    protected void bindView(Bundle savedInstanceState) {
        setTitleText("管理员列表");
        statusLayout.setErrorAction(v -> getData());
        orderId = getIntent().getStringExtra(Constant.ORDER_ID);
        adapter = new WorkersRvAdapter(this, R.layout.item_chose_artisan, data);
        recyclerView.setLayoutManager(new LinearLayoutManager(getCtx()));
        recyclerView.setAdapter(adapter);
        getData();
    }

    private void getData() {
    }
}
