package com.wuyou.merchant.mvp.circle;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.gs.buluo.common.widget.StatusLayout;
import com.wuyou.merchant.Constant;
import com.wuyou.merchant.R;
import com.wuyou.merchant.adapter.JoinedContractListRvAdapter;
import com.wuyou.merchant.bean.entity.ContractEntity;
import com.wuyou.merchant.bean.entity.ResponseListEntity;
import com.wuyou.merchant.view.fragment.BaseFragment;
import com.wuyou.merchant.view.widget.recyclerHelper.BaseQuickAdapter;
import com.wuyou.merchant.view.widget.recyclerHelper.NewRefreshRecyclerView;
import com.wuyou.merchant.view.widget.recyclerHelper.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by solang on 2018/2/5.
 */

public class JoinedFragment extends BaseFragment<CircleContract.View, CircleContract.Presenter> implements CircleContract.View {
    @BindView(R.id.sl_list_layout)
    StatusLayout statusLayout;
    @BindView(R.id.rv_orders)
    NewRefreshRecyclerView recyclerView;
    List<ContractEntity> data = new ArrayList();
    JoinedContractListRvAdapter adapter;
    String id;

    @Override
    protected int getContentLayout() {
        return R.layout.fragment_joined;
    }

    @Override
    protected CircleContract.Presenter getPresenter() {
        return new CirclePresenter();
    }

    @Override
    protected void bindView(Bundle savedInstanceState) {
        statusLayout.setErrorAndEmptyAction(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                statusLayout.showProgressView();
                adapter.clearData();
                fetchDatas();
            }
        });
        adapter = new JoinedContractListRvAdapter(R.layout.item_contract_joined, data);
        adapter.setOnItemClickListener((adapter1, view, position) -> {
            Intent intent = new Intent(getActivity(), ContractDetailActivity.class);
            intent.putExtra(Constant.CONTRACT_ID, adapter.getItem(position).contract_id);
            intent.putExtra(Constant.CONTRACT_FROM, 2);
            startActivity(intent);
        });
        recyclerView.getRecyclerView().setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                mPresenter.getJoinedContractMore();
            }
        }, recyclerView.getRecyclerView());
        recyclerView.setRefreshAction(new OnRefreshListener() {
            @Override
            public void onAction() {
                adapter.clearData();
                fetchDatas();
            }
        });
    }


    @Override
    public void loadData() {
        statusLayout.showProgressView();
        fetchDatas();
    }

    private void fetchDatas() {
        mPresenter.getJoinedContract();
    }

    @Override
    public void showError(String message, int res) {
        recyclerView.setRefreshFinished();
        statusLayout.showErrorView(message);
    }


    @Override
    public void getSuccess(ResponseListEntity<ContractEntity> data) {
        recyclerView.setRefreshFinished();
        adapter.setNewData(data.list);
        statusLayout.showContentView();
        if (data.has_more.equals("0")) {
            adapter.loadMoreEnd(true);
        }
        if (adapter.getData().size() == 0) {
            statusLayout.showEmptyView("没有合约");
        }
    }

    @Override
    public void getMore(ResponseListEntity<ContractEntity> data) {
        adapter.addData(data.list);
        if (data.has_more.equals("0")) {
            adapter.loadMoreEnd(true);
        }
    }

    @Override
    public void loadMoreError(int code) {
        adapter.loadMoreFail();
    }
}
