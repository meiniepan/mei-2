package com.wuyou.merchant.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.wuyou.merchant.R;
import com.wuyou.merchant.adapter.ServiceProDetailRvAdapter;
import com.wuyou.merchant.view.widget.recyclerHelper.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by solang on 2018/2/6.
 */

public class ServiceProviderDetailActivity extends BaseActivity {
    @BindView(R.id.rv_contract)
    RecyclerView recyclerView;
    List<Integer> data = new ArrayList<>();

    @Override
    protected int getContentLayout() {
        return R.layout.activity_service_provider_detail;
    }

    @Override
    protected void bindView(Bundle savedInstanceState) {
        data.add(1);
        data.add(2);
        recyclerView.setLayoutManager(new LinearLayoutManager(getCtx()));
        ServiceProDetailRvAdapter adapter = new ServiceProDetailRvAdapter(R.layout.item_service_pro_detail, data);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(getCtx(),ContractDetailActivity.class);
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(adapter);
    }
}
