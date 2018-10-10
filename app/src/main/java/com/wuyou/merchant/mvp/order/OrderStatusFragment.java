package com.wuyou.merchant.mvp.order;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.gs.buluo.common.utils.ToastUtils;
import com.gs.buluo.common.widget.recyclerHelper.BaseQuickAdapter;
import com.gs.buluo.common.widget.recyclerHelper.OnRefreshListener;
import com.gs.buluo.common.widget.recyclerHelper.RefreshRecyclerView;
import com.tendcloud.tenddata.TCAgent;
import com.wuyou.merchant.CarefreeApplication;
import com.wuyou.merchant.CarefreeDaoSession;
import com.wuyou.merchant.Constant;
import com.wuyou.merchant.R;
import com.wuyou.merchant.adapter.OrderStatusRvAdapter;
import com.wuyou.merchant.bean.entity.OrderBeanDetail;
import com.wuyou.merchant.bean.entity.OrderInfoEntity;
import com.wuyou.merchant.bean.entity.OrderInfoListEntity;
import com.wuyou.merchant.util.MyRecyclerViewScrollListener;
import com.wuyou.merchant.view.fragment.BaseFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        recyclerView.getRecyclerView().setErrorAction(v -> {
            recyclerView.showProgressView();
            fetchDatas();
        });
        adapter = new OrderStatusRvAdapter(getActivity(), R.layout.item_order_status, data);
        adapter.setOnItemClickListener((adapter1, view, position) -> {
            Intent intent = new Intent(getActivity(), OrderDetailActivity.class);
            intent.putExtra(Constant.ORDER_ID, adapter.getItem(position).order_id);
//            intent.putExtra(Constant.DIVIDE_ORDER_FROM,1);
            startActivity(intent);
            //代码事件
            Map kv = new HashMap();
            kv.put("动作类型", "订单详情");
            kv.put("订单ID",adapter.getItem(position).order_id );
            TCAgent.onEvent(getContext(), "点击首页推荐位", "第3推广位", kv);
        });
        recyclerView.setAdapter(adapter);
//        final MyRecyclerViewScrollListener scrollListener = new MyRecyclerViewScrollListener(getActivity(), toTop);
//        recyclerView.getRecyclerView().addOnScrollListener(scrollListener);
        recyclerView.getRecyclerView().setLayoutManager(new LinearLayoutManager(getContext()));
        adapter.setOnLoadMoreListener(() -> mPresenter.loadMore(CarefreeApplication.getInstance().getUserInfo().getUid(), orderState), recyclerView.getRecyclerView());
        recyclerView.setRefreshAction(new OnRefreshListener() {
            @Override
            public void onAction() {
//                scrollListener.setRefresh();
                OrderStatusFragment.this.fetchDatas();
            }
        });
//        toTop.setOnClickListener(v -> recyclerView.getRecyclerView().smoothScrollToPosition(0));
    }

    @Override
    public void showError(String message, int res) {
        recyclerView.getRefreshLayout().setEnabled(false);
        recyclerView.setRefreshFinished();
        recyclerView.getRecyclerView().showErrorView(getString(R.string.connect_fail));
    }

    @Override
    public void getSuccess(OrderInfoListEntity data) {
        recyclerView.getRefreshLayout().setEnabled(true);
        recyclerView.setRefreshFinished();
        adapter.setNewData(data.list);
        recyclerView.getRecyclerView().showContentView();
        if (data.has_more.equals("0")) {
            adapter.loadMoreEnd(true);
        }
        if (adapter.getData().size() == 0) {
            recyclerView.getRecyclerView().showEmptyView(getString(R.string.order_empty));
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
        orderState = getArguments().getInt("h")+"";
        mPresenter.getOrders(CarefreeDaoSession.getInstance().getUserInfo().getUid(), orderState);
    }

}
