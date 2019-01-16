package com.wuyou.merchant.mvp.store;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.gs.buluo.common.network.ApiException;
import com.gs.buluo.common.network.BaseSubscriber;
import com.gs.buluo.common.widget.StatusLayout;
import com.wuyou.merchant.Constant;
import com.wuyou.merchant.R;
import com.wuyou.merchant.adapter.MechanicRvAdapter;
import com.wuyou.merchant.bean.entity.BaseKunResponse;
import com.wuyou.merchant.bean.entity.KunListEntity;
import com.wuyou.merchant.bean.entity.MechanicEntity;
import com.wuyou.merchant.bean.entity.MechanicReq;
import com.wuyou.merchant.network.CarefreeRetrofitNew;
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
    List<MechanicEntity> data = new ArrayList();
    MechanicRvAdapter adapter;
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
        adapter = new MechanicRvAdapter(R.layout.item_chose_artisan, data);
        recyclerView.setLayoutManager(new LinearLayoutManager(getCtx()));
        recyclerView.setAdapter(adapter);
        getData();
    }

    private void getData() {
        statusLayout.showProgressView();
        MechanicReq req = new MechanicReq();
//        req.shopId = CarefreeDaoSession.getInstance().getUserInfo().getShop_id();
        req.shopId = "5c2775a18ffaedc2ba1cf2f6";
        CarefreeRetrofitNew.getInstance().createApi(OrderApis.class)
                .getMerchanic(req)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<BaseKunResponse<KunListEntity<MechanicEntity>>>() {
                    @Override
                    public void onSuccess(BaseKunResponse<KunListEntity<MechanicEntity>> response) {
                        adapter.setNewData(response.data.mechanicSet);
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
