package com.wuyou.merchant.mvp.circle;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.gs.buluo.common.widget.recyclerHelper.BaseQuickAdapter;
import com.gs.buluo.common.widget.recyclerHelper.OnRefreshListener;
import com.gs.buluo.common.widget.recyclerHelper.RefreshRecyclerView;
import com.wuyou.merchant.Constant;
import com.wuyou.merchant.R;
import com.wuyou.merchant.adapter.CreatedContractListRvAdapter;
import com.wuyou.merchant.adapter.JoinedContractListRvAdapter;
import com.wuyou.merchant.bean.entity.ContractEntity;
import com.wuyou.merchant.bean.entity.ResponseListEntity;
import com.wuyou.merchant.view.fragment.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by solang on 2018/1/31.
 */

public class CircleStatusFragment extends BaseFragment<CircleContract.View, CircleContract.Presenter> implements CircleContract.View {
    @BindView(R.id.rv_orders)
    RefreshRecyclerView recyclerView;
    @BindView(R.id.fl_add)
    View add;
    List<ContractEntity> data = new ArrayList();
    BaseQuickAdapter adapter;
    int status;

    @Override
    protected int getContentLayout() {
        return R.layout.fragment_circle_status;
    }

    @Override
    protected CircleContract.Presenter getPresenter() {
        return new CirclePresenter();
    }

    @Override
    protected void bindView(Bundle savedInstanceState) {
        recyclerView.getRecyclerView().setErrorAction(v -> {
            recyclerView.showProgressView();
            fetchDatas();
        });
        if (1 == status) {
            add.setVisibility(View.VISIBLE);
        } else {
            add.setVisibility(View.GONE);
        }

        if (2 == status) {
            adapter = new JoinedContractListRvAdapter(R.layout.item_contract_joined, data);
        } else {
            adapter = new CreatedContractListRvAdapter(R.layout.item_contract_created, data);
        }
        adapter.setOnItemClickListener((adapter1, view, position) -> {
            Intent intent = new Intent(getActivity(), ContractDetailActivity.class);
            intent.putExtra(Constant.CONTRACT_ID, ((ContractEntity) adapter.getItem(position)).contract_id);
            intent.putExtra(Constant.CONTRACT_FROM, status);
            startActivity(intent);
        });
        recyclerView.getRecyclerView().setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        adapter.setOnLoadMoreListener(() -> {
            switch (status) {
                case 1:
                    mPresenter.loadCreatedContractMore();
                    break;
                case 2:
                    mPresenter.loadJoinedContractMore();
                    break;
                case 3:
                    mPresenter.loadMarketMore();
                    break;
            }
        }, recyclerView.getRecyclerView());
        recyclerView.setRefreshAction(() -> {
            fetchDatas();
        });
    }

    @Override
    public void loadData() {
        recyclerView.showProgressView();
        fetchDatas();
    }

    private void fetchDatas() {
        status = getArguments().getInt("h");
        switch (status) {
            case 1:
                mPresenter.getCreatedContract();
                break;
            case 2:
                mPresenter.getJoinedContract();
                break;
            case 3:
                mPresenter.getContractMarket();
                break;
        }
    }

    @Override
    public void showError(String message, int res) {
        recyclerView.getRefreshLayout().setEnabled(false);
        recyclerView.setRefreshFinished();
        recyclerView.showErrorView(message);
    }

    @Override
    public void getSuccess(ResponseListEntity<ContractEntity> data) {
        recyclerView.getRefreshLayout().setEnabled(true);
        recyclerView.setRefreshFinished();
        adapter.setNewData(data.list);
        recyclerView.showContentView();
        if (data.has_more.equals("0")) {
            adapter.loadMoreEnd(true);
        }
        if (adapter.getData().size() == 0) {
            recyclerView.showEmptyView(getString(R.string.contract_empty));
        }
    }


    @Override
    public void getMore(ResponseListEntity<ContractEntity> data) {
//        if (3 == status) {
//            List list = new ArrayList();
//            for (ContractEntity e : data.list) {
//                if ("0".equals(e.type)) {
//                    list.add(e);
//                }
//            }
//            adapter.addData(list);
//        } else {
        adapter.addData(data.list);
//        }
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
                Intent intent = new Intent(getActivity(), CreateIntelligentContractActivity1.class);
                getActivity().startActivity(intent);
                break;

        }
    }

}
