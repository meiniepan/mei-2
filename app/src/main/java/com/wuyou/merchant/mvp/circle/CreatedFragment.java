package com.wuyou.merchant.mvp.circle;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.gs.buluo.common.widget.StatusLayout;
import com.wuyou.merchant.Constant;
import com.wuyou.merchant.R;
import com.wuyou.merchant.adapter.CreatedContractListRvAdapter;
import com.wuyou.merchant.bean.entity.ContractEntity;
import com.wuyou.merchant.bean.entity.ResponseListEntity;
import com.wuyou.merchant.view.fragment.BaseFragment;
import com.wuyou.merchant.view.widget.recyclerHelper.BaseQuickAdapter;
import com.wuyou.merchant.view.widget.recyclerHelper.NewRefreshRecyclerView;
import com.wuyou.merchant.view.widget.recyclerHelper.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by solang on 2018/1/31.
 */

public class CreatedFragment extends BaseFragment<CircleContract.View, CircleContract.Presenter> implements CircleContract.View {
    @BindView(R.id.sl_list_layout)
    StatusLayout statusLayout;
    @BindView(R.id.rv_orders)
    NewRefreshRecyclerView recyclerView;
    List<ContractEntity> data = new ArrayList();
    CreatedContractListRvAdapter adapter;

    @Override
    protected int getContentLayout() {
        return R.layout.fragment_created;
    }

    @Override
    protected CircleContract.Presenter getPresenter() {
        return new CirclePresenter();
    }

    @Override
    protected void bindView(Bundle savedInstanceState) {
        statusLayout.setErrorAction(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                statusLayout.showProgressView();
                adapter.clearData();
                fetchDatas();
            }
        });
        adapter = new CreatedContractListRvAdapter(R.layout.item_contract_created, data);
        adapter.setOnItemClickListener((adapter1, view, position) -> {
            Intent intent = new Intent(getActivity(), ContractDetailActivity.class);
            intent.putExtra(Constant.CONTRACT_ID, adapter.getItem(position).contract_id);
            intent.putExtra(Constant.CONTRACT_FROM, 1);
            startActivity(intent);
        });
        recyclerView.getRecyclerView().setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                mPresenter.loadListMore("1");
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
        mPresenter.getCreatedContract("1");
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



    @OnClick({R.id.fl_add})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fl_add:
                Intent intent  = new Intent(getActivity(),CreateIntelligentContractActivity1.class);
                getActivity().startActivity(intent);
                break;

        }
    }
}
