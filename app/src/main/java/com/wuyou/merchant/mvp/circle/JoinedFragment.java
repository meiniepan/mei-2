package com.wuyou.merchant.mvp.circle;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.gs.buluo.common.network.ApiException;
import com.gs.buluo.common.network.BaseResponse;
import com.gs.buluo.common.network.BaseSubscriber;
import com.gs.buluo.common.network.QueryMapBuilder;
import com.gs.buluo.common.widget.StatusLayout;
import com.wuyou.merchant.CarefreeApplication;
import com.wuyou.merchant.Constant;
import com.wuyou.merchant.R;
import com.wuyou.merchant.adapter.WorkersRvAdapter;
import com.wuyou.merchant.bean.entity.WorkerEntity;
import com.wuyou.merchant.bean.entity.WorkerListEntity;
import com.wuyou.merchant.network.CarefreeRetrofit;
import com.wuyou.merchant.network.apis.OrderApis;
import com.wuyou.merchant.view.activity.ServiceProviderDetailActivity;
import com.wuyou.merchant.view.fragment.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by solang on 2018/2/5.
 */

public class JoinedFragment extends BaseFragment<CircleContract.View, CircleContract.Presenter> implements CircleContract.View {
    @BindView(R.id.sl_list_layout)
    StatusLayout statusLayout;
    @BindView(R.id.rv_orders)
    RecyclerView recyclerView;
    List<WorkerEntity> data = new ArrayList();
    WorkersRvAdapter adapter;
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
        adapter = new WorkersRvAdapter(getActivity(), R.layout.item_chose_artisan, data);
        adapter.setOnItemClickListener((adapter1, view, position) -> {
            Intent intent = new Intent(getActivity(), ServiceProviderDetailActivity.class);
            intent.putExtra(Constant.MERCHANT_ID, adapter.getItem(position).id);
            startActivity(intent);
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
    }


    @Override
    public void loadData() {
        statusLayout.showProgressView();
        fetchDatas();
    }

    private void fetchDatas() {
        mPresenter.getJoinedAlliance();
    }

    @Override
    public void showError(String message, int res) {
        statusLayout.showErrorView(message);
    }

    @Override
    public void getSuccess(WorkerListEntity data) {
        adapter.setNewData(data.list);
        statusLayout.showContentView();

        if (adapter.getData().size() == 0) {
            statusLayout.showEmptyView("没有订单");
        }
    }

    @Override
    public void getMore(WorkerListEntity data) {

    }

    @Override
    public void loadMoreError(int code) {

    }
}
