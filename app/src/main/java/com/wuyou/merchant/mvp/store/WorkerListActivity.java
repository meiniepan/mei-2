package com.wuyou.merchant.mvp.store;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.gs.buluo.common.network.ApiException;
import com.gs.buluo.common.network.BaseResponse;
import com.gs.buluo.common.network.BaseSubscriber;
import com.gs.buluo.common.network.QueryMapBuilder;
import com.gs.buluo.common.widget.StatusLayout;
import com.wuyou.merchant.CarefreeDaoSession;
import com.wuyou.merchant.Constant;
import com.wuyou.merchant.R;
import com.wuyou.merchant.adapter.WorkersRvAdapter;
import com.wuyou.merchant.bean.entity.WorkerEntity;
import com.wuyou.merchant.bean.entity.WorkerListEntity;
import com.wuyou.merchant.network.CarefreeRetrofit;
import com.wuyou.merchant.network.apis.OrderApis;
import com.wuyou.merchant.view.activity.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by solang on 2018/5/15.
 */

public class WorkerListActivity extends BaseActivity {
    @BindView(R.id.sl_list_layout)
    StatusLayout statusLayout;
    @BindView(R.id.rv_orders)
    RecyclerView recyclerView;
    List<WorkerEntity> data = new ArrayList();
    WorkersRvAdapter adapter;
    String orderId;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_worker_list;
    }

    @Override
    protected void bindView(Bundle savedInstanceState) {
        setTitleText("技工列表");
        statusLayout.setErrorAction(v -> getData());
        orderId = getIntent().getStringExtra(Constant.ORDER_ID);
        adapter = new WorkersRvAdapter( this,R.layout.item_chose_artisan, data);
        recyclerView.setLayoutManager(new LinearLayoutManager(getCtx()));
        recyclerView.setAdapter(adapter);
        getData();
    }

    private void getData() {
        statusLayout.showProgressView();
        CarefreeRetrofit.getInstance().createApi(OrderApis.class)
                .getWorkersInfo(QueryMapBuilder.getIns().put("shop_id", CarefreeDaoSession.getInstance().getUserInfo().getUid())
                        .put("start_id", "0")
                        .put("flag", "1")
                        .put("size", "10")
                        .buildGet())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<BaseResponse<WorkerListEntity>>() {
                    @Override
                    public void onSuccess(BaseResponse<WorkerListEntity> response) {
                        adapter.setNewData(response.data.list);
                        statusLayout.showContentView();
                        if (adapter.getData().size() == 0) {
                            statusLayout.showEmptyView("没有名单");
                        }
                    }

                    @Override
                    protected void onFail(ApiException e) {
                        statusLayout.showErrorView(e.getDisplayMessage());
                    }
                });
    }
}
