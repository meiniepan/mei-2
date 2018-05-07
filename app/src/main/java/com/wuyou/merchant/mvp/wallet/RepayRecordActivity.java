package com.wuyou.merchant.mvp.wallet;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.gs.buluo.common.widget.StatusLayout;
import com.wuyou.merchant.R;
import com.wuyou.merchant.adapter.RepayRecordListRvAdapter;
import com.wuyou.merchant.bean.entity.RepayRecordEntity;
import com.wuyou.merchant.bean.entity.ResponseListEntity;
import com.wuyou.merchant.view.activity.BaseActivity;
import com.wuyou.merchant.view.widget.recyclerHelper.BaseQuickAdapter;
import com.wuyou.merchant.view.widget.recyclerHelper.NewRefreshRecyclerView;
import com.wuyou.merchant.view.widget.recyclerHelper.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Solang on 2018/3/21.
 */

public class RepayRecordActivity extends BaseActivity<WalletContract.View,WalletContract.Presenter> implements WalletContract.View{
    @BindView(R.id.rv_list)
    NewRefreshRecyclerView recyclerView;
    @BindView(R.id.sl_list_layout)
    StatusLayout statusLayout;
    List<RepayRecordEntity> data = new ArrayList();
    RepayRecordListRvAdapter adapter;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_repay_record;
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
        adapter = new RepayRecordListRvAdapter(R.layout.item_repay_record, data);
        adapter.setOnItemClickListener((adapter1, view, position) -> {
//            Intent intent = new Intent(getCtx(), ServiceProviderDetailActivity.class);
//            intent.putExtra(Constant.MERCHANT_ID, adapter.getItem(position).record_id);
//            startActivity(intent);
        });
        recyclerView.getRecyclerView().setLayoutManager(new LinearLayoutManager(getCtx()));
        recyclerView.setAdapter(adapter);
        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                mPresenter.loadMoreRepayRecords();
            }
        }, recyclerView.getRecyclerView());
        recyclerView.setRefreshAction(new OnRefreshListener() {
            @Override
            public void onAction() {
                adapter.clearData();
                fetchDatas();
            }
        });
        fetchDatas();
    }

    @Override
    protected WalletContract.Presenter getPresenter() {
        return new WalletPresenter();
    }

    private void fetchDatas() {
        statusLayout.showProgressView();
        mPresenter.getRepayRecordsList();
    }

    @Override
    public void showError(String message, int res) {
        recyclerView.setRefreshFinished();
        statusLayout.showErrorView(message);
    }

    @Override
    public void getSuccess(ResponseListEntity data) {
        recyclerView.setRefreshFinished();
        adapter.setNewData(data.list);
        statusLayout.showContentView();
        if (data.has_more.equals("0")) {
            adapter.loadMoreEnd(true);
        }
        if (adapter.getData().size() == 0) {
            statusLayout.showEmptyView("没有还款记录");
        }
    }


    @Override
    public void getMore(ResponseListEntity data) {
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
