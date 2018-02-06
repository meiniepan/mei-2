package com.wuyou.merchant.mvp.order;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.wuyou.merchant.R;
import com.wuyou.merchant.adapter.OrderBeforeRvAdapter;
import com.wuyou.merchant.view.fragment.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by solang on 2018/1/31.
 */

public class OrderAfterFragment extends BaseFragment {

    @BindView(R.id.rv_orders)
    RecyclerView recyclerView;
    List<Integer> data = new ArrayList();

    @Override
    protected int getContentLayout() {
        return R.layout.fragment_order_before;
    }

    @Override
    protected void bindView(Bundle savedInstanceState) {
        data.add(1);
        data.add(2);
        data.add(3);
        OrderBeforeRvAdapter adapter = new OrderBeforeRvAdapter(getActivity(),R.layout.item_order_ing, data);
        adapter.setOnItemClickListener((adapter1, view, position) -> {
            Intent intent = new Intent(getActivity(),OrderDetailActivity.class);
            startActivity(intent);
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void showError(String message, int res) {

    }
}
