package com.wuyou.merchant.mvp.order;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.gs.buluo.common.utils.ToastUtils;
import com.wuyou.merchant.R;
import com.wuyou.merchant.adapter.OrderBeforeRvAdapter;
import com.wuyou.merchant.adapter.OtherRvAdapter;
import com.wuyou.merchant.view.fragment.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by solang on 2018/1/31.
 */

public class OrderIngFragment extends BaseFragment<OrderContract.View, OrderContract.Presenter> implements OrderContract.View {


    @BindView(R.id.rv_orders)
    RecyclerView recyclerView;
    List<Integer> data = new ArrayList();

    @Override
    protected int getContentLayout() {
        return R.layout.fragment_order_before;
    }

    @Override
    protected OrderContract.Presenter getPresenter() {
        return new OrderPresenter();
    }

    @Override
    protected void bindView(Bundle savedInstanceState) {
        data.add(1);
        data.add(2);
        data.add(3);
        OtherRvAdapter adapter = new OtherRvAdapter(getActivity(), R.layout.item_order_ing, data);
        adapter.setOnItemClickListener((adapter1, view, position) -> {
            Intent intent = new Intent(getActivity(), OrderDetailActivity.class);
            startActivity(intent);
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void showError(String message, int res) {

    }

    @Override
    public void getSuccess() {
        dismissDialog();
    }

    @Override
    public void loadData() {
        showLoadingDialog();
        mPresenter.getOrders(114, 1, 0, 1);
        Log.e("haha","2");
    }
}
