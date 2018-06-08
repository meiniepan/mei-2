package com.wuyou.merchant.mvp.order;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.gs.buluo.common.utils.ToastUtils;
import com.gs.buluo.common.widget.recyclerHelper.BaseQuickAdapter;
import com.gs.buluo.common.widget.recyclerHelper.OnRefreshListener;
import com.gs.buluo.common.widget.recyclerHelper.RefreshRecyclerView;
import com.wuyou.merchant.CarefreeApplication;
import com.wuyou.merchant.Constant;
import com.wuyou.merchant.R;
import com.wuyou.merchant.adapter.OrderStatusRvAdapter;
import com.wuyou.merchant.bean.entity.OrderBeanDetail;
import com.wuyou.merchant.bean.entity.OrderInfoEntity;
import com.wuyou.merchant.bean.entity.OrderInfoListEntity;
import com.wuyou.merchant.util.MyRecyclerViewScrollListener;
import com.wuyou.merchant.view.fragment.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by solang on 2018/1/31.
 */

public class OrderStatusFragment extends BaseFragment<OrderContract.View, OrderContract.Presenter> implements OrderContract.View {
    @BindView(R.id.rv_orders)
    RefreshRecyclerView recyclerView;
    @BindView(R.id.rl_to_top)
    View toTop;
    OrderStatusRvAdapter adapter;
    List<OrderInfoEntity> data = new ArrayList();
    private String orderState;

    @Override
    protected int getContentLayout() {
        return R.layout.fragment_order_status;
    }

    @Override
    protected OrderContract.Presenter getPresenter() {
        return new OrderPresenter();
    }

    @Override
    protected void bindView(Bundle savedInstanceState) {
        adapter = new OrderStatusRvAdapter(getActivity(), R.layout.item_order_status, data);
        adapter.setOnItemClickListener((adapter1, view, position) -> {
            Intent intent = new Intent(getActivity(), OrderDetailActivity.class);
            intent.putExtra(Constant.ORDER_ID, adapter.getItem(position).order_id);
//            intent.putExtra(Constant.DIVIDE_ORDER_FROM,1);
            startActivity(intent);
        });
        recyclerView.setAdapter(adapter);
        final MyRecyclerViewScrollListener scrollListener = new MyRecyclerViewScrollListener(getActivity(), toTop);
//        recyclerView.getRecyclerView().addOnScrollListener(scrollListener);
        recyclerView.getRecyclerView().setLayoutManager(new LinearLayoutManager(getContext()));
        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                mPresenter.loadMore(CarefreeApplication.getInstance().getUserInfo().getUid(), orderState);
            }
        }, recyclerView.getRecyclerView());
        recyclerView.setRefreshAction(new OnRefreshListener() {
            @Override
            public void onAction() {
                scrollListener.setRefresh();
                adapter.clearData();
                fetchDatas();
            }
        });
        toTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerView.getRecyclerView().smoothScrollToPosition(0);
            }
        });
    }

    @Override
    public void showError(String message, int res) {
        recyclerView.setRefreshFinished();
        if (res == 100) {
            ToastUtils.ToastMessage(mCtx, R.string.connect_fail);
        } else {
            recyclerView.getStatusLayout().showErrorView(getString(R.string.connect_fail));
        }
    }

    @Override
    public void getSuccess(OrderInfoListEntity data) {
        recyclerView.setRefreshFinished();
        adapter.setNewData(data.list);
        recyclerView.getStatusLayout().showContentView();
        if (data.has_more.equals("0")) {
            adapter.loadMoreEnd(true);
        }
        if (adapter.getData().size() == 0) {
            recyclerView.getStatusLayout().showEmptyView(getString(R.string.order_empty));
        }
    }

    @Override
    public void getMore(OrderInfoListEntity data) {
        adapter.addData(data.list);
        if (data.has_more.equals("0")) {
            adapter.loadMoreEnd(true);
        }
    }

    @Override
    public void loadMoreError(int code) {
        adapter.loadMoreFail();
    }

    @Override
    public void getOrderDetailSuccess(OrderBeanDetail bean) {

    }


    @Override
    public void loadData() {
        recyclerView.showProgressView();
        fetchDatas();
    }

    private void fetchDatas() {
//        if (orderState.equals("4"))
//            orderState = "0";
        mPresenter.getOrders(CarefreeApplication.getInstance().getUserInfo().getUid(), orderState);
    }

    public void setOrderState(int orderState) {
        this.orderState = orderState + "";
    }
}
